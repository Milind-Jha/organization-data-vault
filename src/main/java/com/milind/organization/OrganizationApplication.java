package com.milind.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrganizationApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrganizationApplication.class, args);
	}
}
