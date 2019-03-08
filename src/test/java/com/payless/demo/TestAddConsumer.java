package com.payless.demo;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payless.demo.model.CareProduct;
import com.payless.demo.model.City;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.MeatProduct;
import com.payless.demo.model.MilkProduct;
import com.payless.demo.model.Zone;
import com.payless.demo.repositories.CareProductRepository;
import com.payless.demo.repositories.CityRepository;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.MeatProductRepository;
import com.payless.demo.repositories.MilkProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAddConsumer {

	
	@Autowired
	private ConsumerRepository consumerRepository;

	
	
	@Test
	public void createConsumer(){
	
		Consumer n = new Consumer("Consumer", "654s", 987987, "mauricio", "Londono"); 
		consumerRepository.save(n);
		
	}

	
	
	
	
	
	

	
	
}
