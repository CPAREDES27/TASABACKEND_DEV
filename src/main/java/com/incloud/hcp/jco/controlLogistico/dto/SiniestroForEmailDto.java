package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.ArrayList;

public class SiniestroForEmailDto {
    private String codIncidente;
    private String descCodIncidente;
    private String descripcion;

    public String getCodIncidente() {
        return codIncidente;
    }

    public void setCodIncidente(String codIncidente) {
        this.codIncidente = codIncidente;
    }

    public String getDescCodIncidente() {
        return descCodIncidente;
    }

    public void setDescCodIncidente(String descCodIncidente) {
        this.descCodIncidente = descCodIncidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
