package com.login.controller;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import java.security.GeneralSecurityException;
import java.io.IOException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.login.bean.Login;
import com.login.dto.LoginDTO;
import com.login.jwt.JwtTokenProvider;
import com.login.service.LoginService;
import com.login.service.UserDetailsServiceImpl;


@RestController
@RequestMapping("/sso")
public class LoginController {
	

	
	private final JwtTokenProvider jwtTokenProvider;
	private final RestTemplate restTemplate;
	private final LoginService loginservice;
	private final UserDetailsServiceImpl userDetailsService;
	
	
	@Autowired
	public LoginController(RestTemplate restTemplate,LoginService loginservice,UserDetailsServiceImpl userDetailsService,JwtTokenProvider jwtTokenProvider)
	{
		this.loginservice=loginservice;
		this.restTemplate=restTemplate;
		this.userDetailsService=userDetailsService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/pass")
	public String log()
	{
		System.out.println("Hello in login controller");
	    String page= restTemplate.getForObject("http://PAGE/land/page", String.class);
		return page;
		
	}
	
	 @PostMapping("/login")
	 public ResponseEntity<?> authenticateUser(@RequestBody Login loginRequest) {
	        if (loginservice.authenticate(loginRequest.getName(), loginRequest.getPassword())) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getName());
	            System.out.println("user details: "+userDetails.getUsername());
	            String token = jwtTokenProvider.GenerateToken(userDetails.getUsername());
	            LoginDTO logindto = new LoginDTO();
	            logindto.setName(userDetails.getUsername());
	            String role = userDetails.getAuthorities().toString();
	            role = role.substring(1,role.length()-1);
	            logindto.setRole(role);
	            logindto.setToken(token);
	            return ResponseEntity.ok(logindto);
	        }
	        else {
	        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }
 }
	 
	 @PostMapping("/google-login")
	    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
	        String tokenId = payload.get("token");

	        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
	                .setAudience(Collections.singletonList("501266156796-rgqs9fmrasgrtcedsie132ku55cg9pdd.apps.googleusercontent.com"))
	                .build();
	        GoogleIdToken idToken;
	        try {
	            idToken = verifier.verify(tokenId);
	            if (idToken != null) {
	            	
	                GoogleIdToken.Payload googlePayload = idToken.getPayload();
	                String email = googlePayload.getEmail();
	               
	                UserDetails userDetails = userDetailsService.loadUserByEmail(email);
	                
	                String jwt = jwtTokenProvider.GenerateToken(userDetails.getUsername());           
	                LoginDTO logindto = new LoginDTO();
		            logindto.setName(userDetails.getUsername());
		            String role = userDetails.getAuthorities().toString();
		            role = role.substring(1,role.length()-1);
		            logindto.setRole(role);
		            logindto.setToken(jwt);
	                return ResponseEntity.ok(logindto);
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	            }
	        } catch (GeneralSecurityException | IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	        }
	    }
	 
	 @PostMapping("/add")
	 public Login add(@RequestBody Login user)
	 {
		 return loginservice.addnew(user);
	 }

}
