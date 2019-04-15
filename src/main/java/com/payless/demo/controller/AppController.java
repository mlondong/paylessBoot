package com.payless.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

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
	public String consumer() {
		System.out.println("Direccionado en CONSUMER...");
		return "consumer";
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
