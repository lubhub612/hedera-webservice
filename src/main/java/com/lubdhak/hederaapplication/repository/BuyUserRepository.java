package com.lubdhak.hederaapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lubdhak.hederaapplication.model.BuyUser;

@Repository
public interface BuyUserRepository extends JpaRepository<BuyUser, Long> {
	
	Optional<BuyUser> findByUsername(String username);
	
	////BuyUser  findByAccountid(String accountid);
	List<BuyUser> findByAccountid(String username);
	
	List<BuyUser> findByFiatprice(String fiatprice);
	
	List<BuyUser> findByStoplose(String stoplose);
	
	List<BuyUser> findByStoplose1(String stoplose1);
	
	List<BuyUser> findByStoplose2(String stoplose2);
	
	List<BuyUser> findByStoplose3(String stoplose3);
	
	List<BuyUser> findByStoplose4(String stoplose4);
	
	Optional<BuyUser> findById(Long id);
}
