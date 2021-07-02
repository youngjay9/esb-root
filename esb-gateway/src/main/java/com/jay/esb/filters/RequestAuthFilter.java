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
@Order(-1)
/**
 * 實作 GlobalFilter 的 class 都為 pre-filter
 * 會在 invoke target route 前執行
 * 執行的順序根據 Order annotation
 */
public class RequestAuthFilter implements GlobalFilter {

  private static final Logger logger = LoggerFactory.getLogger(RequestAuthFilter.class);

  @Override
  /**
   * Order(-1) 的 filter 先執行
   */
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    logger.info("RequestAuthFilter working!!");
    return chain.filter(exchange);
  }
}
