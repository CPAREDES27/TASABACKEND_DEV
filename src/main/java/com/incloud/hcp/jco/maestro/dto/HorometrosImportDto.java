package com.incloud.hcp.jco.maestro.dto;

public class HorometrosImportDto {
    private String evento;
    private String marea;
    private String centro;
    private String nroEvento;

    public String getNroEvento() {
        return nroEvento;
    }

    public void setNroEvento(String nroEvento) {
        this.nroEvento = nroEvento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getMarea() {
        return marea;
    }

    public void setMarea(String marea) {
        this.marea = marea;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }
}
