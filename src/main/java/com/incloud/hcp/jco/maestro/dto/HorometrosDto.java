package com.incloud.hcp.jco.maestro.dto;

public class HorometrosDto {
    private String codigo;
    private String indicador;
    private String tipoHorometro;
    private String descTipoHorom;
    private String lectura;
    private String Averiado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLectura() {
        return lectura;
    }

    public void setLectura(String lectura) {
        this.lectura = lectura;
    }

    public String getAveriado() {
        return Averiado;
    }

    public void setAveriado(String Averiado) {
        this.Averiado = Averiado;
    }

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
