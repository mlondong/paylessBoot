package com.payless.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payless.demo.model.Address;
import com.payless.demo.model.CareProduct;
import com.payless.demo.model.City;
import com.payless.demo.model.MeatProduct;
import com.payless.demo.model.MilkProduct;
import com.payless.demo.model.Role;
import com.payless.demo.model.Trader;
import com.payless.demo.model.Zone;
import com.payless.demo.repositories.CareProductRepository;
import com.payless.demo.repositories.CityRepository;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.MeatProductRepository;
import com.payless.demo.repositories.MilkProductRepository;
import com.payless.demo.repositories.RoleRepository;
import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.util.Passgenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPoblateData {

	
	@Autowired
	private CityRepository cityRepository;

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
	private RoleRepository roleRepository;

	
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
		
	}

	

	
	@Test
	public void crearCity(){
	/*	City baires = new City("Buenos Aires");
		Zone baires_zone1 = new Zone("Recoleta");
		Zone baires_zone2= new Zone("Palermo");
		Zone baires_zone3 = new Zone("Boedo");
		Zone baires_zone4= new Zone("Chacarita");
		Zone baires_zone5 = new Zone("Caballito");
		Zone baires_zone6 = new Zone("San Telmo");
			
		baires_zone1.setCiti(baires);
		baires_zone2.setCiti(baires);
		baires_zone3.setCiti(baires);
		baires_zone4.setCiti(baires);
		baires_zone5.setCiti(baires);
		baires_zone6.setCiti(baires);
		
		baires.addZone(baires_zone1);
		baires.addZone(baires_zone2);
		baires.addZone(baires_zone3);
		baires.addZone(baires_zone4);
		baires.addZone(baires_zone5);
		baires.addZone(baires_zone6);
		
		cityRepository.save(baires);

		

		City plata= new City("La Plata");
		Zone plata_zone1 = new Zone("Berazattegui");
		Zone plata_zone2= new Zone("Catedral");
		Zone plata_zone3 = new Zone("Berizzo");
		
		plata_zone1.setCiti(plata);
		plata_zone2.setCiti(plata);
		plata_zone3.setCiti(plata);
		
		baires.addZone(plata_zone1);
		baires.addZone(plata_zone2);
		baires.addZone(plata_zone3);
		
		cityRepository.save(plata);		*/
		
	}
	
	@Test
	public void createConsumer(){
		/***ROLES DEFINIDOS 	 * (1, 'ROLE_ADMIN');  (2, 'ROLE_CONSUMER'); (3, 'ROLE_TRADER');*/

		/*Role roleAsiged = roleRepository.findById(2L).get();//ROLE_CONSUMER ID 2L PARA AMBOS CONSUMRES
		Passgenerator encoder = new Passgenerator(4);
		String pass= encoder.generate("1234");
	
		Consumer n1 = new Consumer("consumer1", pass , 10217047, "Fabio", "Londono", "calle 24" , 1, 1); 
		consumerRepository.save(n1);
		n1.addRole(roleAsiged);
		consumerRepository.save(n1);

		
		Consumer n2 = new Consumer("consumer2", pass, 10217048, "Martha", "Garcia", "calle 24" , 1, 1); 
		consumerRepository.save(n2);
		n2.addRole(roleAsiged);
		consumerRepository.save(n2);*/

	}


	@Test
	public void createTrader(){
	/*	List <Address> address = new ArrayList<Address>();
		address.add(new Address("Av Colon 1450", 1, 1));
		address.add(new Address("Billinghuts 269", 1, 2));
		address.add(new Address("Av Rivadavia 3045", 1, 5));
		
		Role roleAsiged = roleRepository.findById(3L).get();//ROLE_TRADER ID 2L PARA AMBOS traders
		Passgenerator encoder = new Passgenerator(4);
		String pass= encoder.generate("1234");
	
		Trader t1 = new  Trader("trader1", pass, 101111,address);
		t1.setEmail("trader@gmail.com");
		t1.setScore(5);
		t1.setNameEnterprise("EXITO");
		traderRepository.save(t1);
		
		t1.createStock();
		t1.addRole(roleAsiged);
		traderRepository.save(t1);
		
		
	
		List <Address> address2 = new ArrayList<Address>();
		address2.add(new Address("Pueyredon 500", 1, 1));
		Trader t2 = new  Trader("trader2", pass, 22222,address2); //ROLE_TRADER ID 2L PARA AMBOS traders
		t2.setEmail("trader2@gmail.com");
		t2.setScore(5);
		t2.setNameEnterprise("CARULLA");
		traderRepository.save(t2);
		
		t2.createStock();
		t2.addRole(roleAsiged);
		traderRepository.save(t2);*/

	}	


	@Test
	public void createAdmin(){
		Role roleAsiged = roleRepository.findById(1L).get();//ROLE_TRADER ID 1L PARA ADMIN
		Passgenerator encoder = new Passgenerator(4);
		String pass= encoder.generate("admin123");
	
		Trader admin = new  Trader("admin", pass, 987987);
		admin.setEmail("admin@gmail.com");
		admin.setScore(5);
		admin.setNameEnterprise("ADMIN");
		traderRepository.save(admin);
	
		admin.addRole(roleAsiged);
		traderRepository.save(admin);
	
	}
	
	
	
	
}
