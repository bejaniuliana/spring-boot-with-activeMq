package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * DemoApplication main class to start the spring boot application
 * 
 * @author iuliana.bejan
 *
 */
@SpringBootApplication
@EnableJms
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
