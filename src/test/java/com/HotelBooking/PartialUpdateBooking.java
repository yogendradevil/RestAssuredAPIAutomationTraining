package com.HotelBooking;

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
import io.restassured.specification.RequestSpecification;

public class PartialUpdateBooking extends BaseClass{
	// Global Variable
		String bookingid;
		String outh_token;

		@BeforeTest
		public void beforeTest() {
			BaseClass base = new BaseClass();
			base.createToken("admin", "password123");
			bookingid = base.bookingid;
			outh_token = base.outh_token;
		}
	
	
		

		@Test(priority=1)
		public void createBooking() throws IOException, ParseException, org.json.simple.parser.ParseException {
			
			JSONObject prodjsonobj = BaseClass.readFile(".\\TestData\\CreateBooking.json");

			RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			request.body(prodjsonobj.toJSONString());
			// POST the Response
			Response response = request.request(Method.POST, "/booking");
			// Response response = request.request(Method.POST,"/spree_oauth/token");
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);
			// To get the Token from JSON Response
			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			String fname = jsonPathEvaluator.get("booking.firstname").toString();
			System.out.println("First Name is =>  " + fname);
			Assert.assertEquals("Yogendra", fname);
			bookingid = jsonPathEvaluator.get("bookingid").toString();

		}

		@Test(priority=2)
		public void partial_UpdateBooking()

		{
			RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			RequestSpecification request = RestAssured.given();

			JSONObject requestParams = new JSONObject();
			requestParams.put("firstname", "Test");
			requestParams.put("lastname", "API");
			// Add a header stating the Request body is a JSON
			request.header("Content-Type", "application/json");
			request.header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=");
			request.body(requestParams.toJSONString());
			// POST the Response
			Response response = request.request(Method.PATCH, "/booking/" + bookingid);
			// Response response = request.request(Method.POST,"/spree_oauth/token");
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);
			// To get the Token from JSON Response
			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			String fname = jsonPathEvaluator.get("firstname").toString();
			System.out.println("First Name is =>  " + fname);
			Assert.assertEquals("Test", fname);
		}
		
}
