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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hederahashgraph.api.proto.java.Duration;
import com.hedera.hashgraph.sdk.TransactionReceipt;


@Entity
@Table(name = "hedlu_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
public class User {
	
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

    public User() {}

    public User(String username, String password, String privatekey, String publickey, String accountid, String privateAddress, String publicAddress, String cpharse) throws HederaException, MalformedURLException, IOException {
       
    	            var newKey = Ed25519PrivateKey.generate();
			        var newPublicKey = newKey.getPublicKey();

			        System.out.println("Private key = " + newKey);
			        System.out.println("Public key = " + newPublicKey);
			        var client = Helpers.createHederaClient();
			        AccountCreateTransaction tx = new AccountCreateTransaction(client)
			                // The only _required_ property here is `key`
			                .setKey(newKey.getPublicKey())
			                .setInitialBalance(1000)
			                .setTransactionFee(80_000_000);

			            // This will wait for the receipt to become available
			            TransactionReceipt receipt = tx.executeForReceipt();

			            AccountId newAccountId = receipt.getAccountId();
					System.out.println(newAccountId);
		String publickey1 = String.valueOf(newPublicKey);
		String accountid1 = String.valueOf(newAccountId);
		String privatekey1 = String.valueOf(newKey);

		
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
		
    	this.username = username;
        this.password = password;
        this.privatekey = privatekey1;
        this.publickey = publickey1;
        this.accountid = accountid1;
        this.privateaddress = new String(pair.getSecretSeed());
        this.publicaddress = pair.getAccountId();
        this.cpharse = cpharse;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
