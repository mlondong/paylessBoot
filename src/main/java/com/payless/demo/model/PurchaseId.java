package com.payless.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * ESTA CLASE ES @EMBEDABLE Y DEBE IMPLEMENTAR HASCODE AND EQUALS ASI MISMO DEBE SER SERIALIZABLE
 * EN ELLA ESTAN LOS OBJETOS DE PRCHASE Y PRODUCT, CUANDO SE GENERE EL OBJETO PURCHASEPRODUCT TOMATA EMBEBIDO
 * LOS VALORRES DE PURHCASEID con el @EmbeddedId en purchaseproduct
 * ESTA CLASE CONTROLA MANTIENE LOS IDS DE PURCHASE PRODUCT Y DE PRODUCTID
 * **/


/**ESTE ERROR ES MUY COMUN CUANDO LOS NOMBRES DE LAS VARIABLES SON DISTINTAS AL NOMBRE DE LOS CAMPOS Y GENERA
 * CAMPOS ADICIONALES , AL PARECER ES NECESARIO TENER EL MISMO NOMBRE DE VARIABLES Q DE CAMPOS
 * Caused by: java.sql.SQLException: Field 'product_PRODUCT_ID' doesn't have a default value
 * */

@Embeddable
public class PurchaseId implements Serializable{

	@Column(name="PURCHASE_ID")
	private long PURCHASE_ID;

	@Column(name="PRODUCT_ID")
	private long PRODUCT_ID;



	public PurchaseId(){}
	
	
	public PurchaseId(long pu, long pr){
		super();
		this.PURCHASE_ID=pu;
		this.PRODUCT_ID=pr;
	}




	public long getPURCHASE_ID() {
		return PURCHASE_ID;
	}

	public void setPURCHASE_ID(long pURCHASE_ID) {
		PURCHASE_ID = pURCHASE_ID;
	}

	public long getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(long pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (PRODUCT_ID ^ (PRODUCT_ID >>> 32));
		result = prime * result + (int) (PURCHASE_ID ^ (PURCHASE_ID >>> 32));
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
		PurchaseId other = (PurchaseId) obj;
		if (PRODUCT_ID != other.PRODUCT_ID)
			return false;
		if (PURCHASE_ID != other.PURCHASE_ID)
			return false;
		return true;
	}




}
