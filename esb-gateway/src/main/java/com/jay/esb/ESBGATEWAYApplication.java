package com.jay.esb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ESBGATEWAYApplication {

  public static void main(String[] args) {
    SpringApplication.run(ESBGATEWAYApplication.class, args);
  }
}
