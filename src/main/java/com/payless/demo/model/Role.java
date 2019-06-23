package com.payless.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Role {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ROLE_ID")
	private long id;
	
	@Column(name="ROLE_NAME")
	private String name;
	
	@ManyToMany(mappedBy="roles")
	@JsonBackReference
	private Set<Usser> users = new HashSet<Usser>();
	

	public Role(){}
	
	
	public Role(String name, Set<Usser> users) {
		this.name = name;
		this.users = users;
	}
	
	
	public Set<Usser> getUsers() {
		return users;
	}
	public void setUsers(Set<Usser> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}







}


