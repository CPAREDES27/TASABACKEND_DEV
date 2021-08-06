package com.incloud.hcp.jco.maestro.dto;

public class AlmacenExtDto {
    public String Centro;               //WERKS
    public String NombreAlmacen;        //LGORT
    public String DenominacionAlmacen;  //LGOBE

    public String getCentro() {
        return Centro;
    }

    public void setCentro(String centro) {
        Centro = centro;
    }

    public String getNombreAlmacen() {
        return NombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        NombreAlmacen = nombreAlmacen;
    }

    public String getDenominacionAlmacen() {
        return DenominacionAlmacen;
    }

    public void setDenominacionAlmacen(String denominacionAlmacen) {
        DenominacionAlmacen = denominacionAlmacen;
    }



}
