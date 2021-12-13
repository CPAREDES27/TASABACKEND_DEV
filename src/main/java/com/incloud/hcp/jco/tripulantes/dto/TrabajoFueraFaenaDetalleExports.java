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
    private String codEmbarcacion;
    private String embarcacion;
    private List<HashMap<String, Object>>t_trabaff;
    private List<HashMap<String, Object>>t_textos;
    private List<HashMap<String, Object>>t_trabaj;
    private List<HashMap<String, Object>>t_mensaje;
    private List<HashMap<String, Object>>t_fechas;

    public List<HashMap<String, Object>> getT_fechas() {
        return t_fechas;
    }

    public void setT_fechas(List<HashMap<String, Object>> t_fechas) {
        this.t_fechas = t_fechas;
    }

    public List<HashMap<String, Object>> getT_textos() {
        return t_textos;
    }

    public void setT_textos(List<HashMap<String, Object>> t_textos) {
        this.t_textos = t_textos;
    }

    public List<HashMap<String, Object>> getT_trabaj() {
        return t_trabaj;
    }

    public void setT_trabaj(List<HashMap<String, Object>> t_trabaj) {
        this.t_trabaj = t_trabaj;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> getT_trabaff() {
        return t_trabaff;
    }

    public void setT_trabaff(List<HashMap<String, Object>> t_trabaff) {
        this.t_trabaff = t_trabaff;
    }

    public String getCodEmbarcacion() {
        return codEmbarcacion;
    }

    public void setCodEmbarcacion(String codEmbarcacion) {
        this.codEmbarcacion = codEmbarcacion;
    }

    public String getEmbarcacion() {
        return embarcacion;
    }

    public void setEmbarcacion(String embarcacion) {
        this.embarcacion = embarcacion;
    }

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
