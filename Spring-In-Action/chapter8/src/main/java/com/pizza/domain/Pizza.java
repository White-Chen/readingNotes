package com.pizza.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Pizza implements Serializable {
    private PizzaSize size;
    private List<Topping> toppings;

    public Pizza() {
        toppings = new ArrayList<Topping>();
        size = PizzaSize.LARGE;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public void setSize(String sizeString) {
        this.size = PizzaSize.valueOf(sizeString);
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public void setToppings(String[] toppingStrings) {
        for (int i = 0; i < toppingStrings.length; i++) {
            toppings.add(Topping.valueOf(toppingStrings[i]));
        }
    }
}
