package com.jay.esb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="fep")
public class FepController {
  final Logger logger = LoggerFactory.getLogger(FepController.class);

  @RequestMapping(method = RequestMethod.GET, value = "/pay")
  public void pay(){
    logger.info("FEPController pay!!");
  }
}
