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
import com.payless.demo.model.Stock;
import com.payless.demo.model.Trader;
import com.payless.demo.services.TraderServiceImp;


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



	/*HOME WITH LIST*/
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

	/**VIEW OF SEARCH*/
	@GetMapping(path="/main/search")
	public String showsearchTrader(Trader trader) {
		return "searchtrader";
	}

	/**VIEW OD SEARCH TRADER BY CUIT
	 *sepuede usar  	//public String searchTrader(@ModelAttribute Trader trader)   */
	@PostMapping(path="/main/search")
	public String searchTrader(@Valid Trader trader, BindingResult result, Model model) {
		System.out.println("llego trader.." + trader);
		model.addAttribute("results", traderServiceImp.searchByCuit(trader.getCuit()));
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



	/**VIEW ADD STOCK OF TRADER*/
	@RequestMapping(value="/main/stock/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String addProductStockTrader(@PathVariable("id") long id  ) {
			Trader traderdb = traderServiceImp.getTrader(id);
			traderServiceImp.save(traderdb);
			
			return "redirect:/main/search";
		
	}








}








