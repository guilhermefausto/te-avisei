package br.com.guilhermefausto.teavisei.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.guilhermefausto.teavisei.model.Perfil;
import br.com.guilhermefausto.teavisei.model.PerfilEnum;
import br.com.guilhermefausto.teavisei.model.Usuario;
import br.com.guilhermefausto.teavisei.repository.PerfilRepository;

public class UsuarioCadastrarForm {
	
	@Email @NotBlank @NotEmpty
	private String email;
	
	@NotBlank @NotEmpty @Length(min = 6)
	private String senha;
	
	@NotBlank @NotEmpty @Length(min = 3)
	private String nome;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Usuario converter(PerfilRepository perfilRepository) {
		String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
		Perfil perfilUsuario = perfilRepository.findByNome(PerfilEnum.ROLE_USUARIO.toString()).get();
		
		return new Usuario(this.nome, this.email, senhaCriptografada, perfilUsuario);
	}

}
