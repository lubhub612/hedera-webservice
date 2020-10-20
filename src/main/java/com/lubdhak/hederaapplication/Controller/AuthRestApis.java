package com.lubdhak.hederaapplication.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.HederaNetworkException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.CryptoTransferTransaction;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.lubdhak.hederaapplication.message.request.BuyOrderForm;
import com.lubdhak.hederaapplication.message.request.HederaExchangeForm;
import com.lubdhak.hederaapplication.message.request.HederaTransferForm;
import com.lubdhak.hederaapplication.message.request.LoginForm;
import com.lubdhak.hederaapplication.message.request.LoginPrForm;
import com.lubdhak.hederaapplication.message.request.LumenExchangeForm;
import com.lubdhak.hederaapplication.message.request.SellOrderForm;
import com.lubdhak.hederaapplication.message.request.SignUpForm;
import com.lubdhak.hederaapplication.message.request.SignUpPrForm;
import com.lubdhak.hederaapplication.message.response.BuyUserResponse;
import com.lubdhak.hederaapplication.message.response.JwtResponse;
import com.lubdhak.hederaapplication.message.response.ResponseMessage;
import com.lubdhak.hederaapplication.message.response.SellUserResponse;
import com.lubdhak.hederaapplication.model.BuyUser;
import com.lubdhak.hederaapplication.model.Helpers;
import com.lubdhak.hederaapplication.model.ImpUser;
import com.lubdhak.hederaapplication.model.Role;
import com.lubdhak.hederaapplication.model.RoleName;
import com.lubdhak.hederaapplication.model.SellUser;
import com.lubdhak.hederaapplication.model.User;
import com.lubdhak.hederaapplication.repository.BuyUserRepository;
import com.lubdhak.hederaapplication.repository.ImpUserRepository;
import com.lubdhak.hederaapplication.repository.RoleRepository;
import com.lubdhak.hederaapplication.repository.SellUserRepository;
import com.lubdhak.hederaapplication.repository.UserRepository;
import com.lubdhak.hederaapplication.security.jwt.JwtProvider;
import com.lubdhak.hederaapplication.security.services.BuyUserService;
import com.lubdhak.hederaapplication.security.services.SellUserService;

import io.github.cdimascio.dotenv.Dotenv;
import javassist.NotFoundException;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestApis {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
    
	@Autowired
	ImpUserRepository impuserRepository;
	
	@Autowired
	BuyUserRepository buyuserRepository;
	
	@Autowired
	SellUserRepository selluserRepository;
	
	
	@Autowired
	BuyUserService buserService;
	
	@Autowired
	SellUserService suserService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) throws HederaException, IOException {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User userData = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + loginRequest.getUsername()));
		System.out.println(userData.getAccountid());
		System.out.println(userData.getId());
		System.out.println(userData.getPrivatekey());
		System.out.println(userData.getPublickey());
		System.out.println(userData.getPrivateaddress());
		System.out.println(userData.getPublicaddress());
		var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
        System.out.println(operatorId);
		var newAccountId = AccountId.fromString(userData.getAccountid());
		System.out.println(newAccountId);
		var balance1 = client.getAccountBalance(newAccountId);
		String balance = String.valueOf(balance1);
		System.out.println("balance = " + balance1); 
		///String  balance = "100000";
		///System.out.println("newbalance = " + balance);
		
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),userData.getPrivatekey(),userData.getAccountid(),userData.getPublickey(),userData.getPrivateaddress(),userData.getPublicaddress(), balance, userDetails.getAuthorities()));
	
	}		

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws HederaException, MalformedURLException, IOException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		

		// Creating user's account
		User user = new User(signUpRequest.getUsername(),
				encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getPrivatekey(),
				signUpRequest.getPublickey(),
				signUpRequest.getAccountid(),
				signUpRequest.getPrivateaddress(),
				signUpRequest.getPublicaddress(),
				signUpRequest.getCpharse());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		
		
	  strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	
	@PostMapping("/hedexlumen")
	public ResponseEntity<?> exchangeUser(@Valid @RequestBody LumenExchangeForm lumenExchangeRequest) throws IOException, HederaException {
		
		System.out.println(lumenExchangeRequest.getPrivatekey());
		System.out.println(lumenExchangeRequest.getAccountid());
		System.out.println(lumenExchangeRequest.getLumenamount());
		System.out.println(lumenExchangeRequest.getHedamount());
		
		Network.usePublicNetwork();
		@SuppressWarnings("resource")
		Server server1 = new Server("https://horizon.stellar.org");

		KeyPair source = KeyPair.fromSecretSeed(lumenExchangeRequest.getPrivatekey());
		KeyPair destination = KeyPair.fromAccountId("GC6W2PB7TBSJG6ROQTYXX7BT3KCD3X3G6WBPM3BGEGGP72LRTJDWBDXP");
        server1.accounts().account(destination);
        AccountResponse sourceAccount = server1.accounts().account(source);
        Transaction transaction = new Transaction.Builder(sourceAccount)
		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), lumenExchangeRequest.getLumenamount()).build())
		        
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
		
		
		
		String str2 = "100000000";
    	String str1 = Double.toString(lumenExchangeRequest.getHedamount());
    	System.out.println(lumenExchangeRequest.getHedamount());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * lumenExchangeRequest.getHedamount()));
		var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
		System.out.println(lumenExchangeRequest.getAccountid());
		var newAccountId = AccountId.fromString(lumenExchangeRequest.getAccountid());
		System.out.println(newAccountId);
		var balance = client.getAccountBalance(operatorId);
		var balance1 = client.getAccountBalance(newAccountId);
		System.out.println("balance = " + balance); 
		System.out.println("newbalance = " + balance1);  
		var amount = 210000; 
		System.out.println(amount);
	    //long num = Long.parseLong(lumenExchangeRequest.getHedamount());  
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
		
		
		return new ResponseEntity<>(new ResponseMessage("User Exchange hBar with Strellar  successfully!"), HttpStatus.OK);
	}
	
	@PostMapping("/lumenexhed")
