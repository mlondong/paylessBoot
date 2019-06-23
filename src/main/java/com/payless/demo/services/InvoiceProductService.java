package com.payless.demo.services;

import java.util.Optional;

import com.payless.demo.model.InvoiceProduct;

public interface InvoiceProductService {

	Optional<InvoiceProduct> findById(long arg0);
	
}
