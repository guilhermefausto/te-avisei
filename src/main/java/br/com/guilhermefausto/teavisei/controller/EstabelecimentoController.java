package br.com.guilhermefausto.teavisei.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.guilhermefausto.teavisei.controller.dto.EstabelecimentoDto;
import br.com.guilhermefausto.teavisei.controller.dto.IEstabelecimentoListaComMediaDto;
import br.com.guilhermefausto.teavisei.controller.form.AtualizacaoEstabelecimentoForm;
import br.com.guilhermefausto.teavisei.controller.form.EstabelecimentoForm;
import br.com.guilhermefausto.teavisei.model.Estabelecimento;
import br.com.guilhermefausto.teavisei.repository.EstabelecimentoRepository;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@GetMapping
	public Page<IEstabelecimentoListaComMediaDto> lista(@RequestParam(required = false) String cidade, 
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
		if(cidade == null) {
			return estabelecimentoRepository.listarEstabelecimentosComMediaDeNota(paginacao);
		}
		else {
			return estabelecimentoRepository.listarEstabelecimentosComMediaDeNotaPorCidade(cidade,paginacao);
		}
	}
	
	@PostMapping
	public ResponseEntity<EstabelecimentoDto> cadastrar(@RequestBody @Valid EstabelecimentoForm estabelecimentoForm, UriComponentsBuilder uriBuilder){
		Estabelecimento estabelecimento = estabelecimentoForm.converter();
		estabelecimentoRepository.save(estabelecimento);
		
		URI uri = uriBuilder.path("/estabelecimentos/{id}").buildAndExpand(estabelecimento.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new EstabelecimentoDto(estabelecimento));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EstabelecimentoDto> atualizar(@PathVariable Integer id, @RequestBody @Valid AtualizacaoEstabelecimentoForm form){
		Optional<Estabelecimento> optional = estabelecimentoRepository.findById(id);
		if(optional.isPresent()) {
			Estabelecimento estabelecimento = form.atualizar(id,estabelecimentoRepository);
			return ResponseEntity.ok(new EstabelecimentoDto(estabelecimento));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstabelecimentoDto> detalhar(@PathVariable Integer id){
		Optional<Estabelecimento> optional = estabelecimentoRepository.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(new EstabelecimentoDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		Optional<Estabelecimento> optional = estabelecimentoRepository.findById(id);
		if(optional.isPresent()) {
			estabelecimentoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
