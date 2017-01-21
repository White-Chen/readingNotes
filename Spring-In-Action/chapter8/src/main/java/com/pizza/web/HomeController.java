package com.pizza.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	public String redirectToFlow() {
		return "redirect:/pizza"; 
	}
	
}
