package com.payless.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author root
 * ESTA TABLA ES UNA TABLAID de ESTRATEGIA MUCHOS A MUCHOS CON CAMPOS ADICIONALES 
 * CONSTIENE LOS IDS DE INVOICE Y DE PRODUCT, ASI MISMO GENERA SU PROPIO ID
 * SIEMPRE SE USA DE TIPO @EMBEDDABLE Y QUIEN LA USA LA TOMA COMO OBJETO TIPO @EMBBEDDEDID 
 * VER INVOICEPRODUCT  al usar @EMBEDDABLE SE DEBE SERIALIZAR E IMPLEMENTAR LOS METODOS EQUSLS Y HASHCODE
 *  */

@Embeddable
public class InvoiceProductId implements Serializable{

	@Column(name="INVOICE_ID")
	private long INVOICE_ID;
	
	@Column(name="PRODUCT_ID")
	private long PRODUCT_ID;

	
	
	public InvoiceProductId(){
		
	}
	
	public InvoiceProductId(long idinvoice, long idproduct) {
		super();
		this.INVOICE_ID = idinvoice;
		this.PRODUCT_ID = idproduct;
	}

	public long getINVOICE_ID() {
		return INVOICE_ID;
	}


	public void setINVOICE_ID(long iNVOICE_ID) {
		INVOICE_ID = iNVOICE_ID;
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
		result = prime * result + (int) (INVOICE_ID ^ (INVOICE_ID >>> 32));
		result = prime * result + (int) (PRODUCT_ID ^ (PRODUCT_ID >>> 32));
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
		InvoiceProductId other = (InvoiceProductId) obj;
		if (INVOICE_ID != other.INVOICE_ID)
			return false;
		if (PRODUCT_ID != other.PRODUCT_ID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceProductId [INVOICE_ID=" + INVOICE_ID + ", PRODUCT_ID=" + PRODUCT_ID + "]";
	}



	
	
	
	
	
}
