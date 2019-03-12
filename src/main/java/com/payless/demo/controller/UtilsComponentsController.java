package com.payless.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.ZoneRepository;
import com.payless.demo.services.ConsumerServiceImp;


@Controller
public class UtilsComponentsController {


	@Autowired
	private ZoneRepository zoneRepository;
	
	
	
	/*** CONTROLLES FOR COMPONENTS*/
	
	@RequestMapping(path="/zones",method={RequestMethod.POST, RequestMethod.GET})
	public String getZonesInCity(@RequestParam("idcity") long idcity,  Model model){
		System.out.println("llego..." + idcity);
		System.out.println("llego..." + zoneRepository.findAllZonesByIdCity(idcity));
		model.addAttribute("zones", zoneRepository.findAllZonesByIdCity(idcity));
		return "registerinvoice :: #zone";
	}
	
	
	
	
}
