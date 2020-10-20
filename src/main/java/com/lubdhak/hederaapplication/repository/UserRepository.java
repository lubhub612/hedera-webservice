package com.lubdhak.hederaapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lubdhak.hederaapplication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 Optional<User> findByUsername(String username);
	 Optional<User> findByPrivatekey(String privatekey);
	    Boolean existsByUsername(String username);
	
}
