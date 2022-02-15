package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.LinkedHashMap;
import java.util.List;

public class PesCompetenciaProdImports {

    private String titulo;
    private String fechaInicio;
    private String fechaFin;
    private List<LinkedHashMap<String, Object>> columnas;
    private List<LinkedHashMap<String, Object>> filas;

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
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<LinkedHashMap<String, Object>> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<LinkedHashMap<String, Object>> columnas) {
        this.columnas = columnas;
    }

    public List<LinkedHashMap<String, Object>> getFilas() {
        return filas;
    }

    public void setFilas(List<LinkedHashMap<String, Object>> filas) {
        this.filas = filas;
    }
}
