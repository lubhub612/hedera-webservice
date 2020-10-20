package com.lubdhak.hederaapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lubdhak.hederaapplication.model.ImpUser;


@Repository
public interface ImpUserRepository extends JpaRepository<ImpUser, Long> {

	
	Optional<ImpUser> findByUsername(String username);
	 Optional<ImpUser> findByPrivatekey(String privatekey);
	    Boolean existsByUsername(String username);
	    Boolean existsByPrivatekey(String privatekey);
	    Boolean existsByAccountid(String accountid);
}
