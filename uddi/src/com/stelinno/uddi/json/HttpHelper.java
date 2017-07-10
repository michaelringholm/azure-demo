package com.stelinno.uddi.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.stelinno.uddi.controllers.CustomResponse;
import com.stelinno.uddi.entities.Service;

public class HttpHelper {
	@Autowired private Gson gson;
	
	public HttpResponse post(Service service, String targetUrl) {
		HttpResponse rawResponse = null;
		HttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			String json = gson.toJson(service);

			StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			HttpPost postMethod = new HttpPost(targetUrl);
			postMethod.setEntity(requestEntity);
			rawResponse = httpClient.execute(postMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawResponse;
	}
	
	public HttpResponse get(String serviceName, String targetUrl) {
		HttpResponse rawResponse = null;
		HttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			/*StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			HttpGet getMethod = new HttpGet(targetUrl);*/
			
			URIBuilder builder = new URIBuilder(targetUrl);
			builder.setParameter("serviceName", serviceName);
			URI uri = builder.build();
			HttpGet httpGet = new HttpGet(uri);
			
			//getMethod.setParams(arg0);setEntity(requestEntity);
			rawResponse = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawResponse;
	}	

	public CustomResponse getJsonResponse(HttpResponse httpResponse) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				CustomResponse json = gson.fromJson(output, CustomResponse.class);
				return json;
			}
			throw new RuntimeException("Empty response from server!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to get response from server!");
		}
	}
}
