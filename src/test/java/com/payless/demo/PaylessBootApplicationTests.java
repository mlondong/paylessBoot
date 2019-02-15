package com.payless.demo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payless.demo.repositories.ConsumerRepository;

import junit.framework.Assert;

import com.payless.demo.model.Consumer;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PaylessBootApplicationTests {

	
	@Autowired
	private ConsumerRepository consumerRepository;
	
	
	@Test
	public void crearUser(){
		Consumer c = new Consumer("Boot", "12", 10, "SpreingBot", "2.1.2");
		consumerRepository.save(c);
		assertNotNull(consumerRepository.findByDni(10L));
	}
	
	
	@Test
	public void findAllUsers() {
		Iterable <Consumer> it = consumerRepository.findAll();
		assertNotNull(it);
	}

	
		
	@Test
	public void getByDni(){
	Consumer c1= consumerRepository.findByDni(10L);
	assertNotNull(c1);
	}
	
	
}

