package com.payless.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payless.demo.repositories.ConsumerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAddConsumer {

	
	@Autowired
	private ConsumerRepository consumerRepository;

	
	
	@Test
	public void createConsumer(){
	
		//Consumer n = new Consumer("Consumer", "654s", 987987, "mauricio", "Londono"); 
		//consumerRepository.save(n);
		
	}

	
	
	
	
	
	

	
	
}
