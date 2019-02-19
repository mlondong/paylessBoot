package com.payless.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.Product;
import com.payless.demo.model.Purchase;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.PurchaseRepository;
import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.ProductRepository;;


/** ACTIONS FOR PURCHASE IN CONSUMER
 * - GET PURCHASE
 * - GET CANASTA FAMILIAR
 * - UPDATE PRODUCTS IN A PURCHASE
*/

@RestController
@RequestMapping(path="/paylessboot") 
public class PurchaseService {

	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ProductRepository productRepository;

	
	@Autowired
	private ConsumerRepository consumerRepository;


	@Autowired
	private TraderRepository tRepository;

	
	/***CREATE A PURCHASE*/
	@PostMapping(path="/consumer/addpurchase/{id}")
	public boolean createPurchaseConsumer(@PathVariable("id") long id){
		boolean flag=false;

		if(consumerRepository.existsById(id)){
			Consumer consumerdb = consumerRepository.findById(id).get();
			Purchase purchase = new Purchase();
			purchase.setConsumer(consumerdb);
			consumerdb.addPurchase(purchase);
			consumerRepository.save(consumerdb);
			flag=true;
		}
		return flag;
	}


	public boolean isIdInListPurchase(List<Purchase> list, long id){
		
		boolean flag=false;
		for (int i=0 ; i<list.size() ; i++ ){
			if(list.get(i).getId() == id){
				flag=true;
				break;
			}
		}

		return flag;
	}

	
	
	
	@PostMapping(path="/purchase/consumer/{id}/codpurchase/{cod}/prod/{prod}/cantidad/{cant}")
	public boolean addProductInPurchase(@PathVariable("id") long id, @PathVariable("cod") long idpurchase, 
			@PathVariable("prod") long idprod , @PathVariable("cant") int cantidad){
		
		boolean flag=false;
		
	
		if(cantidad!=0) {
			Consumer consumerdb = consumerRepository.findById(id).get();
			List<Purchase> listPurchase = (List<Purchase>) consumerdb.getPurchase();
			
			if(isIdInListPurchase(listPurchase, idpurchase) == true){
				Purchase dbpurchase = purchaseRepository.findById(idpurchase).get();
				dbpurchase.setConsumer(consumerdb);
				
				Product dbproduct = productRepository.findById(idprod).get();
				dbpurchase.addProductInPurchaseProduct(dbproduct, cantidad);
				purchaseRepository.save(dbpurchase);
				flag=true;
			}

		}	
		
		return flag;
	}
	

	
	
	
	
}
