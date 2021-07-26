package com.jay.esb.route;

import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory;
import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory.Config;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import com.google.gson.Gson;


@Configuration
public class RouteBuilder {

  private Logger logger = LoggerFactory.getLogger(RouteBuilder.class);

  /**
   * spring cloud gateway 可使用多個 predicate factory 用來偵測 HttpRequest 的各種 attribute 且可用 .and() 來串接不同類型的
   * predicate factory Ref: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-after-route-predicate-factory
   *
   * @param builder
   * @return
   */
  @Bean
  public RouteLocator buildRoutes(RouteLocatorBuilder builder, VIPCustomerPredicateFactory mf) {
    return builder.routes()
        .route("use_path_predicate_factory",
            r ->
                // 使用 build in path route predicate factory
                r.path("/deposit/getAccount")
                    .and()
                // 使用自定義的 VIPCustomerPredicateFactory 偵測 cookie,有符合才會導入 ESB_BANKS
                .predicate(mf.apply(new Config(true,
                    "vipCustomerCookie")))
                  .uri("lb://ESB-BANKS"))
        // toDO 使用 globalFilter抽出來做 modify
        .route("modify_request_body",
            r -> r.path("/deposit/saveAccount")
            .filters(f -> f.modifyRequestBody(
                String.class, String.class,
                (serverWebExchange, requestBody) -> {
                  logger.info("original requestBody :{}", requestBody);
                  // 以下將 reqeust body 的 accountName 參數轉成大寫
                  Gson gson = new Gson();
                  Map<String, Object> map = gson.fromJson(requestBody, Map.class);
                  String accountName = (String)map.get("accountName");
                  if(accountName != null && !accountName.equals("")){
                    map.put("accountName", accountName.toUpperCase());
                  }
                  logger.info("after modify requestBody :{}", gson.toJson(map, Map.class));
                  return Mono.just(gson.toJson(map, Map.class));
                }
            )).uri("lb://ESB-BANKS"))
        .route("fep",
            r -> r.path("/fep/**")
                .uri("lb://ESB-FEP"))
        .build();
  }

  static class Hello {

    String message;

    public Hello() {
    }

    public Hello(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }

}
