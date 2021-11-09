package br.com.guilhermefausto.teavisei.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "avaliacoes", uniqueConstraints = { 
													@UniqueConstraint(name = "UniqueUsuarioEstabelecimento",columnNames = {"usuario_id","estabelecimento_id"})
												})

public class Avaliacao{
	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dataHoraAvaliacao = LocalDateTime.now();
	
	@Column(columnDefinition = "TEXT")
	private String comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Estabelecimento estabelecimento;
	
	@OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<AvaliacaoCriterio> avaliacoesCriterio = new HashSet<AvaliacaoCriterio>();
	
	public Avaliacao(String comentarios, Usuario usuario, Estabelecimento estabelecimento) {
		this.comentarios = comentarios;
		this.usuario = usuario;
		this.estabelecimento = estabelecimento;
	}
	
	public Avaliacao() {
		
	}
	
	public Set<AvaliacaoCriterio> getAvaliacoesCriterio() {
		return avaliacoesCriterio;
	}

	public void setAvaliacoesCriterio(Set<AvaliacaoCriterio> avaliacaoCriterio) {
		this.avaliacoesCriterio = avaliacaoCriterio;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
	public void adicionarAvaliacaoCriterio(Criterio criterio, BigDecimal nota) {
		AvaliacaoCriterio avaliacaoCriterio = new AvaliacaoCriterio(this, criterio, nota);
		avaliacoesCriterio.add(avaliacaoCriterio);
		criterio.getAvaliacoesCriterio().add(avaliacaoCriterio);
	}
	
	
}
