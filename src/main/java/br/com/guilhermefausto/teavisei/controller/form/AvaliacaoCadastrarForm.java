package br.com.guilhermefausto.teavisei.controller.form;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.guilhermefausto.teavisei.model.Avaliacao;
import br.com.guilhermefausto.teavisei.model.Criterio;
import br.com.guilhermefausto.teavisei.model.Estabelecimento;
import br.com.guilhermefausto.teavisei.repository.CriterioRepository;
import br.com.guilhermefausto.teavisei.repository.EstabelecimentoRepository;

public class AvaliacaoCadastrarForm {
	
	
	private String comentarios;
	
	@NotNull
	private Integer estabelecimento;
	
	@NotNull @NotEmpty @Valid
	private List<AvaliacaoCriterioForm> avaliacoes;
		
	
	public String getComentarios() {
		return comentarios;
	}



	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Integer getEstabelecimento() {
		return estabelecimento;
	}



	public void setEstabelecimento(Integer estabelecimento) {
		this.estabelecimento = estabelecimento;
	}



	public List<AvaliacaoCriterioForm> getAvaliacoes() {
		return avaliacoes;
	}



	public void setAvaliacoes(List<AvaliacaoCriterioForm> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}



	public Avaliacao converterAvaliacao( 
								EstabelecimentoRepository establecimentoRepo,
								CriterioRepository criterioRepo){
		Boolean verificacoes = false;
		Avaliacao avaliacao = new Avaliacao();
		
		Optional<Estabelecimento> optEstabelecimento = establecimentoRepo.findById(this.estabelecimento);
		
		if(optEstabelecimento.isPresent()) {
			avaliacao.setComentarios(comentarios);
			avaliacao.setEstabelecimento(optEstabelecimento.get());
		}else {
			verificacoes = true;
		}	
		
		for (AvaliacaoCriterioForm avaliacaoCriterioForm : avaliacoes) {
			Optional<Criterio> optCriterio = criterioRepo.findById(avaliacaoCriterioForm.getCriterio());
			if(optCriterio.isPresent()) {
				avaliacao.adicionarAvaliacaoCriterio(optCriterio.get(), avaliacaoCriterioForm.getNota());
			}else {
				verificacoes = true;
			}
		}			

		if(!verificacoes) {
			return avaliacao;
		}else {
			throw new IllegalArgumentException("Não foi possível cadastrar a avaliação! Verifique se o Estabelecimento ou Criterios informados existem");
		}
	}
}
