package com.stelinno.uddi.controllers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.stelinno.uddi.entities.Service;

@RestController
@RequestMapping("/service")
public class UDDIController {
  @RequestMapping("/home")
  public String home() {
    return "Welcome to the UDDI";
  }
  
  private static Gson gson = new Gson();
  
  @RequestMapping(value="/upsert", method=RequestMethod.POST)
  public void upsert(@RequestBody Service service) {
    System.out.println("Calling remote upsert service in the Cloud...");
    
    try {
	    HttpClient httpClient = HttpClientBuilder.create().build();
	    String json = gson.toJson(service);
	    
	    StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
	    HttpPost postMethod = new HttpPost("http://service-registry-dot-stelinno-dev.appspot.com/upsert");
	    postMethod.setEntity(requestEntity);
	    HttpResponse rawResponse = httpClient.execute(postMethod);
	    	
	    System.out.println(rawResponse.toString());
    } 
    catch (Exception e) {
		e.printStackTrace();
	}
    
    System.out.println("Done.");
  }

  /**
   * <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
   * App Engine health checking</a> requires responding with 200 to {@code /_ah/health}.
   */
  @RequestMapping("/_ah/health")
  public String healthy() {
    // Message body required though ignored
    return "Still surviving.";
  }

}
