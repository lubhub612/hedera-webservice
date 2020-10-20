package com.lubdhak.hederaapplication.Stask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.HederaNetworkException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.CryptoTransferTransaction;
import com.lubdhak.hederaapplication.model.BuyUser;
import com.lubdhak.hederaapplication.model.Helpers;
import com.lubdhak.hederaapplication.model.SellUser;
import com.lubdhak.hederaapplication.repository.BuyUserRepository;
import com.lubdhak.hederaapplication.repository.SellUserRepository;
import com.lubdhak.hederaapplication.security.services.BuyUserService;
import com.lubdhak.hederaapplication.security.services.SellUserService;

import io.github.cdimascio.dotenv.Dotenv;


@Component
public class SchedularTask {

	@Autowired
	BuyUserRepository buyuserRepository;
	
	@Autowired
	SellUserRepository selluserRepository;
	
	@Autowired
	BuyUserService buserService;
	
	@Autowired
	SellUserService suserService;
	
	@Scheduled(fixedRate = 30000)
	public void scheduleTaskWithFixedRate() throws IOException, HederaNetworkException, HederaException {
		 
		 
		    
		String sURL = "https://api.bittrex.com/api/v1.1/public/getticker?market=BTC-Hbar";
	    URL url = new URL(sURL);
	    URLConnection request = url.openConnection();
	    request.connect();
	    JsonParser jp = new JsonParser(); 
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject();
	    JsonObject hbarresult = rootobj.getAsJsonObject("result");
	    JsonElement lastprice1 = hbarresult.get("Last");
		String lastprice = lastprice1.toString();
		System.out.println("Current hBar price is:: " +lastprice);
		    List<BuyUser> userData2 = buserService.findAllBuyersPrice(lastprice);
		     if (userData2.isEmpty()) {
		      System.out.println("No Buyer Found by fiatprice");
		     }
		    
		     for(BuyUser model : userData2) {
		         System.out.println(model.getStatus());
		         if(model.getStatus()==0) {
		         model.setStatus(1);
		         buyuserRepository.save(model);
		         
		          Network.usePublicNetwork();
		 		@SuppressWarnings("resource")
		 		Server server1 = new Server("https://horizon.stellar.org");

		 		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		 		KeyPair destination = KeyPair.fromAccountId(model.getPublicaddress());

		 		
		 		server1.accounts().account(destination);

		 		
		 		AccountResponse sourceAccount = server1.accounts().account(source);

		 		
		 		Transaction transaction = new Transaction.Builder(sourceAccount)
		 		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), model.getLumenamount()).build())
		 		        
		 		        .addMemo(Memo.text("Test Transaction"))
		 		        .setTimeout(1000)
		 		        
		 		        .build();
		 	
