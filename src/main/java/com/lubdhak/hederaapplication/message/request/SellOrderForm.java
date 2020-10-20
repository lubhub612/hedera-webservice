package com.lubdhak.hederaapplication.message.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SellOrderForm {

	@NotBlank
    @Size(min = 3, max = 50)
    private String username;

    private Set<String> role;

    private String privatekey;
    
    private String publickey;
    
    private String accountid;
    
    private String privateaddress;
    
    private String publicaddress;
    
    private String fiatprice;
	
    private Double hedamount;
    
    private String lumenamount;
    
    private String totalprice;
    
    private String stoplose;
    
    private String stoplose1;
    
    private String stoplose2;
    
    private String stoplose3;
    
    private String stoplose4;
    
    private Integer status;
    
    private Integer ostatus;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
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

	public String getFiatprice() {
		return fiatprice;
	}

	public void setFiatprice(String fiatprice) {
		this.fiatprice = fiatprice;
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
