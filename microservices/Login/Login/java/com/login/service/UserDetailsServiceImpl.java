package com.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.login.bean.CustomUserDetails;
import com.login.bean.Login;
import com.login.repo.Loginrepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Loginrepo FSPRepository;

    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login user = FSPRepository.findByName(username);
        //System.out.println("loadUserbyUsername: "+user.getName());
        if(user == null){
           
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        return new CustomUserDetails(user);
    }
    
  
    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        Login user = FSPRepository.findByEmail(username);
        //System.out.println("loadUserByEmail: " + user.getEmail());

        if(user == null){
           
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        return new CustomUserDetails(user);
    }
    
}