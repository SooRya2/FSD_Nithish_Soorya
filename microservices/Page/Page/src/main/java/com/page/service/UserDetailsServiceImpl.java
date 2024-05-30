package com.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.page.bean.CustomUserDetails;
import com.page.bean.Login;
import com.page.repo.Loginrepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Loginrepo FSPRepository;

    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login user = FSPRepository.findByName(username);
       
        if(user == null){
           
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        return new CustomUserDetails(user);
    }
    
  
    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        Login user = FSPRepository.findByEmail(username);
     

        if(user == null){
           
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        return new CustomUserDetails(user);
    }
    
}