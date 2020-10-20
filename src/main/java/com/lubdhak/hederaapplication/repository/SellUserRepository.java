package com.lubdhak.hederaapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lubdhak.hederaapplication.model.SellUser;

@Repository
public interface SellUserRepository extends JpaRepository<SellUser, Long> {

	Optional<SellUser> findByUsername(String username);
	List<SellUser>     findByAccountid(String accountid);
	List<SellUser>     findByFiatprice(String fiatprice);
	List<SellUser>     findByStoplose(String stoplose);
	List<SellUser>     findByStoplose1(String stoplose1);
	List<SellUser>     findByStoplose2(String stoplose2);
	List<SellUser>     findByStoplose3(String stoplose3);
	List<SellUser>     findByStoplose4(String stoplose4);
	Optional<SellUser> findById(Long id);
}