		 		transaction.sign(source);

		 		
		 		try {
		 		  SubmitTransactionResponse response1 = server1.submitTransaction(transaction);
		 		  System.out.println("Success!");
		 		  System.out.println(response1);
		 		} catch (Exception e) {
		 		  System.out.println("Something went wrong!");
		 		  System.out.println(e.getMessage());
		 		  
		 		}
		          
		         
		         }else {
		        	 System.out.println(model.getUsername() + " Already Buy Stellar");
		         }
		         
	     }
	 List<SellUser> userData3 = suserService.findAllSellersPrice(lastprice);
		   
		     if (userData3.isEmpty()) {
		      System.out.println("No Seller Found by seller price");
		     }
		     for(SellUser model2 : userData3) {
		         System.out.println(model2.getStatus());
		         if(model2.getStatus()==0) {
		         model2.setStatus(1);
		         selluserRepository.save(model2);
		        
		         var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(model2.getAccountid());
		var newAccountId = AccountId.fromString(model2.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
		String str2 = "100000000";
    	String str1 = Double.toString(model2.getHedamount());
    	System.out.println(model2.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * model2.getHedamount()));
	    
        System.out.println(hedamount1);
		
		var senderBalanceBefore = client.getAccountBalance(operatorId);
		var receiptBalanceBefore = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceBefore);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceBefore);

		new CryptoTransferTransaction(client)
		      .addSender(operatorId, hedamount1)
		      .addRecipient(newAccountId, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "...");

		
		var senderBalanceAfter = client.getAccountBalance(operatorId);
		var receiptBalanceAfter = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceAfter);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceAfter); 
		         
		         }else {
		        	 System.out.println(model2.getUsername() + " Already Sell Stellar");
		         }
		         
	     }
		     
		
		     List<BuyUser> userData4 = buserService.findAllBuyersLose(lastprice);
		     if (userData4.isEmpty()) {
		      System.out.println("No Buyer Found by Stoplose Price");
		     }
		    for(BuyUser model3 : userData4) {
		         System.out.println(model3.getStatus());
		         if(model3.getStatus()==0) {
		         model3.setStatus(1);
		         buyuserRepository.save(model3);
		         
		          Network.usePublicNetwork();
		 		@SuppressWarnings("resource")
		 		Server server1 = new Server("https://horizon.stellar.org");

		 		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		 		KeyPair destination = KeyPair.fromAccountId(model3.getPublicaddress());

		 		
		 		server1.accounts().account(destination);

		 		
		 		AccountResponse sourceAccount = server1.accounts().account(source);

		 		
		 		Transaction transaction = new Transaction.Builder(sourceAccount)
		 		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), model3.getLumenamount()).build())
		 		        
		 		        .addMemo(Memo.text("Test Transaction"))
		 		        .setTimeout(1000)
		 		        
		 		        .build();
		 	
		 		transaction.sign(source);

		 		
		 		try {
		 		  SubmitTransactionResponse response1 = server1.submitTransaction(transaction);
		 		  System.out.println("Success!");
		 		  System.out.println(response1);
		 		} catch (Exception e) {
		 		  System.out.println("Something went wrong!");
		 		  System.out.println(e.getMessage());
		 		  
		 		}
		          
		         }else {
		        	 System.out.println(model3.getUsername() + " Already Buy Stellar");
		         }
		         
	     } 
		 
		    List<BuyUser> userData41 = buserService.findAllBuyersLose1(lastprice);
		     if (userData41.isEmpty()) {
		      System.out.println("No Buyer Found by Stoplose Price");
		     }
		    for(BuyUser model31 : userData41) {
		         System.out.println(model31.getStatus());
		         if(model31.getStatus()==0) {
		         model31.setStatus(1);
		         buyuserRepository.save(model31);
		         
		          Network.usePublicNetwork();
		 		@SuppressWarnings("resource")
		 		Server server1 = new Server("https://horizon.stellar.org");

		 		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		 		KeyPair destination = KeyPair.fromAccountId(model31.getPublicaddress());

		 		
		 		server1.accounts().account(destination);

		 		
		 		AccountResponse sourceAccount = server1.accounts().account(source);

		 		
		 		Transaction transaction = new Transaction.Builder(sourceAccount)
		 		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), model31.getLumenamount()).build())
		 		        
		 		        .addMemo(Memo.text("Test Transaction"))
		 		        .setTimeout(1000)
		 		        
		 		        .build();
		 	
		 		transaction.sign(source);

		 		
		 		try {
		 		  SubmitTransactionResponse response1 = server1.submitTransaction(transaction);
		 		  System.out.println("Success!");
		 		  System.out.println(response1);
		 		} catch (Exception e) {
		 		  System.out.println("Something went wrong!");
		 		  System.out.println(e.getMessage());
		 		  
		 		}
		          
		         }else {
		        	 System.out.println(model31.getUsername() + " Already Buy Stellar");
		         }
		         
	     } 
		 
		    List<BuyUser> userData42 = buserService.findAllBuyersLose2(lastprice);
		     if (userData42.isEmpty()) {
		      System.out.println("No Buyer Found by Stoplose Price");
		     }
		    for(BuyUser model32 : userData42) {
		         System.out.println(model32.getStatus());
		         if(model32.getStatus()==0) {
		         model32.setStatus(1);
		         buyuserRepository.save(model32);
		         
		          Network.usePublicNetwork();
		 		@SuppressWarnings("resource")
		 		Server server1 = new Server("https://horizon.stellar.org");

		 		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		 		KeyPair destination = KeyPair.fromAccountId(model32.getPublicaddress());

		 		
		 		server1.accounts().account(destination);

		 		
		 		AccountResponse sourceAccount = server1.accounts().account(source);

		 		
		 		Transaction transaction = new Transaction.Builder(sourceAccount)
		 		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), model32.getLumenamount()).build())
		 		        
		 		        .addMemo(Memo.text("Test Transaction"))
		 		        .setTimeout(1000)
		 		        
		 		        .build();
		 	
		 		transaction.sign(source);

		 		
		 		try {
		 		  SubmitTransactionResponse response1 = server1.submitTransaction(transaction);
		 		  System.out.println("Success!");
		 		  System.out.println(response1);
		 		} catch (Exception e) {
		 		  System.out.println("Something went wrong!");
		 		  System.out.println(e.getMessage());
		 		  
		 		}
		          
		         }else {
		        	 System.out.println(model32.getUsername() + " Already Buy Stellar");
		         }
		         
	     }   
		    
		    List<BuyUser> userData43 = buserService.findAllBuyersLose3(lastprice);
		     if (userData43.isEmpty()) {
		      System.out.println("No Buyer Found by Stoplose Price");
		     }
		    for(BuyUser model33: userData43) {
		         System.out.println(model33.getStatus());
		         if(model33.getStatus()==0) {
		         model33.setStatus(1);
		         buyuserRepository.save(model33);
		         
		          Network.usePublicNetwork();
		 		@SuppressWarnings("resource")
		 		Server server1 = new Server("https://horizon.stellar.org");

		 		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		 		KeyPair destination = KeyPair.fromAccountId(model33.getPublicaddress());

		 		
		 		server1.accounts().account(destination);

		 		
		 		AccountResponse sourceAccount = server1.accounts().account(source);

		 		
		 		Transaction transaction = new Transaction.Builder(sourceAccount)
		 		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), model33.getLumenamount()).build())
		 		        
		 		        .addMemo(Memo.text("Test Transaction"))
		 		        .setTimeout(1000)
		 		        
		 		        .build();
		 	
		 		transaction.sign(source);

		 		
		 		try {
		 		  SubmitTransactionResponse response1 = server1.submitTransaction(transaction);
		 		  System.out.println("Success!");
		 		  System.out.println(response1);
		 		} catch (Exception e) {
		 		  System.out.println("Something went wrong!");
		 		  System.out.println(e.getMessage());
		 		  
		 		}
		          
		         }else {
		        	 System.out.println(model33.getUsername() + " Already Buy Stellar");
		         }
		         
	     }
		    
		    List<BuyUser> userData44 = buserService.findAllBuyersLose4(lastprice);
		     if (userData44.isEmpty()) {
		      System.out.println("No Buyer Found by Stoplose Price");
		     }
		    for(BuyUser model34 : userData44) {
		         System.out.println(model34.getStatus());
		         if(model34.getStatus()==0) {
		         model34.setStatus(1);
		         buyuserRepository.save(model34);
		         
		          Network.usePublicNetwork();
		 		@SuppressWarnings("resource")
		 		Server server1 = new Server("https://horizon.stellar.org");

		 		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		 		KeyPair destination = KeyPair.fromAccountId(model34.getPublicaddress());

		 		
		 		server1.accounts().account(destination);

		 		
		 		AccountResponse sourceAccount = server1.accounts().account(source);

		 		
		 		Transaction transaction = new Transaction.Builder(sourceAccount)
		 		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), model34.getLumenamount()).build())
		 		        
		 		        .addMemo(Memo.text("Test Transaction"))
		 		        .setTimeout(1000)
		 		        
		 		        .build();
		 	
		 		transaction.sign(source);

		 		
		 		try {
		 		  SubmitTransactionResponse response1 = server1.submitTransaction(transaction);
		 		  System.out.println("Success!");
		 		  System.out.println(response1);
		 		} catch (Exception e) {
		 		  System.out.println("Something went wrong!");
		 		  System.out.println(e.getMessage());
		 		  
		 		}
		          
		         }else {
		        	 System.out.println(model34.getUsername() + " Already Buy Stellar");
		         }
		         
	     } 
		    
		   
		     List<SellUser> userData5 = suserService.findAllSellersLose(lastprice);
		   
		     if (userData5.isEmpty()) {
		      System.out.println("No Seller Found by stop lose price");
		     }
		     for(SellUser model4 : userData5) {
		         System.out.println(model4.getStatus());
		         if(model4.getStatus()==0) {
		         model4.setStatus(1);
		         selluserRepository.save(model4);
		        
		var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(model4.getAccountid());
		var newAccountId = AccountId.fromString(model4.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
		String str2 = "100000000";
    	String str1 = Double.toString(model4.getHedamount());
    	System.out.println(model4.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * model4.getHedamount()));  
        System.out.println(hedamount1);
		
		var senderBalanceBefore = client.getAccountBalance(operatorId);
		var receiptBalanceBefore = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceBefore);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceBefore);

		new CryptoTransferTransaction(client)
		      .addSender(operatorId, hedamount1)
		      .addRecipient(newAccountId, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "...");

		
		var senderBalanceAfter = client.getAccountBalance(operatorId);
		var receiptBalanceAfter = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceAfter);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceAfter); 
		         
		         }else {
		        	 System.out.println(model4.getUsername() + " Already Sell Stellar");
		         }
		         
	     }
		    
		     List<SellUser> userData51 = suserService.findAllSellersLose1(lastprice);
			   
		     if (userData51.isEmpty()) {
		      System.out.println("No Seller Found by stop lose price");
		     }
		     for(SellUser model41 : userData51) {
		         System.out.println(model41.getStatus());
		         if(model41.getStatus()==0) {
		         model41.setStatus(1);
		         selluserRepository.save(model41);
		        
		         var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(model41.getAccountid());
		var newAccountId = AccountId.fromString(model41.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
		String str2 = "100000000";
    	String str1 = Double.toString(model41.getHedamount());
    	System.out.println(model41.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * model41.getHedamount()));  
        System.out.println(hedamount1);
		
		var senderBalanceBefore = client.getAccountBalance(operatorId);
		var receiptBalanceBefore = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceBefore);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceBefore);

		new CryptoTransferTransaction(client)
		      .addSender(operatorId, hedamount1)
		      .addRecipient(newAccountId, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "...");

		
		var senderBalanceAfter = client.getAccountBalance(operatorId);
		var receiptBalanceAfter = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceAfter);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceAfter); 
		         
		         }else {
		        	 System.out.println(model41.getUsername() + " Already Sell Stellar");
		         }
		         
	     }
		     
		     List<SellUser> userData52 = suserService.findAllSellersLose2(lastprice);
			   
		     if (userData52.isEmpty()) {
		      System.out.println("No Seller Found by stop lose price");
		     }
		     for(SellUser model42 : userData52) {
		         System.out.println(model42.getStatus());
		         if(model42.getStatus()==0) {
		         model42.setStatus(1);
		         selluserRepository.save(model42);
		        
		var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(model42.getAccountid());
		var newAccountId = AccountId.fromString(model42.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
		String str2 = "100000000";
    	String str1 = Double.toString(model42.getHedamount());
    	System.out.println(model42.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * model42.getHedamount()));  
        System.out.println(hedamount1);
		
		var senderBalanceBefore = client.getAccountBalance(operatorId);
		var receiptBalanceBefore = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceBefore);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceBefore);

		new CryptoTransferTransaction(client)
		      .addSender(operatorId, hedamount1)
		      .addRecipient(newAccountId, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "...");

		
		var senderBalanceAfter = client.getAccountBalance(operatorId);
		var receiptBalanceAfter = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceAfter);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceAfter); 
		         
		         }else {
		        	 System.out.println(model42.getUsername() + " Already Sell Stellar");
		         }
		         
	     }
		     
		     List<SellUser> userData53 = suserService.findAllSellersLose3(lastprice);
			   
		     if (userData53.isEmpty()) {
		      System.out.println("No Seller Found by stop lose price");
		     }
		     for(SellUser model43 : userData53) {
		         System.out.println(model43.getStatus());
		         if(model43.getStatus()==0) {
		         model43.setStatus(1);
		         selluserRepository.save(model43);
		        
		         var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(model43.getAccountid());
		var newAccountId = AccountId.fromString(model43.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
		String str2 = "100000000";
    	String str1 = Double.toString(model43.getHedamount());
    	System.out.println(model43.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * model43.getHedamount())); 
        System.out.println(hedamount1);
		
		var senderBalanceBefore = client.getAccountBalance(operatorId);
		var receiptBalanceBefore = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceBefore);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceBefore);

		new CryptoTransferTransaction(client)
		      .addSender(operatorId, hedamount1)
		      .addRecipient(newAccountId, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "...");

		
		var senderBalanceAfter = client.getAccountBalance(operatorId);
		var receiptBalanceAfter = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceAfter);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceAfter); 
		         
		         }else {
		        	 System.out.println(model43.getUsername() + " Already Sell Stellar");
		         }
		         
	     }    
		    
		     List<SellUser> userData54 = suserService.findAllSellersLose4(lastprice);
			   
		     if (userData54.isEmpty()) {
		      System.out.println("No Seller Found by stop lose price");
		     }
		     for(SellUser model44 : userData54) {
		         System.out.println(model44.getStatus());
		         if(model44.getStatus()==0) {
		         model44.setStatus(1);
		         selluserRepository.save(model44);
		        
		         var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(model44.getAccountid());
		var newAccountId = AccountId.fromString(model44.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
		String str2 = "100000000";
    	String str1 = Double.toString(model44.getHedamount());
    	System.out.println(model44.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * model44.getHedamount()));  
        System.out.println(hedamount1);
		
		var senderBalanceBefore = client.getAccountBalance(operatorId);
		var receiptBalanceBefore = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceBefore);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceBefore);

		new CryptoTransferTransaction(client)
		      .addSender(operatorId, hedamount1)
		      .addRecipient(newAccountId, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "...");

		
		var senderBalanceAfter = client.getAccountBalance(operatorId);
		var receiptBalanceAfter = client.getAccountBalance(newAccountId);

		
		System.out.println("" + operatorId + " balance = " + senderBalanceAfter);
		System.out.println("" + newAccountId + " balance = " + receiptBalanceAfter); 
		        
		         }else {
		        	 System.out.println(model44.getUsername() + " Already Sell Stellar");
		         }
		         
	     }
		     
		     
		     
	}
}
