package com.notes;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import bsh.ParseException;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class createNote {
	String token;
	public static String id;
	@BeforeTest
	public void login() {
		BaseClass obj = new BaseClass();
		token = obj.createToken("yogibijapur@gmail.com", "admin@123");
	}
	@Test
	  public void createnotes() throws IOException, ParseException, org.json.simple.parser.ParseException 
	  {
		  
		  //Create json object of JSONParser class to parse the JSON data
		  JSONParser jsonparser=new JSONParser();
		  //Create object for FileReader class, which help to load and read JSON file
		  FileReader reader = new FileReader(".\\TestData\\createNotes.json");
		  //Returning/assigning to Java Object
		  Object obj = jsonparser.parse(reader);
		  //Convert Java Object to JSON Object, JSONObject is typecast here
		  JSONObject prodjsonobj = (JSONObject)obj;
		  
		  
		  RestAssured.baseURI = "https://practice.expandtesting.com";
	      RequestSpecification request = RestAssured.given();
		  request.header("Content-Type", "application/json");
		  request.header("X-Auth-Token", token);
	      request.body(prodjsonobj.toJSONString());
	      // POST the Response
	      Response response = request.request(Method.POST,"/notes/api/notes");
	      //Response response = request.request(Method.POST,"/spree_oauth/token");
	      response.prettyPrint();
	      int statusCode = response.getStatusCode();
	      // System.out.println(response.asString());
	      Assert.assertEquals(statusCode, 200);
	      // To get the Token from JSON Response
	      JsonPath jsonPathEvaluator = response.getBody().jsonPath();
	      id = jsonPathEvaluator.get("data.id").toString();
	      System.out.println("Notes ID is =>  " + id);
	  }
	
	@Test(priority = 1)
	  public void updatenotes() throws IOException, ParseException, org.json.simple.parser.ParseException 
	  {
		  
		  //Create json object of JSONParser class to parse the JSON data
		  JSONParser jsonparser=new JSONParser();
		  //Create object for FileReader class, which help to load and read JSON file
		  FileReader reader = new FileReader(".\\TestData\\updatenotes.json");
		  //Returning/assigning to Java Object
		  Object obj = jsonparser.parse(reader);
		  //Convert Java Object to JSON Object, JSONObject is typecast here
		  JSONObject prodjsonobj = (JSONObject)obj;
		  
		  RestAssured.baseURI = "https://practice.expandtesting.com";
	      RequestSpecification request = RestAssured.given();
		  request.header("Content-Type", "application/json");
		  request.header("X-Auth-Token", token);
	      request.body(prodjsonobj.toJSONString());
	      // POST the Response
	      Response response = request.request(Method.PUT,"/notes/api/notes/"+id);
	      //Response response = request.request(Method.POST,"/spree_oauth/token");
	      response.prettyPrint();
	      int statusCode = response.getStatusCode();
	      // System.out.println(response.asString());
	      Assert.assertEquals(statusCode, 200);
	      // To get the Token from JSON Response
//	      JsonPath jsonPathEvaluator = response.getBody().jsonPath();
//	      id = jsonPathEvaluator.get("data.id").toString();
//	      System.out.println("Notes ID is =>  " + id);
	  }
	@Test(priority = 2)
	  public void deletenotes() throws IOException, ParseException, org.json.simple.parser.ParseException 
	  {

		  RestAssured.baseURI = "https://practice.expandtesting.com";
	      RequestSpecification request = RestAssured.given();
		  request.header("Content-Type", "application/json");
		  request.header("X-Auth-Token", token);
	      Response response = request.request(Method.DELETE,"/notes/api/notes/"+id);
	      response.prettyPrint();
	      int statusCode = response.getStatusCode();
	      Assert.assertEquals(statusCode, 200);
	  }
}
