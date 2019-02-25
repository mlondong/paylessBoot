package com.payless.demo.restfull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payless.demo.model.CareProduct;
import com.payless.demo.model.MeatProduct;
import com.payless.demo.model.MilkProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.CareProductRepository;
import com.payless.demo.repositories.MeatProductRepository;
import com.payless.demo.repositories.MilkProductRepository;
import com.payless.demo.repositories.ProductRepository;
import com.payless.demo.repositories.BaseProductRepository;
import com.payless.demo.repositories.StockRepository;
import com.payless.demo.repositories.TraderRepository;



/**
 * THIS SERVICE IS FOR STOCK AND BEHAVIOR WITH TRADER:
 * -ADD STOCK I TRADER
 * -DELETE STOCK IN TRADER
 * -ADD PRODUCTS IN STOCK FOR TRADER
 * -REMOVE PRODUCTS IN STOCK FOR TRADER
 * -DELETE ALL PRODUCRS IN STOCK FOR TRADER
 * - UPDATE QUANTITY PRODUCTS IN STOCK
 * */



@RestController
@RequestMapping(path="/paylessboot") 
public class StockService {

	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private StockRepository stockRepository;


	@Autowired
	private ProductRepository productRepository;




	/**POSTMAPING FOR CREATE STOCK FOR TRADER*/
	@PostMapping(path="/trader/addstock/{id}")
	public Stock addStock(@PathVariable long id){
		Trader traderdb = traderRepository.findById(id).get();
		Stock stock = new  Stock();
		stock.setTrader(traderdb);
		return stockRepository.save(stock);
	}



	/***DELETE MAPING AN OBJECT*/
	@DeleteMapping(path="/trader/delete/stock/{id}")
	public boolean deleteStock(@PathVariable long id){
		stockRepository.deleteById(id);
		return true;
	}


	@PutMapping(path="/stock/addproduct/trader/{id}/prod/{prod}/cantidad/{canti}")
	public boolean addProduct(@PathVariable("id") long id ,@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){
		Trader traderdb = traderRepository.findById(id).get();

		if(productRepository.existsById(idprod)){
			System.out.println("existe");
			Product mt =  productRepository.findById(idprod).get();
			traderdb.getStock().addProduct(mt, cantidad);
		}

		traderRepository.save(traderdb);
		return true;
	}



	@DeleteMapping("/stock/removeproduct/trader/{id}/prod/{prod}")
	public boolean removeProduct(@PathVariable("id") long id ,@PathVariable("prod") long idprod){
		Trader traderdb = traderRepository.findById(id).get();
		Collection <StockProducts> stockProducts;

		if(productRepository.existsById(idprod)){
			System.out.println("existe");
			Product mt =  productRepository.findById(idprod).get();
			stockProducts =   traderdb.getStock().getStockproducts();
			stockProducts.removeIf((StockProducts st) -> st.getProduct().equals(mt)  );
		}

		traderRepository.save(traderdb);

		return true;
	}


	@DeleteMapping("/stock/removeallproducts/trader/{id}")
	public boolean deleteAll(@PathVariable("id") long id ){
		Trader traderdb = traderRepository.findById(id).get();
		traderdb.getStock().getStockproducts().clear(); 
		traderRepository.save(traderdb);
		return true;
	}



	@PutMapping("/stock/updateproduct/trader/{id}/prod/{prod}/cantidad/{canti}")
	public boolean updateQuantityProduct(@PathVariable("id") long id ,@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){
		Trader traderdb = traderRepository.findById(id).get();
		List <StockProducts> stockProducts;
		boolean flag= false;

		if(cantidad!=0){
			if(productRepository.existsById(idprod)){
				stockProducts = (List<StockProducts>) traderdb.getStock().getStockproducts();
				for(int i=0; i< stockProducts.size();i++){
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
