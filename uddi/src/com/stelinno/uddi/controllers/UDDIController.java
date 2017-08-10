package com.stelinno.uddi.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.stelinno.uddi.entities.Payload;
import com.stelinno.uddi.entities.PayloadParameter;
import com.stelinno.uddi.entities.Service;
import com.stelinno.uddi.json.HttpHelper;
import com.stelinno.uddi.models.ServiceModel;

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
  @Autowired private HttpHelper httpHelper;
  
  @RequestMapping(value="/insert", method=RequestMethod.POST)
  private ResponseEntity<String> insert(@RequestBody Service service) throws Exception {
	  System.out.println("Calling remote insert service in the Cloud...");
	  HttpResponse rawResponse = httpHelper.post(service, baseUDDIServiceUrl + "/insert.ctl");	  	  
	  
	  CustomResponse jsonResponse = httpHelper.getCustomResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");

	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }
  
  @RequestMapping(value="/update", method=RequestMethod.POST)
  private ResponseEntity<String> update(@RequestBody Service service) {
	  System.out.println("Calling remote update service in the Cloud...");
	  HttpResponse rawResponse = httpHelper.post(service, baseUDDIServiceUrl + "/update.ctl");
	  
	  CustomResponse jsonResponse = httpHelper.getCustomResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }  
  
  @RequestMapping(value="/upsert", method=RequestMethod.POST)
  public ResponseEntity<String> upsert(@RequestBody Service service) throws Exception {
	  if(service != null && service.getId() != 0)
		  return update(service);
	  else
		  return insert(service);
  }
  
  /***
   * curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "parcel" http://localhost:8080/service/search.ctl
   * @param service
   * @return
   */
  @RequestMapping(value="/search", method=RequestMethod.POST)
  public ResponseEntity<String> search(@RequestBody String keywords) {
	  System.out.println("Calling remote find service in the Cloud...");
	  Payload payload = new Payload();
	  List<PayloadParameter> payloadParams = new ArrayList<PayloadParameter>();
	  PayloadParameter param = new PayloadParameter();
	  param.setParamName("serviceName");
	  param.setParamValue(keywords.toString());
	  payloadParams.add(param);
	  payload.setParameters(payloadParams);
	  HttpResponse rawResponse = httpHelper.get(payload, baseUDDIServiceUrl + "/search.ctl");
	  
	  CustomResponse jsonResponse = httpHelper.getCustomResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }
  
  /***
   * curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d {"id":"5712536552865792"} http://localhost:8080/service/searchById.ctl
   * @param service
   * @return
   */
  @RequestMapping(value="/searchById", method=RequestMethod.POST)
  public ResponseEntity<String> searchById(@RequestBody Service service) {
	  System.out.println("Calling remote find service in the Cloud...");
	  Payload payload = new Payload();
	  List<PayloadParameter> payloadParams = new ArrayList<PayloadParameter>();
	  PayloadParameter param = new PayloadParameter();
	  param.setParamName("serviceId");
	  param.setParamValue(Long.toString(service.getId()));
	  payloadParams.add(param);
	  payload.setParameters(payloadParams);
	  HttpResponse rawResponse = httpHelper.get(payload, baseUDDIServiceUrl + "/searchById.ctl");
	  
	  CustomResponse jsonResponse = httpHelper.getCustomResponse(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(jsonResponse);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(jsonResponse), jsonHttpHeaders, HttpStatus.OK);
  }
  
  @RequestMapping(value="/test", method=RequestMethod.POST)
  public ResponseEntity<String> test(@RequestBody ServiceModel serviceModel) {
	  System.out.println("Calling remote endpoint in the Cloud...");
	  //HttpResponse rawResponse = httpHelper.get("serviceId", Long.toString(serviceId), baseUDDIServiceUrl + "/searchById.ctl");
	  HttpResponse rawResponse = httpHelper.get(serviceModel.service.getPayload(), serviceModel.service.getEndpoint());
	  
	  String responsePayload = httpHelper.getResponsePayload(rawResponse);
	  if(rawResponse.getStatusLine().getStatusCode() != 200)
		  return new ResponseEntity<String>(null, jsonHttpHeaders, HttpStatus.valueOf(rawResponse.getStatusLine().getStatusCode()));
	  
	  System.out.println(responsePayload);
	  System.out.println("Done.");
	  return new ResponseEntity<String>(gson.toJson(responsePayload), jsonHttpHeaders, HttpStatus.OK);
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
