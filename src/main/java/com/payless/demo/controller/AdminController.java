package com.payless.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.payless.demo.model.Address;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Role;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.services.AddressServiceImp;
import com.payless.demo.services.CityServiceImp;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.InvoiceServiceImp;
import com.payless.demo.services.ProductServiceImp;
import com.payless.demo.services.RoleServiceImpl;
import com.payless.demo.services.StockServiceImp;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.services.ZoneServiceImp;
import com.payless.demo.util.Passgenerator;

/**
 * @RestController = @Controller + @ResponseBody  
 * */
@Controller
@RequestMapping(path="/admin")
public class AdminController {

	@Autowired
	private  TraderServiceImp traderServiceImp;
	@Autowired
	private  ProductServiceImp productServiceImp;
	@Autowired
	private  InvoiceServiceImp invoicetServiceImp;
	@Autowired
	private  ConsumerServiceImp consumerServiceImp;
	@Autowired
	private  InvoiceServiceImp invoiceServiceImp;
	@Autowired
	private  StockServiceImp stockServiceImp;
	@Autowired
	private  RoleServiceImpl roleServiceImpl;
	@Autowired
	private CityServiceImp cityServiceImp;
	@Autowired
	private ZoneServiceImp zoneServiceImp;
	@Autowired
	private AddressServiceImp addressServiceImp;
	

	
	
	
    
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
			return "redirect:/admin/main/formupdate/"+id;
		}else{
			Trader taderdb= traderServiceImp.getTrader(id);
			taderdb.setName(trader.getName());
			taderdb.setPassword(trader.getPassword());
			taderdb.setState(trader.isState());
			taderdb.setCuit(trader.getCuit());
			taderdb.setScore(trader.getScore());
			traderServiceImp.save(taderdb);			
			return "redirect:/admin/main/searchtrader?cuit="+trader.getCuit();
		}		
	}

	/** DELETE TRADER*/
	@RequestMapping(path="/main/delete/{id}")
	public String deleteTrader( @PathVariable("id") long id) {
		traderServiceImp.deleteTrader(id);
		return "redirect:/admin/main/viewform";
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
			Passgenerator p = new Passgenerator(4);
			Optional<Role> role = roleServiceImpl.findById(3L);
			trader.setPassword(p.generate(trader.getPassword()));
			trader.addRole(role.get());;
			
			traderServiceImp.save(trader); 
			Trader traderdb = traderServiceImp.searchByCuit(trader.getCuit());
			model.addAttribute("traderInfo", traderdb);
			 
			}	
		return "registertrader";
	}


	@RequestMapping(path="/redirect_trader/{cuit}" ,method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView redirectTrader( @PathVariable("idinvoice") long cuit , RedirectAttributes redirectAttributes ) {
		redirectAttributes.addAttribute("cuit", cuit);
		return new RedirectView("/admin/main/searchtrader");
	}





	/****************************/
	/**CONTROLS TRADER SEARCH****/
	/****************************/

	@RequestMapping(path="/main/viewform", method = {RequestMethod.POST, RequestMethod.GET})
	public String viewsearchTrader() {
		return "searchtrader";
	}

	
	@RequestMapping(path="/main/searchtrader", method = {RequestMethod.POST, RequestMethod.GET})
	public String searchTrader(@RequestParam(name="cuit" , required=false) Long cuit,  Model model) {
		Trader traderdb=  traderServiceImp.searchByCuit(cuit); 
		if(traderdb==null){
			model.addAttribute("notfind", "Cuit: "+ cuit + " not find it.. ");
		}else{
			model.addAttribute("results", traderdb);
		}

		return "searchtrader";
	}

	
	@RequestMapping(path="/zones",method={RequestMethod.POST, RequestMethod.GET})
	public String getZonesInCity(@RequestParam("idcity") long idcity,  Model model){
		model.addAttribute("zones", zoneServiceImp.findAllZonesByIdCity(idcity));
		return "registerinvoice :: #zone";
	}

	@RequestMapping(path="/traders",method={RequestMethod.POST, RequestMethod.GET})
	public String getTraderInZone(@RequestParam("idzone") int idZone,  Model model){
	
		List <Trader> allTraders = traderServiceImp.getAllTraders();
		List <Trader> filterTraders= new ArrayList<>();
		
		for(Trader trader: allTraders){
			List <Address> addressList = trader.getAddress();
			if(trader.getStock()!=null){
				for(Address address: addressList){
					if(address.getZone().getId()== idZone){
						if(!filterTraders.contains(trader)){
							filterTraders.add(trader);
						}
					}
				}
			}
		}
		model.addAttribute("traders", filterTraders);
		return "registerinvoice :: #traders";
	}

	
	
	/***************************************/
	/**CONTROLS CONSUMER OPERATIONS        */
	/***************************************/
	
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
				Passgenerator p = new Passgenerator(4);
				Optional<Role> role = roleServiceImpl.findById(2L);
				
				consumer.setPassword(p.generate(consumer.getPassword()));
				consumer.addRole(role.get());
				
				Consumer consumerdb =  consumerServiceImp.save(consumer);
				model.addAttribute("consumerInfo", consumerdb);
			}
		return "registerconsumer";
	}	
		

	@RequestMapping(path="/consumer/viewsearch" , method={RequestMethod.POST, RequestMethod.GET})
	public String viewsearchConsumer(){
			return "searchconsumer";
    }
	
	@RequestMapping(path="/consumer/search" , method={RequestMethod.POST, RequestMethod.GET})
	public String searchConsumer(@RequestParam(value="firstName", required=false, defaultValue="" ) String firstName ,
								 @RequestParam(value="dni", required=false, defaultValue="0") Long dni, Model model){
		
			if(dni!=0 && firstName.equals("")){
			 	Consumer consumerdb  = consumerServiceImp.findByDni(dni);
				model.addAttribute("consumers", consumerdb);
			}else if(dni==0 && firstName.equals("")){
				Iterable<Consumer> consumerdb = consumerServiceImp.findAll();
				model.addAttribute("consumers", consumerdb);
			}else if(dni==0 && !firstName.equals("")){
				List<Consumer> consumerdb =  consumerServiceImp.findByFirstNameLike(firstName);
				model.addAttribute("consumers", consumerdb);
			}
		return "searchconsumer"; 
	}


	@RequestMapping(path="/consumer/show_update", method={RequestMethod.POST, RequestMethod.GET})
	public String showupdateConsumer(@RequestParam(value="idconsumer", required=false) Long idConsumer, Model model){
		Consumer consumerdb = consumerServiceImp.findById(idConsumer).get();
		System.out.println(consumerdb.toString());
		model.addAttribute("consumer", consumerdb);	
		return "updateconsumer";
	}
	
	
	@PostMapping(path="/consumer/update")
	public String updateConsumer(@Valid Consumer consumer, BindingResult result, Model model){
		Consumer consumerdb= null;
		
		if(result.hasErrors()){
			model.addAttribute("Error", "Error en data Information...");
			return "updateconsumer";
		}else{
			
			   Passgenerator p = new Passgenerator(4);
			   consumerdb = consumerServiceImp.findById(consumer.getId()).get();
			   consumerdb.setFirstName(consumer.getFirstName());
			   consumerdb.setLastName(consumer.getLastName());
			   consumerdb.setName(consumer.getName());
			   consumerdb.setPassword(p.generate(consumer.getPassword()));
			   consumerdb.setState(consumer.isState());
			   consumerdb.setDni(consumer.getDni());
			   consumerServiceImp.save(consumerdb);
			   return "redirect:/admin/consumer/search?dni="+consumer.getDni();
		}
	}
			
	
	@RequestMapping(path="/consumer/delete" , method={RequestMethod.POST, RequestMethod.GET})
	public String deleteConsumer(@RequestParam("idconsumer") Long idConsumer){
		consumerServiceImp.deleteById(idConsumer);
		return "redirect:/admin/consumer/search";
	}	
	
	
	
	@RequestMapping(path="/consumer/addaddress" , method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView viewAddressConsumer(@RequestParam(value="idconsumer", required=true) Long idConsumer){
		ModelAndView modelAndView = new ModelAndView("addaddressconsumer");
		Consumer consumer = consumerServiceImp.findById(idConsumer).get();
		modelAndView.addObject("consumer", consumer);
		modelAndView.addObject("selectCities", cityServiceImp.findAll());
		return modelAndView; 
	}	
	
	@RequestMapping(path="/consumer/address/add" , method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView addAddressConsumer(@RequestParam(value="idconsumer", required=true) Long idConsumer,
										   @RequestParam(value="city", required=true) Long idCity,
										   @RequestParam(value="zone", required=true) Long idZone,
										   @RequestParam(value="description", required=true) String description ){
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/consumer/addaddress?idconsumer="+idConsumer);
		Consumer consumer = consumerServiceImp.findById(idConsumer).get();
		consumer.setAddress(new Address(description, cityServiceImp.findById(idCity).get(), zoneServiceImp.findById(idZone).get())); 
		consumerServiceImp.save(consumer);
		return modelAndView; 
	}
	
	@RequestMapping(path="/consumer/address/del" , method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView delAddressConsumer(@RequestParam(value="idaddress", required=true) Long idAddress ,@RequestParam(value="idconsumer", required=true) Long idConsumer){
		
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/consumer/addaddress?idconsumer="+idConsumer);
		Consumer consumer = consumerServiceImp.findById(idConsumer).get();
		consumer.setAddress(null);
		consumerServiceImp.save(consumer);
		Address  address = addressServiceImp.findById(idAddress).get();
		addressServiceImp.delete(address);
		
		return modelAndView; 
	}
	
	
	
	

	
	/***************************************/
	/**CONTROLS TRADER OPERATIONS ADDRESS  */
	/***************************************/
	
	/**VIEW ADDRES AFTER SEND PARAMETERS ID OF TRADER*/
	@RequestMapping(path="/main/addaddress", method = {RequestMethod.POST, RequestMethod.GET})
	public String showFormAddressTrader(@RequestParam(value="idtrader" , required = true) long idtrader, 
										@RequestParam(value="city",required = false) Long idCity, 
										@RequestParam(value="zone",required = false) Long idZone ,	
										@RequestParam(value="description",required = false) String descrp, Model model){

		Trader traderdb = traderServiceImp.getTrader(idtrader);
		model.addAttribute("infoTrader", traderdb);
		model.addAttribute("selectCities", cityServiceImp.findAll());
		return "addaddress";
	
	}

	
	@RequestMapping(path="/address/zones",method={RequestMethod.POST, RequestMethod.GET})
	public String zonesInCity(@RequestParam("idcity") long idcity,  Model model){
		model.addAttribute("zones", zoneServiceImp.findAllZonesByIdCity(idcity));
		return "addaddress :: #zone";
	}

	
	
	
	/**SAVE ADDRESS**/
	@RequestMapping(path="/main/add-address" , method = {RequestMethod.POST, RequestMethod.GET})
	public String addAddressTrader(@RequestParam(value="idtrader" , required = true) long idtrader, 
								   @RequestParam(value="city",required = true) Long idCity, 
								   @RequestParam(value="zone",required = true) Long idZone ,	
								   @RequestParam(value="description",required = true) String descrp, Model model) {
		
		if(idCity!=null || idZone!= null ){
				Address addressnew = new Address(descrp, cityServiceImp.findById(idCity).get(), zoneServiceImp.findById(idZone).get() );
				Trader traderdb = traderServiceImp.getTrader(idtrader);
				traderdb.addAddress(addressnew);
				traderServiceImp.save(traderdb);
		}

		return "redirect:/admin/main/addaddress?idtrader="+idtrader;
	}	


	/**DELETE ADDRESS IN TRADER**/
	@RequestMapping(path="/delete-address" , method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteAddressTrader(@RequestParam(value="idtrader" , required = true) long idtrader, 
									  @RequestParam(value="indice" , required = true) int indice){
		
		Trader traderdb = traderServiceImp.getTrader(idtrader);
		traderdb.getAddress().remove(indice);
		traderServiceImp.save(traderdb);		
		return "redirect:/admin/main/addaddress?idtrader="+idtrader;
	} 

	
	
	
	/***********************************/
	/**CONTROLS TRADER OPERATIONS STOCK*/
	/***********************************/

	/**SEARCH STOCK FOR ADD RODUCTS /TRADERS*/
	@GetMapping(path="/main/viewformsearchstock")
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
		return "redirect:/admin/main/searchtrader?cuit="+traderdb.getCuit();
	}


	@PostMapping(value="/main/viewformsearchstoch")
	public String searchFormSearchStockTrader( @Valid Trader trader, BindingResult result, Model model  ) {
		Trader traderdb = traderServiceImp.searchByCuit(trader.getCuit());
		System.out.println(traderdb);
		
		if(traderdb != null ){
			model.addAttribute("traderInfo", traderdb);
		}else{
			model.addAttribute("notfind", "Cuit not find it .. "+trader.getCuit());
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
			return "redirect:/admin/main/viewformsearchstoch";	
		}
	}	

	@RequestMapping(path="/main/putproductinstock" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String putProductInStock(@RequestParam("trader") long idtrader,	
								    @RequestParam("product") long idprod,  
								    @RequestParam("quantity") int cant,
								    @RequestParam("salesprice") int salesprice	) {

		Trader traderdb = traderServiceImp.getTrader(idtrader);
		if(productServiceImp.existsById(idprod)){
			Product mt =  productServiceImp.findById(idprod).get();
			traderdb.getStock().addProduct(mt, cant, salesprice);
			traderServiceImp.save(traderdb);
		}
		return "redirect:/admin/main/sendproducttostock/"+idtrader;
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
			return "redirect:/admin/main/sendproducttostock/"+idtrader;
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
		return "redirect:/admin/main/sendproducttostock/"+idTrader;
	}


	@RequestMapping(path="/main/deleteproduct/{idprod}/trader/{idtrader}" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteProductsInStock(@PathVariable("idtrader") long idtrader , @PathVariable("idprod") long idprod, Model model){
		Collection <StockProducts> stockProducts;
		Trader traderdb = traderServiceImp.getTrader(idtrader);
		Product mt =  productServiceImp.findById(idprod).get();
		stockProducts =   traderdb.getStock().getStockproducts();
		stockProducts.removeIf((StockProducts st) -> st.getProduct().equals(mt)  );
		traderServiceImp.save(traderdb);
		return "redirect:/admin/main/sendproducttostock/"+idtrader;
	} 





	/**************************************/
	/**CONTROLS TRADER OPERATIONS INVOICES*/
	/**************************************/

	/**VIEW INVOICES IN TRADER*/
	@RequestMapping(path="/invoice/showforminvoice" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public String  showFormInvoice(){
		return "invoice";
	}

	@RequestMapping(path="/invoice/searchinvoice")
	public String searchInvoiceByCuit(@RequestParam(required=false, name="cuit", defaultValue="0" ) Long cuit, Model model){
		
		 if(cuit==0){
			model.addAttribute("Error", " Fill the form... to find information " );
		}else{
				Trader traderlist = traderServiceImp.searchByCuit(cuit);
				if(traderlist == null){
					model.addAttribute("Error", " Trader not find it, with Cuit. " + cuit);
				}else if(traderlist.getInvoice().isEmpty()){
					model.addAttribute("Error", " There isn't Invoices in Trader with Cuit. " + cuit);	
				}else{
					model.addAttribute("TraderInfo", traderlist );	
					model.addAttribute("Show", traderlist.getInvoice() );	
				}
		}
		return "invoice";
	}

	
	
	@RequestMapping(path = "/invoice/getInvoiceProduct",  method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
   public Product loadInvoiceProduct(@RequestParam("idInvoice") Long idInvoice , @RequestParam("idProd") Long idProd) {
		InvoiceProduct invoiceProduct=null;
		Invoice invoicedb = invoicetServiceImp.findById(idInvoice).get();
		invoiceProduct = invoicedb.getInvoiceProductWithProduct(idProd);
		return invoiceProduct.getPoduct();
    }
	
	
	
	@RequestMapping(path="/invoice/updateprod" , method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView update_product_invoice(@RequestParam("idprod") Long idProd ,  
										 @RequestParam("idinvoice") Long idInvoice ,
										 @RequestParam("quantity") int cant){

		
		Invoice invoicedb = invoicetServiceImp.findById(idInvoice).get();

		ModelAndView modelAndView = new ModelAndView("redirect:/invoice/showdetail?idinvoice="+idInvoice+"&idtrader="+invoicedb.getTrader().getId());

		InvoiceProduct invoiceProduct = invoicedb.getInvoiceProductWithProduct(idProd);
		Stock stockTrader = invoicedb.getTrader().getStock();
		StockProducts stockProduct= stockTrader.findProductInOwnStock(idProd);

		
		int quantityInStock = stockProduct.getQuantity(); 
		int quantityInvoice = invoiceProduct.getQuantity(); 
		int quantityToUpdate=0;
		
		if(quantityInStock==0){
			if(quantityInvoice < cant){
				quantityToUpdate= cant;
				quantityInStock = quantityInStock + (quantityInvoice - cant);
			}else{
					modelAndView.addObject("Error", "Quantity in stock is 0, you can't update your quantity ");
				 }	
		}else if(quantityInStock < cant){
			modelAndView.addObject("Error", "Quantity in stock: "+quantityInStock+ "is less, that "+cant+", try again!");
		}else{
				System.out.println("quantityInvoice  " + quantityInvoice + "cant "+ cant );
			
				if(quantityInvoice < cant){
					quantityToUpdate = cant ;
					quantityInStock=  quantityInStock - ( cant - quantityInvoice );
				}if(quantityInvoice > cant){
					quantityToUpdate = cant ;
					quantityInStock=  quantityInStock + (quantityInvoice - cant);
				}if(quantityInvoice==cant){
					quantityToUpdate=cant;
				}
		
				
			stockProduct.setQuantity(quantityInStock);
			invoiceProduct.setQuantity(quantityToUpdate); 
			stockServiceImp.save(stockTrader);
			invoicetServiceImp.save(invoicedb);
		}
		
		return modelAndView;
	}


	
	
	
	@RequestMapping(path="/invoice/deleteinvoice", method = {RequestMethod.POST, RequestMethod.GET})
	public String removeProductInvoice(@RequestParam("idinvoice") long idinvoice, @RequestParam("idprod") long idProduct){
		
		Invoice invoicedb = invoicetServiceImp.findById(idinvoice).get();
		InvoiceProduct invoiceProduct = invoicedb.getInvoiceProductWithProduct(idProduct);
		
		Stock stockTrader = invoicedb.getTrader().getStock();
		StockProducts stockProduct= stockTrader.findProductInOwnStock(idProduct);
	
		int newQuantity = stockProduct.getQuantity() + invoiceProduct.getQuantity();
		stockProduct.setQuantity(newQuantity);
		stockServiceImp.save(stockTrader);

		invoicedb.removeInvoiceProduct(invoiceProduct);
		invoicetServiceImp.save(invoicedb);
		
		return "redirect:/admin/invoice/showdetail?idinvoice="+idinvoice+"&idtrader="+idProduct;
	}
	
	
	@RequestMapping(path="/invoice/delete_Invoice/{idinvoice}/cuit/{cuit}" ,method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView delete_Invoice(@PathVariable("idinvoice") long idinvoice , @PathVariable("cuit") long cuit , RedirectAttributes redirectAttributes   ){
		invoicetServiceImp.deleteById(idinvoice);
		redirectAttributes.addAttribute("cuit", cuit);
		return new RedirectView("/admin/invoice/searchinvoice");
	}


	@RequestMapping(path="/invoice/show_add" ,method = {RequestMethod.POST, RequestMethod.GET})
	public String show_create_Invoice(Model model){
		model.addAttribute("selectCities", cityServiceImp.findAll());
		return "registerinvoice";
	}
	
	
	
	
	@RequestMapping(path="/invoice/dnisearch",  method = {RequestMethod.POST, RequestMethod.GET})
	public String  searchInvoice(@RequestParam(required=false, name="dni", defaultValue="0" ) Long dni, Model model){
		
		if(dni==0){
			model.addAttribute("Error", " Fill the form... to find information " );
		}else{
				Consumer consumerdb = consumerServiceImp.findByDni(dni);
				if(consumerdb == null){
					model.addAttribute("Error", " Consumer not find it, with Dni. " + dni);
				}else if(consumerdb.getInvoices().isEmpty()){
					model.addAttribute("Error", " There isn't Invoices in Consumer with Dni. " + dni);	
				}else{
					model.addAttribute("ConsumerInfo", consumerdb );	
					model.addAttribute("consumerInvoices", consumerdb.getInvoices());	
				}
		}
	return "invoice";
	}
	
	
	@RequestMapping(path="/invoice/numinvoice",  method = {RequestMethod.POST, RequestMethod.GET})
	public String  searchInvoiceNumeber(@RequestParam(required=false, name="numInvoice", defaultValue="0" ) Long numInvoice, Model model){
		
		
		if(numInvoice==0){
			model.addAttribute("Error", " Fill the form... to find information " );
		}else{
				Optional<Invoice> invoicedb = invoiceServiceImp.findByNumInvoice(numInvoice);
				System.out.println(" datos "  + invoicedb.isPresent());
				
				if(invoicedb.isPresent()==false){
					model.addAttribute("Error", " Consumer not find it, with Invoice. " + numInvoice);
				}else{
					  model.addAttribute("consumerInvoices", invoicedb.get() );	
				}
		}
		return "invoice";
	}
	
	
	
	
	@RequestMapping(path="/invoice/add" ,method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView create_Invoice(@RequestParam("traders") Long idTrader, 
									   @RequestParam("dni-autocomplete") Long dni, 
									   RedirectAttributes attributes){
		ModelAndView modelAndView = null; 
		Consumer consumerdb = consumerServiceImp.findByDni(dni);
		
		if(consumerdb == null){
			modelAndView = new ModelAndView("registerinvoice");
			modelAndView.addObject("Error", "Consumer with dni "+dni+ " is not find it!");
		}else{
		
				Trader traderdb     = traderServiceImp.getTrader(idTrader);
				Invoice inv = new  Invoice(traderdb, consumerdb);
				invoicetServiceImp.save(inv);	
				modelAndView = new ModelAndView("redirect:/admin/invoice/addproducts?numInvoice="+inv.getNumInvoice());
		}
		return modelAndView;
	}

	
	@RequestMapping(path="/invoice/addproducts" ,method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView add_products_in_Invoice(@RequestParam("numInvoice") Long numInvoice){

		ModelAndView modelAndView = null;
		Optional<Invoice> inv = invoicetServiceImp.findByNumInvoice(numInvoice);
		
		if(inv.isPresent()==false){
			modelAndView = new ModelAndView("registerinvoice");
			modelAndView.addObject("Error", "Invoice not generated ,try in a moment!!!");
		}else{
				modelAndView = new ModelAndView("addproductininvoice");
				modelAndView.addObject("invoice", inv.get()); 
				
				Stock stockdb = inv.get().getTrader().getStock();
				modelAndView.addObject("stockproducts", stockdb.matchInvoiceStockInTrader(inv.get()));
				//modelAndView.addObject("currentstock", stockdb.getStockproducts());
				
				
				
		}
		return modelAndView;
	} 
	
	
	
	
	
	@RequestMapping(path="/invoice/addprodinv" ,  method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView putProductInInvoice(@RequestParam("numInvoice") Long numInvoice, 
									  @RequestParam("idProduct") Long idProduct, 
									  @RequestParam("cant") int cant) {

		System.out.println("datos....... " +  cant +" "+idProduct+" " +numInvoice);
		ModelAndView modelAndView = null;
		
		Optional<Invoice> invoicedb = invoicetServiceImp.findByNumInvoice(numInvoice);
		if(invoicedb.isPresent()==false){
			modelAndView = new ModelAndView("registerinvoice");
			modelAndView.addObject("Error", "Invoice not generated ,try in a moment!!!");
		}else{
				Stock stockdb = invoicedb.get().getTrader().getStock();
				StockProducts stockProductDB = stockdb.findProductInOwnStock(idProduct); 
				
				int newQuantity = stockProductDB.getQuantity()-cant;
				stockProductDB.setQuantity(newQuantity);
			
				Product productdb = productServiceImp.findById(idProduct).get();	
				invoicedb.get().addInvoiceProduct(productdb, cant, stockProductDB.getSalesprice());
				invoicetServiceImp.save(invoicedb.get());
				
			    modelAndView = new ModelAndView("addproductininvoice");
				modelAndView.addObject("invoice", invoicedb.get()); 
				modelAndView.addObject("stockproducts", stockdb.getStockproducts());
		}
		return modelAndView;
		
	}	
	
	

	
	
	
	
	
	
	
	/**************************************/
	/**CONTROLS PRODUCTS                  */
	/**************************************/

	
	
	@RequestMapping(path="/products", method={RequestMethod.POST, RequestMethod.GET})
	public String viewProducts(Model model){
		model.addAttribute("products", productServiceImp.findAll());	
		return "products";
	}
	
	@RequestMapping(path="/products/detail" , method={RequestMethod.POST, RequestMethod.GET})
	public String detailproduct(@RequestParam("id") long id , Model model){
		Product productdb = productServiceImp.findById(id).get();	
		model.addAttribute("productInfo", productdb);
		return "updateProducts";
	}	
		
	

	
	
	
	@RequestMapping(path="/products/update" , method={RequestMethod.POST, RequestMethod.GET})
	public String updateProductDB(@RequestParam("id") long id, @RequestParam("code") String code, @RequestParam("description") String description,
			@RequestParam("producer") String producer, @RequestParam("priceUnit") Float priceUnit , @RequestParam(name="typeAnimal", required=false) String typeAnimal,
			@RequestParam(name="dateExpiry", required=false) String dateExpiry){
		
		Product productdb = productServiceImp.findById(id).get();
		productdb.setCode(code);
		productdb.setDescription(description);
		productdb.setProducer(producer);
		productdb.setPriceUnit(priceUnit);
		productServiceImp.save(productdb);
		return "redirect:/admin/products";
	}

}








