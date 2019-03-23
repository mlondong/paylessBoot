package com.payless.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.ZoneRepository;
import com.payless.demo.services.ConsumerServiceImp;


@Controller
public class UtilsComponentsController {


	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	private  ConsumerServiceImp consumerServiceImp;
	
	
	
	
	/*** CONTROLLES FOR COMPONENTS*/
	
	@RequestMapping(path="/zones",method={RequestMethod.POST, RequestMethod.GET})
	public String getZonesInCity(@RequestParam("idcity") long idcity,  Model model){
		model.addAttribute("zones", zoneRepository.findAllZonesByIdCity(idcity));
		return "registerinvoice :: #zone";
	}
	
	
	@RequestMapping(path="/consumer",method={RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public List<Consumer> getConsumerByDni(@RequestParam("dni") long dni){
		System.out.println("llego.. " + dni);
		List<Consumer> listConsumers = consumerServiceImp.queryByDni(dni);
		return listConsumers;
	}


	
	
	
	
}
