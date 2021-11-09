package br.com.guilhermefausto.teavisei.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guilhermefausto.teavisei.controller.dto.AvaliacoesDoUsuarioDto;
import br.com.guilhermefausto.teavisei.controller.dto.ComentariosDto;
import br.com.guilhermefausto.teavisei.controller.dto.MediaAvaliacaoDoEstabelecimentoPorCriterio;
import br.com.guilhermefausto.teavisei.model.Avaliacao;
import br.com.guilhermefausto.teavisei.model.Estabelecimento;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

	//Tres formas diferentes de buscar o mesmo resultado
	
	List<Avaliacao> findByEstabelecimento(Estabelecimento estabelecimento);
	
	List<Avaliacao> findByEstabelecimentoId(Integer id);
	
	@Query(value = "SELECT a, e FROM Avaliacao a JOIN a.estabelecimento e WHERE e.id= :id")
	List<Avaliacao> buscarAvaliacoesPorEstabelecimentoComObjetoEstabelecimento(@Param("id") Integer id);

	@Query(value = "SELECT AVG(ac.nota) as media, c.nome as criterio "
			+ "FROM criterios c INNER JOIN avaliacoes_criterios ac ON ac.criterio_id = c.id "
			+ "INNER JOIN avaliacoes a ON ac.avaliacao_id = a.id "
			+ "WHERE a.estabelecimento_id = :id "
			+ "GROUP BY criterio_id", nativeQuery = true)
	//@Query(value = "SELECT SUM(a.nota) AVG(a.nota) FROM Avaliacao a JOIN a.criterio c WHERE a.Estabelecimento.id = :")
	List<MediaAvaliacaoDoEstabelecimentoPorCriterio> buscarMediaDeAvaliacaoDoEstabelecimentoPorCriterio(@Param("id")Integer id);

	
	@Query(value = "SELECT a FROM Avaliacao a JOIN FETCH a.avaliacoesCriterio ac JOIN FETCH ac.criterio c "
			+ "WHERE a.usuario.id = :idUsuario AND a.estabelecimento.id = :idEstabelecimento")
	Avaliacao buscarAvalicaoComCriteriosPorEstabelecimento(@Param("idUsuario") Integer idUsuario, @Param("idEstabelecimento") Integer idEstabelecimento);
	
	@Query(value = "SELECT a.comentarios as comentarios, a.dataHoraAvaliacao as dataHoraAvaliacao FROM Avaliacao a WHERE a.estabelecimento.id = :id")
	Page<ComentariosDto> buscarComentariosEDataDasAvaliacoes(@Param("id") Integer id, Pageable paginacao);
	
	@Query(value = "SELECT a.id as id, a.dataHoraAvaliacao as dataHoraAvaliacao, "
			+ "a.estabelecimento.id as estabelecimentoId, a.estabelecimento.nome as nomeEstabelecimento "
			+ "FROM Avaliacao a WHERE a.usuario.id = :id")
	Page<AvaliacoesDoUsuarioDto> buscarAvaliacoesDoUsuario(@Param("id") Integer id, Pageable paginacao);
}
