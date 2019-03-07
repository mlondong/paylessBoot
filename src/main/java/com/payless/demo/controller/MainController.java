package com.payless.demo.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.payless.demo.model.Address;
import com.payless.demo.model.City;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.model.Usser;
import com.payless.demo.repositories.CityRepository;
import com.payless.demo.repositories.ProductRepository;
import com.payless.demo.repositories.ZoneRepository;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.services.InvoiceServiceImp;
import com.payless.demo.services.ProductServiceImp;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.assertj.core.util.Lists;
import org.hamcrest.core.IsNull;
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
	private  InvoiceServiceImp invoicetServiceImp;



	@Autowired
	private ProductRepository  productRepository;

	@Autowired
	private CityRepository cityrepository;

	@Autowired
	private ZoneRepository zonerepository;

	
	
	
	
	/**HOME WITH LIST*/
	@RequestMapping(path="/")
	public String home(Model model){
		model.addAttribute("message", "SpringBoot Thymeleaf rocks");
		return "index"; 

	}



	/***********************************/
	/**CONTROLS TRADER OPERATIONS INFO*/
	/***********************************/

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
			model.addAttribute("errors", result.getFieldError() );	
			return "redirect:/main/formupdate/"+id;
		}else{
			Trader taderdb= traderServiceImp.getTrader(id);
			taderdb.setName(trader.getName());
			taderdb.setPassword(trader.getPassword());
			taderdb.setState(trader.isState());
			taderdb.setCuit(trader.getCuit());
			taderdb.setScore(trader.getScore());
			traderServiceImp.save(taderdb);			
			return "redirect:/main/searchtrader?cuit="+trader.getCuit();
		}		
	}

	/** DELETE TRADER*/
	@RequestMapping(path="/main/delete/{id}")
	public String deleteTrader( @PathVariable("id") long id) {
		traderServiceImp.deleteTrader(id);
		return "redirect:/main/search";
	}	





	/****************************/
	/**CONTROLS TRADER REGISTER */
	/****************************/
	/**@RequestMapping(value="/signup", method={RequestMethod.POST, RequestMethod.GET})*/
	@GetMapping(path="/main/register")
	public String showformTrader(Trader trader){
		return "registertrader"; 
	}

	/**VIEW TRADER SAVE AND LIST*/
	@PostMapping(path="/main/result")
	public String registerTrader(@Valid Trader trader, BindingResult result, Model model) {
		if (result.hasErrors() || trader.getName().isEmpty() || trader.getPassword().isEmpty()) {
			model.addAttribute("error", "Information incorrect or not complete, please verify!");
		}else{
			
			 traderServiceImp.save(trader); 
			 Trader traderdb = traderServiceImp.searchByCuit(trader.getCuit());
			 model.addAttribute("traderInfo", traderdb);
			 
			}	
		return "registertrader";
	}


	@RequestMapping(path="/redirect_trader/{cuit}" ,method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView redirectTrader( @PathVariable("idinvoice") long cuit , RedirectAttributes redirectAttributes ) {
		redirectAttributes.addAttribute("cuit", cuit);
		return new RedirectView("/main/search");
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







	/****************************/
	/**CONTROLS TRADER SEARCH****/
	/****************************/

	@RequestMapping(path="/main/viewform", method = {RequestMethod.POST, RequestMethod.GET})
	public String viewsearchTrader() {
		return "searchtrader";
	}

	
	@RequestMapping(path="/main/searchtrader", method = {RequestMethod.POST, RequestMethod.GET})
	public String searchTrader(@RequestParam(name="cuit" , required=false) long cuit,  Model model) {
		Trader traderdb=  traderServiceImp.searchByCuit(cuit); 
		if(traderdb==null){
			model.addAttribute("notfind", "Cuit: "+ cuit + " not find it.. ");
		}else{
			model.addAttribute("results", traderdb);
		}

		return "searchtrader";
	}







	/***************************************/
	/**CONTROLS TRADER OPERATIONS ADDRESS  */
	/***************************************/

	/**VIEW ADDRES AFTER SEND PARAMETERS ID OF TRADER*/
	@RequestMapping(value="/main/addaddress/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String showAddressTrader(@Valid Address address, BindingResult result, Model model , @PathVariable("id") long id  ) {
		model.addAttribute("infoTrader", traderServiceImp.getTrader(id));
		model.addAttribute("selectCities", cityrepository.findAll());
		model.addAttribute("selectZones", zonerepository.findAll());
		System.out.println(zonerepository.findAll());
		
		return "addaddress";
	}

	/**SAVE ADDRESS**/
	// tambien sirve @RequestMapping(value="/add-address/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	@PostMapping(path="/add-address/{id}")
	public String addAddressTrader(@PathVariable("id") long id , @Valid Address address, BindingResult result, Model model ) {
		Trader traderdb = traderServiceImp.getTrader(id);
		traderdb.addAddress(address);
		traderServiceImp.save(traderdb);
		return "redirect:/main/addaddress/"+id;
	}	

	
	
	






	/***********************************/
	/**CONTROLS TRADER OPERATIONS STOCK*/
	/***********************************/

	/**SEARCH STOCK FOR ADD RODUCTS /TRADERS*/
	@GetMapping(path="/main/viewformsearchstoch")
	public String viewFormSearchStockTrader(Trader trader) {
		return "showproductinstock";
	}


	/**ADD STOCK OF TRADER*/
	@RequestMapping(value="/main/stock/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String addStockTrader(@PathVariable("id") long id  ) {
		
		Trader traderdb = traderServiceImp.getTrader(id);
		Stock st = new Stock();
		st.setTrader(traderdb);
		traderdb.setStock(st);
		
		traderServiceImp.save(traderdb);
		
		return "redirect:/main/searchtrader?cuit="+traderdb.getCuit();
	
	}


	@PostMapping(value="/main/viewformsearchstoch")
	public String searchFormSearchStockTrader( @Valid Trader trader, BindingResult result, Model model  ) {
		Trader traderdb = traderServiceImp.searchByCuit(trader.getCuit());
		if(traderdb != null ){
			model.addAttribute("traderInfo", traderdb);
		}else{
			model.addAttribute("notfind", "Cuit not find.. ");
		}
		return "showproductinstock";
	}	

	@RequestMapping(path="/main/sendproducttostock/{id}")
	public String sendProductInStock(@PathVariable("id") long id  , @Valid Trader trader, BindingResult result, Model model ) {
		Trader traderdb = traderServiceImp.getTrader(id);

		System.out.println(traderdb);	
		if(traderdb!=null ){
			model.addAttribute("traderInfo", traderdb);	
			model.addAttribute("products", productServiceImp.findAll());
			return "addproductinstock";
		}else{
			return "redirect:/main/viewformsearchstoch";	
		}
	}	

	@RequestMapping(path="/main/putproductinstock" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String putProductInStock(@RequestParam("trader") long idtrader,	@RequestParam("product") long idprod,  
			@RequestParam("quantity") int cant) {

		Trader traderdb = traderServiceImp.getTrader(idtrader);
		if(productRepository.existsById(idprod)){
			Product mt =  productRepository.findById(idprod).get();
			traderdb.getStock().addProduct(mt, cant);
			traderServiceImp.save(traderdb);
		}
		return "redirect:/main/sendproducttostock/"+idtrader;
	}	


	@RequestMapping(path="/main/updateproduct/{idprod}/trader/{idtrader}")
	public String showUptadeProductInStock(@PathVariable("idtrader") long idtrader , @PathVariable("idprod") long idprod, Model model){
		Trader traderdb = traderServiceImp.getTrader(idtrader);
		StockProducts stproduct = traderdb.getStock().findProductInOwnStock(idprod); 
		if(stproduct!=null){
			model.addAttribute("traderdb", traderdb); 
			model.addAttribute("stproductdb", stproduct); 
			return "updateproduct";
		}else{
			return "redirect:/main/sendproducttostock/"+idtrader;
		}
	}

	@RequestMapping(path="/main/save" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String saveChangesProductsInStock(@RequestParam("quantity") int cant, @RequestParam("idproduct") long  idProduct , 
			@RequestParam("idtrader") long  idTrader ){

		if(cant!=0){
			if(productServiceImp.existsById(idProduct)){
				Trader traderdb = traderServiceImp.getTrader(idTrader);
				StockProducts stproduct = traderdb.getStock().findProductInOwnStock(idProduct); 
				stproduct.setQuantity(cant);
				stproduct.setDate(new Date());
				traderServiceImp.save(traderdb);
			}
		}
		return "redirect:/main/sendproducttostock/"+idTrader;
	}


	@RequestMapping(path="/main/deleteproduct/{idprod}/trader/{idtrader}" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteProductsInStock(@PathVariable("idtrader") long idtrader , @PathVariable("idprod") long idprod, Model model){
		Collection <StockProducts> stockProducts;
		Trader traderdb = traderServiceImp.getTrader(idtrader);
		Product mt =  productServiceImp.findById(idprod).get();
		stockProducts =   traderdb.getStock().getStockproducts();
		stockProducts.removeIf((StockProducts st) -> st.getProduct().equals(mt)  );
		traderServiceImp.save(traderdb);
		return "redirect:/main/sendproducttostock/"+idtrader;
	} 








	/**************************************/
	/**CONTROLS TRADER OPERATIONS INVOICES*/
	/**************************************/


	/**VIEW INVOICES IN TRADER*/
	@RequestMapping(path="/invoice/showforminvoice" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String  showFormInvoice(){
		return "invoice";
	}

	@RequestMapping(path="/invoice/searchinvoice",  method = {RequestMethod.POST, RequestMethod.GET})
	public String  searchInvoice(@RequestParam("cuit") long cuit , Model model){
		Trader traderlist = traderServiceImp.searchByCuit(cuit);
		if(traderlist == null){
			model.addAttribute("Error", " Trader not find it, with Cuit. " + cuit);
		}else if(traderlist.getInvoice().isEmpty()){
			model.addAttribute("Error", " There isn't Invoices in Trader with Cuit. " + cuit);	
		}else{
			model.addAttribute("TraderInfo", traderlist );	
			model.addAttribute("Show", traderlist.getInvoice() );	
		}
		return "invoice";
	}


	@RequestMapping(path="/invoice/show_producs_invoice/{idinvoice}/trader/{idtrader}")
	public String show_producs_invoice(@PathVariable("idinvoice") long idInvoice , @PathVariable("idtrader") long idTrader){
		return "index";
	}

	@RequestMapping(path="/invoice/delete_Invoice/{idinvoice}/cuit/{cuit}" ,method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView delete_Invoice(@PathVariable("idinvoice") long idinvoice , @PathVariable("cuit") long cuit , RedirectAttributes redirectAttributes   ){
		invoicetServiceImp.deleteById(idinvoice);
		redirectAttributes.addAttribute("cuit", cuit);
		return new RedirectView("/invoice/searchinvoice");
	}



}








