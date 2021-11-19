package br.com.guilhermefausto.teavisei.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.guilhermefausto.teavisei.controller.dto.AvaliacaoCadastrarDto;
import br.com.guilhermefausto.teavisei.controller.dto.AvaliacoesDoUsuarioDto;
import br.com.guilhermefausto.teavisei.controller.dto.AvaliacoesPorEstabelecimentoEUsuarioDto;
import br.com.guilhermefausto.teavisei.controller.dto.ComentariosDto;
import br.com.guilhermefausto.teavisei.controller.dto.MediaAvaliacaoDoEstabelecimentoPorCriterio;
import br.com.guilhermefausto.teavisei.controller.form.AvaliacaoCadastrarForm;
import br.com.guilhermefausto.teavisei.model.Avaliacao;
import br.com.guilhermefausto.teavisei.model.Usuario;
import br.com.guilhermefausto.teavisei.repository.AvaliacaoRepository;
import br.com.guilhermefausto.teavisei.repository.CriterioRepository;
import br.com.guilhermefausto.teavisei.repository.EstabelecimentoRepository;
import br.com.guilhermefausto.teavisei.repository.UsuarioRepository;
import br.com.guilhermefausto.teavisei.security.TokenService;

@RestController
public class AvaliacaoController {
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CriterioRepository criterioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/estabelecimentos/avaliacoes/{id}")
	public ResponseEntity<List<MediaAvaliacaoDoEstabelecimentoPorCriterio>> mostrarMediaDeAvaliacaoDoEstabelecimentoPorCriterio(@PathVariable Integer id){
		List<MediaAvaliacaoDoEstabelecimentoPorCriterio> avaliacoes = avaliacaoRepository.buscarMediaDeAvaliacaoDoEstabelecimentoPorCriterio(id);
		if(avaliacoes.size() > 0) {
			return ResponseEntity.ok().body(avaliacoes);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/estabelecimentos/avaliacoes/comentarios/{id}")
	public ResponseEntity<?> mostrarComentariosEDataDasAvaliacaoes(@PathVariable Integer id,
				@PageableDefault(sort = "dataHoraAvaliacao", direction = Direction.DESC) Pageable paginacao){
		//Page<ComentariosDto> comentarios = avaliacaoRepository.buscarComentariosEDataDasAvaliacoes(id, paginacao);
		//return comentarios;
		
		if(estabelecimentoRepository.findById(id).isPresent()) {
				Page<ComentariosDto> comentarios = avaliacaoRepository.buscarComentariosEDataDasAvaliacoes(id, paginacao);
				return ResponseEntity.ok(comentarios);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/avaliacao")
	public ResponseEntity<?> cadastrar(@RequestBody @Valid AvaliacaoCadastrarForm form,
									   HttpServletRequest request){
		try {
			String token = tokenService.recuperarToken(request);
			Usuario usuario = usuarioRepository.findById(tokenService.getIdUsuario(token)).get();
			
			Avaliacao avaliacao = form.converterAvaliacao(estabelecimentoRepository, criterioRepository);
			avaliacao.setUsuario(usuario);
			avaliacaoRepository.save(avaliacao);
			return ResponseEntity.ok().body(new AvaliacaoCadastrarDto(avaliacao));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/avaliacao/{id}")
	public ResponseEntity<AvaliacoesPorEstabelecimentoEUsuarioDto> avaliacaoDoUsuarioPorEstabelecimento(@PathVariable Integer id, HttpServletRequest request) {
		try {
			String token = tokenService.recuperarToken(request);
			Integer idUsuario = tokenService.getIdUsuario(token);
			
			Avaliacao avaliacao = avaliacaoRepository.buscarAvalicaoComCriteriosPorEstabelecimento(idUsuario,id);
			
			return ResponseEntity.ok().body(new AvaliacoesPorEstabelecimentoEUsuarioDto(avaliacao));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/avaliacao/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id, HttpServletRequest request) {
		String token = tokenService.recuperarToken(request);
		Integer idUsuarioToken = tokenService.getIdUsuario(token);
		
		Optional<Avaliacao> optional = avaliacaoRepository.findById(id);
		
		if(optional.isPresent() && idUsuarioToken == optional.get().getUsuario().getId()) {
			avaliacaoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/avaliacao")
	public ResponseEntity<Page<AvaliacoesDoUsuarioDto>> avaliacoesDoUsuario(HttpServletRequest request,
			@PageableDefault(sort = "dataHoraAvaliacao", direction = Direction.DESC) Pageable paginacao) {
			String token = tokenService.recuperarToken(request);
			Integer idUsuario = tokenService.getIdUsuario(token);
			
			Page<AvaliacoesDoUsuarioDto> avaliacoes = avaliacaoRepository.buscarAvaliacoesDoUsuario(idUsuario, paginacao);
			
			return ResponseEntity.ok(avaliacoes);
	}

}
