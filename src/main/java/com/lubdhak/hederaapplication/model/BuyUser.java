package com.lubdhak.hederaapplication.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.HederaNetworkException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.CryptoTransferTransaction;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;

import io.github.cdimascio.dotenv.Dotenv;

@Entity
@Table(name = "buyhed_users")
public class BuyUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    @NotBlank
    @Size(min=3, max = 50)
    private String username;
    
    @NotBlank
    private String privatekey;
    
    @NotBlank
    private String publickey;
    
    @NotBlank
    private String accountid;
    
    @NotBlank
    private String privateaddress;
    
    @NotBlank
    private String publicaddress;
    
    @NotBlank
    private String fiatprice;
    
    @NotNull
    private  Double hedamount;
    
    @NotBlank
    private String lumenamount;
    
    @NotBlank
    private String totalprice;
    
    @NotBlank
    private String stoplose;
    
    @NotBlank
    private String stoplose1;
    
    @NotBlank
    private String stoplose2;
    
    @NotBlank
    private String stoplose3;
    
    @NotBlank
    private String stoplose4;
    
    @NotNull
    private Integer status;
    
    @NotNull
    private Integer ostatus;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buyuser_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    public BuyUser() {}
    
    
    public BuyUser(String username, String privatekey, String publickey, String accountid, String privateAddress, String publicAddress, String fiatPrice, Double hedAmount, String lumenAmount, String totalPrice, String stoplose, String stoplose1, String stoplose2, String stoplose3, String stoplose4, Integer status, Integer ostatus) throws  HederaNetworkException, HederaException {
    
    	String str2 = "100000000";
    	String str1 = Double.toString(hedAmount);
    	System.out.println(hedAmount);
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * hedAmount));
        
        System.out.println(hedamount1);

   	     var newAccountId = AccountId.fromString(accountid);
		System.out.println(newAccountId);
		var newAccountId2 = AccountId.fromString("0.0.907");
		System.out.println(newAccountId2);
		var nodeAddress = Objects.requireNonNull(Dotenv.load().get("NODE_ADDRESS"));
		System.out.println(nodeAddress);
		var nodeId = AccountId.fromString(Dotenv.load().get("NODE_ID"));
		System.out.println(nodeId);
		var operatorKey = Ed25519PrivateKey.fromString(privatekey);
		var client1 = new Client(Map.of(nodeId, nodeAddress));
		client1.setOperator(newAccountId, operatorKey);
		new CryptoTransferTransaction(client1)
		      .addSender(newAccountId, hedamount1)
		      .addRecipient(newAccountId2, hedamount1)
		      .setMemo("transfer test")
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "..."); 
		var senderBalanceAfter = client1.getAccountBalance(newAccountId);
		System.out.println("" + newAccountId + " balance = " + senderBalanceAfter);
   	 
    	System.out.println("price: " + fiatPrice);
    	System.out.println("stellar amount : " + lumenAmount);
    	System.out.println("hbar amount : " +  hedAmount);
    	fiatPrice.replaceFirst("^0.", ".");
    	System.out.println(fiatPrice);
    	System.out.println(fiatPrice.charAt(0));
    	System.out.println(fiatPrice.charAt(1));
    	if(fiatPrice.charAt(1) == '.') {
    		fiatPrice.substring(2);
    		System.out.println(fiatPrice.substring(2));
    	String output = fiatPrice.substring(2).trim();
    	System.out.println("aa:" + output);
    	System.out.println(output.length());
    	String strl5 = "00";
    	String strl6 = "0";
    	String output1;
    	if(output.length() == 6) {
    		output1 = output+strl5;
    		System.out.println("a :" +output1);
    	}
    	else if(output.length() == 7){
    		output1 = output+strl6;
    		System.out.println("b :" +output1);
    	}
    	else {
    		output1 = output;
    		System.out.println("c :" +output1);
    	}
    	
    	System.out.println("d: "+output1);
    	System.out.println(output1.length());
    	for( ;output1.length() > 1 && output1.charAt(0) == '0'; output1 = output1.substring(1));

    	System.out.println("CC :"+ output);
    	System.out.println(output.length());
    	
    	System.out.println("DD :" +output1);
    	System.out.println(output1.length());
    	
    	String str =   "1";
    	String str11 = "2";
    	String str12 = "3";
    	String str13 = "4";
    	String str14 = "5";
    	long d = Long.parseLong(str);
    	long d1 = Long.parseLong(str11);
    	long d2 = Long.parseLong(str12);
    	long d3 = Long.parseLong(str13);
    	long d4 = Long.parseLong(str14);
    	long fiatPrice1 = Long.parseLong(output1);
    	System.out.println("aaa :" + d);
    	System.out.println("aaa1 :" + d1);
    	System.out.println("aaa2 :" + d2);
    	System.out.println("aaa3 :" + d3);
    	System.out.println("aaa4 :" + d4);
    	
    	Long  add1 = Long.sum(d, fiatPrice1);
    	Long  add2 = Long.sum(d1, fiatPrice1);
    	Long  add3 = Long.sum(d2, fiatPrice1);
    	Long  add4 = Long.sum(d3, fiatPrice1);
    	Long  add5 = Long.sum(d4, fiatPrice1);
    	System.out.println("aaa5 :" +add1);
    	System.out.println("aaa6 :" +add2);
    	System.out.println("aaa7 :" +add3);
    	System.out.println("aaa8 :" + add4);
    	System.out.println("aaa9 :" + add5);
    	String l1 = Long.toString(add1);
    	String l2 = Long.toString(add2);
    	String l3 = Long.toString(add3);
    	String l4 = Long.toString(add4);
    	String l5 = Long.toString(add5);
    	System.out.println("aa1 :" + l1);
    	System.out.println("aa2 :" + l2);
    	System.out.println("aa3 :" + l3);
    	System.out.println("aa4 :" + l4);
    	System.out.println("aa5 :" + l5);
    	System.out.println(l1.length());
    	String s1;
    	if(l1.length() == 3) {
   		   s1 = "0.00000";
   	    }
   	    else if(l1.length() == 2) {
   		   s1 = "0.000000";
   	    }
   	    else {
   		   s1 = "0.0000000";
   	    }
    	String add11 = s1+l1; 
    	String add12 = s1+l2;
    	String add13 = s1+l3;
    	String add14 = s1+l4;
    	String add15 = s1+l5;
    	System.out.println("a1 :" + add11);
    	System.out.println("a2 :" + add12);
    	System.out.println("a3 :" + add13);
    	System.out.println("a4 :" + add14);
    	System.out.println("a5 :" + add15);
    	
    	
    	this.username = username;
        this.privatekey = privatekey;
        this.publickey = publickey;
        this.accountid = accountid;
        this.privateaddress = privateAddress;
        this.publicaddress = publicAddress;
        this.fiatprice = fiatPrice;
        this.hedamount = hedAmount;
    	this.lumenamount = lumenAmount;
    	this.totalprice = totalPrice;
    	this.stoplose  = add11;
    	this.stoplose1 = add12;
    	this.stoplose2 = add13;
    	this.stoplose3 = add14;
    	this.stoplose4 = add15;
    	this.status  = 0;
    	this.ostatus  = 0;
    	}
    	else {
    		throw new java.lang.RuntimeException("Hedera Price not suported..... ");
    	}
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPrivatekey() {
		return privatekey;
	}


	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}


	public String getPublickey() {
		return publickey;
	}


	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}


	public String getAccountid() {
		return accountid;
	}


	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}


	public String getPrivateaddress() {
		return privateaddress;
	}


	public void setPrivateaddress(String privateaddress) {
		this.privateaddress = privateaddress;
	}


	public String getPublicaddress() {
		return publicaddress;
	}


	public void setPublicaddress(String publicaddress) {
		this.publicaddress = publicaddress;
	}


	public String getLumenamount() {
		return lumenamount;
	}


	public void setLumenamount(String lumenamount) {
		this.lumenamount = lumenamount;
	}


	public String getTotalprice() {
		return totalprice;
	}


	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getStoplose() {
		return stoplose;
	}


	public void setStoplose(String stoplose) {
		this.stoplose = stoplose;
	}


	public Double getHedamount() {
		return hedamount;
	}


	public void setHedamount(Double hedamount) {
		this.hedamount = hedamount;
	}


	public String getFiatprice() {
		return fiatprice;
	}


	public void setFiatprice(String fiatprice) {
		this.fiatprice = fiatprice;
	}


	public String getStoplose1() {
		return stoplose1;
	}


	public void setStoplose1(String stoplose1) {
		this.stoplose1 = stoplose1;
	}


	public String getStoplose2() {
		return stoplose2;
	}


	public void setStoplose2(String stoplose2) {
		this.stoplose2 = stoplose2;
	}


	public String getStoplose3() {
		return stoplose3;
	}


	public void setStoplose3(String stoplose3) {
		this.stoplose3 = stoplose3;
	}


	public String getStoplose4() {
		return stoplose4;
	}


	public void setStoplose4(String stoplose4) {
		this.stoplose4 = stoplose4;
	}


	public Integer getOstatus() {
		return ostatus;
	}


	public void setOstatus(Integer ostatus) {
		this.ostatus = ostatus;
	}


	


	
    
    
    
  }
    
