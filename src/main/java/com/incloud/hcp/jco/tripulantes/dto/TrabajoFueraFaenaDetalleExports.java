package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class TrabajoFueraFaenaDetalleExports {


    private String nrTrabajo;
    private String tipoTrabajo;
    private String semana;
    private String fechaInicio;
    private String fechaFin;
    private String observacion;
    private List<HashMap<String, Object>> detalle;
    private String mensaje;

    public String getNrTrabajo() {
        return nrTrabajo;
    }

    public void setNrTrabajo(String nrTrabajo) {
        this.nrTrabajo = nrTrabajo;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<HashMap<String, Object>> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<HashMap<String, Object>> detalle) {
        this.detalle = detalle;
    }



    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
