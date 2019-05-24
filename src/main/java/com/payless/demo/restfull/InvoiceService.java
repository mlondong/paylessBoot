package com.payless.demo.restfull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.Product;
import com.payless.demo.model.Rating;
import com.payless.demo.model.Trader;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.InvoiceRepository;
import com.payless.demo.repositories.ProductRepository;
import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.services.InvoiceServiceImp;

/**
 * THIS SERVICE INVOICE FOR TRADER:
 * -ADD INVOICE
 * -REMOVE INVOICE IN A TRADER
 * -DELETE ALL INVOICE IN A TRADER
 * -ADD PRODUCTS IN A INVOICE OD A TRADER
 * -DELETE A PRODUCT AN INVOICE
 * -UPDATE A QUANTITY IN A INVOICEPRODUCT
 * */


@RestController
@RequestMapping(path="/services") 
public class InvoiceService {


	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ConsumerRepository consumerRepository;
	
	@Autowired
	private  InvoiceServiceImp invoiceServiceImp;



	@GetMapping(path = "/invoice/commentproduct", produces={MediaType.APPLICATION_JSON_VALUE})
	public Collection<Rating> getCommenstInProduct(@RequestParam("idProduct") Long idProd, @RequestParam("idInvoice") Long idInvoice){
		Invoice invoice = invoiceServiceImp.findById(idInvoice).get();
		InvoiceProduct invP = invoice.getInvoiceProductWithProduct(idProd);
		return invP.getRatings();
	}
	
	
	
	@GetMapping(path = "/invoice/getinvoice",produces={MediaType.APPLICATION_JSON_VALUE})
	public Invoice myPurchases(@RequestParam("numInvoice") Long numInvoice){
		Optional<Invoice>  invoice = invoiceServiceImp.findInvoiceDetails(numInvoice);
		Invoice result=null;	
		if(invoice.isPresent()){
			result=invoice.get();
		}
		System.out.println(result);
		return result;
	}

	/*ojo esta es para traer los comentarios aun no eta implementada en c_mypurchase*/
	@GetMapping(path = "/invoice/getinvoicecomments",produces={MediaType.APPLICATION_JSON_VALUE})
	public List<InvoiceProduct> getInvoiceComments(@RequestParam("numInvoice") Long numInvoice){
		Optional<Invoice>  invoice = invoiceServiceImp.findInvoiceDetails(numInvoice);
		List<InvoiceProduct> invoiceProduct = (List<InvoiceProduct>) invoice.get().getProducts();
		return invoiceProduct;
	}

	
	
	
	
	
	@PostMapping(path="/invoice/addinvoice/trader/{idtrader}/consumer/{idconsumer}")
	public Invoice addInvoice(@PathVariable("idtrader") long idTrader , @PathVariable("idconsumer") long idConsumer ){
		
		Trader traderdb = traderRepository.findById(idTrader).get();
		Consumer consumerdb = consumerRepository.findById(idConsumer).get();
		Invoice invoice= new  Invoice(traderdb,consumerdb);
		return invoiceRepository.save(invoice);
	}

	@DeleteMapping(path="/invoice/deleteinvoice/invoice/{id}")
	public boolean deleteInvoice(@PathVariable("id") long id){
		invoiceRepository.deleteById(id);
		return true;
	}

	@DeleteMapping(path="/invoice/deleteinvoiceall/trader/{id}")
	public boolean deleteInvoiceAll(@PathVariable("id") long id){
		Trader traderdb = traderRepository.findById(id).get();
		traderdb.getInvoice().clear();
		traderRepository.save(traderdb);
		return true;
	}


	public boolean isIdInListInvoices(List<Invoice> list, long id){
		boolean flag=false;
		for (int i=0 ; i<list.size() ; i++ ){
			if(list.get(i).getId() == id){
				flag=true;
				break;
			}
		}	

		return flag;
	}



	@PostMapping(path="/invoice/addproduct/trader/{id}/invoice/{idinvoice}/prod/{prod}/cantidad/{canti}")
	public boolean addProductInvoice(@PathVariable("id") long id, @PathVariable("idinvoice") long idinvoice ,
			@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){

		boolean flag=false;

		if(cantidad!=0) {
			Trader traderdb = traderRepository.findById(id).get();
			List<Invoice> listInvoices = (List<Invoice>) traderdb.getInvoice();

			if(isIdInListInvoices(listInvoices, idinvoice) == true){
				Invoice dbinvoice = invoiceRepository.findById(idinvoice).get();
				dbinvoice.setTrader(traderdb);
				Product dbproduct = productRepository.findById(idprod).get();
				dbinvoice.addInvoiceProduct(dbproduct, cantidad);				
				invoiceRepository.save(dbinvoice);
				flag=true;
			}
		}

		return flag;

	}

	@DeleteMapping(path="/invoice/deleteinvoice/{idinvoice}/product/{idprod}")
	public boolean removeProductInvoice(@PathVariable("id") long id, @PathVariable("idprod") long idprod){

		boolean flag=false;
		Collection<InvoiceProduct>  invoiceProducDB;

		if(invoiceRepository.existsById(id)){

			Product pr =  productRepository.findById(idprod).get();
			Invoice invoicedb = invoiceRepository.findById(id).get();
			invoiceProducDB = invoicedb.getProducts();
			invoiceProducDB.removeIf((InvoiceProduct ip) -> ip.getPoduct().equals(pr));
			invoiceRepository.save(invoicedb);
			flag=true;

		}


		return flag;

	}



	@PostMapping(path="/invoice/udproduct/trader/{id}/invoice/{idinvoice}/prod/{prod}/cantidad/{canti}")
	public boolean updateQuantityProduct(@PathVariable("id") long id, @PathVariable("idinvoice") long idinvoice ,
			@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){


		boolean flag= false;

		if(cantidad!=0) {

			Trader traderdb = traderRepository.findById(id).get();
			List<Invoice> listInvoices = (List<Invoice>) traderdb.getInvoice();

			if(isIdInListInvoices(listInvoices, idinvoice) == true){

					Invoice invoidedb  = invoiceRepository.findById(idinvoice).get();
					List<InvoiceProduct>  invoiceProducDB = (List<InvoiceProduct>) invoidedb.getProducts();
					for(int i=0; i< invoiceProducDB.size();i++){
						if(invoiceProducDB.get(i).getPoduct().getId() == idprod){
					
							invoiceProducDB.get(i).setQuantity((int)cantidad);
							invoiceRepository.save(invoidedb);
							flag=true;
							break;
						}	
					}
			
			}


		}

		return flag;
	}





}
