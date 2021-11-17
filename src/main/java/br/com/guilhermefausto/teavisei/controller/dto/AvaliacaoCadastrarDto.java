package br.com.guilhermefausto.teavisei.controller.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.guilhermefausto.teavisei.model.Avaliacao;
import br.com.guilhermefausto.teavisei.model.AvaliacaoCriterio;

public class AvaliacaoCadastrarDto {
	
	private Integer id;
	private LocalDateTime dataHoraAvaliacao;
	private String comentarios;
	private Integer idUsuario;
	private Integer idEstabelecimento;
	private Set<CriterioNotaDto> avaliacoes = new HashSet<CriterioNotaDto>();
	
	public AvaliacaoCadastrarDto(Avaliacao avaliacao) {
		this.id = avaliacao.getId();
		this.dataHoraAvaliacao = avaliacao.getDataHoraAvaliacao();
		this.comentarios = avaliacao.getComentarios();
		this.idUsuario = avaliacao.getUsuario().getId();
		this.idEstabelecimento = avaliacao.getEstabelecimento().getId();
		
		for (AvaliacaoCriterio avaliacaoCriterio : avaliacao.getAvaliacoesCriterio()) {
			this.avaliacoes.add(new CriterioNotaDto(avaliacaoCriterio));
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(Integer idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public Set<CriterioNotaDto> getAvaliacoes() {
		return avaliacoes;
	}
	
	public void setAvaliacoes(Set<CriterioNotaDto> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
}
