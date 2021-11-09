package br.com.guilhermefausto.teavisei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guilhermefausto.teavisei.model.Criterio;
import br.com.guilhermefausto.teavisei.repository.CriterioRepository;

@RestController
@RequestMapping("/criterios")
public class CriterioController {
	
	@Autowired
	private CriterioRepository criterioRepository;
	
	@GetMapping
	public List<Criterio> listar() {
		List<Criterio> criterios = criterioRepository.findAll();
		return criterios;
	}
	
//	@PostMapping
//	public ResponseEntity<EstabelecimentoDto> cadastrar(@RequestBody @Valid EstabelecimentoForm estabelecimentoForm, UriComponentsBuilder uriBuilder){
//		Estabelecimento estabelecimento = estabelecimentoForm.converter();
//		estabelecimentoRepository.save(estabelecimento);
//		
//		URI uri = uriBuilder.path("/estabelecimentos/{id}").buildAndExpand(estabelecimento.getId()).toUri();
//		
//		return ResponseEntity.created(uri).body(new EstabelecimentoDto(estabelecimento));
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<EstabelecimentoDto> atualizar(@PathVariable Integer id, @RequestBody @Valid AtualizacaoEstabelecimentoForm form){
//		Optional<Estabelecimento> optional = estabelecimentoRepository.findById(id);
//		if(optional.isPresent()) {
//			Estabelecimento estabelecimento = form.atualizar(id,estabelecimentoRepository);
//			return ResponseEntity.ok(new EstabelecimentoDto(estabelecimento));
//		}
//		return ResponseEntity.notFound().build();
//	}
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<EstabelecimentoDto> detalhar(@PathVariable Integer id){
//		Optional<Estabelecimento> optional = estabelecimentoRepository.findById(id);
//		if(optional.isPresent()) {
//			return ResponseEntity.ok(new EstabelecimentoDto(optional.get()));
//		}
//		return ResponseEntity.notFound().build();
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> excluir(@PathVariable Integer id) {
//		Optional<Estabelecimento> optional = estabelecimentoRepository.findById(id);
//		if(optional.isPresent()) {
//			estabelecimentoRepository.deleteById(id);
//			return ResponseEntity.ok().build();
//		}
//		return ResponseEntity.notFound().build();
//	}
}
