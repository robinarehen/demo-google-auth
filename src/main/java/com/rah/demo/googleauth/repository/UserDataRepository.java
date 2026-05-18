package com.rah.demo.googleauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rah.demo.googleauth.entity.UserDataEntity;
import java.util.List;



@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, Integer> {

	boolean existsByUserEmail(String userEmail);
	
	Optional<UserDataEntity> findByUserEmail(String userEmail);
	
	List<UserDataEntity> getByUserEmail(String userEmail);

	boolean existsByLastCode(String lastCode);
}
