package com.payless.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RATING_ID", nullable = false, unique = true)
	private long id;

	@Column(name = "SCORE")
	private int score;

	@Column(name = "COMMENT")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private InvoiceProduct invoiceProduct;

	public Rating() {
	}

	public Rating(InvoiceProduct invoiceProduct , int score, String comment) {
		this.invoiceProduct=invoiceProduct;
		this.score = score;
		this.comment = comment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public InvoiceProduct getInvoiceProduct() {
		return invoiceProduct;
	}

	public void setInvoiceProduct(InvoiceProduct invoiceProduct) {
		this.invoiceProduct = invoiceProduct;
	}

}
