package com.lubdhak.hederaapplication.message.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpPrForm {

	@NotBlank
    @Size(min = 3, max = 50)
    private String username;

    
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String privatekey;
    
    private String publickey;
    
    private String accountid;
    
    private String privateaddress;
    
    private String publicaddress;
    
    private String cpharse;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCpharse() {
		return cpharse;
	}

	public void setCpharse(String cpharse) {
		this.cpharse = cpharse;
	}
	
	
}
