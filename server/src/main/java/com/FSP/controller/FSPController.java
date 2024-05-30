package com.FSP.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.security.GeneralSecurityException;
import java.io.IOException;
import java.util.Collections;
import com.google.api.client.json.gson.GsonFactory;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.FSP.Jwt.JwtTokenProvider;
import com.FSP.bean.FSP;
import com.FSP.dto.LoginDTO;
import com.FSP.service.FSPservice;
import com.FSP.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/sso")
public class FSPController {


	private final FSPservice userService;
	private final UserDetailsServiceImpl userDetailsService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public FSPController(FSPservice userService,UserDetailsServiceImpl userDetailsService,JwtTokenProvider jwtTokenProvider) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	
	@GetMapping("/home")
	public String Hello() {
		return "Hello World";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/pass")
	public String pass() {
		return "Secured Page";
	}

	 @PostMapping("/login")
	 public ResponseEntity<?> authenticateUser(@RequestBody FSP loginRequest) {
	        if (userService.authenticate(loginRequest.getName(), loginRequest.getPassword())) {
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
	 public FSP add(@RequestBody FSP user)
	 {
		 return userService.addnew(user);
	 }
}
