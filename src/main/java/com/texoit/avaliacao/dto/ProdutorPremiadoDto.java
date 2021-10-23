package com.texoit.avaliacao.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutorPremiadoDto {
	@JsonProperty("previousWin")
	private Long anoVitoriaAnterior;
	@JsonProperty("followingWin")
	private Long anoVitoriaSeguinte;
	@JsonProperty("producer")
	private String nomeProdutor;
	@JsonProperty("interval")
	private int intervaloPremiacao;

	public void setAnoVitoriaAnterior(Long anoVitoriaAnterior) {
		this.anoVitoriaAnterior = anoVitoriaAnterior;
	}

	public void setAnoVitoriaSeguinte(Long anoVitoriaSeguinte) {
		this.anoVitoriaSeguinte = anoVitoriaSeguinte;
	}

	public void setNomeProdutor(String nomeProdutor) {
		this.nomeProdutor = nomeProdutor;
	}

	public void setIntervaloPremiacao(int intervaloPremiacao) {
		this.intervaloPremiacao = intervaloPremiacao;
	}

	public Long getAnoVitoriaAnterior() {
		return anoVitoriaAnterior;
	}

	public Long getAnoVitoriaSeguinte() {
		return anoVitoriaSeguinte;
	}

	public String getNomeProdutor() {
		return nomeProdutor;
	}

	public int getIntervaloPremiacao() {
		return intervaloPremiacao;
	}

	public ProdutorPremiadoDto() {
	}
}
