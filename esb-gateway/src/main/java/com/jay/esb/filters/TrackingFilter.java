package com.jay.esb.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(0)
/**
 * 實作 GlobalFilter 的 class 都為 pre-filter
 * 會在 invoke target route 前執行
 * 執行的順序根據 Order annotation
 */
public class TrackingFilter implements GlobalFilter {

  private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

  @Override
  /**
   * Order(0) 在 Order(-1) 後執行
   */
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    logger.info("TrackingFilter working!!");
    return chain.filter(exchange);
  }
}
