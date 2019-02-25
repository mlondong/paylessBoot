package com.payless.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

/* 
 * Clase Abstracta User
 * se usa estrategia InheritanceType.JOINED para mapeo de Herencia
 * 
 * */



@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Usser {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID",nullable=false,updatable=false)
	private long id;

	@NotBlank(message = "Name is mandatory")
	@Column(name="NAME",nullable=false,updatable=true )
	private String name;
	
	@NotBlank(message = "Password is mandatory")
	@Column(name="PASSWORD", nullable=false,updatable=true)
	private String password;
	
	@Column(name="STATE", updatable=true)
	private boolean state;
	
	
	
	public Usser(){}
	
	public Usser(String _name, String _pass){
		this.state=true;
		this.name=_name;
		this.password=_pass;
	}
	
	
	
	
	
	
	/*METODOS OPERACIONALES*/
	
	
	
	
	
	/*METODOS SETTER AND GETTER*/
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	
	
	
}
