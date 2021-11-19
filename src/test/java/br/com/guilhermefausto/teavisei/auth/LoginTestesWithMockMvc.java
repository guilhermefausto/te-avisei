package br.com.guilhermefausto.teavisei.auth;

import java.net.URI;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.guilhermefausto.teavisei.TeAviseiApplicationTests;

@AutoConfigureMockMvc
public class LoginTestesWithMockMvc extends TeAviseiApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeAll
	public void setUpAndPopulate() throws Exception {
		URI uri = new URI("/signup");
		
		String json = "{"
				+ "\"nome\":\"Usuario Teste\","
				+ "\"email\":\"teste2@email.com\","
				+ "\"senha\":\"123456\""
				+ "}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
		URI uri = new URI("/auth");
		
		String json = "{"
				+ "\"email\":\"teste@email.com\","
				+ "\"senha\":\"123\""
				+ "}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(MockMvcResultMatchers
					.status()
					.is(401));
	}
	
	@Test
	public void deveriaRetornarStatus200ComOTokenDeAutenticacao() throws Exception {
		URI uri = new URI("/auth");
		
		String email = "teste2@email.com";
		String senha = "123456";
		
		JSONObject objRequest = new JSONObject();
		objRequest.put("email", email);
		objRequest.put("senha", senha);
		
		MockHttpServletResponse response = mockMvc.
				perform(MockMvcRequestBuilders
						.post(uri)
						.contentType(MediaType.APPLICATION_JSON).content(objRequest.toString())
						).andReturn().getResponse();
		
		JSONObject objResponse = new JSONObject(response.getContentAsString());
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertFalse(objResponse.getString("token").isEmpty());
	}

}
