package br.com.guilhermefausto.teavisei.avaliacao;

import static io.restassured.RestAssured.given;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import br.com.guilhermefausto.teavisei.BaseConfigTestes;
import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AvaliacaoTestes extends BaseConfigTestes {
	
	private Integer idEstabelecimento;
	private String tokenAdmin;
	private String tokenModerador;
	private String tokenUsuario;
	private Integer idAvaliacao;
	
	@BeforeAll
	public void setupAvaliacao() throws JSONException {
		
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
		
		// ----------------Fazendo login com o usuario com Perfil de Usuario Normal criado no arquivo data.sql --------------------------
		String emailUsu = "usuario@email.com";
		
		JSONObject objLoginUsu = new JSONObject();
		objLoginUsu.put("email", emailUsu);
		objLoginUsu.put("senha", senha);
		
		JSONObject jsonResponseUsu = new JSONObject(given(BaseConfigTestes.request).body(objLoginUsu.toString()).post("/auth").asString());
		tokenUsuario = jsonResponseUsu.getString("token");

		
		// -------------------- Cadastrando Estabelecimento --------------------------
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
		idEstabelecimento = objResponse.getInt("id");
		
		//Moderando Estabelecimento cadastrado
		given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenAdmin)
				.post("/estabelecimentos/moderacao/"+idEstabelecimento);
	}
	
	//@PostMapping("/avaliacao")
	@Order(1)
	@Test
	public void deveriaDevolver400AoCadastrarAvaliacoesComCriterioInvalido() throws JSONException {
		String comentarios = "Comentários da Avaliacao 1";
		
		JSONArray avaliacoes = new JSONArray();
		avaliacoes.put(new JSONObject().put("nota", "10.00").put("criterio",99));
		avaliacoes.put(new JSONObject().put("nota", "9.00").put("criterio",2));
		avaliacoes.put(new JSONObject().put("nota", "8.00").put("criterio",3));
		avaliacoes.put(new JSONObject().put("nota", "7.00").put("criterio",4));
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("comentarios", comentarios);
		objRequest.put("estabelecimento", idEstabelecimento);
		objRequest.put("avaliacoes", avaliacoes);
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenAdmin)
				.body(objRequest.toString())
				.post("/avaliacao");
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}
	
	@Order(2)
	@Test
	public void deveriaDevolver400AoCadastrarAvaliacoesComEstabelecimentoInvalido() throws JSONException {
		String comentarios = "Comentários da Avaliacao 1";
		
		JSONArray avaliacoes = new JSONArray();
		avaliacoes.put(new JSONObject().put("nota", "10.00").put("criterio",1));
		avaliacoes.put(new JSONObject().put("nota", "9.00").put("criterio",2));
		avaliacoes.put(new JSONObject().put("nota", "8.00").put("criterio",3));
		avaliacoes.put(new JSONObject().put("nota", "7.00").put("criterio",4));
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("comentarios", comentarios);
		objRequest.put("estabelecimento", 99);
		objRequest.put("avaliacoes", avaliacoes);
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenAdmin)
				.body(objRequest.toString())
				.post("/avaliacao");
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}
	
	@Order(3)
	@Test
	public void deveriaDevolver400AoCadastrarAvaliacoesComDadosIncorretosOuVazios() throws JSONException {
		String comentarios = "Comentários da Avaliacao 1";
		
		JSONArray avaliacoes = new JSONArray();
		avaliacoes.put(new JSONObject().put("nota", "").put("criterio",1));
		avaliacoes.put(new JSONObject().put("nota", "9.00").put("criterio",2));
		avaliacoes.put(new JSONObject().put("nota", "8.00").put("criterio",3));
		avaliacoes.put(new JSONObject().put("nota", "7.00").put("criterio",4));
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("comentarios", comentarios);
		objRequest.put("estabelecimento", idEstabelecimento);
		objRequest.put("avaliacoes", avaliacoes);
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenAdmin)
				.body(objRequest.toString())
				.post("/avaliacao");
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}
	
	@Order(4)
	@Test
	public void deveriaDevolver200AoCadastrarAvaliacoesComNotasECriterios() throws JSONException {
		String comentarios = "Comentários da Avaliacao 1";
		
		JSONArray avaliacoes = new JSONArray();
		avaliacoes.put(new JSONObject().put("nota", "10.00").put("criterio",1));
		avaliacoes.put(new JSONObject().put("nota", "9.00").put("criterio",2));
		avaliacoes.put(new JSONObject().put("nota", "8.00").put("criterio",3));
		avaliacoes.put(new JSONObject().put("nota", "7.00").put("criterio",4));
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("comentarios", comentarios);
		objRequest.put("estabelecimento", idEstabelecimento);
		objRequest.put("avaliacoes", avaliacoes);
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.body(objRequest.toString())
				.post("/avaliacao");
		
		JSONObject jsonResponse = new JSONObject(response.asString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(response.asString().isEmpty());
		Assertions.assertEquals(1, jsonResponse.getInt("id"));
		idAvaliacao = jsonResponse.getInt("id");
	}
	
	//@GetMapping("/estabelecimentos/avaliacoes/{id}")
	@Order(5)
	@Test
	public void deveriaDevolver404AoBuscarAMediaDasAvaliacoesDeEstabelecimentoInvalido() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/estabelecimentos/avaliacoes/99");
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(6)
	@Test
	public void deveriaDevolver200AMediaDasAvaliacoesDoEstabelecimento() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/estabelecimentos/avaliacoes/"+idEstabelecimento);
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertFalse(response.asString().isEmpty());
	}
	
	
	//@GetMapping("/estabelecimentos/avaliacoes/comentarios/{id}")
	@Order(7)
	@Test
	public void deveriaDevolver200ComTodosOsComentariosDasAvaliacoesDoEstabelecimento() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/estabelecimentos/avaliacoes/comentarios/"+idEstabelecimento);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	

	@Order(8)
	@Test
	public void deveriaDevolver404AoBuscarComentariosDeEstabelecimentoInvalido() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/estabelecimentos/avaliacoes/comentarios/99");
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	//@GetMapping("/avaliacao/{id}")
	@Order(9)
	@Test
	public void deveriaDevolver404CasoOUsuarioNaoTenhaAvaliacaoCadastradaComOEstabelecimentoInformado() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/avaliacao/99");
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(10)
	@Test
	public void deveriaDevolver200ComAvaliacaoDoUsuarioLogadoParaOEstabelecimentoInformado() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/avaliacao/"+idEstabelecimento);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	
	
	//@GetMapping("/avaliacao")
	@Order(11)
	@Test
	public void deveriaDevolver200TodasAsAvaliacoesFeitasPeloUsuarioLogado() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.get("/avaliacao/");
		JSONObject objResponse = new JSONObject(response.asString());
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertNotEquals(0, objResponse.getInt("totalElements"));
	}
	
	@Order(12)
	@Test
	public void deveriaDevolver200ComPageVazioQuandoUsuarioNaoTemAvaliacoesCadastradas() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenModerador)
				.get("/avaliacao/");
		JSONObject objResponse = new JSONObject(response.asString());
		System.out.println(response.asString());
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertEquals(0, objResponse.getInt("totalElements"));
	}
	
	//@DeleteMapping("/avaliacao/{id}")
	@Order(13)
	@Test
	public void deveriaDevolver404QuandoOIdDaAvaliacaoNaoExista() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.delete("/avaliacao/99");
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(14)
	@Test
	public void deveriaDevolver404QuandoOIdDaAvaliacaoNaoSejaDoUsuario() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenModerador)
				.delete("/avaliacao/99");
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Order(15)
	@Test
	public void deveriaDevolver200AoExcluirAAvaliacao() throws JSONException {
		
		Response response = given(BaseConfigTestes.request)
				.header("Authorization", "Bearer "+tokenUsuario)
				.delete("/avaliacao/"+idAvaliacao);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
}
