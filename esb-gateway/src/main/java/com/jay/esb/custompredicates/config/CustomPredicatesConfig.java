package com.jay.esb.custompredicates.config;

import com.jay.esb.custompredicates.factories.VIPCustomerPredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomPredicatesConfig {

  @Bean
  public VIPCustomerPredicateFactory myPathRoutePredicateFactory(){
    return new VIPCustomerPredicateFactory();
  }
}
