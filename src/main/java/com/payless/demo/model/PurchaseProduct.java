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

/**
 * ESTA CLASE TENDRA LOS PRODUCTOS RELACIONADOS AL PURCHASE, TAMBIEN PERMITIRA QUE DENTRO DE OTRO PURCHASE
 * SE REPITAN PRODUCTOS DE OTRAS PURCHASE, luego se MAPEAN LOS OBJETOS PURCHASE Y PRODUCT DE MUCHOS A UNO
 * HACIA PURCHASE**/

@Entity
public class PurchaseProduct {

	
	@EmbeddedId
	private PurchaseId purchaseId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("PURCHASE_ID")
	private Purchase purchase;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("PRODUCT_ID")
	private Product product;
	
	@Column(name="QUANTITY", nullable=false)
	private int quantity;

	
	
	public PurchaseProduct(){}
	
	public PurchaseProduct(Purchase purchase, Product product, int quantity) {
		super();
		
		/*SE GENERA EL OBJETO PURCHASEID*/
		this.purchaseId = new PurchaseId(purchase.getId(), product.getId());
		
		this.purchase = purchase;
		this.product = product;
		this.quantity = quantity;
		
		
	}

	
	
	
	
	
	
	public PurchaseId getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(PurchaseId purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
}
