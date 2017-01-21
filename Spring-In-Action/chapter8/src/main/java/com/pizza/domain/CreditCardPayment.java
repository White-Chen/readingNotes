package com.pizza.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * \* Created with Chen Zhe on 1/21/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Component
@Scope("prototype")
public class CreditCardPayment extends Payment {
    private static final long serialVersionUID = 4257691700052885232L;
    private String authorization;

    public CreditCardPayment() {}
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String toString() {
        return "CREDIT:  $" + getAmount() + " ; AUTH: " + authorization;
    }
}
