package com.stelinno.uddi.controllers;

import org.apache.http.Header;
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
  
  @RequestMapping(value="/insert", method=RequestMethod.POST)
  public void insert(@RequestBody Service service) {
	  System.out.println("Calling remote insert service in the Cloud...");
	  HttpResponse rawResponse = postJson(service, "https://service-registry-dot-stelinno-prod.appspot.com/insert");
	  
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  throw new RuntimeException("Unable to insert service!"); // rawResponse.getStatusLine().toString()
	  
	  System.out.println(rawResponse.toString());        
	  System.out.println("Done.");
  }
  
  @RequestMapping(value="/update", method=RequestMethod.POST)
  public void update(@RequestBody Service service) {
	  System.out.println("Calling remote update service in the Cloud...");
	  HttpResponse rawResponse = postJson(service, "https://service-registry-dot-stelinno-prod.appspot.com/update");
	  System.out.println(rawResponse.toString());
	  System.out.println("Done.");
  }  
  
  @Deprecated
  @RequestMapping(value="/upsert", method=RequestMethod.POST)
  public void upsert(@RequestBody Service service) {
	  System.out.println("Calling remote upsert service in the Cloud...");
	  HttpResponse rawResponse = postJson(service, "https://service-registry-dot-stelinno-prod.appspot.com/upsert");	    	
	  System.out.println(rawResponse.toString());    
	  System.out.println("Done.");
  }

  private HttpResponse postJson(Service service, String targetUrl) {
	  HttpResponse rawResponse = null;
	  try {
		  HttpClient httpClient = HttpClientBuilder.create().build();
		  String json = gson.toJson(service);
		
		  StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
		  HttpPost postMethod = new HttpPost(targetUrl);
		  postMethod.setEntity(requestEntity);
		  rawResponse = httpClient.execute(postMethod);
	  }
	  catch (Exception e) {
		  e.printStackTrace();
	  }
	  
	  return rawResponse;
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
