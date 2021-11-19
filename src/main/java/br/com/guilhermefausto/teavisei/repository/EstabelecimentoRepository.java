package br.com.guilhermefausto.teavisei.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guilhermefausto.teavisei.controller.dto.EstabelecimentoDto;
import br.com.guilhermefausto.teavisei.controller.dto.IEstabelecimentoListaComMediaDto;
import br.com.guilhermefausto.teavisei.model.Estabelecimento;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {

	@Query(value = "SELECT e.id as id, e.cidade as cidade, e.nome as nome, e.telefone as telefone, e.redes_sociais as redesSociais, "
			+ "avg(ac.nota) as media "
			+ "FROM estabelecimentos e "
			+ "LEFT JOIN avaliacoes a ON a.estabelecimento_id = e.id "
			+ "LEFT JOIN avaliacoes_criterios ac ON ac.avaliacao_id = a.id "
			+ "WHERE e.moderado "
			+ "GROUP BY e.id ",
			nativeQuery = true,
			countQuery = "SELECT COUNT(e.id) "
					+ "FROM estabelecimentos e "
					+ "LEFT JOIN avaliacoes a ON a.estabelecimento_id = e.id "
					+ "LEFT JOIN avaliacoes_criterios ac ON ac.avaliacao_id = a.id "
					+ "WHERE e.moderado "
					+ "GROUP BY e.id ")
	Page<IEstabelecimentoListaComMediaDto> listarEstabelecimentosComMediaDeNota(Pageable paginacao);

	
	@Query(value = "SELECT e.id as id, e.cidade as cidade, e.nome as nome, e.telefone as telefone, e.redes_sociais as redesSociais, "
			+ "avg(ac.nota) as media "
			+ "FROM estabelecimentos e "
			+ "LEFT JOIN avaliacoes a ON a.estabelecimento_id = e.id "
			+ "LEFT JOIN avaliacoes_criterios ac ON ac.avaliacao_id = a.id "
			+ "WHERE e.moderado AND e.cidade = :nomeCidade "
			+ "GROUP BY e.id ",
			nativeQuery = true,
			countQuery = "SELECT COUNT(e.id) "
					+ "FROM estabelecimentos e "
					+ "LEFT JOIN avaliacoes a ON a.estabelecimento_id = e.id "
					+ "LEFT JOIN avaliacoes_criterios ac ON ac.avaliacao_id = a.id "
					+ "WHERE e.moderado AND e.cidade = :nomeCidade "
					+ "GROUP BY e.id ")
	Page<IEstabelecimentoListaComMediaDto> listarEstabelecimentosComMediaDeNotaPorCidade(@Param("nomeCidade") String nomeCidade,Pageable paginacao);

	@Query(value = "SELECT e FROM Estabelecimento e WHERE e.moderado=FALSE")
	Page<EstabelecimentoDto> listarEstabelecimentosParaModerar(Pageable paginacao);

	@Query(value = "SELECT e FROM Estabelecimento e WHERE e.moderado=FALSE AND id=:id")
	Optional<Estabelecimento> buscarEstabelecimentoPorIdParaModerar(@Param("id") Integer id);
}
