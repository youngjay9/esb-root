package com.jay.esb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
public class ESBBanksApplication {

  public static void main(String[] args) {
    SpringApplication.run(ESBBanksApplication.class, args);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/deposit")
  public void getDeposit(){
      System.out.println("ESB-BANKS getDeposit!!");
  }
}
