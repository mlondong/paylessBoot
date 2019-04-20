package com.payless.demo.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class Passgenerator {

	private BCryptPasswordEncoder bCryptPasswordEncoder=null;
	
	public Passgenerator(int strength){
		bCryptPasswordEncoder = new BCryptPasswordEncoder(strength);
	}
	
	public String generate(String pass){
		return bCryptPasswordEncoder.encode(pass);
	}
	

}
