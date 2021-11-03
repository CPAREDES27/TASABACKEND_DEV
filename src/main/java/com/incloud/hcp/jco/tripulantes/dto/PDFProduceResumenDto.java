package com.incloud.hcp.jco.tripulantes.dto;

public class PDFProduceResumenDto {

    private String fechaInicio;
    private String fechaFin;
    private String embarcacion;
    private String matricula;
    private String pescaDescargada;
    private String cantidadDescargada;

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEmbarcacion() {
        return embarcacion;
    }

    public void setEmbarcacion(String embarcacion) {
        this.embarcacion = embarcacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPescaDescargada() {
        return pescaDescargada;
    }

    public void setPescaDescargada(String pescaDescargada) {
        this.pescaDescargada = pescaDescargada;
    }

    public String getCantidadDescargada() {
        return cantidadDescargada;
    }

    public void setCantidadDescargada(String cantidadDescargada) {
        this.cantidadDescargada = cantidadDescargada;
    }
}
