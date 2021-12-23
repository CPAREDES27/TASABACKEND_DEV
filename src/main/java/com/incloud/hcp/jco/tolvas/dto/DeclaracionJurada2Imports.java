package com.incloud.hcp.jco.tolvas.dto;

import java.util.HashMap;
import java.util.List;

public class DeclaracionJurada2Imports {
    private String centro;
    private String fecha;
    private String tolva;
    private String ubicacion;
    private List<HashMap<String, Object>> descargas;
    private String observacion;

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTolva() {
        return tolva;
    }

    public void setTolva(String tolva) {
        this.tolva = tolva;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<HashMap<String, Object>> getDescargas() {
        return descargas;
    }

    public void setDescargas(List<HashMap<String, Object>> descargas) {
        this.descargas = descargas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
