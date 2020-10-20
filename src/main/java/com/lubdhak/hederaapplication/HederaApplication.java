package com.lubdhak.hederaapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class HederaApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(HederaApplication.class, args);

	}

}
