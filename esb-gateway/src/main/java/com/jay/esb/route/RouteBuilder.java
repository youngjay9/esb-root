package com.jay.esb.route;

import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory;
import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory.Config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteBuilder {

  /**
   * spring cloud gateway 可使用多個 predicate factory 用來偵測 HttpRequest 的各種 attribute 並可用 .and() 來串接不同的
   * predicate factory Ref: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-after-route-predicate-factory
   *
   * @param builder
   * @return
   */
  @Bean
  public RouteLocator buildRoutes(RouteLocatorBuilder builder, VIPCustomerPredicateFactory mf) {
    return builder.routes()
        .route("deposit",
            r -> r.path("/deposit/**") // path route predicate factory
                .and()
                .predicate(mf.apply(new Config(true, "vipCustomerCookie"))) // 使用自定義的 VIPCustomerPredicateFactory 偵測 cookie
                .uri("lb://ESB-BANKS"))
        .route("fep",
            r -> r.path("/fep/**")
                .uri("lb://ESB-FEP"))
        .build();
  }
}
