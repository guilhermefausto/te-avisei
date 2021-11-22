package br.com.guilhermefausto.teavisei.criterio;

import static io.restassured.RestAssured.given;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import br.com.guilhermefausto.teavisei.BaseConfigTests;
import io.restassured.response.Response;

public class CriterioTests extends BaseConfigTests {

	private String tokenUsuario;
	
	@BeforeAll
	public void setupCriterio() throws JSONException {
		// ----------------Fazendo login com o usuario com Perfil de Usuario Normal criado no arquivo data.sql --------------------------
		String emailUsu = "usuario@email.com";
		String senha = "123456";
		
		JSONObject objLoginUsu = new JSONObject();
		objLoginUsu.put("email", emailUsu);
		objLoginUsu.put("senha", senha);
		
		JSONObject jsonResponseUsu = new JSONObject(given(BaseConfigTests.request).body(objLoginUsu.toString()).post("/auth").asString());
		tokenUsuario = jsonResponseUsu.getString("token");
	}
	
	@Test
	public void deveriaRetornar403AoTentarBuscarCriteriosSemUsuarioLogado() throws JSONException {
		Response response = given(BaseConfigTests.request)
				.get("/criterios");
		
		Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
	}
	
	@Test
	public void deveriaRetornar200ComAListaDeCriteriosCadastrados() throws JSONException {
		Response response = given(BaseConfigTests.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/criterios");
		JSONArray objJson = new JSONArray(response.asString());
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertNotEquals(0, objJson.length());
	}
	
}
