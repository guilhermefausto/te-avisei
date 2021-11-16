package br.com.guilhermefausto.teavisei.auth;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class AutenticacaoTestes extends BaseCriacaoPerfilUsuarios {
//
//	private String token = new Login().getToken();
//	
//	@Test
//	public void tokenIguais() {
//		String token2 = new Login().getToken();
//		
//		Assertions.assertEquals(token,token2);
//	}
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@Test
//	public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
//		URI uri = new URI("/auth");
//		
//		String json = "{"
//				+ "\"email\":\"invalido@email.com\","
//				+ "\"senha\":\"123456\""
//				+ "}";
//		
//		mockMvc
//			.perform(MockMvcRequestBuilders
//					.post(uri)
//					.content(json)
//					.contentType(MediaType.APPLICATION_JSON))
//			
//			.andExpect(MockMvcResultMatchers
//					.status()
//					.is(400));
//	}

//}
