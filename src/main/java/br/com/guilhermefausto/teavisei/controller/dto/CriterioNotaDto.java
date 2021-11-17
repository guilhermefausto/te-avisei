package br.com.guilhermefausto.teavisei.controller.dto;

import java.math.BigDecimal;

import br.com.guilhermefausto.teavisei.model.AvaliacaoCriterio;

public class CriterioNotaDto {
	
	private String criterio;
	private BigDecimal nota;

	public CriterioNotaDto(AvaliacaoCriterio avaliacaoCriterio) {
		this.criterio = avaliacaoCriterio.getCriterio().getNome();
		this.nota = avaliacaoCriterio.getNota();
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public BigDecimal getNota() {
		return nota;
	}

	public void setNota(BigDecimal nota) {
		this.nota = nota;
	}
	
	
	
}
