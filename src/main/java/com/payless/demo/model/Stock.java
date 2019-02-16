package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;



/**
 * @author root 
 * ESTA CLASE ES UN COMPOSITE DE PRODUCT QUE ES USADA POR EL TRADER ARA CREAR SU STOCK DE DIFERENTES
 * PRODUCTOS, ADICIONALMENTE USA LA ESTRATEGIA DE MANY TO MANY CONTRA PRODUCT GENERANDO UNA TABLA INTERMEDIA LLAMADA 
 * STOCKPRODUCTS QUE HACE US DE OTRA TABLA DE IDS LLAMADA STOCKPROCTID
 * PERO ABAJO HAY UN CAMBIO EN LOS METODOS DDE ADDPRODUCT Y REMOVEPRODUCT 
 *  */

@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="STOCK_ID", nullable=false, unique=true )
	private long id;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_STOCK", nullable=false)
	private Date dateStock;
	
	
	/*ESTA ES UNA ESTRATEGIA DE MANY TO MANY  PARTIR DE UN OBJETO STOCKPRODUCID QUECONTINE AMBOS IDS DE STOCK Y DE PRODUCTS*/
	@OneToMany(mappedBy="stock", 
			   cascade=CascadeType.ALL,
			   orphanRemoval=true)
	@JsonManagedReference
	private Collection<StockProducts> stockproducts;
	
	
	@OneToOne(fetch = FetchType.LAZY,orphanRemoval=true,optional=false)
	@JoinColumn(name = "USER_ID")	
	@JsonBackReference//esto evita problemas en el json ver el ref en trader
	private Trader trader;
	

	
	
	
	
	public Stock(){
		this.stockproducts = new ArrayList<StockProducts>();
		this.dateStock= new Date();
	}
	
		

	/**********************************************************************************************************************************/
	/*metodos operacionales*/
	
	/*ESTE METODO REEMPLAZA A LA COLLECION DE StockProducts EN LA CLASE PRODUCT,  DEJANDOLO SINGLE SIDE BIDIRECCIONAL, asi mismo
	 * USA EL METODO REMOVE ABAJO DESCRITO PARA REMOVER LOS STOCKPRODUCTS CON SU PRODUCTO ejemplo tomado de
	 * https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/*/
	
	public void addProduct(Product p, int quantity){
		StockProducts stockproducs = new StockProducts(this,p, quantity);
		stockproducts.add(stockproducs);
	}
	
	public void removeProduct(Product p){
		 for (Iterator<StockProducts> iterator = stockproducts.iterator(); iterator.hasNext(); ) {
			     StockProducts stockproducs = iterator.next();
		 
		        if (stockproducs.getProduct().equals(this) && stockproducs.getStock().equals(p)) {
		            iterator.remove();
		            System.out.println("stocks .." + stockproducs.getStock());
		            //stockproducs.setProduct(null);
		        }
		 }
	}
	
	
	
	
	/**********************************************************************************************************************************/
	/*metodos setter and getter*/
	
		
	public long getId() {
		return id;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateStock() {
		return dateStock;
	}

	public void setDateStock(Date dateStock) {
		this.dateStock = dateStock;
	}



	public Collection<StockProducts> getStockproducts() {
		return stockproducts;
	}



	public void setStockproducts(Collection<StockProducts> stockproducts) {
		this.stockproducts = stockproducts;
	}




	


		
	
		
	
}
