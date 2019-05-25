package com.payless.demo.services;

import java.util.Optional;

import com.payless.demo.model.Role;

public interface RoleService {
	void save(Role role);
	Optional<Role>findById(Long id);
	Iterable<Role> findByName(String name);
	
}
