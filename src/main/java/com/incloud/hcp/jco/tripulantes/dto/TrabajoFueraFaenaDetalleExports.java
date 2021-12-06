package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class TrabajoFueraFaenaDetalleExports {


    private String nrTrabajo;
    private String tipoTrabajo;
    private String semana;
    private String fechaInicio;
    private String fechaFin;
    private String descripcionTrabajo;
    private String observacion;
    private String usuarioCreacion;
    private String fechaHoraCreacion;
    private String usuarioModif;
    private String fechaHoraModif;
    private String[] fechas;
    private List<TrabajoFFDetalleDto> detalle;
    private String mensaje;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String[] getFechas() {
        return fechas;
    }

    public void setFechas(String[] fechas) {
        this.fechas = fechas;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModif() {
        return usuarioModif;
    }

    public void setUsuarioModif(String usuarioModif) {
        this.usuarioModif = usuarioModif;
    }

    public String getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(String fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public String getFechaHoraModif() {
        return fechaHoraModif;
    }

    public void setFechaHoraModif(String fechaHoraModif) {
        this.fechaHoraModif = fechaHoraModif;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

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

    public List<TrabajoFFDetalleDto> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<TrabajoFFDetalleDto> detalle) {
        this.detalle = detalle;
    }



    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
