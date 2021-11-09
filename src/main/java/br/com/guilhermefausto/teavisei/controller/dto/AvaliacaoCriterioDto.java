package br.com.guilhermefausto.teavisei.controller.dto;

import java.math.BigDecimal;

import br.com.guilhermefausto.teavisei.model.AvaliacaoCriterio;

public class AvaliacaoCriterioDto {
	
	private BigDecimal nota;
	private String criterioNome;
	private Integer criterioId;
	
	public AvaliacaoCriterioDto(AvaliacaoCriterio avaliacaoCriterio) {
		this.nota = avaliacaoCriterio.getNota();
		this.criterioId = avaliacaoCriterio.getCriterio().getId();
		this.criterioNome = avaliacaoCriterio.getCriterio().getNome();
	}
	
	public BigDecimal getNota() {
		return nota;
	}
	public void setNota(BigDecimal nota) {
		this.nota = nota;
	}
	public String getCriterioNome() {
		return criterioNome;
	}
	public void setCriterioNome(String criterioNome) {
		this.criterioNome = criterioNome;
	}
	public Integer getCriterioId() {
		return criterioId;
	}
	public void setCriterioId(Integer criterioId) {
		this.criterioId = criterioId;
	}
	
	

}
