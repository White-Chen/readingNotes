package com.pizza.flow;


import com.pizza.domain.Order;


public interface PricingEngine {
  public float calculateOrderTotal(Order order);
}
