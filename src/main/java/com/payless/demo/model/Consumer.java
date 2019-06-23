package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Consumer extends Usser {

	@Column(name="DNI",nullable=false,unique=true)
	private long dni;

	@Size(min=5, max=30)
	@Column(name="FIRSTNAME",nullable=false)
	private String firstName;

	@Size(min=5, max=30)
	@Column(name="LASTNAME",nullable=false)
	private String lastName;


	/*MAPEO BIDIRECCIONAL DE 1 CONSUMER A MUCHOS PURCHASE MAPPEDBY CONSUMER DESDE PURCHASE*/
	@OneToMany(mappedBy="consumer", 
			fetch=FetchType.LAZY,
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	@JsonManagedReference
	private Collection<Invoice> invoices = new ArrayList<>();


	/*MAPEO DE ADDRESS ONE TO ONE CONSUMER-ADDRESS*/

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	@JsonManagedReference
	private Address address;



	public Consumer(){}

	public Consumer(String _name, String _pass, long _dni, String _firstName, String _lastName ){
		super(_name, _pass);
		this.dni=_dni;
		this.firstName=_firstName;
		this.lastName=_lastName;
	}

	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Collection<Invoice> invoices) {
		this.invoices = invoices;
	}

	public void addInvoice(Invoice p){
		this.invoices.add(p);	
	}

	public void removeInvoice(Invoice p){
		this.invoices.remove(p);	
	}

	public Optional<Invoice> findInvoiceByNumber(Long numInvoice){
		return this.invoices.stream().filter(invoice-> numInvoice.equals(invoice.getNumInvoice()) ).findFirst();
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	public Collection<InvoiceProduct> getAllProductsInMyBuyes(){
		Collection<InvoiceProduct> invoiceProduc = new ArrayList<InvoiceProduct>();
		for(Invoice invoice : this.getInvoices()) {
			for(InvoiceProduct inp: invoice.getProducts()) {
				invoiceProduc.add(inp);
			}
		}
		return invoiceProduc; 
	}
	

	
	public TreeMap<Integer, Product>  getAllAcumulatedProducts() {
		
		Map<Integer, Product> productsList = new HashMap<Integer, Product>();
		Collection<InvoiceProduct> invoiceProdct =this.getAllProductsInMyBuyes();  
		List<String> codes = invoiceProdct.stream()
													.map(d-> d.getPoduct().getCode())
													.collect(Collectors.toList());
		Set<String> setCodes = new HashSet<String>(codes);
		for(String code : setCodes) {
			int acumQuantity= invoiceProdct.stream()
												.filter(d->code.equals(d.getPoduct().getCode()))
												.mapToInt(d->d.getQuantity())
												.sum();
			Optional<InvoiceProduct> invoiceProduct = 
												invoiceProdct.stream()
												.filter(d-> code.equals(d.getPoduct().getCode()))
												.findFirst();
			productsList.put(acumQuantity, invoiceProduct.get().getPoduct() );
		}
		return new TreeMap<Integer, Product>(productsList);
	}

	
	
	
	
	public TreeMap<Integer, Product>  getAllAcumulatedPrices() {
		Map<Integer, Product> productsList = new HashMap<Integer, Product>();
		
		Collection<InvoiceProduct> invoiceProdct =this.getAllProductsInMyBuyes();  
		List<String> codes = invoiceProdct.stream()
													.map(d-> d.getPoduct().getCode())
													.collect(Collectors.toList());
		Set<String> setCodes = new HashSet<String>(codes);
		for(String code : setCodes) {
			int acumPrice= invoiceProdct.stream()
												.filter(d->code.equals(d.getPoduct().getCode()))
												.mapToInt(d->d.getPricebuy())
												.sum();
			Optional<InvoiceProduct> invoiceProduct = 
												invoiceProdct.stream()
												.filter(d-> code.equals(d.getPoduct().getCode()))
												.findFirst();
			
			productsList.put(acumPrice, invoiceProduct.get().getPoduct() );
		}
		
		return new TreeMap<Integer, Product>(productsList);
	}
	
	
	
	public InvoiceProduct getAllAcumulated(String code, List<InvoiceProduct> invProd) {
		int acumlated_price	= invProd.stream().filter(d->code.equals(d.getPoduct().getCode() )).mapToInt(temp->temp.getPricebuy()).sum();
		int acumlated_quantity= invProd.stream().filter(d->code.equals(d.getPoduct().getCode() )).mapToInt(temp->temp.getQuantity()).sum();
		Optional<InvoiceProduct> invoiceAcummulate = invProd.stream().filter(d-> code.equals(d.getPoduct().getCode())).findFirst();
		invoiceAcummulate.get().setPricebuy(acumlated_price);
		invoiceAcummulate.get().setQuantity(acumlated_quantity);
		return invoiceAcummulate.get();
	}


	
	public Map<Long, Trader> getMoreVisited() {
		Collection<Invoice> invoices = this.getInvoices();
		List<Long> idsTraders = invoices.stream().map(d-> d.getTrader().getId()).collect(Collectors.toList());				
		return this.getTradersInvolvedInMyBuyes(idsTraders);
	}
	

	public Map<Long, Trader> getTradersInvolvedInMyBuyes(List<Long> idsTraders) {
		Map<Long, Trader> traders = new HashMap<Long, Trader>();
		
		for(Long id: idsTraders) {
				Long cant= invoices.stream().filter(d-> id.equals(d.getTrader().getId())).count();
				Optional<Invoice>invoice = invoices.stream().filter(d-> id.equals(d.getTrader().getId())).findFirst();
				traders.put(cant, invoice.get().getTrader()); 
		}
				
		return traders;
	}
	
	
	public Map<Long, Product> getAllMyRatings() {
		
		Collection<InvoiceProduct> invoiceProdct = this.getAllProductsInMyBuyes();  
		Collection<Rating> ratings = new ArrayList<>();  
		Map<Long, Product> myRatings = new HashMap<Long, Product>();
		
		for(InvoiceProduct i : invoiceProdct) {
			for(Rating r: i.getRatings())
			ratings.add(r);
		}
		
		List<Long> idsProducts = ratings.stream().map(d-> d.getInvoiceProduct().getPoduct().getId()).collect(Collectors.toList());				
		
		for(Long id: idsProducts) {
			Long cant= ratings.stream().filter(d-> id.equals(d.getInvoiceProduct().getPoduct().getId())).count();
			int sumScore= ratings.stream().filter(d->id.equals(d.getInvoiceProduct().getPoduct().getId())).mapToInt(temp->temp.getScore()).sum();
			Optional<Rating> rating = ratings.stream().filter(d->id.equals(d.getInvoiceProduct().getPoduct().getId())).findFirst();
			long porcentaje = sumScore/cant;
			myRatings.put(porcentaje, rating.get().getInvoiceProduct().getPoduct()); 
		}
		return myRatings;
	}

	
	
	public Map<Long, Product> getAllGerenalRatings(Iterable<Rating> ratingGeneral) {
		
		Map<Long, Product> generalRatings = new HashMap<Long, Product>();
		List<Rating> ratings = StreamSupport.stream(ratingGeneral.spliterator(), false).collect(Collectors.toList());
		
		List<Long> idsProducts = ratings.stream().map(d-> d.getInvoiceProduct().getPoduct().getId()).collect(Collectors.toList());				
		
		for(Long id: idsProducts) {
				Long cant= ratings.stream().filter(d-> id.equals(d.getInvoiceProduct().getPoduct().getId())).count();
				int sumScore= ratings.stream().filter(d->id.equals(d.getInvoiceProduct().getPoduct().getId())).mapToInt(temp->temp.getScore()).sum();
				Optional<Rating> rating = ratings.stream().filter(d->id.equals(d.getInvoiceProduct().getPoduct().getId())).findFirst();
				long porcentaje = (long)(sumScore/cant);
				generalRatings.put(porcentaje, rating.get().getInvoiceProduct().getPoduct()); 
			}
		return generalRatings;

	}
	
	
	
	

	@Override
	public String toString() {
		return "Consumer [dni=" + dni + ", firstName=" + firstName + ", lastName=" + lastName + ", invoices=" + invoices
				+ ", address=" + address + "]";
	}
}
