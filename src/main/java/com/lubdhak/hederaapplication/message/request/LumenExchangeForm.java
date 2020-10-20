package com.lubdhak.hederaapplication.message.request;

public class LumenExchangeForm {
	
    private String privatekey;
    
    private String accountid;
    
    private String lumenamount;
    
    private Double hedamount;

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

	public String getLumenamount() {
		return lumenamount;
	}

	public void setLumenamount(String lumenamount) {
		this.lumenamount = lumenamount;
	}

	public Double getHedamount() {
		return hedamount;
	}

	public void setHedamount(Double hedamount) {
		this.hedamount = hedamount;
	}

	


}
