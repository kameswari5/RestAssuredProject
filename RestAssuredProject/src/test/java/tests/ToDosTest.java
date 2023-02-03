package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.net.http.HttpResponse.BodyHandler;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.internal.path.json.JSONAssertion;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.Response;
import pojo.ToDos;

public class ToDosTest {
	
	String BASE_URL ="https://jsonplaceholder.typicode.com/todos";
	
	private static Logger log = LogManager.getLogger( ToDosTest.class);
	
	
	@Test
	public void GetAllMethod() {
		Response response = RestAssured.with()
				.headers("content-Type",ContentType.JSON,"Accept",ContentType.JSON)
				.when().get(BASE_URL).then().extract().response();
		
	log.info("Response of GetAllMethod",response.asString());
		
	Assert.assertEquals(200, response.getStatusCode());
	}
	
	@Test
	
	public void postMethod() throws JsonProcessingException {
	
		//Method1:
		JSONObject obj = new JSONObject();
		obj.put("userId",10);
		obj.put("id",201);
		obj.put("title","my rest api project");
		obj.put("completed",true );
		
		Response response = given().header("content-Type",ContentType.JSON,"Accept",ContentType.JSON)
				.body(obj.toString())
				.when().post(BASE_URL).then().extract()
				.response();
		
		log.info("Response of  postMethod",response.asString());
	
		Assert.assertEquals(201, response.getStatusCode());
		
	//	Method 2: post method with using pojo
		
		 ToDos todo = new ToDos();
	       todo.setUserId(11);
	       todo.setId(222);
	       todo.setTitle("my rest api");
	    todo.setCompleted(true);
	    log.info("Converting pojo class object to json by using ObjectMapper object");
	    ObjectMapper mapper = new ObjectMapper();
	    String json = mapper.writeValueAsString(todo);
	    log.info("json string " ,json);
        
	        given()
	            .contentType(ContentType.JSON)       
	        .when()
	            .body(todo)
	            .post(BASE_URL)
	        .then()
	         .statusCode(201);
	}
	
	
	@Test
		public void GetById() {
		Response response = RestAssured.with()
				.headers("content-Type",ContentType.JSON,"Accept",ContentType.JSON)
				.when().get(BASE_URL+"/1").then().extract().response();	
		
		  log.info("Response of GetById " ,response.asString());
		Assert.assertEquals(200, response.getStatusCode());
	}
	
	
	@Test
	public void putMethod() throws JsonProcessingException {

	 ToDos todo = new ToDos();
	 todo.setUserId(10);
     todo.setId(200);
     todo.setTitle("my Rest assured rest api");
     todo.setCompleted(true);
     
   ObjectMapper mapper = new ObjectMapper();
   String json = mapper.writeValueAsString(todo);
   log.info("json string ",json);
  
       given()
           .contentType(ContentType.JSON)
             .when()
           .body(todo)
           .put(BASE_URL+"/5")
       .then()
           .statusCode(200);
		
  //    Method 2 :
       
        JSONObject obj = new JSONObject();
		obj.put("userId",10);
		obj.put("id",200);
		obj.put("title","This is my RestAssured  api project");
		obj.put("completed",true );
		
		Response response = given().header("content-Type",ContentType.JSON,"Accept",ContentType.JSON)
				.body(obj.toString())
				.when().put(BASE_URL+"/5").then().extract()
				.response();
		
		log.info("Response of putMethod " ,response.asString());
		
		Assert.assertEquals(200, response.getStatusCode());
}
	
	@Test
	public void deleteMethod() throws JsonProcessingException {
		
		 ToDos todo = new ToDos();
	   ObjectMapper mapper = new ObjectMapper();
	   String json = mapper.writeValueAsString(todo);
	   
	   log.info("json string ",json);
	  
	       given()
	           .contentType(ContentType.JSON)         
	       .when()
	           .delete(BASE_URL+"/1")
	       .then()
	           .statusCode(200);
	}
	
}
