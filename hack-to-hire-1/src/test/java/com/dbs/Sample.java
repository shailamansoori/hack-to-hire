package com.dbs;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sample {

	private static final String BASE_URL = "https://reqres.in/";

	@BeforeClass
	public static void beforeClass() {
		RestAssured.baseURI=BASE_URL;
	}
	
	@Test
	public void getUser() {
		
		RestAssured.given().
						param("page", "2").
						when().
						get("api/users").
						then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
	}
	
	@Test
	public void getUserFromPath() {
		RestAssured.given().
		pathParam("userId", 3000).
		when().
		get("api/users/{userId}").
		then().assertThat().statusCode(404);
		
	}
	
	@Test
	 public void makeSureThatGoogleIsUp() {
		RestAssured.given().
		 when().
		 get("http://www.google.com").
		 then().statusCode(200);

      }
}
