package com.payless.demo.services;

import java.util.List;

import com.payless.demo.model.Trader;

public interface TraderService {
	
	Trader save(Trader trader);
	List<Trader> searchByCuit(long cuit);

	Trader getTrader(Long id);
	Trader editTrader(Trader trader);
	
	void deleteTrader(Trader trader);
	void deleteTrader(Long id);
	
	List<Trader> getAllTraders(int pageNumber, int pageSiz);
	List<Trader> getAllTraders();
	
}
