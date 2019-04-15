package com.payless.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.validation.constraints.NotNull;

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


	@NotNull(message = "Name is mandatory")
	@Column(name="NAME",nullable=false,updatable=true )
	private String name;
	
	@NotNull(message = "Password is mandatory")
	@Column(name="PASSWORD", nullable=false,updatable=true)
	private String password;
	
	@Column(name="STATE", updatable=true)
	private boolean state=true;
	
	 @ManyToMany(fetch = FetchType.EAGER,
			 	 cascade = {
			 			 		CascadeType.PERSIST,
			 			 		CascadeType.MERGE
		    			  })
	@JoinTable(name="USSER_ROLE",
			   joinColumns= {@JoinColumn(name="USER_ID")},
			   inverseJoinColumns={@JoinColumn(name="ROLE_ID")} 
			   )
	private Set<Role> roles = new HashSet<Role>();
	
	
	
	
	public Usser(){}
	
	public Usser(String _name, String _pass){
		this.name=_name;
		this.password=_pass;
	}
	
	
	
	
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	

	
	
	
	
}
