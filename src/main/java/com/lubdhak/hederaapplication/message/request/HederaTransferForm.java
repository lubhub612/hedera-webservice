package com.lubdhak.hederaapplication.message.request;

public class HederaTransferForm {
	
    private String hedaccountid;
    
    private String hedprivatekey;
    
    private String thedaccountid;
    
    private Double hedamount1;

	public String getHedaccountid() {
		return hedaccountid;
	}

	public void setHedaccountid(String hedaccountid) {
		this.hedaccountid = hedaccountid;
	}

	public String getHedprivatekey() {
		return hedprivatekey;
	}

	public void setHedprivatekey(String hedprivatekey) {
		this.hedprivatekey = hedprivatekey;
	}

	public String getThedaccountid() {
		return thedaccountid;
	}

	public void setThedaccountid(String thedaccountid) {
		this.thedaccountid = thedaccountid;
	}

	public Double getHedamount1() {
		return hedamount1;
	}

	public void setHedamount1(Double hedamount1) {
		this.hedamount1 = hedamount1;
	}

	

}
