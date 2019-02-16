package com.payless.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author root
 * ESTA TABLA ES UNA TABLAID de ESTRATEGIA MUCHOS A MUCHOS CON CAMPOS ADICIONALES 
 * CONSTIENE LOS IDS DE STOCK Y DE PRODUCT, ASI MISMO GENERA SU PROPIO ID
 * SIEMPRE SE USA DE TIPO @EMBEDDABLE Y QUIEN LA USA LA TOMA COMO OBJETO TIPO @EMBBEDDEDID VER STOCKPRODUCTS
 *  al usar @EMBEDDABLE SE DEBE SERIALIZAR E IMPLEMENTAR LOS METODOS EQUSLS Y HASHCODE
 *  */


@Embeddable
public class StockProducId implements Serializable{

	@Column(name="STOCK_ID")	
	@JsonBackReference
	private long STOCK_ID;
	
	@Column(name="PRODUCT_ID")
	@JsonBackReference
	private long PRODUCT_ID;

	
	public StockProducId(){}
	
	
	public StockProducId(long idStock, long idPrduct) {
		this.STOCK_ID = idStock;
		this.PRODUCT_ID = idPrduct;
	}


	
	public long getIdStock() {
		return STOCK_ID;
	}


	public void setIdStock(long idStock) {
		this.STOCK_ID = idStock;
	}


	public long getIdPrduct() {
		return PRODUCT_ID;
	}

	public void setIdPrduct(long idPrduct) {
		this.PRODUCT_ID = idPrduct;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (PRODUCT_ID ^ (PRODUCT_ID >>> 32));
		result = prime * result + (int) (STOCK_ID ^ (STOCK_ID >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockProducId other = (StockProducId) obj;
		if (PRODUCT_ID != other.PRODUCT_ID)
			return false;
		if (STOCK_ID != other.STOCK_ID)
			return false;
		return true;
	}


	
	
	
		
	
}
