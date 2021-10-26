package com.texoit.avaliacao.repository;

import java.util.List;

import com.texoit.avaliacao.model.FilmeConcurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmeConcursoRepository extends JpaRepository<FilmeConcurso, Integer> {
	@Query("SELECT t from FilmeConcurso t where LOWER(t.nomeFilme) LIKE LOWER(CONCAT('%', ?1,'%'))")
	List<FilmeConcurso> findByNomeFilme(String nomeFilme);

	@Query("SELECT filmeConcurso FROM FilmeConcurso filmeConcurso WHERE filmeConcurso.vencedor IS TRUE AND filmeConcurso.nomeProdutor IN (SELECT t.nomeProdutor FROM FilmeConcurso t WHERE t.vencedor IS TRUE GROUP BY t.nomeProdutor HAVING MOD(COUNT(t.nomeProdutor),2) = 0) ORDER BY filmeConcurso.nomeProdutor, filmeConcurso.numeroAnoEvento DESC")
	List<FilmeConcurso> recuperarMaisPremiados();

}
