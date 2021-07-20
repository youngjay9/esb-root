package com.jay.esb.route;

import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory;
import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
            r -> r.path("/deposit/getAccount") // 使用 build in path route predicate factory
                .and() // 用 and() 串接不同的 predicate factory
                .predicate(mf.apply(new Config(true,
                    "vipCustomerCookie"))) // 使用自定義的 VIPCustomerPredicateFactory 偵測 cookie
                .uri("lb://ESB-BANKS"))
        .route("modify_request_body", r -> r.path("/deposit/saveAccount")
            .filters(f -> f.modifyRequestBody(
                String.class, String.class,
                (exchange, s) -> {
                  Gson gson = new Gson();
                  logger.info("request str:{}", s);
                  return Mono.just(s);
                  }
            ))
            .uri("lb://ESB-BANKS"))
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
