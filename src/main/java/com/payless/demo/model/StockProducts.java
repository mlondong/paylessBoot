package com.payless.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;




/**
 * @author root
 * ESTABLA TABLA ES DE MUCHOS A MUCHOS A TRAVES DELA TABLA STOCKPRODUCTID + STOCK + PRODUCT
 * EN UNA ESTRATEGIA MUCHOS A MUCHOS
 */


@Entity
public class StockProducts {

	@EmbeddedId
	private StockProducId id;

	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("STOCK_ID")
	@JsonBackReference
	private Stock stock;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("PRODUCT_ID")
	@JsonBackReference
	private Product product;


	@Column(name="QUANTITY")
	private int quantity;
	
	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE")
	private Date date = new Date();
	
	
	public StockProducts(){}
	
	
	public StockProducts(Stock stock, Product product, int cantidad) {
		super();
		this.id = new StockProducId(stock.getId(), product.getId());
		this.stock = stock;
		this.product = product;
		this.quantity=cantidad;
	}


	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public StockProducId getId() {
		return id;
	}


	public void setId(StockProducId id) {
		this.id = id;
	}


	public Stock getStock() {
		return stock;
	}


	public void setStock(Stock stock) {
		this.stock = stock;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	@Override
	public String toString() {
		return "StockProducts [id=" + id + ", stock=" + stock + ", product=" + product + ", quantity=" + quantity
				+ ", date=" + date + "]";
	}



}
