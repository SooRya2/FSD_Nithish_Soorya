package com.FSP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.FSP.bean.CustomUserDetails;
import com.FSP.bean.FSP;
import com.FSP.repo.FSPrepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FSPrepo FSPRepository;

    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FSP user = FSPRepository.findByName(username);
        //System.out.println("loadUserbyUsername: "+user.getName());
        if(user == null){
           
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        return new CustomUserDetails(user);
    }
    
  
    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        FSP user = FSPRepository.findByEmail(username);
        //System.out.println("loadUserByEmail: " + user.getEmail());

        if(user == null){
           
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        return new CustomUserDetails(user);
    }
    
}