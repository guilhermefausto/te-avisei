package br.com.guilhermefausto.teavisei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guilhermefausto.teavisei.model.AvaliacaoCriterio;
import br.com.guilhermefausto.teavisei.model.AvaliacaoCriterioId;

public interface AvaliacaoCriterioRepository extends JpaRepository<AvaliacaoCriterio, AvaliacaoCriterioId> {
}
