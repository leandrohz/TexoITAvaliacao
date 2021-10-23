package com.texoit.avaliacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IntervaloRepeticaoPremioDto {
    @JsonProperty("min")
    private List<ProdutorPremiadoDto> intervaloMinimo;
    @JsonProperty("max")
    private List<ProdutorPremiadoDto> intervaloMaximo;

    public List<ProdutorPremiadoDto> getIntervaloMinimo() {
        return intervaloMinimo;
    }

    public void setIntervaloMinimo(List<ProdutorPremiadoDto> intervaloMinimo) {
        this.intervaloMinimo = intervaloMinimo;
    }

    public List<ProdutorPremiadoDto> getIntervaloMaximo() {
        return intervaloMaximo;
    }

    public void setIntervaloMaximo(List<ProdutorPremiadoDto> intervaloMaximo) {
        this.intervaloMaximo = intervaloMaximo;
    }
}
