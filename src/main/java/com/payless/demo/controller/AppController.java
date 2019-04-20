package com.payless.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Consumer;
import com.payless.demo.services.ConsumerServiceImp;

@Controller
public class AppController {

	
	@Autowired
	private ConsumerServiceImp consumerServiceImp;
	
	
	
	@GetMapping({"/","/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/admin")
	public String admin() {
		System.out.println("Direccionado en ADMIN...");
		return "admin"; 
	}
	
	@GetMapping("/consumer")
	public ModelAndView consumer() {
		ModelAndView modelAndView = new ModelAndView("consumer");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + consumer.getFirstName() + " " + consumer.getLastName() + " (" + consumer.getDni() + ")");
        modelAndView.addObject("info", consumer.toString());
        return modelAndView;
	}
	
	
	/*
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}*/
	
		
	
	/*@GetMapping("/trader")
	public String trader() {
		return "trader";
	}*/
	
	
	
}
