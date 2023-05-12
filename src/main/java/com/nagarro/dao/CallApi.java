package com.nagarro.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nagarro.entity.Books;

@Component
public class CallApi{
	
	HttpClient client = HttpClient.newHttpClient();
	
	
	public List<Books> getData() throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException {
		ObjectMapper jacksonObjMapper = new ObjectMapper();
		System.out.println("Inside api get data");

		
//		HttpRequest request = HttpRequest.newBuilder().GET()
//		          .uri(URI.create("http://localhost:8081/books"))
//		           .build();
//		System.out.println(request);
//		 String s= (String) client.sendAsync(request,BodyHandlers.ofString()) 
//		          .thenApply(HttpResponse::body).get();
//		 
//		 JsonNode new_json= jacksonObjMapper.readValue(s.toString(),JsonNode.class); 
//		 System.out.println("this is object-> "+ s);
////		 System.exit(0);
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("http://localhost:8081/books", String.class);
		System.out.println(result);
		Books[] listbook = jacksonObjMapper.readValue(result, Books[].class);
		 
//		 for (JsonNode jsonNode : new_json) {
//			 Books book= new Books();
//             book.setBookcode(jsonNode.get("bookcode").asText());
//             book.setBookname(jsonNode.get("bookname").asText());
//             book.setAuthor(jsonNode.get("author").asText());
//             book.setDataadded(jsonNode.get("dataadded").asText());
//             list.add(book);
//         }
		
	//	List<Books> list = Arrays.asList(listbook);
		List<Books> list = Arrays.asList(listbook);
	//	 System.out.println(list.toString());

		 return list;
		 
	}
	
	public void postData(Books book) throws JsonProcessingException, InterruptedException, ExecutionException {
		
		
			ObjectMapper jacksonObjMapper = new ObjectMapper();
			String rawjson= jacksonObjMapper.writeValueAsString(book);
		JsonNode json= jacksonObjMapper.readValue(rawjson,JsonNode.class);
		ObjectNode node = (ObjectNode) json;
		
		 HttpRequest request = HttpRequest.newBuilder()  
		            .uri(URI.create("http://localhost:8081/books"))    
		            .setHeader("Content-Type", "application/json")
		            .POST(BodyPublishers.ofString(rawjson))
		            .build();
		 
		 String s= (String) client.sendAsync(request,BodyHandlers.ofString()).thenApply(HttpResponse::body).get();
		 JsonNode new_json= jacksonObjMapper.readValue(s.toString(),JsonNode.class); 
		 System.out.println(new_json.toPrettyString());
		 
		 
		 book=null;
		 
		
	}
	
	public void putData(Books book,String bookId) throws JsonProcessingException, InterruptedException, ExecutionException {
		ObjectMapper jacksonObjMapper = new ObjectMapper();
		String rawjson= jacksonObjMapper.writeValueAsString(book);
		JsonNode json= jacksonObjMapper.readValue(rawjson.toString(),JsonNode.class);
		ObjectNode node = (ObjectNode) json;
		HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create("localhost:8081/books"+"/"+bookId))
	            .setHeader("Content-Type", "application/json")
	            .PUT(HttpRequest.BodyPublishers.ofString(rawjson))	            
	            .build();
		String s= (String) client.sendAsync(request,BodyHandlers.ofString()) .thenApply(HttpResponse::body).get();
		JsonNode new_json= jacksonObjMapper.readValue(s.toString(),JsonNode.class); 
//		String number="" ;
//	    String sys_id1="";  
//    	   for (JsonNode jsonNode : new_json) {
//                 number = jsonNode.get("number").asText();      
//                 sys_id1=jsonNode.get("sys_id").asText();
//             }
		
	}
	
	

}
