package com.payless.demo.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {



	protected Log logger = LogFactory.getLog(this.getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();




	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
										HttpServletResponse response, Authentication authentication)
										throws IOException {
		System.out.println("REQUEST " + request + "RESPONSE " + response);
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)	throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		int target=0;
		Collection<? extends GrantedAuthority> authorities 	= authentication.getAuthorities();
		
		for (GrantedAuthority grantedAuthority : authorities) {
			System.out.println("--> " + grantedAuthority.getAuthority());
			
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				System.out.println("ROLE_ADMIN AUTHORTHY");
				target=1;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_CONSUMER")) {
				System.out.println("ROLE_CONSUMER AUTHORTHY");
				target=2;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_TRADER")) {
				System.out.println("ROLE_TRADER AUTHORTHY");
				target=3;
				break;
			
			}
		}

		
		System.out.println("VALOR DE TARGET... " + target);
		if (target==1) {
			System.out.println("direccionando to ADMIN");
			return "/admin";
		} else if (target == 2) {
			System.out.println("direccionando to CONSUMER");
			return "/consumer";
		}else if (target == 3) {
			System.out.println("return trader");
			return "/admin";
		}else {
			System.out.println("ERRRRORRRRRRR... IllegalStateException()");
			throw new IllegalStateException();
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		System.out.println("SESSION.... " + request.getSession());
		HttpSession session = request.getSession(false);
		//will return current session if current session exists, then it will not create a new session.
		
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}  