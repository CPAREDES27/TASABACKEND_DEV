package com.incloud.hcp.jco.controlLogistico.dto;

public class AnalisisCombusImports {

    private String p_user;
    private String p_nrmar;
    private String[] fields;


    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_nrmar() {
        return p_nrmar;
    }

    public void setP_nrmar(String p_nrmar) {
        this.p_nrmar = p_nrmar;
    }
}
