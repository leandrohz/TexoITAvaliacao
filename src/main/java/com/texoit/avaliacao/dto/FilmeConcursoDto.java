package com.texoit.avaliacao.dto;

import com.texoit.avaliacao.model.FilmeConcurso;

import java.util.List;
import java.util.stream.Collectors;

public class FilmeConcursoDto {

	private Integer idFilmeConcurso;
	private Long numeroAnoEvento;
	private String nomeFilme;
	private String nomeEstudio;
	private String nomeProdutor;
	private Boolean vencedor;

	public Integer getIdFilmeConcurso() {
		return idFilmeConcurso;
	}

	public Long getNumeroAnoEvento() {
		return numeroAnoEvento;
	}

	public String getNomeFilme() {
		return nomeFilme;
	}

	public String getNomeEstudio() {
		return nomeEstudio;
	}

	public String getNomeProdutor() {
		return nomeProdutor;
	}

	public Boolean getVencedor() {
		return vencedor;
	}

	public FilmeConcursoDto(FilmeConcurso filmesConcursos) {
		this.idFilmeConcurso = filmesConcursos.getIdFilmeConcurso();
		this.numeroAnoEvento = filmesConcursos.getNumeroAnoEvento();
		this.nomeFilme = filmesConcursos.getNomeFilme();
		this.nomeEstudio = filmesConcursos.getNomeEstudio();
		this.nomeProdutor = filmesConcursos.getNomeProdutor();
		this.vencedor = filmesConcursos.getVencedor();
	}

	public static List<FilmeConcursoDto> converterParaDto(List<FilmeConcurso> filmesConcursos) {
		return filmesConcursos.stream().map(FilmeConcursoDto::new).collect(Collectors.toList());
	}
}
