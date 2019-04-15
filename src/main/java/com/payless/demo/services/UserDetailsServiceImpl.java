package com.payless.demo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.UsserRepository;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Role;
import com.payless.demo.model.Trader;
import com.payless.demo.model.Usser;


/*PARA ESTE CASO ES POSIBLE REAIZAR
 * AuthenticationProvider vs UserDetailsService
 * */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private ConsumerRepository consumerRepository;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Trader> traderUser =  traderRepository.findByName(username);
		Usser appUser=null; 

		if(traderUser.isPresent()){
			appUser= (Usser)traderUser.get();
		}else{
				Optional<Consumer> consumerUser =  consumerRepository.findNameContainInConsumer(username);//aca por @Query
				if(consumerUser.isPresent() ){
					appUser= (Usser)consumerUser.get();	
				}else{
						throw new UsernameNotFoundException("Invalid User" + username);
					
				}
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role authority: appUser.getRoles()) {
			 grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}

		UserDetails user = (UserDetails) new User(appUser.getName(), appUser.getPassword(), grantedAuthorities);
		System.out.println("RETURN---> " + user);
		return user;
	}
}