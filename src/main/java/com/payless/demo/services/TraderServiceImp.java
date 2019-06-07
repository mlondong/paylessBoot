package com.payless.demo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Trader;
import com.payless.demo.repositories.TraderRepository;


@Service
public class TraderServiceImp implements TraderService{
	
	
	
	@Autowired
	private TraderRepository traderRepository;
	
	
	@Override
	public Trader queryFindByUserName(String firstName) {
		return traderRepository.queryFindByUserName(firstName);
	}

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

	@Override
	public List<Trader> queryByParametersCityZone(long zone, long city, List<Long> idsProducts) {
		System.out.println("llego en metodo " + zone + city);
		return traderRepository.queryByParametersCityZone(zone, city, idsProducts);
	}






}
