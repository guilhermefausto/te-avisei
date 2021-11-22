package br.com.guilhermefausto.teavisei.auth;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import br.com.guilhermefausto.teavisei.BaseConfigTests;
import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTests extends BaseConfigTests{

	@Order(1)
	@Test
	public void deveriaDevolver200CasoOUsuarioSejaCadastrado() throws Exception {
		String nome = "Nome Teste";
		String email = "teste_usuario@email.com";
		String senha = "123456";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("email", email);
		objRequest.put("senha", senha);
		
		
		Response response = given(BaseConfigTests.request)
								.body(objRequest.toString())
								.post("/signup");
		JSONObject objResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertEquals(nome, objResponse.getString("nome"));
		Assertions.assertEquals(email, objResponse.getString("email"));
		
	}
	
	@Order(2)
	@Test
	public void deveriaDevolver400CasoCamposInformadosEstejamIncorretosOuVazios() throws Exception {
		String nome = "Nome Teste";
		String email = "teste_usuario@email.com";
		String senha = "";
		
		JSONObject objJson = new JSONObject();
		objJson.put("nome", nome);
		objJson.put("email", email);
		objJson.put("senha", senha);
		
		Response response = given(BaseConfigTests.request)
								.body(objJson.toString())
								.post("/signup");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());	
	}
	
	@Order(3)
	@Test
	public void deveriaDevolver409CasoUsuarioJaExista() throws Exception {
		String nome = "Nome Teste";
		String email = "teste_usuario@email.com";
		String senha = "123456";
		
		JSONObject objJson = new JSONObject();
		objJson.put("nome", nome);
		objJson.put("email", email);
		objJson.put("senha", senha);
		
		Response response = given(BaseConfigTests.request)
								.body(objJson.toString())
								.post("/signup");
		
		Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());	
	}
}
