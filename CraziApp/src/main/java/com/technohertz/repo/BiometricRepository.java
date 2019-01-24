package com.technohertz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.technohertz.model.Biometric;
import com.technohertz.model.UserProfile;
@Repository
public interface BiometricRepository extends JpaRepository<Biometric, Integer> {
//@Query(value="")
	Biometric findByRegisterUserId(Integer userId);
}
