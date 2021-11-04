package com.incloud.hcp.jco.controlLogistico.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.HashMap;
import java.util.List;

public class AnalisisCombusLisImports {
    private String p_user;
    private String p_row;
    private String embarcacionIni;
    private String motivoIni;
    private String fechaIni;
    private String fechaFin;

    public String getEmbarcacionIni() {
        return embarcacionIni;
    }

    public void setEmbarcacionIni(String embarcacionIni) {
        this.embarcacionIni = embarcacionIni;
    }

    public String getMotivoIni() {
        return motivoIni;
    }

    public void setMotivoIni(String motivoIni) {
        this.motivoIni = motivoIni;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_row() {
        return p_row;
    }

    public void setP_row(String p_row) {
        this.p_row = p_row;
    }

}
