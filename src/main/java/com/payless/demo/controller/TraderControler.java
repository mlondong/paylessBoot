package com.payless.demo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payless.demo.model.Address;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.services.CityServiceImp;
import com.payless.demo.services.ProductServiceImp;
import com.payless.demo.services.TraderServiceImp;
import com.payless.demo.services.ZoneServiceImp;
import com.payless.demo.util.Passgenerator;

@Controller
public class TraderControler {

	@Autowired
	private TraderServiceImp traderServiceImp;
	@Autowired
	private CityServiceImp cityServiceImp;
	@Autowired
	private ZoneServiceImp zoneServiceImp;
	@Autowired
	private ProductServiceImp productServiceImp;

	
	
	@GetMapping("/trader/viewprofile")
	public ModelAndView viewProfile() {
		ModelAndView modelAndView = new ModelAndView("t_profile");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		System.out.println(trader);
		modelAndView.addObject("trader", trader);
		modelAndView.addObject("city", cityServiceImp.findAll());
		// modelAndView.addObject("zone",zoneServiceImp.findAll());
		return modelAndView;
	}

	/** DELETE ADDRESS IN TRADER **/
	@RequestMapping(path = "/trader/delete-address", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteAddressTrader(@RequestParam(value = "indice", required = true) int indice) {
		ModelAndView modelAndView = new ModelAndView("t_profile");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		trader.getAddress().remove(indice);
		traderServiceImp.save(trader);
		return "redirect:/trader/viewprofile";
	}

	/** UPDATE TRADER */
	@RequestMapping(path = "/trader/upgeneral", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView updatingTrader(@RequestParam("cuit") Long cuit, @RequestParam("email") String email,
			@RequestParam("nameEnterprise") String nameEnterprise) {
		ModelAndView modelAndView = new ModelAndView("t_profile");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		modelAndView.addObject("trader", trader);

		try {
			trader.setCuit(cuit);
			trader.setEmail(email);
			trader.setNameEnterprise(nameEnterprise);
			traderServiceImp.save(trader);
			modelAndView = new ModelAndView("redirect:/trader/viewprofile");
		} catch (Exception e) {
			modelAndView.addObject("Error", "Error in data Information...");
		}

		return modelAndView;
	}

	/** UPDATE LOGIN IN TRADER */
	@RequestMapping(path = "/trader/uplogin", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView updatingLogin(@RequestParam("username") String username, @RequestParam("pass") String pass) {
		ModelAndView modelAndView = new ModelAndView("t_profile");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		modelAndView.addObject("trader", trader);

		try {
			Passgenerator p = new Passgenerator(4);
			trader.setPassword(p.generate(pass));
			trader.setName(username);
			traderServiceImp.save(trader);
			modelAndView = new ModelAndView("redirect:/trader/viewprofile");
		} catch (Exception e) {
			modelAndView.addObject("Error", "Error in data Information...");
		}

		return modelAndView;
	}

	/** PUT NEW ADDRESS IN TRADER */
	@RequestMapping(path = "/trader/newaddress", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView newAddressTrader(@RequestParam("description") String description,
			@RequestParam("city") Long city, @RequestParam("zone") Long zone) {
		ModelAndView modelAndView = new ModelAndView("t_profile");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		modelAndView.addObject("trader", trader);

		try {
			Address address = new Address();
			address.setCity(cityServiceImp.findById(city).get());
			address.setZone(zoneServiceImp.findById(zone).get());
			address.setDescription(description);
			trader.getAddress().add(address);
			traderServiceImp.save(trader);
			modelAndView = new ModelAndView("redirect:/trader/viewprofile");
		} catch (Exception e) {
			modelAndView.addObject("Error", "Error in data Information...");
		}

		return modelAndView;
	}

	@RequestMapping(path = "/trader/address/zones", method = { RequestMethod.POST, RequestMethod.GET })
	public String zonesInCity(@RequestParam("idcity") long idcity, Model model) {
		model.addAttribute("zones", zoneServiceImp.findAllZonesByIdCity(idcity));
		return "addaddress :: #zone";
	}

	/** PUT NEW ADDRESS IN TRADER */
	@RequestMapping(path = "/trader/myinvoices", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView myPurchases() {
		ModelAndView modelAndView = new ModelAndView("t_myinvoices");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		modelAndView.addObject("trader", trader);

		Collection<Invoice> invoices = trader.getInvoice();

		if (invoices.isEmpty()) {
			modelAndView.addObject("error", "Invoices not found!.");
		} else {
				modelAndView.addObject("invoices", invoices);
				modelAndView.addObject("consumers", trader.getMyconsumers());
		}
		return modelAndView;
	}

	@RequestMapping(path = "/trader/filter", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView filterMyConsumers(@RequestParam(value = "consumers", required = false ,defaultValue="0") long idConsumer) {
		ModelAndView modelAndView = new ModelAndView("t_myinvoices");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		modelAndView.addObject("trader", trader);
		List<Invoice> invoices = trader.getInvoiceByConsumer(idConsumer);
		modelAndView.addObject("invoices", invoices);
		modelAndView.addObject("consumers", trader.getMyconsumers());

		return modelAndView;
	}

	@RequestMapping(path="/trader/mystock")
	public ModelAndView myStock() {
		ModelAndView modelAndView = new ModelAndView("t_stock");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		modelAndView.addObject("trader", trader);
		modelAndView.addObject("stock", trader.getStock());
		Stock stock = trader.getStock();
		Set<Product> notProductsInMyStock =stock.getDiferencialWithMyStock(productServiceImp.findAll());
		modelAndView.addObject("products", notProductsInMyStock);
		
		return modelAndView;
	}	
	

	@RequestMapping(path="/trader/deleteproduct/{idprod}",  method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteProductInMyStock(@PathVariable("idprod") Long idprod) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		StockProducts stockProductStock =trader.getStock().findProductInOwnStock(idprod);
		trader.getStock().removeProduct(stockProductStock.getProduct());
		traderServiceImp.save(trader);
		return "redirect:/trader/mystock";
	}	
	
	@RequestMapping(path = "/invoice/getInvoiceProduct",  method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
    public Product getProductInMyStock(@RequestParam("idprod") Long idProd) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		StockProducts stockProductStock  =trader.getStock().findProductInOwnStock(idProd);
		return stockProductStock.getProduct();
    }
	
	
	@RequestMapping(path="/trader/updateprod",  method = {RequestMethod.POST, RequestMethod.GET})
	public String updateProductInMyStock(@RequestParam("update-idprod") Long idprod, @RequestParam("update-quantity") int quantity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		StockProducts stockProducInMyStock = trader.getStock().findProductInOwnStock(idprod);
		stockProducInMyStock.setQuantity(quantity);
		traderServiceImp.save(trader);
		return "redirect:/trader/mystock";
	}	
	
	
	@RequestMapping(path="/trader/addproductinmystock",  method = {RequestMethod.POST, RequestMethod.GET})
	public String addProductInMyStock(@RequestParam("idproduct") Long idprod, @RequestParam("quantity") int quantity, @RequestParam("salesprice") int price) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Trader trader = traderServiceImp.queryFindByUserName(auth.getName());
		Product product =productServiceImp.findById(idprod).get();
		trader.getStock().addProduct(product, quantity, price);
		traderServiceImp.save(trader);
		return "redirect:/trader/mystock";
	}	

	
}
