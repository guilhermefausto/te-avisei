package br.com.guilhermefausto.teavisei.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class AvaliacaoCriterioId implements Serializable{	

		private static final long serialVersionUID = 1L;

		private Integer avaliacaoId;
		
		private Integer criterioId;
		
		public AvaliacaoCriterioId() {
		}
		
		public AvaliacaoCriterioId(Integer avaliacaoId, Integer criterioId) {
			this.avaliacaoId = avaliacaoId;
			this.criterioId = criterioId;
		}



		public Integer getAvaliacaoId() {
			return avaliacaoId;
		}

		public void setAvaliacaoId(Integer avaliacaoId) {
			this.avaliacaoId = avaliacaoId;
		}

		public Integer getCriterioId() {
			return criterioId;
		}

		public void setCriterioId(Integer criterioId) {
			this.criterioId = criterioId;
		}

		@Override
		public boolean equals(Object o) {
		if (this == o) return true;
		 
		if (o == null || getClass() != o.getClass()) return false;
		 
		AvaliacaoCriterioId that = (AvaliacaoCriterioId) o;
		        return Objects.equals(avaliacaoId, that.avaliacaoId) &&
		               Objects.equals(criterioId, that.criterioId);
		    }
		 
		    @Override
		    public int hashCode() {
		        return Objects.hash(avaliacaoId, criterioId);
		    }
		
		
}
