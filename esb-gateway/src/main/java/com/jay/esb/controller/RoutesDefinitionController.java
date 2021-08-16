package com.jay.esb.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "gateway")
public class RoutesDefinitionController {

  final Logger logger = LoggerFactory.getLogger(RoutesDefinitionController.class);

  @RequestMapping(value = "/addRoute", method = RequestMethod.GET)
  public void addRoute() {
    logger.info("addRoute!!");

    RestTemplate restTemplate = new RestTemplate();

    RouteDefinition routeDefinition = new RouteDefinition();
    routeDefinition.setId("delAccount");
    routeDefinition.setUri(URI.create("lb://ESB-BANKS"));
    routeDefinition.setOrder(1000);

    PredicateDefinition predicateDefinition = new PredicateDefinition("Path=/deposit/delAccount");
    List<PredicateDefinition> predicates = new ArrayList<>();
    predicates.add(predicateDefinition);

    routeDefinition.setPredicates(predicates);

    HttpEntity<RouteDefinition> request = new HttpEntity<>(routeDefinition);
    String url = "http://localhost:9090/actuator/gateway/routes/delAccount";

    ResponseEntity<Void> obj = restTemplate.postForEntity(url, request, Void.class);

    // refresh route cache
    restTemplate.postForEntity("http://localhost:9090/actuator/gateway/refresh", Void.class, Void.class);
  }

  @RequestMapping(value = "/deleteRoute", method = RequestMethod.GET)
  public void deleteRoute() {
    logger.info("deleteRoute!!");

    RestTemplate restTemplate = new RestTemplate();

    String url = "http://localhost:9090/actuator/gateway/routes/delAccount";

    restTemplate.delete(url);

    // refresh route cache
    restTemplate.postForEntity("http://localhost:9090/actuator/gateway/refresh", Void.class, Void.class);

  }

}
