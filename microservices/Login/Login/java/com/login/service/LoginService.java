package com.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.bean.Login;
import com.login.repo.Loginrepo;

@Service
public class LoginService {
	
	@Autowired
	private Loginrepo Loginrepository;
	
	public Login addnew(Login user)
	 {
		 return Loginrepository.save(user);
	 }
	
	public boolean authenticate(String username, String password) {
    	System.out.println(username);
        Login user = Loginrepository.findByName(username);
        if (user != null && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

}
