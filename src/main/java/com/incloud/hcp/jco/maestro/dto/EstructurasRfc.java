package com.incloud.hcp.jco.maestro.dto;

import java.util.ArrayList;

public class EstructurasRfc {
    public String getNombreTabla() {
        return NombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        NombreTabla = nombreTabla;
    }

    public ArrayList<CamposEstructuraRfc> getCampos() {
        return Campos;
    }

    public void setCampos(ArrayList<CamposEstructuraRfc> campos) {
        Campos = campos;
    }

    private String NombreTabla;
    private ArrayList<CamposEstructuraRfc> Campos;
}
