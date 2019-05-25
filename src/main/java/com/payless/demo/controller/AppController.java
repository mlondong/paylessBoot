package com.payless.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Trader;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.TraderServiceImp;

@Controller
public class AppController {

	
	@Autowired
	private ConsumerServiceImp consumerServiceImp;
	
	@Autowired
	private TraderServiceImp traderServiceImp;
	
	
	
	
	@GetMapping({"/","/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/admin")
	public ModelAndView admin() {
		ModelAndView modelAndView = new ModelAndView("admin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + trader.getNameEnterprise());
        modelAndView.addObject("admin", trader);
        return modelAndView;
	}
	

	@GetMapping("/trader")
	public ModelAndView trader() {
		ModelAndView modelAndView = new ModelAndView("trader");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       
        Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
        System.out.println(trader);
        modelAndView.addObject("userName", "Welcome " + trader.getNameEnterprise() + " (" + trader.getCuit() + ")");
        modelAndView.addObject("trader", trader);
        return modelAndView;
	}
	
	
	
	@GetMapping("/consumer")
	public ModelAndView consumer() {
		ModelAndView modelAndView = new ModelAndView("consumer");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + consumer.getFirstName() + " " + consumer.getLastName() + " (" + consumer.getDni() + ")");
        modelAndView.addObject("consumer", consumer);
        return modelAndView;
	}
	
	
	
	/*
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}*/
	
		
	
	
	
	
}
