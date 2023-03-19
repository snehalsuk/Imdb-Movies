package com.imdb.imdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImdbApplication {

	public static void main(String[] args) {

		SpringApplication.run(ImdbApplication.class, args);


	}
	@Bean

	public ObjectMapper getObjectMapper() {

		return new ObjectMapper();

	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}



}
