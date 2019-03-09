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
 * ESTABLA TABLA ES DE MUCHOS A MUCHOS A TRAVES DELA TABLA INVOICEPRODUCTS + 
 * INVOICE CON SU ID
 * PRODUCTCON SU ID
 * EN UNA ESTRATEGIA MUCHOS A MUCHOS
 * EN EL CONSTRUCTOR SE SETEA EL OBJETO InvoiceProductId 
 */

@Entity
public class InvoiceProduct {

	@EmbeddedId
	private InvoiceProductId invProdId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("INVOICE_ID")
	@JsonBackReference
	private Invoice invoice;

	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("PRODUCT_ID")
	@JsonBackReference
	private Product poduct;
	
	@Column(name="CUANTITY")
	private int quantity;
	


	public InvoiceProduct(){}
	
	
	public InvoiceProduct(Invoice invoice, Product poduct, int quantity) {
		super();
		this.invProdId = new InvoiceProductId(invoice.getId(), poduct.getId() );
		this.invoice = invoice;
		this.poduct = poduct;
		this.quantity = quantity;
	}

	
	public InvoiceProductId getInvProdId() {
		return invProdId;
	}

	public void setInvProdId(InvoiceProductId invProdId) {
		this.invProdId = invProdId;
	}



	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Product getPoduct() {
		return poduct;
	}

	public void setPoduct(Product poduct) {
		this.poduct = poduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "InvoiceProduct [invProdId=" + invProdId + ", invoice=" + invoice + ", poduct=" + poduct + ", quantity="
				+ quantity + "]";
	}



	
	
	
	
}
