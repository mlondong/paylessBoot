package com.payless.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Address;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Rating;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.services.CityServiceImp;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.InvoiceServiceImp;
import com.payless.demo.services.ProductServiceImp;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.services.ZoneServiceImp;
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
	
	@Autowired
	private CityServiceImp cityServiceImp;
	
	@Autowired
	private ZoneServiceImp zoneServiceImp;
	
	
	
	@GetMapping(path="/consumer/findproducts")
	public ModelAndView findProducts(){
		ModelAndView modelAndView = new ModelAndView("c_findProducts");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("consumer", consumer);
       
   	return modelAndView;
	}
	

	
	
	@GetMapping("/consumer/viewprofile")
	public ModelAndView viewProfile() {
		ModelAndView modelAndView = new ModelAndView("c_profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("consumer", consumer);
        modelAndView.addObject("city", cityServiceImp.findAll());
        modelAndView.addObject("zone", zoneServiceImp.findAll());
        
        
        return modelAndView;
	}
	

	@RequestMapping(path="/consumer/address/zones",method={RequestMethod.POST, RequestMethod.GET})
	public String zonesInCity(@RequestParam("idcity") long idcity,  Model model){
		model.addAttribute("zone", zoneServiceImp.findAllZonesByIdCity(idcity));
		return "c_profile :: #zone";
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
			   modelAndView = new ModelAndView("redirect:/consumer/viewprofile");
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
				modelAndView = new ModelAndView("redirect:/consumer/viewprofile");

		}catch(Exception e){
			modelAndView.addObject("ErrorLogin", "Error in data Information ...");
		}
	
		return modelAndView;
	}
			

	
	@RequestMapping(path="/consumer/upaddress",  method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView  updatingConsumerAddress(@RequestParam("description") String description,
											     @RequestParam("city") Long city,
										         @RequestParam("zone") Long zone){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    ModelAndView modelAndView = new ModelAndView("c_profile");
		modelAndView.addObject("consumer", consumer);
		
		try{
				Address address = consumer.getAddress();
				address.setDescription(description);
				address.setCity(cityServiceImp.findById(city).get());
				address.setZone(zoneServiceImp.findById(zone).get());
				consumerServiceImp.save(consumer);
				modelAndView = new ModelAndView("redirect:/consumer/viewprofile");

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
	
	
	@GetMapping(path="/consumer/getallmyratings")
	public ModelAndView viewMyRating(){
		ModelAndView modelAndView = new ModelAndView("c_myrating");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    Collection<Invoice> invoices = consumer.getInvoices();
	    List<InvoiceProduct> invoiceProducts = new  ArrayList<InvoiceProduct>(); 
	    for(Invoice i :invoices){
	    	for(InvoiceProduct ivp : i.getProducts()){
	    		invoiceProducts.add(ivp);
	    	}
	    }
	    modelAndView.addObject("consumer", consumer);
	    modelAndView.addObject("invoiceproducts", invoiceProducts);
		return modelAndView;
	}
	
	
	@GetMapping(path = "/consumer/invoice")
	public ModelAndView consumerProductsOnnvoice(@RequestParam("numInvoice") Long numInvoice){
		ModelAndView modelAndView = new ModelAndView("c_invoice_products");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    Optional<Invoice>  invoice = consumer.findInvoiceByNumber(numInvoice) ;
		if(invoice.isPresent()){
			modelAndView.addObject("invoice", invoice.get() );
		}
		modelAndView.addObject("consumer", consumer);
		return modelAndView;
	}

	
	@RequestMapping(path = "/consumer/comment", method={RequestMethod.POST, RequestMethod.GET})
	public String addCommentInProduct(@RequestParam("idProduct") Long idProd,
									  @RequestParam("idInvoice") Long idInvoice,
									  @RequestParam("comment") String comment,
									  @RequestParam("score") int score){
	
		Invoice invoice = invoiceServiceImp.findById(idInvoice).get();
		InvoiceProduct invP = invoice.getInvoiceProductWithProduct(idProd);
		invP.getRatings().add(new Rating(invP, score, comment));
		invoiceServiceImp.save(invoice);
		return "redirect:/consumer/invoice?numInvoice="+invoice.getNumInvoice();
	}
	
	
	
	/***FIND ALL PRODUCTS BY DESCRIPTION IN ZONE CONSUMER*/
	@GetMapping(path = "/consumer/findproduct")
	public ModelAndView  resultSearchProducts(@RequestParam("description") String description ){

		ModelAndView modelAndView = new ModelAndView("c_findProducts");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    
	    List<Product> productToFind = productServiceImp.findByContainDescription(description);
	    List<Long> idsProducts =productToFind.stream().map(d->d.getId()).collect(Collectors.toList());
	    
		System.out.println("city " + consumer.getAddress().getCity().getId() +" zone "+consumer.getAddress().getZone().getId());
		System.out.println(" productos " + idsProducts);
		
		List<Trader> traders =  traderServiceImp.queryByParametersCityZone(consumer.getAddress().getZone().getId(), consumer.getAddress().getCity().getId(), idsProducts ); 
		System.out.println("traders--> " + traders);
		
		
		return modelAndView;
	}


	
	
}
