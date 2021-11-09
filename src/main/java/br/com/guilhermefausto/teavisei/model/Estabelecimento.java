package br.com.guilhermefausto.teavisei.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "estabelecimentos")
public class Estabelecimento {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	private String cidade;
	private String redesSociais;
	private String telefone;
	private Boolean moderado = false;
	
	@OneToMany(mappedBy = "estabelecimento")//cascade = CascadeType.ALL
	private List<Avaliacao> avaliacoes;
	
	public Estabelecimento(String nome, String cidade, String redesSociais, String telefone) {
		this.nome = nome;
		this.cidade = cidade;
		this.redesSociais = redesSociais;
		this.telefone = telefone;
	}
	
	public Estabelecimento() {
		
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
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getRedesSociais() {
		return redesSociais;
	}
	public void setRedesSociais(String redesSociais) {
		this.redesSociais = redesSociais;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Boolean getModerado() {
		return moderado;
	}
	
	public void setModerado(Boolean moderado) {
		this.moderado = moderado;
	}
	
}
