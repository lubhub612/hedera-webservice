package com.lubdhak.hederaapplication.message.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private String privatekey;
	private String accountid;
	private String publickey;
	private String privateaddress;
	private String publicaddress;
	private String balance;

	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String accessToken, String username, String accountid, String publickey, String privatekey, String privateaddress, String publicaddress, String balance, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
        this.privatekey = privatekey;
		this.accountid = accountid;
		this.publickey = publickey;
		this.privateaddress = privateaddress;
		this.publicaddress = publicaddress;
		this.balance = balance;
		this.authorities = authorities;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
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

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	

	

	

	

	
	
	
}
