package br.com.guilhermefausto.teavisei.auth;

import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

public class LoginTestes extends BaseConfigTestes{
	
	@BeforeAll
	public void setup() throws JSONException {
		String nome = "Nome Teste";
		String email = "teste@email.com";
		String senha = "123456";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("email", email);
		objRequest.put("senha", senha);
		
		
		given(BaseConfigTestes.request)
								.body(objRequest.toString())
								.post("/signup");
	}
	
	@Test
	public void deveriaRetornarStatus200ComOTokenDeAutenticacao() throws JSONException {
		String email = "teste@email.com";
		String senha = "123456";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("email", email);
		objRequest.put("senha", senha);
		
		Response response = given(BaseConfigTestes.request).body(objRequest.toString()).post("/auth");
		
		JSONObject objResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(objResponse.getString("token").isEmpty());
	}
	
	@Test
	public void deveriaRetornarStatus401CasoUsuarioOuSenhaEstejamIncorretos() throws JSONException {
		String email = "teste@email.com";
		String senha = "123";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("email", email);
		objRequest.put("senha", senha);
		
		Response response = given(BaseConfigTestes.request).body(objRequest.toString()).post("/auth");
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
}
