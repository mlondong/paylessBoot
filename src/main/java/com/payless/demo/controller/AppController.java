package com.payless.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Rating;
import com.payless.demo.model.Trader;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.RatingServiceImp;
import com.payless.demo.services.TraderServiceImp;

@Controller
public class AppController {

	
	@Autowired
	private ConsumerServiceImp consumerServiceImp;
	@Autowired
	private TraderServiceImp traderServiceImp;
	@Autowired	
	private RatingServiceImp ratingServiceImp;

	
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
           
        /*get all acumulated prices*/
        modelAndView.addObject("pricesAcumulated", consumer.getAllAcumulatedPrices());
        
        /*get all acumulated products*/
        modelAndView.addObject("consumeAcumulated", consumer.getAllAcumulatedProducts());
      
        /*get places more visied*/
        modelAndView.addObject("placesVisited",  consumer.getMoreVisited() );

        /*get my ratings*/
        modelAndView.addObject("myRatings",  consumer.getAllMyRatings() );
        
        /*get general ratings*/
        Iterable<Rating> rating = ratingServiceImp.findAll();
        modelAndView.addObject("generalRatings",  consumer.getAllGerenalRatings(rating) );
        
        return modelAndView;
	}
	

	
	
	
}
