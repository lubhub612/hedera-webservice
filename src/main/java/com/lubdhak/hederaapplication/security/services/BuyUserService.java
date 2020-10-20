package com.lubdhak.hederaapplication.security.services;

import java.util.List;

import com.lubdhak.hederaapplication.model.BuyUser;

public interface BuyUserService {
	List<BuyUser> findAllBuyers(String accountid);
	List<BuyUser> findAllBuyersPrice(String fiatprice);
	List<BuyUser> findAllBuyersLose(String stoplose);
	List<BuyUser> findAllBuyersLose1(String stoplose1);
	List<BuyUser> findAllBuyersLose2(String stoplose2);
	List<BuyUser> findAllBuyersLose3(String stoplose3);
	List<BuyUser> findAllBuyersLose4(String stoplose4);
	//////////List<BuyUser> findBuyerOrder(Long id);
}
