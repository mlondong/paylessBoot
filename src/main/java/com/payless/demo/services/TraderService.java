package com.payless.demo.services;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.payless.demo.model.Trader;
import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.model.Address;


/**
 * THIS SERVICE IS FOR TRADER IN GENERAL:
 * -FINDALL TRADERS
 * -GET A TRADER
 * -ADD A NEW TRADER
 * -UPDATE ADDRESS IN TRADER
 * -UPDATE TRADER
 * -DELETE TRADER
 * -DELETE ADDRESS
 * */


@RestController
@RequestMapping(path="/paylessboot") 
public class TraderService {


	@Autowired
	private TraderRepository traderRepository;



	/**GET ALL TRADER CON PAGE AND LIMIT**/
	/*@GetMapping()
	public String getConsumers(@RequestParam(value="page") int page , @RequestParam(value="limit") int limit){
		return "page " + page + "Limit " + limit;
	}*/


	/**GETMAPING FOR GET CONSUMER*/
	@GetMapping(path="/trader")
	public Iterable<Trader> findAll(){
		return traderRepository.findAll();
	}


	@GetMapping(path="/trader/{id}")
	public Trader getTrader(@PathVariable long id){
		
		System.out.println(traderRepository.findById(id));
		return traderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No encntrado con id " + id ));
	}



	/**POSTMAPING FOR CREATE CONSUMER*/
	@PostMapping(path="/trader/put")
	public Trader addTrader(@RequestBody Trader requestConsumer){
		return traderRepository.save(requestConsumer);
	}


	



	/*********************************************************************************************************************************************/
	/**POSTMAPPING ADDRESS ADRESS SECUENCIALMENTE
	 * SE DECIDE DEJAR ADDRESS ACA EN EL SERVICE DE TRADER DADO QUE ES UNA CLASE EMBEDDED Y NO UNA ENTIDAD
	 * SE ENVIAN DE ESTAFORMA  {"description": "Calle X", "city": 1, "zona": 4 }, { "description": "Calle y", "city": 1, "zona": 4 }*/
	@PutMapping(path="/trader/add_adresss/{id}")
	public String  updateAdress(@RequestBody Address address, @PathVariable("id") long id){
		System.out.println(address);
		Trader traderdb = traderRepository.findById(id).get();
		traderdb.getAddress().add(address);
		traderRepository.save(traderdb);
		return "Actualizado.";
	}


	
	
	

	/***PUTMMAPING FOR UPDATE*/
	@PutMapping(path="/trader/update/{id}")
	public Trader updateTrader(@RequestBody Trader trader , @PathVariable("id") long id ){

		Optional <Trader> opt = traderRepository.findById(id);
		Trader storeTrader = opt.get();
		storeTrader.setName(trader.getName());
		storeTrader.setPassword(trader.getPassword());
		storeTrader.setState(trader.isState());

		storeTrader.setCuit(trader.getCuit());

		traderRepository.save(storeTrader);

		return storeTrader;

	} 

	/*********************************************************************************************************************************************/
	/***DELETE MAPING AN OBJECT*/
	@DeleteMapping(path="/trader/delete/{id}")
	public boolean deleteTrader(@PathVariable long id){
		traderRepository.deleteById(id);
		return true;
	}

	
	/***DELETE MAPING AN OBJECT*/
	@PutMapping(path="/trader/delete/address/id={id}&index={index}")
	public boolean deleteAddress(@PathVariable("index") int index, @PathVariable("id") long id){
		Trader bdtrader = traderRepository.findById(id).get();
		bdtrader.getAddress().remove(index);
		traderRepository.save(bdtrader);
		return true;
	}

	
}
