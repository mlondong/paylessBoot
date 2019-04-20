package com.payless.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.MeatProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Trader;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.ProductServiceImp;


@Controller
@RequestMapping(path="/consumer")
public class ConsumerController {


	@Autowired
	private  ConsumerServiceImp consumerServiceImp;
	
	@Autowired
	private  ProductServiceImp productServiceImp;
	
	
	
	
	
	@GetMapping(path="/findproducts")
	public String findProducts(){
		return "c_findProducts";
	}
	
	
	
	//@RequestMapping(path="/product", method= {RequestMethod.POST , RequestMethod.GET} )
	@PostMapping(path="/product")
	public String findProducts(@Valid MeatProduct product, BindingResult result, Model model  ){
		
		System.out.println("llego ..."  + product.toString());
		
		
		/*		ModelAndView modelview= new ModelAndView("Products");
			
		if(name==""){
			Iterable<Product> products = productServiceImp.findAll();	
			modelview.addObject("products", products);
			
		}else{
				List <Product> products = productServiceImp.findByContainDescription(name);
				modelview.addObject("products",products);
			}
		 */	
	    
		return "c_findProducts";
	}

	
		
	

	
}
