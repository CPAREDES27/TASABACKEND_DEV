package com.incloud.hcp.jco.maestro.dto;

public class HorometrosDto {
    private String indicador;
    private String tipoHorometro;
    private String descTipoHorom;

    public String getTipoHorometro() {
        return tipoHorometro;
    }

    public void setTipoHorometro(String tipoHorometro) {
        this.tipoHorometro = tipoHorometro;
    }

    public String getDescTipoHorom() {
        return descTipoHorom;
    }

    public void setDescTipoHorom(String descTipoHorom) {
        this.descTipoHorom = descTipoHorom;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }
}
