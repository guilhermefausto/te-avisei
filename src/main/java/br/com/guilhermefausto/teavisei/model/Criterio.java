package br.com.guilhermefausto.teavisei.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "criterios")
public class Criterio {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String descricao;
	
	@OneToMany(mappedBy = "criterio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<AvaliacaoCriterio> avaliacoesCriterio;

	public Criterio() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Set<AvaliacaoCriterio> getAvaliacoesCriterio() {
		return avaliacoesCriterio;
	}
	public void setAvaliacoesCriterio(Set<AvaliacaoCriterio> avaliacaoCriterio) {
		this.avaliacoesCriterio = avaliacaoCriterio;
	}	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	        Criterio criterio = (Criterio) o;
	        return Objects.equals(nome, criterio.nome);
	}
	 
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
	
}
