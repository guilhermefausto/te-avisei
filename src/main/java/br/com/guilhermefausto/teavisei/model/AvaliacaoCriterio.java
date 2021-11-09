package br.com.guilhermefausto.teavisei.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "avaliacoes_criterios")
public class AvaliacaoCriterio {	
	
	@EmbeddedId
	private AvaliacaoCriterioId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@MapsId("avaliacaoId")
	private Avaliacao avaliacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("criterioId")
	private Criterio criterio;
	
	private BigDecimal nota;
	
	public AvaliacaoCriterio() {
	}
	
	
	
	public AvaliacaoCriterio(Avaliacao avaliacao, Criterio criterio, BigDecimal nota) {
		this.avaliacao = avaliacao;
		this.criterio = criterio;
		this.nota = nota;
		this.id = new AvaliacaoCriterioId(avaliacao.getId(),criterio.getId());
	}



	public AvaliacaoCriterioId getId() {
		return id;
	}

	public void setId(AvaliacaoCriterioId id) {
		this.id = id;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	public BigDecimal getNota() {
		return nota;
	}

	public void setNota(BigDecimal nota) {
		this.nota = nota;
	}

    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
 
    if (o == null || getClass() != o.getClass())
            return false;
 
        AvaliacaoCriterio that = (AvaliacaoCriterio) o;
        return Objects.equals(avaliacao, that.avaliacao) &&
               Objects.equals(criterio, that.criterio);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(avaliacao, criterio);
    }

}	
