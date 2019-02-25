package com.payless.demo.restfull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(){
		this("NO encontrado..");
	}

	public ResourceNotFoundException(String msj) {
		this(msj,null);
	}
	
	public ResourceNotFoundException(String msj, Throwable cause) {
		super(msj, cause);
	}


}
