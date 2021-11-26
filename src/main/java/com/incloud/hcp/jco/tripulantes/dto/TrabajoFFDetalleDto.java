package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class TrabajoFFDetalleDto {

    private String nroPersona;
    private String nombre;
    private String cargo;
    private HashMap<String,Object> fechas;
    private String origen;
    private String centro;
    private String destino;


    public String getNroPersona() {
        return nroPersona;
    }

    public void setNroPersona(String nroPersona) {
        this.nroPersona = nroPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public HashMap<String, Object> getFechas() {
        return fechas;
    }

    public void setFechas(HashMap<String, Object> fechas) {
        this.fechas = fechas;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
