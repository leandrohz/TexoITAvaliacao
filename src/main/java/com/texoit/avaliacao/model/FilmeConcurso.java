package com.texoit.avaliacao.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_FILME_CONCURSO")
public class FilmeConcurso {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FILME_CONCURSO")
	private Integer idFilmeConcurso;
	@Column(name = "NR_ANO_EVENTO")
	@NotNull
	private Long numeroAnoEvento;
	@Column(name = "NM_FILME")
	@NotNull
	private String nomeFilme;
	@Column(name = "NM_ESTUDIO")
	@NotNull
	private String nomeEstudio;
	@Column(name = "NM_PRODUTOR")
	@NotNull
	private String nomeProdutor;
	@Column(name = "ST_VENCEDOR")
	@NotNull
	private Boolean vencedor;

	public FilmeConcurso() {
	}

	public FilmeConcurso(Long numeroAnoEvento, String nomeFilme, String nomeEstudio, String nomeProdutor, Boolean vencedor) {
		this.numeroAnoEvento = numeroAnoEvento;
		this.nomeFilme = nomeFilme;
		this.nomeEstudio = nomeEstudio;
		this.nomeProdutor = nomeProdutor;
		this.vencedor = vencedor;
	}

	public Integer getIdFilmeConcurso() {
		return idFilmeConcurso;
	}

	public void setIdFilmeConcurso(Integer idFilmeConcurso) {
		this.idFilmeConcurso = idFilmeConcurso;
	}

	public Long getNumeroAnoEvento() {
		return numeroAnoEvento;
	}

	public void setNumeroAnoEvento(Long numeroAnoEvento) {
		this.numeroAnoEvento = numeroAnoEvento;
	}

	public String getNomeFilme() {
		return nomeFilme;
	}

	public void setNomeFilme(String nomeFilme) {
		this.nomeFilme = nomeFilme;
	}

	public String getNomeEstudio() {
		return nomeEstudio;
	}

	public void setNomeEstudio(String nomeEstudio) {
		this.nomeEstudio = nomeEstudio;
	}

	public String getNomeProdutor() {
		return nomeProdutor;
	}

	public void setNomeProdutor(String nomeProdutor) {
		this.nomeProdutor = nomeProdutor;
	}

	public Boolean getVencedor() {
		return vencedor;
	}

	public void setVencedor(Boolean vencedor) {
		this.vencedor = vencedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFilmeConcurso == null) ? 0 : idFilmeConcurso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilmeConcurso other = (FilmeConcurso) obj;
		if (idFilmeConcurso == null) {
			if (other.idFilmeConcurso != null)
				return false;
		} else if (!idFilmeConcurso.equals(other.idFilmeConcurso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FilmeConcurso{" +
				", numeroAnoEvento=" + numeroAnoEvento +
				", nomeFilme='" + nomeFilme + '\'' +
				", nomeEstudio='" + nomeEstudio + '\'' +
				", nomeProdutor='" + nomeProdutor + '\'' +
				", vencedor=" + vencedor +
				'}';
	}
}
