package com.jay.esb.controller;

import com.jay.esb.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="deposit")
public class DepositController {

  final Logger logger = LoggerFactory.getLogger(DepositController.class);

  @RequestMapping(value = "/getAccount", method = RequestMethod.GET)
  public ResponseEntity<Account> getAccount(){
    logger.info("DepositController getAccount!!");
    Account account = new Account();
    account.setAccountId("2337");
    account.setAccountName("Ping");
    return ResponseEntity.ok(account);
  }

  @RequestMapping(value="/saveAccount",method = RequestMethod.PUT)
  public void saveAccount(@RequestBody Account account) {
      logger.info("DepositController saveAccount accountName:{}", account.getAccountName());
  }


}
