package com.lubdhak.hederaapplication.security.services;


import com.lubdhak.hederaapplication.model.BuyUser;
import com.lubdhak.hederaapplication.model.ImpUser;
import com.lubdhak.hederaapplication.model.SellUser;
import com.lubdhak.hederaapplication.model.User;
import com.lubdhak.hederaapplication.repository.BuyUserRepository;
import com.lubdhak.hederaapplication.repository.ImpUserRepository;
import com.lubdhak.hederaapplication.repository.SellUserRepository;
import com.lubdhak.hederaapplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService,BuyUserService,SellUserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ImpUserRepository impuserRepository;
	
	@Autowired
	BuyUserRepository buyuserRepository;
	
	
	@Autowired
	SellUserRepository selluserRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

		return UserPrinciple.build(user);
	}
	
	@Transactional
	public UserDetails loadImpUserByUsername(String username) throws UsernameNotFoundException {

		ImpUser impuser = impuserRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

		return ImpUserPrinciple.build(impuser);
	}
	
	@Transactional
	public UserDetails loadBuyUserByUsername(String username) throws UsernameNotFoundException {

		BuyUser buyuser =  (buyuserRepository.findByUsername(username)).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		
		
		return BuyUserPrinciple.build(buyuser);
	}
	
	
	@Transactional
	public UserDetails loadSellUserByUsername(String username) throws UsernameNotFoundException {

		SellUser selluser = selluserRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

		return SellUserPrinciple.build(selluser);
	}
	
	@Transactional
	public List<BuyUser> findAllBuyers(String accountid){
		return buyuserRepository.findByAccountid(accountid);
	}
	
	@Transactional
	public List<SellUser> findAllSellers(String accountid){
		return selluserRepository.findByAccountid(accountid);
	}
	
	@Transactional
	public List<BuyUser> findAllBuyersPrice(String fiatprice){
		return buyuserRepository.findByFiatprice(fiatprice);
	}
	
	@Transactional
	public List<SellUser> findAllSellersPrice(String fiatprice){
		return selluserRepository.findByFiatprice(fiatprice);
	}
	
	
	 @Transactional
	public List<BuyUser> findAllBuyersLose(String stoplose){
		return buyuserRepository.findByStoplose(stoplose);
	}
	 
	@Transactional
	public List<BuyUser> findAllBuyersLose1(String stoplose1){
			return buyuserRepository.findByStoplose1(stoplose1);
	}
	
	@Transactional
	public List<BuyUser> findAllBuyersLose2(String stoplose2){
			return buyuserRepository.findByStoplose2(stoplose2);
	}
	
	@Transactional
	public List<BuyUser> findAllBuyersLose3(String stoplose3){
			return buyuserRepository.findByStoplose3(stoplose3);
	}
	
	@Transactional
	public List<BuyUser> findAllBuyersLose4(String stoplose4){
			return buyuserRepository.findByStoplose4(stoplose4);
	}
	 
	  @Transactional
	public List<SellUser> findAllSellersLose(String stoplose){
		return selluserRepository.findByStoplose(stoplose);
	}
	  
	@Transactional
		public List<SellUser> findAllSellersLose1(String stoplose1){
			return selluserRepository.findByStoplose1(stoplose1);
	}
	 
	@Transactional
		public List<SellUser> findAllSellersLose2(String stoplose2){
			return selluserRepository.findByStoplose2(stoplose2);
	} 
	
	@Transactional
	  public List<SellUser> findAllSellersLose3(String stoplose3){
		return selluserRepository.findByStoplose3(stoplose3);
    }
	
	@Transactional
	  public List<SellUser> findAllSellersLose4(String stoplose4){
		  return selluserRepository.findByStoplose4(stoplose4);
   }
	  
	  /*@Transactional
		public List<BuyUser> findBuyerOrder(Long id){
			return buyuserRepository.findById(id);
		}
	   @Transactional
		public List<SellUser> findSellerOrder(Integer id){
			return selluserRepository.findById(id);
		}*/
}

