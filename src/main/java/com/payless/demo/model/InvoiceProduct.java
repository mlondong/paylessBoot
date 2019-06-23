package com.payless.demo.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author root ESTABLA TABLA ES DE MUCHOS A MUCHOS A TRAVES DELA TABLA
 *         INVOICEPRODUCTS + INVOICE CON SU ID PRODUCTCON SU ID EN UNA
 *         ESTRATEGIA MUCHOS A MUCHOS EN EL CONSTRUCTOR SE SETEA EL OBJETO
 *         InvoiceProductId
 */

@Entity
public class InvoiceProduct  {

	@EmbeddedId
	@JsonIgnore
	private InvoiceProductId invProdId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("INVOICE_ID")
	@JsonBackReference
	private Invoice invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("PRODUCT_ID")
	@JsonManagedReference
	private Product poduct;

	@Column(name = "CUANTITY")
	private int quantity;

	@Column(name = "PRICE_BUY")
	private int pricebuy;
	
	
	
	
	@OneToMany(mappedBy = "invoiceProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Collection<Rating> ratings;

	
	
	public InvoiceProduct() {
	}

	public InvoiceProduct(Invoice invoice, Product poduct, int quantity, int price) {
		this.invProdId = new InvoiceProductId(invoice.getId(), poduct.getId());
		this.invoice = invoice;
		this.poduct = poduct;
		this.quantity = quantity;
		this.pricebuy=price;
	}

	public Collection<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Collection<Rating> ratings) {
		this.ratings = ratings;
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

	
	public int getPricebuy() {
		return pricebuy;
	}

	public void setPricebuy(int pricebuy) {
		this.pricebuy = pricebuy;
	}

	public void  orderInvoiceProduct(List<InvoiceProduct> list) {
		/*aca estoy debo filtrar el array*/
	}

	@Override
	public String toString() {
		return "InvoiceProduct [invProdId=" + invProdId + ", invoice=" + invoice + ", poduct=" + poduct + ", quantity="
				+ quantity + ", pricebuy=" + pricebuy + ", ratings=" + ratings + "]";
	}
	
	


	
	
	
	
	

	

}
