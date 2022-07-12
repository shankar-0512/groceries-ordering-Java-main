package com.groceries.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GroceriesOrderingApp implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(GroceriesOrderingApp.class, args);
	}

}
