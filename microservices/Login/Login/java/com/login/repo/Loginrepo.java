package com.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.login.bean.Login;

import java.util.List;

@Repository
public interface Loginrepo extends JpaRepository<Login, Integer> {
	Login  findByName(String name);
	Login findByEmail(String email);

}
