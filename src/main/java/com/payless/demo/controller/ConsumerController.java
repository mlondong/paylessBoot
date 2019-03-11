package com.payless.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Trader;
import com.payless.demo.services.ConsumerServiceImp;


@Controller
public class ConsumerController {


	@Autowired
	private  ConsumerServiceImp consumerServiceImp;
	
	
	
	
	/*** CONTROLLES FOR CONSUMER*/
	
	@GetMapping(path="/consumer/add")
	public String home(Consumer consumer){
		return "registerconsumer"; 
	}
	
	
	@PostMapping(path="/consumer/create")
	public String  createConsumer(@Valid Consumer consumer, BindingResult result, Model model ){
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getFieldError() );	
		}else{
				Consumer consumerdb =  consumerServiceImp.save(consumer);
				model.addAttribute("consumerInfo", consumerdb);
			}
		return "registerconsumer";
	}	
		

	@RequestMapping(path="/consumer/viewsearch" , method={RequestMethod.POST, RequestMethod.GET})
	public String viewsearchConsumer(){
			return "searchconsumer";
    }
	
	@SuppressWarnings("unused")
	@RequestMapping(path="/consumer/search" , method={RequestMethod.POST, RequestMethod.GET})
	public String searchConsumer(@RequestParam(value="firstName", required=false) String firstName ,
								 @RequestParam(value="dni", required=false) Long dni, Model model){
		
	System.out.println(firstName+ " - "+ dni);	
				if((firstName=="" || firstName.length()==0 ) && dni==null){
					Iterable<Consumer> consumerdb = consumerServiceImp.findAll();
					model.addAttribute("consumers", consumerdb);
				}else if(firstName.length() != 0){
					List<Consumer> consumerdb =  consumerServiceImp.findByFirstNameLike(firstName);
					model.addAttribute("consumers", consumerdb);
				}else{
						Consumer consumerdb  = consumerServiceImp.findByDni(dni);
						model.addAttribute("consumers", consumerdb);
				}
		return "searchconsumer"; 
	}

	
}
