package com.dbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sample {

	private static final String BASE_URL = "https://reqres.in/";

	@Autowired
	DataSource ds;

	@BeforeClass
	public static void beforeClass() {
		RestAssured.baseURI = BASE_URL;
	}

	@Test
	public void getUser() {

		RestAssured.given().param("page", "2").when().get("api/users").then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON);
	}

	@Test
	public void getUserFromPath() throws SQLException {
		RestAssured.given().pathParam("userId", 3000).when().get("api/users/{userId}").then().assertThat()
				.statusCode(404);

	}

	@Test
	public void makeSureThatGoogleIsUp() {
		RestAssured.given().when().get("http://www.google.com").then().assertThat().statusCode(200);

	}

	// REad data from database
	@Test
	public void getDataFromDb() throws SQLException {
		Connection conn = ds.getConnection();
		ResultSet rs = conn.prepareStatement("select * from Test").executeQuery();

		while (rs.next()) {
			System.out.println("Id: " + rs.getString(1) + ", data: " + rs.getString(2));
		}
		System.out.println();
	}

	// insert data into database
	@Test
	public void insertDataIntoDb() throws SQLException {
		Connection conn = ds.getConnection();

		PreparedStatement stmt = conn.prepareStatement("insert into Test values(?,?)");
		stmt.setString(1, String.valueOf(new Date().getTime()));// 1 specifies the first parameter in the query
		stmt.setString(2, "ABC" + new Date().getTime());

		int i = stmt.executeUpdate();
		System.out.println(i + " records inserted");
	}

	// update data into database
	@Test
	public void updateDataIntoDb() throws SQLException {
		Connection conn = ds.getConnection();

		PreparedStatement stmt = conn.prepareStatement("update Test set data=? where id=?");
		stmt.setString(1, "ABC" + new Date().getTime());// 1 specifies the first parameter in the query
		stmt.setString(2, "1");

		int i = stmt.executeUpdate();
		System.out.println(i + " records updated");
	}

	// delete data into database
	@Test
	public void deleteDataIntoDb() throws SQLException {
		Connection conn = ds.getConnection();

		PreparedStatement stmt = conn.prepareStatement("delete from Test where id=?");
		stmt.setString(1, "1");// 1 specifies the first parameter in the query

		int i = stmt.executeUpdate();
		System.out.println(i + " records deleted");
	}

	@Test
	public void MyGETRequest() throws IOException {
		URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");
		String readLine = null;
		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		conection.setRequestMethod("GET");
		conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
		int responseCode = conection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer response = new StringBuffer();
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();
			// print result
			System.out.println("JSON String Result " + response.toString());
			// GetAndPost.POSTRequest(response.toString());
		} else {
			System.out.println("GET NOT WORKED");
		}
	}

	@Test
	public void POSTRequest() throws IOException {
		final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" + "    \"id\": 101,\r\n"
				+ "    \"title\": \"Test Title\",\r\n" + "    \"body\": \"Test Body\"" + "\n}";
		System.out.println(POST_PARAMS);
		URL obj = new URL("https://jsonplaceholder.typicode.com/posts");
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("userId", "a1bcdefgh");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_CREATED) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST NOT WORKED");
		}
	}
}
