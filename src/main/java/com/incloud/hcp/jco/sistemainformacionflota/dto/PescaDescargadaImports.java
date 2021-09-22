package com.incloud.hcp.jco.sistemainformacionflota.dto;

public class PescaDescargadaImports {

    private String p_user;
    private String p_fides;
    private String p_ffdes;
    private String[] fieldstr_pta;
    private String[] fielstr_dsd;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_fides() {
        return p_fides;
    }

    public void setP_fides(String p_fides) {
        this.p_fides = p_fides;
    }

    public String getP_ffdes() {
        return p_ffdes;
    }

    public void setP_ffdes(String p_ffdes) {
        this.p_ffdes = p_ffdes;
    }

    public String[] getFieldstr_pta() {
        return fieldstr_pta;
    }

    public void setFieldstr_pta(String[] fieldstr_pta) {
        this.fieldstr_pta = fieldstr_pta;
    }

    public String[] getFielstr_dsd() {
        return fielstr_dsd;
    }

    public void setFielstr_dsd(String[] fielstr_dsd) {
        this.fielstr_dsd = fielstr_dsd;
    }
}
