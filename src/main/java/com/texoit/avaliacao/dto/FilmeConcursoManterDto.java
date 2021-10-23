package com.texoit.avaliacao.dto;
import com.texoit.avaliacao.model.FilmeConcurso;
import com.texoit.avaliacao.repository.FilmeConcursoRepository;
import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class FilmeConcursoManterDto {

	@NotNull @NotEmpty
	@Length(min = 4, max = 4)
	@Pattern(regexp="^[0-9]+$", message="O Ano Evento deve conter apenas dígitos")
	private String numeroAnoEvento;
	@NotNull @NotEmpty
	private String nomeFilme;
	@NotNull @NotEmpty
	private String nomeEstudio;
	@NotNull @NotEmpty
	private String nomeProdutor;
	@NotNull @NotEmpty
	@Pattern(regexp="^(true|false)+$", message="O vencedor deve preenchido com true (sim) ou false (não)")
	private String vencedor;

	public String getNumeroAnoEvento() {
		return numeroAnoEvento;
	}

	public void setNumeroAnoEvento(String numeroAnoEvento) {
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

	public String getVencedor() {
		return vencedor;
	}

	public void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}

	public FilmeConcursoManterDto() {
	}

	public FilmeConcursoManterDto(String numeroAnoEvento, String nomeFilme, String nomeEstudio, String nomeProdutor, String vencedor) {
		this.numeroAnoEvento = numeroAnoEvento;
		this.nomeFilme = nomeFilme;
		this.nomeEstudio = nomeEstudio;
		this.nomeProdutor = nomeProdutor;
		this.vencedor = vencedor;
	}

	public FilmeConcurso converterParaEntidade() {
		return new FilmeConcurso(Long.parseLong(numeroAnoEvento), nomeFilme, nomeEstudio, nomeProdutor, Boolean.parseBoolean(vencedor));
	}

	public FilmeConcurso atualizar(Integer idFilmeConcurso, FilmeConcursoRepository filmeConcursoRepository) {
		FilmeConcurso filmeConcurso = filmeConcursoRepository.getOne(idFilmeConcurso);

		filmeConcurso.setNumeroAnoEvento(Long.parseLong(this.numeroAnoEvento));
		filmeConcurso.setNomeFilme(this.nomeFilme);
		filmeConcurso.setNomeEstudio(this.nomeEstudio);
		filmeConcurso.setNomeProdutor(this.nomeProdutor);
		filmeConcurso.setVencedor(Boolean.parseBoolean(this.vencedor));

		return filmeConcurso;
	}
}
