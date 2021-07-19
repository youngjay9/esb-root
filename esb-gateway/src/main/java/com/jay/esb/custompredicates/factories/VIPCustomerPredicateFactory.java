package com.jay.esb.custompredicates.factories;


import java.util.List;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;

import org.springframework.http.HttpCookie;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自己客制化一個 Predicate Factory,去測試 request 的 path 將其轉導至對應的服務
 * <p>
 * spring cloud gateway 有很多內建的 predicate factories:
 */
public class VIPCustomerPredicateFactory extends
    AbstractRoutePredicateFactory<VIPCustomerPredicateFactory.Config> {

  private static Logger logger = LoggerFactory.getLogger(VIPCustomerPredicateFactory.class);

  public VIPCustomerPredicateFactory() {
    super(Config.class);
  }

  @Override
  public Predicate<ServerWebExchange> apply(VIPCustomerPredicateFactory.Config config) {
    return (ServerWebExchange s) -> {

      logger.info("request path: {}", s.getRequest().getPath());

      List<HttpCookie> cookies = s.getRequest().getCookies().get(config.getVipCustomerCookie());

      boolean isVip;
      if (cookies == null || cookies.isEmpty()) {
        isVip = false;
      } else {
        String vipCustomerCookie = cookies.get(0).getValue();
        isVip = "jay-hung".equals(vipCustomerCookie) ? true : false;
      }
      return isVip;
    };
  }

  public static class Config {

    private boolean isCheckingVIP = true;

    private String vipCustomerCookie = "vipCustomer";

    public Config(boolean isCheckingVIP, String vipCustomerCookie) {
      this.isCheckingVIP = isCheckingVIP;
      this.vipCustomerCookie = vipCustomerCookie;
    }

    public boolean isCheckingVIP() {
      return isCheckingVIP;
    }

    public void setCheckingVIP(boolean checkingVIP) {
      isCheckingVIP = checkingVIP;
    }

    public String getVipCustomerCookie() {
      return vipCustomerCookie;
    }

    public void setVipCustomerCookie(String vipCustomerCookie) {
      this.vipCustomerCookie = vipCustomerCookie;
    }
  }
}
