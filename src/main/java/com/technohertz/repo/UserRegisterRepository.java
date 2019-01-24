package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.technohertz.model.UserRegister;

@EnableJpaRepositories("com.technohertz.repo")
public interface UserRegisterRepository extends JpaRepository<UserRegister, Integer>,JpaSpecificationExecutor<UserRegister>{

}
