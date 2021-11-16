package br.com.guilhermefausto.teavisei.auth;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.web.server.LocalServerPort;

import br.com.guilhermefausto.teavisei.TeAviseiApplicationTests;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseConfigTestes extends TeAviseiApplicationTests {

	@LocalServerPort
	private int port;
	
	public static RequestSpecification request;
	
	@BeforeAll
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		request = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.build();
	}
	
}
