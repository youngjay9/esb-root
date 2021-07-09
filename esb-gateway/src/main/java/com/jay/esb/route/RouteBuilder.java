package com.jay.esb.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteBuilder {

  @Bean
  public RouteLocator buildRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("deposit", r -> r.path("/deposit/**")
            .uri("lb://ESB-BANKS"))
        .route("fep", r -> r.path("/fep/**").uri("lb://ESB-FEP"))
        .build();
  }
}
