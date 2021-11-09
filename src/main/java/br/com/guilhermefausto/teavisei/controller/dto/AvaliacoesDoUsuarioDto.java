package br.com.guilhermefausto.teavisei.controller.dto;

import java.time.LocalDateTime;

public interface AvaliacoesDoUsuarioDto {
	
	public Integer getId();
	public LocalDateTime getDataHoraAvaliacao();
	public Integer getEstabelecimentoId();
	public String getNomeEstabelecimento();

}
