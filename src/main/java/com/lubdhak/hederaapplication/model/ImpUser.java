package com.lubdhak.hederaapplication.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.AccountInfoQuery;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;


@Entity
@Table(name = "hedlu_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
public class ImpUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NotBlank
    private String password;
    
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
    private String cpharse;
    

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hedluuser_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public ImpUser() {}

    public ImpUser(String username, String password, String privateKey, String publicKey, String accountId, String privateAddress, String publicAddress, String cpharse) throws MalformedURLException, IOException, HederaException  {
       
    	KeyPair pair = KeyPair.random();
		
		System.out.println(new String(pair.getSecretSeed()));
		
		System.out.println(pair.getAccountId());
		
		/*String friendbotUrl = String.format(
				  "https://friendbot.stellar.org/?addr=%s",
				  pair.getAccountId());
				InputStream response = new URL(friendbotUrl).openStream();
				@SuppressWarnings("resource")
				String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
				System.out.println("SUCCESS! You have a new account :)\n" + body);
				
				@SuppressWarnings("resource")
				Server server = new Server("https://horizon-testnet.stellar.org");
				AccountResponse account = server.accounts().account(pair);
				System.out.println("Balances for account " + pair.getAccountId());
				for (AccountResponse.Balance balance : account.getBalances()) {
				  System.out.println(String.format(
				    "Type: %s, Code: %s, Balance: %s",
				    balance.getAssetType(),
				    balance.getAssetCode(),
				    balance.getBalance()));
				}*/
				System.out.println("private key = " + privateKey);
				System.out.println("account id = " + accountId);
				var newKey = Ed25519PrivateKey.fromString(privateKey);
		        var newPublicKey = newKey.getPublicKey();
		        var newAccountId = AccountId.fromString(accountId);
				System.out.println("private key = " + newKey);
		        System.out.println("public key = " + newPublicKey);
		        System.out.println("account id = " + newAccountId);
		        var client = Helpers.createHederaClient();
		        var query2 = new AccountInfoQuery(client)
						  .setAccountId(newAccountId);
				var accountinfo = query2.execute();
			   var key = accountinfo.getKey();
			   System.out.println("Public Key = " + key);
				String privatekey1 = String.valueOf(newKey);
				String publickey2 = String.valueOf(key);
				String publickey1 = String.valueOf(newPublicKey);
				String accountid1 = String.valueOf(newAccountId);
				if(publickey1.equals(publickey2)) {
					this.username = username;
			        this.password = password;
			        this.privatekey = privatekey1;
			        this.publickey = publickey1;
			        this.accountid = accountid1;
			        this.privateaddress = new String(pair.getSecretSeed());
			        this.publicaddress = pair.getAccountId();
			        this.cpharse = cpharse;
				}else {
					throw new java.lang.RuntimeException("AccountId or PrivateKey not authenticate ");
				}
		        	//throw new Exception("Private Key and account Mismatch");
		        	
		        
				 
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
}
