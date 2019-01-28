package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technohertz.model.Biometric;
@Repository
public interface BiometricRepository extends JpaRepository<Biometric, Integer> {
//@Query(value="")
//	Biometric findByRegisterUserId(Integer userId);
}
