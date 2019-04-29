package com.payless.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Product;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.ProductServiceImp;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.util.UtilOperations;


@Controller
public class ConsumerController {


	@Autowired
	private  ConsumerServiceImp consumerServiceImp;
	
	@Autowired
	private  ProductServiceImp productServiceImp;
	
	@Autowired
	private  TraderServiceImp traderServiceImp;
	
	
	
	
	@GetMapping("/consumer")
	public ModelAndView consumer() {
		ModelAndView modelAndView = new ModelAndView("consumer");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + consumer.getFirstName() + " " + consumer.getLastName() + " (" + consumer.getDni() + ")");
        modelAndView.addObject("info", consumer.toString());
        return modelAndView;
	}
	
	
	
	@GetMapping(path="/consumer/findproducts")
	public ModelAndView findProducts(){
		ModelAndView modelAndView = new ModelAndView("c_findProducts");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
        modelAndView.addObject("data", consumer);
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
	    modelAndView.addObject("data", consumer);
	    
	    
	    
	    List<Trader> traders = traderServiceImp.queryByParametersCityZone(zone, city);
	    Map<String, List<StockProducts>> filters = UtilOperations.filterByProductInZone(traders, desc);
	    
	    
	    modelAndView.addObject("notFilterProducts", traders );
	    modelAndView.addObject("products", filters );
		return modelAndView;
	}

	
	@RequestMapping(path="/consumer/getdescription", method={RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public List<Product> getProductName(@RequestParam("description") String description){
		return productServiceImp.findByContainDescription(description);
	} 



	
}
