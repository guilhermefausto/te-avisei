package br.com.guilhermefausto.teavisei.controller.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ValidacaoListaObjetosForm<T> {
	
	@Valid @NotEmpty @NotNull
	private List<T> avaliacoes;
	
	public ValidacaoListaObjetosForm(List<T> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	public ValidacaoListaObjetosForm() {
	}
	
	public void setAvaliacoes(List<T> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	public List<T> getAvaliacoes() {
		return avaliacoes;
	}

}
