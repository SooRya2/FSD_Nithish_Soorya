package com.FSP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FSP.bean.FSP;
import com.FSP.repo.FSPrepo;

@Service
public class FSPservice {

	@Autowired
	private FSPrepo FSPrepository;

	 public boolean authenticate(String username, String password) {
	    	System.out.println(username);
	        FSP user = FSPrepository.findByName(username);
	        if (user != null && password.equals(user.getPassword())) {
	            return true;
	        }
	        return false;
	    }


	 public FSP addnew(FSP user)
	 {
		 return FSPrepository.save(user);
	 }
}
