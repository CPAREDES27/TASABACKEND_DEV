package com.incloud.hcp.jco.maestro.dto;

public class CamposEstructuraRfc {
    private String NombreCampo;
    private String TipoDato;
    private int Longitud;

    public String getNombreCampo() {
        return NombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        NombreCampo = nombreCampo;
    }

    public String getTipoDato() {
        return TipoDato;
    }

    public void setTipoDato(String tipoDato) {
        TipoDato = tipoDato;
    }

    public int getLongitud() {
        return Longitud;
    }

    public void setLongitud(int longitud) {
        Longitud = longitud;
    }

    public int getDecimales() {
        return Decimales;
    }

    public void setDecimales(int decimales) {
        Decimales = decimales;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    private int Decimales;
    private String Descripcion;

}
