package br.com.guilhermefausto.teavisei.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guilhermefausto.teavisei.model.Estabelecimento;
import br.com.guilhermefausto.teavisei.repository.EstabelecimentoRepository;

public class AtualizacaoEstabelecimentoForm {
	
	@NotNull @NotBlank @Length(min = 5)
	private String nome;
	
	@NotNull @NotBlank @Length(min = 5)
	private String cidade;
	
	@NotNull @NotBlank @Length(min = 5) 
	private String redesSociais;
	
	@NotNull @NotBlank @Length(min = 5)
	private String telefone;
	
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
	
	public Estabelecimento atualizar(Integer id, EstabelecimentoRepository estabelecimentoRepository) {
		Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).get();
		estabelecimento.setNome(nome);
		estabelecimento.setCidade(cidade);
		estabelecimento.setRedesSociais(redesSociais);
		estabelecimento.setTelefone(telefone);
		return estabelecimento;
	}

}
