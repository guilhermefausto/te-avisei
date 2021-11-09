package br.com.guilhermefausto.teavisei.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.guilhermefausto.teavisei.model.Avaliacao;

public class AvaliacoesPorEstabelecimentoEUsuarioDto {
	
	private LocalDateTime dataHoraAvaliacao;
	private String comentarios;
	private List<AvaliacaoCriterioDto> avaliacoesCriterio = new ArrayList<>();
	
	public AvaliacoesPorEstabelecimentoEUsuarioDto(Avaliacao avaliacao) {
		this.dataHoraAvaliacao = avaliacao.getDataHoraAvaliacao();
		this.comentarios = avaliacao.getComentarios();
		avaliacao.getAvaliacoesCriterio().stream().forEach(avaliacaoCriterio -> {
			this.avaliacoesCriterio.add(new AvaliacaoCriterioDto(avaliacaoCriterio));
		});	
	}

	public LocalDateTime getDataHoraAvaliacao() {
		return dataHoraAvaliacao;
	}

	public void setDataHoraAvaliacao(LocalDateTime dataHoraAvaliacao) {
		this.dataHoraAvaliacao = dataHoraAvaliacao;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public List<AvaliacaoCriterioDto> getAvaliacoesCriterio() {
		return avaliacoesCriterio;
	}

	public void setAvaliacoesCriterio(List<AvaliacaoCriterioDto> avaliacoesCriterio) {
		this.avaliacoesCriterio = avaliacoesCriterio;
	}
	
	
}
