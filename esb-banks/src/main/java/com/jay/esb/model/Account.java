package com.jay.esb.model;

import org.springframework.stereotype.Component;

@Component
public class Account {
  String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
