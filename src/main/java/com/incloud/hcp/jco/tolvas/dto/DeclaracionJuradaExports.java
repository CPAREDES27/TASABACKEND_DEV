package com.incloud.hcp.jco.tolvas.dto;

import com.incloud.hcp.jco.dominios.dto.DominioExportsData;

import java.util.HashMap;
import java.util.List;

public class DeclaracionJuradaExports {

    private String titulo;
    private String subtitulo;
    private String centro;
    private String razonSocial;
    private String ubicacionPlanta;
    private String observacion;
    private List<HashMap<String,Object>> detalle;
    private List<DominioExportsData> especies;
    private List<DominioExportsData> destino;
    private String mensaje;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public List<DominioExportsData> getDestino() {
        return destino;
    }

    public void setDestino(List<DominioExportsData> destino) {
        this.destino = destino;
    }

    public List<DominioExportsData> getEspecies() {
        return especies;
    }

    public void setEspecies(List<DominioExportsData> especies) {
        this.especies = especies;
    }

    public String getUbicacionPlanta() {
        return ubicacionPlanta;
    }

    public void setUbicacionPlanta(String ubicacionPlanta) {
        this.ubicacionPlanta = ubicacionPlanta;
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

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