public ResponseEntity<?> exchangeUser1(@Valid @RequestBody HederaExchangeForm hederaExchangeRequest) throws IOException, HederaException {
		
		System.out.println(hederaExchangeRequest.getHedaccountid());
		System.out.println(hederaExchangeRequest.getHedamount1());
		System.out.println(hederaExchangeRequest.getLumenamount1());
		System.out.println(hederaExchangeRequest.getLumenpublickey());
		System.out.println(hederaExchangeRequest.getHedprivatekey());
		
		Network.usePublicNetwork();
		@SuppressWarnings("resource")
		Server server1 = new Server("https://horizon.stellar.org");

		KeyPair source = KeyPair.fromSecretSeed("SDHRC2I5R2T2QBPOHRPHMA5LXMGXPTLAMX56CLYKFHM4GSAYP6SQLP67");
		KeyPair destination = KeyPair.fromAccountId(hederaExchangeRequest.getLumenpublickey());

		
		server1.accounts().account(destination);

		
		AccountResponse sourceAccount = server1.accounts().account(source);

		
		Transaction transaction = new Transaction.Builder(sourceAccount)
		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(),hederaExchangeRequest.getHedamount1() ).build())
		        
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
		
		String str2 = "100000000";
    	String str1 = Double.toString(hederaExchangeRequest.getLumenamount1());
    	System.out.println(hederaExchangeRequest.getLumenamount1());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * hederaExchangeRequest.getLumenamount1()));
		
		var newAccountId = AccountId.fromString(hederaExchangeRequest.getHedaccountid());
		System.out.println(newAccountId);
		var newAccountId2 = AccountId.fromString("0.0.907");
		System.out.println(newAccountId2);
		var nodeAddress = Objects.requireNonNull(Dotenv.load().get("NODE_ADDRESS"));
		System.out.println(nodeAddress);
		var nodeId = AccountId.fromString(Dotenv.load().get("NODE_ID"));
		System.out.println(nodeId);
		var operatorKey = Ed25519PrivateKey.fromString(hederaExchangeRequest.getHedprivatekey());
		var client1 = new Client(Map.of(nodeId, nodeAddress));
		client1.setOperator(newAccountId, operatorKey);
	    var amount = 10000;
	    System.out.println(amount);
	    ////long num = Long.parseLong(hederaExchangeRequest.getLumenamount1());  
        System.out.println(hedamount1);
		new CryptoTransferTransaction(client1)
		      .addSender(newAccountId, hedamount1)
		      .addRecipient(newAccountId2, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		System.out.println("transferred " + hedamount1 + "..."); 
		var senderBalanceAfter = client1.getAccountBalance(newAccountId);
		System.out.println("" + newAccountId + " balance = " + senderBalanceAfter);
		
	
		return new ResponseEntity<>(new ResponseMessage("User Exchange hBar with Strellar  successfully!"), HttpStatus.OK);
	
	}
	
	@PostMapping("/searchpk")
	public ResponseEntity<?> retrivePrUser(@Valid @RequestBody LoginPrForm userPrRequest) throws NotFoundException, HederaException, IOException{
		System.out.println("private key = " + userPrRequest.getHedprivatekey());
		var privateKey = Ed25519PrivateKey.fromString(userPrRequest.getHedprivatekey());
		 System.out.println("private key = " + privateKey);
		String privateKey1 = String.valueOf(privateKey);
		 System.out.println("private key = " + privateKey1);
		User userpk = userRepository.findByPrivatekey(privateKey1).orElseThrow(
				() -> new NotFoundException("User Not Found with -> username or email : " + userPrRequest.getHedprivatekey()));
		
		System.out.println(userpk.getId());
		System.out.println(userpk.getPrivatekey());
		System.out.println(userpk.getPublickey());
		System.out.println(userpk.getUsername());
		System.out.println(userpk.getPassword());
		System.out.println(userpk.getAccountid());
		System.out.println(userpk.getPrivateaddress());
		System.out.println(userpk.getPublicaddress());
		System.out.println(userpk.getCpharse());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userpk.getUsername(), userpk.getCpharse()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		var client = Helpers.createHederaClient();
        var operatorId = AccountId.fromString(Dotenv.load().get("OPERATOR_ID"));
        System.out.println(operatorId);
		var newAccountId = AccountId.fromString(userpk.getAccountid());
		System.out.println(newAccountId);
		var balance1 = client.getAccountBalance(newAccountId);
		String balance = String.valueOf(balance1);
		System.out.println("balance = " + balance1); 
		System.out.println("newbalance = " + balance);
		///String  balance = "100000";
		/////System.out.println("newbalance = " + balance);
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),userpk.getPrivatekey(),userpk.getAccountid(),userpk.getPublickey(),userpk.getPrivateaddress(),userpk.getPublicaddress(), balance, userDetails.getAuthorities()));
	
	
	}
	
	@PostMapping("/impsignup")
	public ResponseEntity<?> registerPrUser(@Valid @RequestBody SignUpPrForm signUpPrRequest) throws HederaException, MalformedURLException, IOException, HederaException{
		if (impuserRepository.existsByUsername(signUpPrRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}
        
		var privateKey = Ed25519PrivateKey.fromString(signUpPrRequest.getPrivatekey());
		 System.out.println("private key = " + privateKey);
		String privateKey1 = String.valueOf(privateKey);
		 System.out.println("private key = " + privateKey1);
		if (impuserRepository.existsByPrivatekey(privateKey1)) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Privatekey is already taken!"),
					HttpStatus.BAD_REQUEST);
		}
		
		if (impuserRepository.existsByAccountid(signUpPrRequest.getAccountid())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Account id is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		ImpUser impuser = new ImpUser(signUpPrRequest.getUsername(),
				encoder.encode(signUpPrRequest.getPassword()),
				signUpPrRequest.getPrivatekey(),
				signUpPrRequest.getPublickey(),
				signUpPrRequest.getAccountid(),
				signUpPrRequest.getPrivateaddress(),
				signUpPrRequest.getPublicaddress(),
				signUpPrRequest.getCpharse());

		Set<String> strRoles = signUpPrRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		impuser.setRoles(roles);
		impuserRepository.save(impuser);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	
	@PostMapping("/buytradeuser")
	public ResponseEntity<?> buyorderUser(@Valid @RequestBody BuyOrderForm buyOrderRequest) throws  HederaNetworkException, HederaException  {
		
		User userData = userRepository.findByUsername(buyOrderRequest.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + buyOrderRequest.getUsername()));
		System.out.println(userData.getAccountid());
		System.out.println(userData.getPrivatekey());
		System.out.println(userData.getPublickey());
		System.out.println(userData.getPrivateaddress());
		System.out.println(userData.getPublicaddress());

		// Creating user's account
		BuyUser buyuser = new BuyUser(buyOrderRequest.getUsername(),
				userData.getPrivatekey(),
				userData.getPublickey(),
				userData.getAccountid(),
				userData.getPrivateaddress(),
				userData.getPublicaddress(),
				buyOrderRequest.getFiatprice(),
				buyOrderRequest.getHedamount(),
				buyOrderRequest.getLumenamount(),
				buyOrderRequest.getTotalprice(),
				buyOrderRequest.getStoplose(),
				buyOrderRequest.getStoplose1(),
				buyOrderRequest.getStoplose2(),
				buyOrderRequest.getStoplose3(),
				buyOrderRequest.getStoplose4(),
				buyOrderRequest.getStatus(),
				buyOrderRequest.getOstatus());

		Set<String> strRoles = buyOrderRequest.getRole();
		Set<Role> roles = new HashSet<>();

		
		
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		buyuser.setRoles(roles);
		buyuserRepository.save(buyuser);

		return new ResponseEntity<>(new ResponseMessage("User Buy Order successfully added!"), HttpStatus.OK);
	}
	
	
	@PostMapping("/selltradeuser")
	public ResponseEntity<?> sellorderUser(@Valid @RequestBody SellOrderForm sellOrderRequest) throws IOException  {
		
		User userData = userRepository.findByUsername(sellOrderRequest.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + sellOrderRequest.getUsername()));
		System.out.println(userData.getAccountid());
		System.out.println(userData.getPrivatekey());
		System.out.println(userData.getPublickey());
		System.out.println(userData.getPrivateaddress());
		System.out.println(userData.getPublicaddress());

		// Creating user's account
		SellUser selluser = new SellUser(sellOrderRequest.getUsername(),
				userData.getPrivatekey(),
				userData.getPublickey(),
				userData.getAccountid(),
				userData.getPrivateaddress(),
				userData.getPublicaddress(),
				sellOrderRequest.getFiatprice(),
				sellOrderRequest.getHedamount(),
				sellOrderRequest.getLumenamount(),
				sellOrderRequest.getTotalprice(),
				sellOrderRequest.getStoplose(),
				sellOrderRequest.getStoplose1(),
				sellOrderRequest.getStoplose2(),
				sellOrderRequest.getStoplose3(),
				sellOrderRequest.getStoplose4(),
				sellOrderRequest.getStatus(),
				sellOrderRequest.getOstatus());

		Set<String> strRoles = sellOrderRequest.getRole();
		Set<Role> roles = new HashSet<>();
        
		 
	   strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		selluser.setRoles(roles);
		selluserRepository.save(selluser);

		return new ResponseEntity<>(new ResponseMessage("User Sell Order successfully added!"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/showbuytradeuser/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<BuyUser>> getUserBuyOrder(@PathVariable("username") String username) throws IOException {
		
		
		User userData1 =  userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		
		System.out.println("a: " + userData1.getAccountid());
		System.out.println("b: " + userData1.getPrivatekey());
		System.out.println("c: " + userData1.getPublickey());
		System.out.println("d: " + userData1.getPrivateaddress());
		System.out.println("e: " + userData1.getPublicaddress());
		
		
		
		
		List<BuyUser> userData2 = buserService.findAllBuyers(userData1.getAccountid());
		if (userData2.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}
			
		
		return new ResponseEntity<List<BuyUser>>(userData2, HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value = "/showselltradeuser/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<SellUser>> getUserSellOrder(@PathVariable("username") String username) {
		
		User userData1 = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		
		System.out.println("a: " + userData1.getAccountid());
		System.out.println("b: " + userData1.getPrivatekey());
		System.out.println("c: " + userData1.getPublickey());
		System.out.println("d: " + userData1.getPrivateaddress());
		System.out.println("e: " + userData1.getPublicaddress());
		
		List<SellUser> userData2 = suserService.findAllSellers(userData1.getAccountid());
		if (userData2.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}
		
		
		return new ResponseEntity<List<SellUser>>(userData2, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/hedtransfer")
	public ResponseEntity<?> exchangeUser11(@Valid @RequestBody HederaTransferForm hederaTransferRequest) throws HederaNetworkException, HederaException{
	
		System.out.println(hederaTransferRequest.getHedaccountid());
		System.out.println(hederaTransferRequest.getHedprivatekey());
		System.out.println(hederaTransferRequest.getThedaccountid());
		System.out.println(hederaTransferRequest.getHedamount1());
		
		String str2 = "100000000";
    	String str1 = Double.toString(hederaTransferRequest.getHedamount1());
    	System.out.println(hederaTransferRequest.getHedamount1());
    	System.out.println(str1);
    	long num2 = Long.parseLong(str2);
    	long hedamount1 = Math.round((double)(num2 * hederaTransferRequest.getHedamount1()));
		
		var newAccountId = AccountId.fromString(hederaTransferRequest.getHedaccountid());
		System.out.println(newAccountId);
		var newAccountId2 = AccountId.fromString(hederaTransferRequest.getThedaccountid());
		System.out.println(newAccountId2);
		var newAccountId3 = AccountId.fromString("0.0.15022");
		System.out.println(newAccountId3);
		var nodeAddress = Objects.requireNonNull(Dotenv.load().get("NODE_ADDRESS"));
		System.out.println(nodeAddress);
		var nodeId = AccountId.fromString(Dotenv.load().get("NODE_ID"));
		System.out.println(nodeId);
		var operatorKey = Ed25519PrivateKey.fromString(hederaTransferRequest.getHedprivatekey());
		var client1 = new Client(Map.of(nodeId, nodeAddress));
		client1.setOperator(newAccountId, operatorKey);
	    var amount = 10000;
	    System.out.println(amount);
	    ///long num = Long.parseLong(hederaTransferRequest.getHedamount1());  
        System.out.println(hedamount1);
		new CryptoTransferTransaction(client1)
		      .addSender(newAccountId, hedamount1)
		      .addRecipient(newAccountId2, hedamount1)
		      .setTransactionFee(1000000)
		      .executeForReceipt();

		/*new CryptoTransferTransaction(client1)
	      .addSender(newAccountId, 3000000)
	      .addRecipient(newAccountId3, 3000000)
	      .setTransactionFee(1000000)
	      .executeForReceipt();*/
		System.out.println("transferred " + hedamount1 + "..."); 
		var senderBalanceAfter = client1.getAccountBalance(newAccountId);
		System.out.println("" + newAccountId + " balance = " + senderBalanceAfter);
		
		
		return new ResponseEntity<>(new ResponseMessage("User Transfer Hedera  successfully!"), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/showcancelbuytradeuser/{orderid}", method = RequestMethod.GET)
	public ResponseEntity<BuyUser> getUserCancelBuyOrder(@PathVariable("orderid") Long orderid) throws IOException {
		
		BuyUser userData2 = buyuserRepository.findById(orderid).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + orderid));
		
		         System.out.println(userData2.getStatus());
		         if(userData2.getStatus()==0) {
		        	 userData2.setStatus(1);
		               buyuserRepository.save(userData2);
		         }
		     
		
		return new ResponseEntity<BuyUser>(userData2, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/showcancelselltradeuser/{orderid}", method = RequestMethod.GET)
	public ResponseEntity<SellUser> getUserCancelSellOrder(@PathVariable("orderid") Long orderid) {
		
		
		SellUser userData3 = selluserRepository.findById(orderid).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + orderid));
		
        System.out.println(userData3.getStatus());
        if(userData3.getStatus()==0) {
        	userData3.setStatus(1);
        	selluserRepository.save(userData3);
        }
		         
			 
		return new ResponseEntity<SellUser>(userData3, HttpStatus.OK);
		
	}
	
}

	
	
	

