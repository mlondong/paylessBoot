package com.payless.demo.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.payless.demo.model.Address;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.ProductRepository;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.services.ProductServiceImp;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * @RestController = @Controller + @ResponseBody  
 * */
@Controller
public class MainController {

	@Autowired
	private  TraderServiceImp traderServiceImp;
	@Autowired
	private  ProductServiceImp productServiceImp;
	@Autowired
	private ProductRepository  productRepository;







	/**HOME WITH LIST*/
	@RequestMapping(path="/")
	public String home(Model model){
		model.addAttribute("message", "SpringBoot Thymeleaf rocks");
		return "index"; 

	}


	/**VIEW TRADER FROM*/
	/**@RequestMapping(value="/signup", method={RequestMethod.POST, RequestMethod.GET})*/
	@GetMapping(path="/main/register")
	public String showformTrader(Trader trader){
		return "registertrader"; 
	}

	/**VIEW TRADER SAVE AND LIST*/
	@PostMapping(path="/main/result")
	public String registerTrader(@Valid Trader trader, BindingResult result, Model model) {
		System.out.println("llegando....."  + trader);
		if (result.hasErrors()) {
			return "index";
		}
		traderServiceImp.save(trader); 
		model.addAttribute("traders", traderServiceImp.searchByCuit(trader.getCuit()));
		return "registertrader";
	}

	/*********************************************************************************************************************
	//OTRO EJEMPLO CO EL @Valid Trader trader
	//@RequestMapping(value="/result", method={RequestMethod.POST, RequestMethod.GET})
	/*@PostMapping(path="/main/result")
	public String register(@Valid Trader trader, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "index";
		}

		traderRepository.save(new Trader(trader.getName(),trader.getPassword(),trader.getCuit()));
		model.addAttribute("traders", traderRepository.findAll());
		return "result";
	}*/





	/**VIEW OF GENERAL SEARCH*/
	@GetMapping(path="/main/search")
	public String showsearchTrader(Trader trader) {
		return "searchtrader";
	}

	/**VIEW OD SEARCH TRADER BY CUIT
	 *sepuede usar  	//public String searchTrader(@ModelAttribute Trader trader)   */
	@PostMapping(path="/main/search")
	public String searchTrader(@Valid Trader trader, BindingResult result, Model model) {

		List<Trader> list=  traderServiceImp.searchByCuit(trader.getCuit()); 

		if(list.isEmpty()){
			model.addAttribute("notfind", "Cuit: "+ trader.getCuit() + " not find it.. ");
		}else{
			model.addAttribute("results", list);



		}

		return "searchtrader";
	}









	/**VIEW ADDRES AFTER SEND PARAMETERS ID OF TRADER*/
	@RequestMapping(value="/main/addaddress/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String showAddressTrader(@Valid Address address, BindingResult result, Model model , @PathVariable("id") long id  ) {

		if (result.hasErrors()) {
			return "index";
		}else{

			System.out.println("en address view id " + id);
			model.addAttribute("infoTrader", traderServiceImp.getTrader(id));
			return "addaddress";
		}
	}

	/**SAVE ADDRESS**/
	// tambien sirve @RequestMapping(value="/add-address/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	@PostMapping(path="/add-address/{id}")
	public String addAddressTrader(@PathVariable("id") long id , @Valid Address address, BindingResult result, Model model ) {

		Trader traderdb = traderServiceImp.getTrader(id);
		traderdb.addAddress(address);
		traderServiceImp.save(traderdb);
		System.out.println("/main/addaddress/"+id);
		return "redirect:/main/addaddress/"+id;
	}	









	/**SEARCH STOCK FOR ADD RODUCTS /TRADERS*/
	@GetMapping(path="/main/viewformsearchstoch")
	public String viewFormSearchStockTrader(Trader trader) {
		return "showproductinstock";
	}

	@PostMapping(value="/main/viewformsearchstoch")
	public String searchFormSearchStockTrader( @Valid Trader trader, BindingResult result, Model model  ) {
		List<Trader> traderdb = traderServiceImp.searchByCuit(trader.getCuit());

		if(!traderdb.isEmpty()){
			model.addAttribute("traderInfo", traderdb);
		}else{
			model.addAttribute("notfind", "Cuit not find.. ");
		}
		return "showproductinstock";
	}	


	@RequestMapping(path="/main/sendproducttostock/{id}")
	public String sendProductInStock(@PathVariable("id") long id  , @Valid Trader trader, BindingResult result, Model model ) {
		Trader traderdb = traderServiceImp.getTrader(id);

		if(traderdb!=null ){
			model.addAttribute("traderInfo", traderdb);	
			model.addAttribute("products", productServiceImp.findAll());
			return "addproductinstock";
		}else{
				return "redirect:/main/viewformsearchstoch";	
			}
	}	


	@RequestMapping(path="/main/putproductinstock" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String putProductInStock(@RequestParam("trader") long idtrader, 
			@RequestParam("product") long idprod,  
			@RequestParam("quantity") int cant) {

		Trader traderdb = traderServiceImp.getTrader(idtrader);

		if(productRepository.existsById(idprod)){
			System.out.println("existe");
			Product mt =  productRepository.findById(idprod).get();
			traderdb.getStock().addProduct(mt, cant);
			traderServiceImp.save(traderdb);

		}
		return "redirect:/main/sendproducttostock/"+idtrader;
	}	




	@RequestMapping(path="/main/updateproduct")
	public String uptadeProductInStock(){
		
		return "redirect:/main/sendproducttostock/{id}";
	}

	
	
	
	

	/**VIEW ADD STOCK OF TRADER*/
	@RequestMapping(value="/main/stock/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String addStockTrader(@PathVariable("id") long id  ) {
		Trader traderdb = traderServiceImp.getTrader(id);
		Stock st = new Stock();
		st.setTrader(traderdb);
		traderdb.setStock(st);
		System.out.println(traderServiceImp.save(traderdb));

		return "redirect:/main/search";
	}









	/**SHOW FORM UPDATE TRADER*/
	@RequestMapping(value="/main/formupdate/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String formupdateTrader(@PathVariable("id") long id , Model model   ) {
		Trader traderdb = traderServiceImp.getTrader(id);
		model.addAttribute("traderdb", traderdb);	
		return "updatetrader";
	}

	/** UPDATE TRADER*/
	@PostMapping(path="/main/update/{id}")
	public String updateTrader( @PathVariable("id") long id, @Valid Trader trader, BindingResult result, Model model  ) {
		if (result.hasErrors()) {
			return "index";
		}else{
			Trader taderdb= traderServiceImp.getTrader(id);
			taderdb.setName(trader.getName());
			taderdb.setPassword(trader.getPassword());
			taderdb.setState(trader.isState());
			taderdb.setCuit(trader.getCuit());
			taderdb.setScore(trader.getScore());
			traderServiceImp.save(taderdb);			
			return "searchtrader";
		}		

	}






	/** DELETE TRADER*/
	@RequestMapping(path="/main/delete/{id}")
	public String deleteTrader( @PathVariable("id") long id) {
		System.out.println("llego... "  +id);
		traderServiceImp.deleteTrader(id);
		return "redirect:/main/search";
	}	



}








