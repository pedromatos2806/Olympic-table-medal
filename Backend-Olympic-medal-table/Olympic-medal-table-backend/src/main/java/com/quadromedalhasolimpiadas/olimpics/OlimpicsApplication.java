package com.quadromedalhasolimpiadas.olimpics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableDiscoveryClient
@EnableSpringDataWebSupport
@SpringBootApplication
public class OlimpicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlimpicsApplication.class, args);
	}

}
