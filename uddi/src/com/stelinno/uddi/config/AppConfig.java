package com.stelinno.uddi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.stelinno.uddi.json.HttpHelper;

@Configuration
public class AppConfig {
	@Bean
	String baseUDDIServiceUrl() {
		//return "https://service-registry-dot-stelinno-prod.appspot.com";
		return "https://uddi-dot-stelinno-dev.appspot.com";
	}
	
	@Bean
	HttpHeaders jsonHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}
	
	@Bean Gson gson() {
		return new Gson();
	}
	
	@Bean HttpHelper httpHelper() {
		return new HttpHelper();
	}
}
