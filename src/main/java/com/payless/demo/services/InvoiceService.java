package com.payless.demo.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payless.demo.model.Invoice;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.repositories.InvoiceRepository;
import com.payless.demo.repositories.ProductRepository;
import com.payless.demo.repositories.TraderRepository;

/**
 * THIS SERVICE INVOICE FOR TRADER:
 * -ADD INVOICE
 * -REMOVE INVOICE IN A TRADER
 * -DELETE ALL INVOICE IN A TRADER
 * -ADD PRODUCTS IN A INVOICE OD A TRADER
 * - DELETE A PRODUCT AN INVOICE
 * -UPDATE A QUANTITY IN A INVOICEPRODUCT
 * */


@RestController
@RequestMapping(path="/paylessboot") 
public class InvoiceService {


	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ProductRepository productRepository;



	@PostMapping(path="/invoice/addinvoice/{id}")
	public Invoice addInvoice(@PathVariable("id") long id){
		Trader traderdb = traderRepository.findById(id).get();
		Invoice invoice= new  Invoice();
		invoice.setTrader(traderdb);
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


	@PostMapping(path="/invoice/addproduct/trader/{id}/invoice/{idinvoice}/prod/{prod}/cantidad/{canti}")
	public boolean addProductInvoice(@PathVariable("id") long id, @PathVariable("idinvoice") long idinvoice ,@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){

		boolean flag=false;

		if(invoiceRepository.existsById(idinvoice)){

			Trader traderdb = traderRepository.findById(id).get();
			Invoice dbinvoice = invoiceRepository.findById(idinvoice).get();
			dbinvoice.setTrader(traderdb);
			Product dbproduct = productRepository.findById(idprod).get();
			dbinvoice.addInvoiceProduct(dbproduct, cantidad);				
			invoiceRepository.save(dbinvoice);
			flag=true;

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



	@PutMapping("/invoice/updateinvoice/{id}/product/{idprod}/cantidad/{cant}")
	public boolean updateQuantityProduct(@PathVariable("id") long id ,@PathVariable("idprod") long prod, @PathVariable("cant") int cantidad){
	
		InvoiceProduct  invoiceProducDB;

		boolean flag= false;

		if(cantidad!=0) {
			
			if(productRepository.existsById(prod)){
				Invoice invoidedb  = invoiceRepository.findById(id).get();
				int size = invoidedb.getProducts().size();
				
				for(int i=0; i< size;i++){
					if(stockProducts.get(i).getProduct().getId()==idprod){
						stockProducts.get(i).setQuantity(cantidad);
						flag=true;
						traderRepository.save(traderdb);
					}	
				}
			}

			
		}

		return flag;
	}

	



}
