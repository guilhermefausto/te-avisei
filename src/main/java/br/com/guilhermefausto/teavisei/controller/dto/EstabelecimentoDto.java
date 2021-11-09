package br.com.guilhermefausto.teavisei.controller.dto;

import br.com.guilhermefausto.teavisei.model.Estabelecimento;

public class EstabelecimentoDto {
	
	private Integer id;
	private String nome;
	private String cidade; 
	private String redesSociais;
	private String telefone;
	private Boolean moderado;
	
	public EstabelecimentoDto(Estabelecimento estabelecimento) {
		this.id = estabelecimento.getId();
		this.nome = estabelecimento.getNome();
		this.cidade = estabelecimento.getCidade();
		this.redesSociais = estabelecimento.getRedesSociais();
		this.telefone = estabelecimento.getTelefone();
		this.moderado = estabelecimento.getModerado();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getRedesSociais() {
		return redesSociais;
	}

	public void setRedesSociais(String redesSociais) {
		this.redesSociais = redesSociais;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getModerado() {
		return moderado;
	}

	public void setModerado(Boolean moderado) {
		this.moderado = moderado;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
