package br.com.guilhermefausto.teavisei.estabelecimento;

import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import br.com.guilhermefausto.teavisei.auth.BaseConfigTestes;
import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EstabelecimentosTestes extends BaseConfigTestes {
	
	private String tokenAdmin;
	private String tokenModerador;
	private String tokenUsuario;
	private Integer idEstabelecimento;
	
	@BeforeAll
	public void setup() throws JSONException {
		//Fazendo login com o usuario com Perfil de ADMINISTRADOR criado no arquivo data.sql
		String email = "adm@email.com";
		String senha = "123456";
		
		JSONObject objLoginAdmin = new JSONObject();
		objLoginAdmin.put("email", email);
		objLoginAdmin.put("senha", senha);
		
		JSONObject jsonResponse = new JSONObject(given(BaseConfigTestes.request).body(objLoginAdmin.toString()).post("/auth").asString());
		tokenAdmin = jsonResponse.getString("token");
		
		
		//Fazendo login com o usuario com Perfil de Moderador criado no arquivo data.sql
		String emailMod = "moderador@email.com";
		
		JSONObject objLoginMod = new JSONObject();
		objLoginMod.put("email", emailMod);
		objLoginMod.put("senha", senha);
		
		JSONObject jsonResponseMod = new JSONObject(given(BaseConfigTestes.request).body(objLoginMod.toString()).post("/auth").asString());
		tokenModerador = jsonResponseMod.getString("token");
		
		//Fazendo login com o usuario com Perfil de Usuario Normal criado no arquivo data.sql
		String emailUsu = "usuario@email.com";
		
		JSONObject objLoginUsu = new JSONObject();
		objLoginUsu.put("email", emailUsu);
		objLoginUsu.put("senha", senha);
		
		JSONObject jsonResponseUsu = new JSONObject(given(BaseConfigTestes.request).body(objLoginUsu.toString()).post("/auth").asString());
		tokenUsuario = jsonResponseUsu.getString("token");
	}
	
	@Order(1)
	@Test
	public void deveriaDevolver403AoCadastrarEstabelecimentoSemUsuarioLogado() throws Exception {
		String nome = "Estabelecimento Teste";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "(22) 99999-8877";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		
		Response response = given(BaseConfigTestes.request)
								.body(objRequest.toString())
								.post("/estabelecimentos");
		
		Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
	}
	
	@Order(2)
	@Test
	public void deveriaDevolver400CasoOsDadosParaCadastrarEstejamIncorretos() throws Exception {
		String nome = "Estabelecimento Teste";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.body(objRequest.toString())
								.post("/estabelecimentos");
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}
		
	@Order(3)
	@Test
	public void deveriaDevolver201CasoEstabelecimentoSejaCadastrado() throws Exception {
		String nome = "Estabelecimento Teste";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "(22) 99999-8877";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.body(objRequest.toString())
								.post("/estabelecimentos");
		JSONObject objResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		Assertions.assertEquals(nome, objResponse.getString("nome"));
		idEstabelecimento = objResponse.getInt("id");
	}

	@Order(4)
	@Test
	public void deveriaDevolver200ComAListaDeEstabelecimentosParaModerarComUsuarioAdministrador() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.get("/estabelecimentos/moderacao");
		JSONObject objResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(response.asString().isEmpty());
		Assertions.assertNotEquals(0, objResponse.getInt("totalElements"));
	}
	
	@Order(5)
	@Test
	public void deveriaDevolver200ComAListaDeEstabelecimentosParaModerarComUsuarioModerador() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenModerador)
								.get("/estabelecimentos/moderacao");
		JSONObject objResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(response.asString().isEmpty());
		Assertions.assertNotEquals(0, objResponse.getInt("totalElements"));
	}
	
	@Order(6)
	@Test
	public void deveriaDevolver403AoConsultarAListaDeEstabelecimentosParaModerarComUsuarioComum() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenUsuario)
								.get("/estabelecimentos/moderacao");
		
		Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
	}
	
	@Order(7)
	@Test
	public void deveriaDevolver404AoModerarEstabelecimentoQueJaFoiModeradoOuQueNaoExiste() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.post("/estabelecimentos/moderacao/99");
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(8)
	@Test
	public void deveriaDevolver200ComOEstabelecimentosQueFoiModeradoComUsuarioAdministrador() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.post("/estabelecimentos/moderacao/"+idEstabelecimento);
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	
	@Order(9)
	@Test
	public void deveriaDevolver200ComAListaDeEstabelecimentosModerados() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.get("/estabelecimentos");
		JSONObject objResponse = new JSONObject(response.asString());
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(response.asString().isEmpty());
		Assertions.assertNotEquals(0, objResponse.getInt("totalElements"));
	}
	
	@Order(10)
	@Test
	public void deveriaDevolver200ComAListaDeEstabelecimentosModeradosFiltradosPorCidade() throws Exception {
		String cidade = "BOM JARDIM";
		
		Response response = given(BaseConfigTestes.request)
								.get("/estabelecimentos/?nomeCidade="+cidade);
		JSONObject objResponse = new JSONObject(response.asString());
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(response.asString().isEmpty());
		Assertions.assertNotEquals(0, objResponse.getInt("totalElements"));
	}
	
	@Order(11)
	@Test
	public void deveriaDevolver400CasoOsDadosParaAlterarEstejamIncorretos() throws Exception {
		String nome = "Estabelecimento Teste Alterado";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.body(objRequest.toString())
								.put("/estabelecimentos/"+idEstabelecimento);
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}
	
	@Order(12)
	@Test
	public void deveriaDevolver404CasoEstabelecimentoParaAlterarNaoExista() throws Exception {
		String nome = "Estabelecimento Teste Alterado";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "(22) 99999-8877";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.body(objRequest.toString())
								.put("/estabelecimentos/99");
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(13)
	@Test
	public void deveriaDevolver403AoAlterarEstabelecimentoCasoUsuarioNaoSejaAdministrador() throws Exception {
		String nome = "Estabelecimento Teste Alterado";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "(22) 99999-8877";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenModerador)
								.body(objRequest.toString())
								.put("/estabelecimentos/"+idEstabelecimento);
		
		Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
	}
	
	@Order(14)
	@Test
	public void deveriaDevolver200CasoEstabelecimentoSejaAlterado() throws Exception {
		String nome = "Estabelecimento Teste Alterado";
		String cidade = "BOM JARDIM";
		String redesSociais = "@teste";
		String telefone = "(22) 99999-8877";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("nome", nome);
		objRequest.put("cidade", cidade);
		objRequest.put("redesSociais", redesSociais);
		objRequest.put("telefone", telefone);
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.body(objRequest.toString())
								.put("/estabelecimentos/"+idEstabelecimento);
		
		JSONObject objResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertEquals(nome, objResponse.getString("nome"));
	}
	
	@Order(15)
	@Test
	public void deveriaDevolver403AoExcluirEstabelecimentoCasoUsuarioNaoSejaAdministrador() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenModerador)
								.delete("/estabelecimentos/"+idEstabelecimento);
		
		Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
	}
	
	@Order(16)
	@Test
	public void deveriaDevolver404AoExcluirEstabelecimentoQueNaoExiste() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.delete("/estabelecimentos/99");
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(17)
	@Test
	public void deveriaDevolver200AoExcluirEstabelecimento() throws Exception {
		
		Response response = given(BaseConfigTestes.request)
								.header("Authorization", "Bearer "+tokenAdmin)
								.delete("/estabelecimentos/"+idEstabelecimento);
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	
	
}
