package com.payless.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.payless.demo.util.Passgenerator;
import com.payless.demo.services.UserDetailsServiceImpl;;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

  
    public WebSecurityConfig() {
        super();
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
        	.antMatchers("/","/index","/login**","/callback/", "/webjars/**", "/error**").permitAll()
        	.antMatchers("/admin*").access("hasRole('ADMIN')")
        	.antMatchers("/consumer*").access("hasRole('CONSUMER')")
        	.antMatchers("/services*").access("hasRole('CONSUMER')")
            .antMatchers("/trader*").access("hasRole('TRADER')")
        	.anyRequest().authenticated()
            
           .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(myAuthenticationSuccessHandler())
            .failureUrl("/login?error=true")
           .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/login?logout");

        // @formatter:on
    	
    	
       /* http
            .authorizeRequests()
	        .antMatchers(resources).permitAll()  
	        .antMatchers("/","/index").permitAll()
	        .antMatchers("/admin*").access("hasRole('ADMIN')")
	        .antMatchers("/user*").access("hasRole('USER') or hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/menu")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout");*/
    }

    
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
        auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());     
    }


    /*redirect acording to login user*/
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }




}