package com.payless.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.repositories.InvoiceProductRepository;

@Service
public class InvoiceProductServiceImp implements InvoiceProductService {

	@Autowired
	private InvoiceProductRepository InvoiceProductRepository;
	
	@Override
	public Optional<InvoiceProduct> findById(Long arg0) {

		return InvoiceProductRepository.findById(arg0);
	}

}
