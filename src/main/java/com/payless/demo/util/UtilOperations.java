package com.payless.demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;

public class UtilOperations {

	
	public static Map<String, List<StockProducts>> filterByProductInZone(List<Trader> traders, String descripProductToFind) throws HibernateException{
		Map<String, List<StockProducts>> filters = new HashMap<String, List<StockProducts>>();
		
		for(Trader trader : traders){
			Collection <StockProducts> stp = trader.getStock().getStockproducts();
			List<StockProducts> selected = new ArrayList<>();
			for(StockProducts stockProduct : stp){
					System.out.println("stockProduct " + descripProductToFind +" - " +stockProduct.getProduct().getDescription());
				 	if(stockProduct.getProduct().getDescription().equals(descripProductToFind.trim())){
						System.out.println("selected " + stockProduct.getProduct().getDescription());
				 		selected.add(stockProduct);
				 	}
			 }
			filters.put(trader.getNameEnterprise(),selected);
		}
		
		
		return filters;
	}
	
}
