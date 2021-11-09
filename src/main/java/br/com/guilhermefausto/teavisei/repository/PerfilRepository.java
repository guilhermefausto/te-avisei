package br.com.guilhermefausto.teavisei.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guilhermefausto.teavisei.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

	Optional<Perfil> findByNome(String nome);
}
