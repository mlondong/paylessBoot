package com.payless.demo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Stock;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.repositories.UsserRepository;


@Service
public class TraderServiceImp implements TraderService{
	
	
	@Autowired
	private TraderRepository traderRepository;
	

	
	
	@Override
	public Trader searchByCuit(long cuit) {
		return traderRepository.findByCuit(cuit);
	}

	@Override
	public Trader save(Trader trader) {
		return traderRepository.save(trader);
	}

		
	@Override
	public Trader getTrader(Long id) {
		
		if(traderRepository.findById(id).isPresent()){
			System.out.println(traderRepository.findById(id));
			return traderRepository.findById(id).get();
		}else{
				return null;
		}

	}

	@Override
	public Trader editTrader(Trader trader) {
		return traderRepository.save(trader);
	}

	@Override
	public void deleteTrader(Trader trader) {
	}

	@Override
	public void deleteTrader(Long id) {
		traderRepository.deleteById(id);
	}

	@Override
	public List<Trader> getAllTraders(int pageNumber, int pageSiz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trader> getAllTraders() {
		Iterator<Trader> traders = traderRepository.findAll().iterator();
		List<Trader> list = new ArrayList<>();
		
		while(traders.hasNext()){
			list.add(traders.next());
		}
			
		return list;
	}



}
