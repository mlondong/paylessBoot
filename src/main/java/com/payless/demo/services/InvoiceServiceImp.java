package com.payless.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Invoice;
import com.payless.demo.repositories.InvoiceRepository;


@Service
public class InvoiceServiceImp implements InvoiceService{

	@Autowired
	private InvoiceRepository invoiceRepositoty;
	
	
	
	@Override
	public Invoice save(Invoice entity) {
		return invoiceRepositoty.save(entity);
	}

	@Override
	public Optional<Invoice> findById(Long id) {
		return invoiceRepositoty.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		
		return invoiceRepositoty.existsById(id);
	}

	@Override
	public Iterable<Invoice> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Invoice> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		invoiceRepositoty.deleteById(id);
	}

	@Override
	public void delete(Invoice entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
