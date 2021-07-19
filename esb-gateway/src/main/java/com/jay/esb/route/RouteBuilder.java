package com.jay.esb.route;

import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory;
import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory.Config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;


@Configuration
public class RouteBuilder {

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
        .route("modify_request_body",
            r -> r.path("/deposit/saveAccount") // path route predicate factory
                .filters(f -> f.modifyRequestBody( // 使用 build in GatewayFilter
                    String.class, Hello.class, MediaType.APPLICATION_JSON_VALUE,
                    (exchange, s) -> Mono.just(new Hello(s.toUpperCase()))))
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
