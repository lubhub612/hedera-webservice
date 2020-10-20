package com.lubdhak.hederaapplication.security.services;

import java.util.List;

import com.lubdhak.hederaapplication.model.SellUser;

public interface SellUserService {

	List<SellUser> findAllSellers(String accountid);
	List<SellUser> findAllSellersPrice(String fiatprice);
	List<SellUser> findAllSellersLose(String stoplose);
	List<SellUser> findAllSellersLose1(String stoplose1);
	List<SellUser> findAllSellersLose2(String stoplose2);
	List<SellUser> findAllSellersLose3(String stoplose3);
	List<SellUser> findAllSellersLose4(String stoplose4);
	/////List<SellUser> findSellerOrder(Integer id);
}
