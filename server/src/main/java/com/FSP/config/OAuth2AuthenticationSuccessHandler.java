package com.FSP.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.FSP.Jwt.JwtTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    
   

  
    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider2) {
		// TODO Auto-generated constructor stub
    	this.jwtTokenProvider = jwtTokenProvider2;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String jwtToken = jwtTokenProvider.cToken(oauthToken.getPrincipal().getName(), oauthToken.getAuthorities());
        response.setHeader("Authorization", "Bearer " + jwtToken);
        response.getWriter().write("Authentication Successful");
    }

	@Override
	public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, Authentication authentication)
			throws IOException, jakarta.servlet.ServletException {
		// TODO Auto-generated method stub
		
	}
}
