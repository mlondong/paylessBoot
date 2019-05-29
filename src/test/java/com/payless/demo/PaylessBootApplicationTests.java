package com.payless.demo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payless.demo.model.City;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Zone;
import com.payless.demo.repositories.CareProductRepository;
import com.payless.demo.repositories.CityRepository;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.MeatProductRepository;
import com.payless.demo.repositories.MilkProductRepository;
import com.payless.demo.repositories.TraderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaylessBootApplicationTests {

	
	@Autowired
	private MeatProductRepository mtpRepository;
	
	@Autowired
	private MilkProductRepository milkpRepository;
	
	
	@Autowired
	private CareProductRepository cpRepository;
	
	
	@Autowired
	private ConsumerRepository consumerRepository;

	
	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private CityRepository cityRepository;

	
	
	
	
	@Test
	public void crearCity(){
		/*City c = new City("Buenos Aires");
		
		Zone zone1 = new Zone("Recoleta");
		zone1.setCiti(c);
		
		Zone zone2 = new Zone("Palermo");
		zone2.setCiti(c);
		
		c.addZone(zone1);
		c.addZone(zone2);
		
		cityRepository.save(c);
	*/
	}
	
	@Test
	public void crearUser(){
		//Consumer c = new Consumer("Boot", "12", 10, "SpreingBot", "2.1.2");
		//consumerRepository.save(c);
		//assertNotNull(consumerRepository.findByDni(10L));
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

	
	
	/*@Test
	public void generateUsers(){
		Trader trader1 = new Trader("Trader1", "111111", 987L);
		traderRepository.save(trader1);
		assertNotNull(traderRepository.findByCuit(987L));
		
		Trader trader2 = new Trader("Trader2", "2222", 654L);
		traderRepository.save(trader2);
		assertNotNull(traderRepository.findByCuit(654L));

		
		Consumer consumer1 = new Consumer("Consumer1", "9999", 123L, "Fabio", "londono");
		Consumer consumer2 = new Consumer("Consumer2", "888", 456L, "Martha", "Garcia");
		consumerRepository.save(consumer1);
		consumerRepository.save(consumer2);

		assertNotNull(consumerRepository.findByDni(123L));
		assertNotNull(consumerRepository.findByDni(456L));

	}		
	
	
	@Test
	public void generateProducts(){
	
	
		MeatProduct mp1 = new MeatProduct("MP1", "Vaca", "Angus",  10, "Vaca", new Date() );
		MeatProduct mp2 = new MeatProduct("MP2", "Pollo", "Angus",  10, "Pollo", new Date() );
		MeatProduct mp3 = new MeatProduct("MP3", "Cerdo", "Cerdo",  10, "Cerdo", new Date() );
		MeatProduct mp4 = new MeatProduct("MP4", "Pez", "Pez",  10, "Pez", new Date() );

		mtpRepository.save(mp1);
		mtpRepository.save(mp2);
		mtpRepository.save(mp3);
		mtpRepository.save(mp4);

		MilkProduct mp5 =  new MilkProduct("MP5", "Leche Entera", "Celema",  50 );
		MilkProduct mp6 =  new MilkProduct("MP6", "Quesillo", "Celema",  50 );
		MilkProduct mp7 =  new MilkProduct("MP7", "Yogurth", "Celema",  50 );

		milkpRepository.save(mp5);
		milkpRepository.save(mp6);
		milkpRepository.save(mp7);

		CareProduct mp8 = new CareProduct("MP8", "Jabon de Manos", "Dove",  100);
		CareProduct mp9 = new CareProduct("MP9", "Shampoo", "Dove",  100);
		
		cpRepository.save(mp8);
		cpRepository.save(mp9);
		
	}*/
	
	
}

