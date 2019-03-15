package com.payless.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.payless.demo.model.Invoice;
import com.payless.demo.services.InvoiceServiceImp;
import com.payless.demo.services.TraderServiceImp;

@Controller
public class InvoiceController {

	@Autowired
	private  InvoiceServiceImp invoiceServiceImp;
	
	
	
	
	
}
