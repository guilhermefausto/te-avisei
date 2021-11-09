package br.com.guilhermefausto.teavisei.controller.dto;

import br.com.guilhermefausto.teavisei.model.Usuario;

public class UsuarioCriadoDto {

	private String nome;
	private String email;
	
	public UsuarioCriadoDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}
