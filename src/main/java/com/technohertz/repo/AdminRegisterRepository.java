package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.technohertz.model.AdminRegister;

@EnableJpaRepositories("com.technohertz.repo")
public interface AdminRegisterRepository extends JpaRepository<AdminRegister, Integer>,JpaSpecificationExecutor<AdminRegister>{
	
	
}
