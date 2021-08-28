package com.incloud.hcp.jco.distribucionflota.dto;

public class DistrFlotaOptions {
    private String p_user;
    private String p_inprp;
    private String p_inubc;
    private String p_cdtem;
    private String[] fieldZonas;
    private String[] fieldPlantas;
    private String[] fieldEmbarc;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_inprp() {
        return p_inprp;
    }

    public void setP_inprp(String p_inprp) {
        this.p_inprp = p_inprp;
    }

    public String getP_inubc() {
        return p_inubc;
    }

    public void setP_inubc(String p_inubc) {
        this.p_inubc = p_inubc;
    }

    public String getP_cdtem() {
        return p_cdtem;
    }

    public void setP_cdtem(String p_cdtem) {
        this.p_cdtem = p_cdtem;
    }

    public String[] getFieldZonas() {
        return fieldZonas;
    }

    public void setFieldZonas(String[] fieldZonas) {
        this.fieldZonas = fieldZonas;
    }

    public String[] getFieldPlantas() {
        return fieldPlantas;
    }

    public void setFieldPlantas(String[] fieldPlantas) {
        this.fieldPlantas = fieldPlantas;
    }

    public String[] getFieldEmbarc() {
        return fieldEmbarc;
    }

    public void setFieldEmbarc(String[] fieldEmbarc) {
        this.fieldEmbarc = fieldEmbarc;
    }
}
