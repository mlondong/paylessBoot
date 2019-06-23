package com.payless.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @SpringBootApplication is equivalent to using
 *  @Configuration, @EnableAutoConfiguration, and @ComponentScan,
 *   and these annotations are frequently used together. 
 * */
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class PaylessBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaylessBootApplication.class, args);
	}

}

