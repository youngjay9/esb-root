package com.jay.esb.custompredicates.factories;

import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自己客制化一個 PredicateFactory,去測試 request 的 path 將其轉導至對應的服務
 * <p>
 * spring cloud gateway 有很多內建的 predicate factories:
 */
public class PathRoutePredicateFactory extends
    AbstractRoutePredicateFactory<PathRoutePredicateFactory.Config> {

  private static Logger logger = LoggerFactory.getLogger(PathRoutePredicateFactory.class);

  public PathRoutePredicateFactory(
      Class<Config> configClass) {
    super(configClass);
  }

  @Override
  public Predicate<ServerWebExchange> apply(PathRoutePredicateFactory.Config config) {
    return (ServerWebExchange s) -> {
      logger.info("PathRoutePredicateFactory request path:{}", s.getRequest().getPath());
      return true;
    };
  }

  public static class Config {

  }
}
