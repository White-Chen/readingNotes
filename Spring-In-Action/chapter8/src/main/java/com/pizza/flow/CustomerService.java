package com.pizza.flow;

import com.pizza.domain.Customer;
import com.pizza.exception.CustomerNotFoundException;

public interface CustomerService {
   Customer lookupCustomer(String phoneNumber) throws CustomerNotFoundException;
}