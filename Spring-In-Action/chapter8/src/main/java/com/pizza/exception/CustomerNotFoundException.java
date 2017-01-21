package com.pizza.exception;

import java.io.Serializable;

public class CustomerNotFoundException extends Exception implements Serializable{
  private static final long serialVersionUID = 1619939410732408877L;

  public CustomerNotFoundException() {}
  
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
