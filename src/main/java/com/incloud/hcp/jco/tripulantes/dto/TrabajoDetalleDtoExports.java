package com.incloud.hcp.jco.tripulantes.dto;

import java.util.List;

public class TrabajoDetalleDtoExports {
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
    private List<TrabajoDetalleDto> detalle;
    private String mensaje;
    private String estado;
    private String fechaCreacion;
    private String horaCreacion;
    private String fechaModif;
    private String nmusm;

    public String getNmusm() {
        return nmusm;
    }

    public void setNmusm(String nmusm) {
        this.nmusm = nmusm;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public String getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(String fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getHoraModif() {
        return horaModif;
    }

    public void setHoraModif(String horaModif) {
        this.horaModif = horaModif;
    }

    private String horaModif;

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

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(String fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public String getUsuarioModif() {
        return usuarioModif;
    }

    public void setUsuarioModif(String usuarioModif) {
        this.usuarioModif = usuarioModif;
    }

    public String getFechaHoraModif() {
        return fechaHoraModif;
    }

    public void setFechaHoraModif(String fechaHoraModif) {
        this.fechaHoraModif = fechaHoraModif;
    }

    public String[] getFechas() {
        return fechas;
    }

    public void setFechas(String[] fechas) {
        this.fechas = fechas;
    }

    public List<TrabajoDetalleDto> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<TrabajoDetalleDto> detalle) {
        this.detalle = detalle;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
