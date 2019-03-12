package com.payless.demo.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.CityRepository;
import com.payless.demo.repositories.ZoneRepository;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.services.InvoiceServiceImp;
import com.payless.demo.services.ProductServiceImp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * @RestController = @Controller + @ResponseBody  
 * */
@Controller
public class TraderController {

	@Autowired
	private  TraderServiceImp traderServiceImp;
	@Autowired
	private  ProductServiceImp productServiceImp;
	@Autowired
	private  InvoiceServiceImp invoicetServiceImp;
	
	
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


	@SuppressWarnings("null")
	@RequestMapping(path="/traders",method={RequestMethod.POST, RequestMethod.GET})
	public String getTraderInZone(@RequestParam("idzone") int idZone,  Model model){
		List <Trader> lista = traderServiceImp.getAllTraders();
		List <Trader> filterTraders= new ArrayList<>();
		
		for(Trader t: lista){
			List <Address> address = t.getAddress();
			for(Address a: address){
				if(a.getZona()==idZone){
					if(!filterTraders.contains(t)){
						filterTraders.add(t);
					}
				}
			}
		}
	
		System.out.println(filterTraders);	
		model.addAttribute("traders", filterTraders);
	
		return "registerinvoice :: #traders";
	}





	/***************************************/
	/**CONTROLS TRADER OPERATIONS ADDRESS  */
	/**
	 * @param list *************************************/

	
	
	
	/**VIEW ADDRES AFTER SEND PARAMETERS ID OF TRADER*/
	@RequestMapping(path="/main/addaddress", method = {RequestMethod.POST, RequestMethod.GET})
	public String showFormAddressTrader(@RequestParam(value="idtrader" , required = true) long idtrader, 
										@RequestParam(value="city",required = false) Long idCity, 
										@RequestParam(value="zone",required = false) Long idZone ,	
										@RequestParam(value="description",required = false) String descrp, Model model){
		
		
		Trader traderdb = traderServiceImp.getTrader(idtrader);
		model.addAttribute("infoTrader", traderdb);
		model.addAttribute("selectCities", cityrepository.findAll());
		model.addAttribute("selectZones", zonerepository.findAll());
		return "addaddress";
	}

	/**SAVE ADDRESS**/
	@RequestMapping(path="/add-address" , method = {RequestMethod.POST, RequestMethod.GET})
	public String addAddressTrader(@RequestParam(value="idtrader" , required = true) long idtrader, 
			@RequestParam(value="city",required = true) Long idCity, 
			@RequestParam(value="zone",required = true) Long idZone ,	
			@RequestParam(value="description",required = true) String descrp, Model model) {
		
		if(idCity!=null || idZone!= null ){
				Address addressnew = new Address(descrp, idCity.intValue() ,idZone.intValue() );
				Trader traderdb = traderServiceImp.getTrader(idtrader);
				traderdb.addAddress(addressnew);
				traderServiceImp.save(traderdb);
		}
		return "redirect:/main/addaddress?idtrader="+idtrader;
	}	


	/**DELETE ADDRESS IN TRADER**/
	@RequestMapping(path="/delete-address" , method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteAddressTrader(@RequestParam(value="idtrader" , required = true) long idtrader, 
									  @RequestParam(value="indice" , required = true) int indice){
		
		System.out.println("/delete-address" + "trader " + idtrader + "index "+ indice );
		Trader traderdb = traderServiceImp.getTrader(idtrader);
		traderdb.getAddress().remove(indice);
		traderServiceImp.save(traderdb);		
		
		return "redirect:/main/addaddress?idtrader="+idtrader;
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
		
		if(productServiceImp.existsById(idprod)){
			Product mt =  productServiceImp.findById(idprod).get();
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
	public String  searchInvoice(@RequestParam(required=false, name="cuit" ) Long cuit , 
								@RequestParam(required=false, name="numInvoice") Integer numInvoice, 
								Model model){
		
		Trader traderlist = traderServiceImp. searchByCuit(cuit);
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


	@RequestMapping(path="/invoice/showdetail", method = {RequestMethod.POST, RequestMethod.GET})
	public String show_product_invoice(@RequestParam(value="idinvoice", required=true) long idInvoice, 
									   @RequestParam(value="idtrader", required=true) long idTrader, Model model){
		
		if(invoicetServiceImp.existsById(idInvoice)){
			Invoice invoicedb = invoicetServiceImp.findById(idInvoice).get();
			model.addAttribute("invoice_detail", invoicedb);
		}
		
		return "invoicedetails";
	}


	@PostMapping(path="/invoice/updateprod")
	public String update_product_invoice(@Valid InvoiceProduct invoiceproduct, BindingResult result, Model model ){
		
		Invoice invoicedb = invoicetServiceImp.findById(invoiceproduct.getInvoice().getId()).get();
		Collection<InvoiceProduct> list_invoiceProducts   = invoicedb.getProducts();
		
		for(InvoiceProduct ip : list_invoiceProducts){
			if(ip.getPoduct().equals(invoiceproduct.getPoduct())){
				ip.setQuantity(invoiceproduct.getQuantity());
				invoicetServiceImp.save(invoicedb);
				break;
			}
		}
		return "redirect:/invoice/showdetail?idinvoice="+invoicedb.getId()+"&idtrader="+invoicedb.getTrader().getId();
	}

	
	@RequestMapping(path="/invoice/deleteinvoice", method = {RequestMethod.POST, RequestMethod.GET})
	public String removeProductInvoice(@RequestParam("idinvoice") long idinvoice, @RequestParam("idprod") long idprod){
	
		Invoice invoicedb = invoicetServiceImp.findById(idinvoice).get();
		Product pr =  productServiceImp.findById(idprod).get();
		Collection<InvoiceProduct> _listinvoiceProducts  = invoicedb.getProducts();
		_listinvoiceProducts.removeIf((InvoiceProduct ip) -> ip.getPoduct().equals(pr));
		invoicetServiceImp.save(invoicedb);
		
		return "redirect:/invoice/showdetail?idinvoice="+idinvoice+"&idtrader="+idprod;
	}
	
	
	@RequestMapping(path="/invoice/delete_Invoice/{idinvoice}/cuit/{cuit}" ,method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView delete_Invoice(@PathVariable("idinvoice") long idinvoice , @PathVariable("cuit") long cuit , RedirectAttributes redirectAttributes   ){
		invoicetServiceImp.deleteById(idinvoice);
		redirectAttributes.addAttribute("cuit", cuit);
		return new RedirectView("/invoice/searchinvoice");
	}


	@RequestMapping(path="/invoice/show_add" ,method = {RequestMethod.POST, RequestMethod.GET})
	public String show_create_Invoice(Model model){
		model.addAttribute("selectCities", cityrepository.findAll());
		return "registerinvoice";
	}
	
	@RequestMapping(path="/invoice/add" ,method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView create_Invoice(){
		return new RedirectView("/invoice/searchinvoice");
	}

	

	
	
	
	
	

}








