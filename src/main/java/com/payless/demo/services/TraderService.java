package com.payless.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.TraderRepository;

@RestController
@RequestMapping(path="/paylessboot") 
public class TraderService {

	
	@Autowired
	private TraderRepository traderRepository;

	
	/**GET ALL TRADER CON PAGE AND LIMIT**/
	@GetMapping()
	public String getConsumers(@RequestParam(value="page") int page , @RequestParam(value="limit") int limit){
		return "page " + page + "Limit " + limit;
	}

	
	/**GETMAPING FOR GET CONSUMER*/
	@GetMapping(path="/trader")
	public Iterable<Trader> findAll(){
		return traderRepository.findAll();
	}

	@GetMapping(path="/trader/{id}")
	public Trader show(@PathVariable long id){
		return traderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No encntrado con id " + id ));
	}


	/**POSTMAPING FOR CREATE CONSUMER*/
	@PostMapping(path="/trader/put")
	public Trader create(@RequestBody Trader requestConsumer){
		return traderRepository.save(requestConsumer);
	}
	
	
	/***PUTMMAPING FOR UPDATE*/
	@PutMapping(path="/trader/update/{id}")
	public Trader updateConsumer(@RequestBody Trader trader , @PathVariable("id") long id ){
		
		Optional <Trader> opt = traderRepository.findById(id);
		Trader storeTrader = opt.get();
		storeTrader.setName(trader.getName());
		storeTrader.setPassword(trader.getPassword());
		storeTrader.setState(trader.isState());
		
		storeTrader.setCuit(trader.getCuit());
		
		traderRepository.save(storeTrader);
		
		return storeTrader;
		
	} 

	/***DELETE MAPING AN OBJECT*/
	@DeleteMapping(path="/trader/delete/{id}")
    public boolean delete(@PathVariable long id){
		traderRepository.deleteById(id);
        return true;
    }
	

}
