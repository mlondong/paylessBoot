package com.payless.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payless.demo.model.Address;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.MeatProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.InvoiceServiceImp;
import com.payless.demo.services.ProductServiceImp;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.util.Passgenerator;






@Controller
public class ConsumerController {


	@Autowired
	private  ConsumerServiceImp consumerServiceImp;
	
	@Autowired
	private  ProductServiceImp productServiceImp;
	
	@Autowired
	private  TraderServiceImp traderServiceImp;
	
	
	@Autowired
	private  InvoiceServiceImp invoiceServiceImp;
	
	
	
	
	
	@GetMapping("/consumer")
	public ModelAndView consumer() {
		ModelAndView modelAndView = new ModelAndView("consumer");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + consumer.getFirstName() + " " + consumer.getLastName() + " (" + consumer.getDni() + ")");
        modelAndView.addObject("consumer", consumer);
        return modelAndView;
	}
	
	
	
	@GetMapping(path="/consumer/findproducts")
	public ModelAndView findProducts(){
		ModelAndView modelAndView = new ModelAndView("c_findProducts");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("consumer", consumer);
       
   	return modelAndView;
	}
	

	@RequestMapping(path="/consumer/resulproducts", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView resultSearchProducts(@RequestParam("description") String desc , 
									   @RequestParam("dni") Long id, 
									   @RequestParam("zone") int zone, 
									   @RequestParam("city") int city){
	
		ModelAndView modelAndView= new ModelAndView("c_findProducts");
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    /*TRADERS IN ZONE OF CONSUMER*/
	    List<Trader> traders = traderServiceImp.queryByParametersCityZone(zone, city);
	    /*RESULT OF PRODUCTS FIND IT*/
	    List<Product> findProducts = productServiceImp.findByContainDescription(desc);
	   
	    /*MATCH BETWEEN PRODUCTS AND TRADERS IN ZONE*/
		List<StockProducts> matchStockProducts= new ArrayList<StockProducts>(); 
	    for(Trader trader: traders){
	        for(Product product: findProducts){
	        	matchStockProducts.add(trader.getStock().findProductInOwnStock(product));
	        }
	     
	    }
	   
	 	modelAndView.addObject("consumer", consumer);
	   	modelAndView.addObject("stockproducts", matchStockProducts);
	    
	    
	    return modelAndView;
	}

	
	@GetMapping("/consumer/viewprofile")
	public ModelAndView viewProfile() {
		ModelAndView modelAndView = new ModelAndView("c_profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("consumer", consumer);
        return modelAndView;
	}
	

	@RequestMapping(path="/consumer/upgeneral",  method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView  updatingConsumer(@RequestParam("firstName") String firstName, 
										  @RequestParam("lastName") String lastName, @RequestParam("dni") Long dni ){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    ModelAndView modelAndView = new ModelAndView("c_profile");
		modelAndView.addObject("consumer", consumer);
	   	
		try{
			   consumer.setFirstName(firstName);
			   consumer.setLastName(lastName);
			   consumer.setName(consumer.getName());
			   consumer.setDni(dni);
			   consumerServiceImp.save(consumer);
			   modelAndView.addObject("Update", "Update it !"); 
		}catch(Exception e){
			modelAndView.addObject("Error", "Error in data Information...");
		}
	
		return modelAndView;
	}
			

	@RequestMapping(path="/consumer/uplogin",  method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView  updatingConsumerLogin(@RequestParam("username") String username, 
										       @RequestParam("pass") String pass){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    ModelAndView modelAndView = new ModelAndView("c_profile");
		modelAndView.addObject("consumer", consumer);
	   	System.out.println(consumer);
		
		try{
				Passgenerator encoder = new Passgenerator(4);
				String newpass= encoder.generate(pass);
			    consumer.setName(username);
			    consumer.setPassword(newpass);
			    consumerServiceImp.save(consumer);
			   modelAndView.addObject("UpdateLogin", "Update it !"); 
		}catch(Exception e){
			modelAndView.addObject("ErrorLogin", "Error in data Information ...");
		}
	
		return modelAndView;
	}
			

	
	@RequestMapping(path="/consumer/upaddress",  method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView  updatingConsumerAddress(@RequestParam("description") String description,
											     @RequestParam("city") int city,
										         @RequestParam("zona") int zona){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    ModelAndView modelAndView = new ModelAndView("c_profile");
		modelAndView.addObject("consumer", consumer);
	   	System.out.println(consumer);
		
		try{
				Address address = new Address(description, city, zona);
				consumer.setAddress(address);;
			    consumerServiceImp.save(consumer);
				modelAndView.addObject("UpdateAddress", "Update it !"); 
		}catch(Exception e){
			modelAndView.addObject("ErrorAddress", "Error in data Information ...");
		}
	
		return modelAndView;
	}

	
	
	@GetMapping(path="/consumer/purchase")
	public ModelAndView myPurchases(){
		ModelAndView modelAndView = new ModelAndView("c_mypurchase");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("consumer", consumer);
       
        Collection<Invoice> invoices = consumer.getInvoices();
        
        if(invoices.isEmpty()){
        	modelAndView.addObject("error", "Invoices not found!.");
        }else{
        		modelAndView.addObject("invoices", invoices);
              }
        
   	return modelAndView;
	}
	
	
	
	
}
