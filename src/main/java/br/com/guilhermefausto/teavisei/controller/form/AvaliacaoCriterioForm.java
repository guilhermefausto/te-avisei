package br.com.guilhermefausto.teavisei.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AvaliacaoCriterioForm {
	
	@NotNull @Min(0) @Max(10)
	private BigDecimal nota;
	
	@NotNull
	private Integer criterio;

	public BigDecimal getNota() {
		return nota;
	}

	public void setNota(BigDecimal nota) {
		this.nota = nota;
	}

	public Integer getCriterio() {
		return criterio;
	}

	public void setCriterio(Integer criterio) {
		this.criterio = criterio;
	}

	

}
