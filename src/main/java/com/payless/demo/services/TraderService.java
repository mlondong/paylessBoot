package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.payless.demo.model.Stock;
import com.payless.demo.model.Trader;

public interface TraderService {
	
	Trader save(Trader trader);
	Trader searchByCuit(long cuit);
	Trader getTrader(Long id);
	Trader editTrader(Trader trader);
	void deleteTrader(Long id);
	
	List<Trader> getAllTraders(int pageNumber, int pageSiz);
	List<Trader> getAllTraders();
	
	
	Trader queryFindByUserName(String firstName);
	List<Trader> queryByParametersCityZone(long zone, long city, List<Long> idsProducts);
	
}
