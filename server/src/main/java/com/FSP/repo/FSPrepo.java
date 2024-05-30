package com.FSP.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.FSP.bean.FSP;
import java.util.List;





@Repository
public interface FSPrepo extends JpaRepository<FSP, Integer> {
	FSP  findByName(String name);
	FSP  findByEmail(String email);

}