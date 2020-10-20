package com.lubdhak.hederaapplication.message.response;



public class BuyUserResponse {

    private String username;
    private String privatekey;
    private String publickey;
    private String accountid;
    private String privateaddress;
    private String publicaddress;
    private String fiatprice;
    private String hedamount;
    private String lumenamount;
    private String totalprice;
    private Integer status;
	 
    public BuyUserResponse(String username, String privatekey, String publickey, String accountid, String privateAddress, String publicAddress, String fiatPrice, String hedAmount, String lumenAmount, String totalPrice, Integer status) {
    	
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
    	this.status = status;
    
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

	public String getFiatprice() {
		return fiatprice;
	}

	public void setFiatprice(String fiatprice) {
		this.fiatprice = fiatprice;
	}

	public String getHedamount() {
		return hedamount;
	}

	public void setHedamount(String hedamount) {
		this.hedamount = hedamount;
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
	
}
