package br.com.guilhermefausto.teavisei.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.guilhermefausto.teavisei.TeAviseiApplicationTests;
import br.com.guilhermefausto.teavisei.model.Criterio;


//@DataJpaTest
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CriterioRepositoryTests extends TeAviseiApplicationTests {
	
	@Autowired
	private CriterioRepository repository;
	
	@Test
	public void deveriaCarregarUmCriterioAoBuscarPeloSeuNome() {
		String nomeCriterio = "Comida";
		
		Criterio criterio = repository.findByNome(nomeCriterio);
		
		Assertions.assertNotNull(criterio);
		Assertions.assertEquals(nomeCriterio, criterio.getNome());
	}
	
	@Test
	public void naoDeveriaCarregarUmCursoCujoNomeNaoEstejaCadastrado() {
		String nomeCriterio = "Teste";
		Criterio criterio = repository.findByNome(nomeCriterio);
		
		Assertions.assertNull(criterio);
	}

}
