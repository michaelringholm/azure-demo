package com.stelinno.uddi.controllers;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.stelinno.uddi.entities.Service;
import com.stelinno.uddi.json.HttpHelper;

@RestController
@RequestMapping("/service")
public class UDDIController {
  @RequestMapping("/home")
  public String home() {
    return "Welcome to the UDDI";
  }
  
  @Autowired private Gson gson;
  @Autowired private HttpHeaders jsonHttpHeaders;
  @Autowired private String baseUDDIServiceUrl;
  @Autowired private HttpHelper jsonHelper;
  
  @RequestMapping(value="/insert", method=RequestMethod.POST)
  private ResponseEntity<String> insert(@RequestBody Service service) throws Exception {
	  System.out.println("Calling remote insert service in the Cloud...");
	  HttpResponse rawResponse = jsonHelper.post(service, baseUDDIServiceUrl + "/insert.ctl");	  	  
	  
	  CustomResponse jsonResponse = jsonHelper.getJsonResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");

	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }
  
  @RequestMapping(value="/update", method=RequestMethod.POST)
  private ResponseEntity<String> update(@RequestBody Service service) {
	  System.out.println("Calling remote update service in the Cloud...");
	  HttpResponse rawResponse = jsonHelper.post(service, baseUDDIServiceUrl + "/update.ctl");
	  
	  CustomResponse jsonResponse = jsonHelper.getJsonResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }  
  
  @RequestMapping(value="/upsert", method=RequestMethod.POST)
  public ResponseEntity<String> upsert(@RequestBody Service service) throws Exception {
	  if(service != null && service.id != 0)
		  return update(service);
	  else
		  return insert(service);
  }
  
  /***
   * curl -H "Accept: text/html" -H "Content-type: text/html" -X POST -d "parcel" http://localhost:8080/service/search.ctl
   * @param service
   * @return
   */
  @RequestMapping(value="/search", method=RequestMethod.POST)
  public ResponseEntity<String> search(@RequestBody String keywords) {
	  System.out.println("Calling remote find service in the Cloud...");
	  HttpResponse rawResponse = jsonHelper.get(keywords.toString(), baseUDDIServiceUrl + "/search.ctl");
	  
	  CustomResponse jsonResponse = jsonHelper.getJsonResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }
  
  /*@RequestMapping(value="/findByName", method=RequestMethod.GET)
  private ResponseEntity<String> findByName(String serviceName) {
	  System.out.println("Calling remote find service in the Cloud...");
	  HttpResponse rawResponse = jsonHelper.postJson(service, baseUDDIServiceUrl + "/find2.ctl");
	  
	  CustomResponse jsonResponse = jsonHelper.getJsonResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }*/
  

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
