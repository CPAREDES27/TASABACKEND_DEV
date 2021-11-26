package com.incloud.hcp.jco.maestro.dto;

public class CargaDescargaArchivosImports {

    private String i_trama;
    private String i_directorio;
    private String i_filename;
    private String i_accion;
    private String i_procesobtp;
    private String i_user;

    public String getI_procesobtp() {
        return i_procesobtp;
    }

    public void setI_procesobtp(String i_procesobtp) {
        this.i_procesobtp = i_procesobtp;
    }

    public String getI_user() {
        return i_user;
    }

    public void setI_user(String i_user) {
        this.i_user = i_user;
    }

    public String getI_trama() {
        return i_trama;
    }

    public void setI_trama(String i_trama) {
        this.i_trama = i_trama;
    }

    public String getI_directorio() {
        return i_directorio;
    }

    public void setI_directorio(String i_directorio) {
        this.i_directorio = i_directorio;
    }

    public String getI_filename() {
        return i_filename;
    }

    public void setI_filename(String i_filename) {
        this.i_filename = i_filename;
    }

    public String getI_accion() {
        return i_accion;
    }

    public void setI_accion(String i_accion) {
        this.i_accion = i_accion;
    }
}
