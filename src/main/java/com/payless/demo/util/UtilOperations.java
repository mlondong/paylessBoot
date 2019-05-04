package com.payless.demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;

import com.payless.demo.model.Product;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;

public class UtilOperations {


	public static List<StockProducts> filterByProductInZone(List<Trader> traders, List<Product> products) throws HibernateException{

		List<StockProducts>  listStockProducts= new ArrayList<StockProducts>();
		StockProducts stockProducts =null;
		for(Trader trader : traders){
			List<StockProducts> st = (List<StockProducts>) trader.getStock().getStockproducts();
			for(StockProducts stp :st ){
				//listStockProducts.add(products.stream().filter(d->d.getCode().equals(stp.getProduct().getCode())).collect(Collectors.toList()));
			}
		}	



		return listStockProducts;
	}

}
