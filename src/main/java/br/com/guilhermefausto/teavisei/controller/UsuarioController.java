package br.com.guilhermefausto.teavisei.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.guilhermefausto.teavisei.controller.dto.UsuarioCriadoDto;
import br.com.guilhermefausto.teavisei.controller.form.UsuarioCadastrarForm;
import br.com.guilhermefausto.teavisei.model.Usuario;
import br.com.guilhermefausto.teavisei.repository.PerfilRepository;
import br.com.guilhermefausto.teavisei.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@PostMapping("signup")
	public ResponseEntity<?> inscrever(@RequestBody @Valid UsuarioCadastrarForm form){
		try {
			Usuario usuario = form.converter(perfilRepository);
			usuarioRepository.save(usuario);
			
			return ResponseEntity.ok().body(new UsuarioCriadoDto(usuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
	
}
