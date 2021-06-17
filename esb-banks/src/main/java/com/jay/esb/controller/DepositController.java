package com.jay.esb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

  @RequestMapping(method = RequestMethod.GET, value = "/deposit")
  public void getDeposit(){
    System.out.println("ESB-BANKS getDeposit!!");
  }
}
