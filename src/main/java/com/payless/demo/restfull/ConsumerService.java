package com.payless.demo.restfull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Rating;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.RatingRepository;
import com.payless.demo.services.ConsumerServiceImp;
import com.payless.demo.services.InvoiceServiceImp;
//import com.payless.demo.repositories.PurchaseRepository;
import com.payless.demo.services.ProductServiceImp;
import com.payless.demo.services.RatingServiceImp;
import com.payless.demo.services.TraderServiceImp;


/**
 * @RequestMapping use GET
 * @RequestParam
 * @RequestBody - to create
 * 
 * @Controller
 * @ResponseBody desde posman se envia el json con todos los campos de user y consumer
 * 
 * THIS SERVICE IS FOR CONSUMER USERS:
 * - GET CONSUMER INFORMATION
 * - UPDATE CONSUMER
 * - DELETE CONSUMER
 * */


@RestController
@RequestMapping(path="/services")
public class ConsumerService {



	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private  ProductServiceImp productServiceImp;

	@Autowired
	private  TraderServiceImp traderServiceImp;

	@Autowired
	private  ConsumerServiceImp consumerServiceImp;

	@Autowired
	private RatingServiceImp ratingServiceImp;

	@Autowired
	private InvoiceServiceImp invoiceServiceImp;



	/**GETMAPING FOR GET CONSUMER*/
	@GetMapping(path="/consumer")
	public Iterable<Consumer> findAll(){
		return consumerRepository.findAll();
	}

	@GetMapping(path="/consumer/{id}")
	public Consumer show(@PathVariable long id){
		return consumerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No encntrado con id " + id ));
	}


	/**POSTMAPING FOR CREATE CONSUMER*/
	@PostMapping(path="/consumer/put")
	public Consumer create(@RequestBody Consumer requestConsumer){
		return consumerRepository.save(requestConsumer);
	}


	/***PUTMMAPING FOR UPDATE*/
	@PutMapping(path="/consumer/update/{id}")
	public Consumer updateConsumer(@RequestBody Consumer consumer , @PathVariable("id") long id ){

		Optional <Consumer> opt = consumerRepository.findById(id);
		Consumer storeConsumer = opt.get();
		storeConsumer.setName(consumer.getName());
		storeConsumer.setPassword(consumer.getPassword());
		storeConsumer.setState(consumer.isState());

		storeConsumer.setDni(consumer.getDni());
		storeConsumer.setFirstName(consumer.getFirstName());
		storeConsumer.setLastName(consumer.getLastName());

		consumerRepository.save(storeConsumer);

		return storeConsumer;

	} 

	/***DELETE MAPING AN OBJECT*/
	@DeleteMapping(path="/consumer/delete/{id}")
	public boolean delete(@PathVariable long id){
		consumerRepository.deleteById(id);
		return true;
	}



	/***FIND ALL PRODUCTS BY DESCRIPTION IN ZONE CONSUMER*/
	@GetMapping(path = "/consumer/resulproducts",produces={MediaType.APPLICATION_JSON_VALUE})
	public List<StockProducts> resultSearchProducts(@RequestParam("description") String desc ,	@RequestParam("zone") int zone,	@RequestParam("city") int city){

		/*TRADERS IN ZONE OF CONSUMER*/
		List<Trader> traders = traderServiceImp.queryByParametersCityZone(zone, city);
		/*RESULT OF PRODUCTS FIND IT*/
		List<Product> findProducts = productServiceImp.findByContainDescription(desc);
		/*MATCH BETWEEN PRODUCTS AND TRADERS IN ZONE*/
		List<StockProducts> matchStockProducts= new ArrayList<StockProducts>(); 
		for(Trader trader: traders){
			for(Product product: findProducts){
				matchStockProducts.add(trader.getStock().findProductInOwnStock(product));
			}

		}

		return matchStockProducts;
	}


	/**PUT SCORE FOR PRODUCT IN INVOICE BY CONSUMER**/
	@GetMapping(path = "/consumer/addscoreinproduct/{idprod}/invoice/{idinvoice}/score/{score}/comment/{comment}")
	public String addScoreInProduct(@PathVariable Long idprod, 
								   @PathVariable Long idinvoice,
								   @PathVariable Integer score,  
								   @PathVariable String comment ){
		
		Invoice invoice = invoiceServiceImp.findById(idinvoice).get();
		InvoiceProduct invoiceProduct =   invoice.getInvoiceProductWithProduct(idprod);
		Rating ratingdb = new Rating(invoiceProduct, score, comment);
		ratingServiceImp.save(ratingdb);

		return comment;
	
	}


	/**GET ALL SCORES IN MY INVOICES BY CONSUMER**/
	@GetMapping(path = "/consumer/getallmyratings")
	public Collection<Invoice> getAllMyRatings(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Consumer consumer = consumerServiceImp.queryFindByUserName(auth.getName());
	    return new LinkedList<>(consumer.getInvoices()); 
	} 
			

}
