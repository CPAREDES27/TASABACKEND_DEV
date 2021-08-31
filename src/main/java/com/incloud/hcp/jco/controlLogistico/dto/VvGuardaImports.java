package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class VvGuardaImports {
    private List<HashMap<String, Object>> st_vvi;
    private List<HashMap<String, Object>> st_pva;
    private String p_user;
    private String [] fieldsT_mensaje;

    public String[] getFieldsT_mensaje() {
        return fieldsT_mensaje;
    }

    public void setFieldsT_mensaje(String[] fieldsT_mensaje) {
        this.fieldsT_mensaje = fieldsT_mensaje;
    }

    public List<HashMap<String, Object>> getSt_vvi() {
        return st_vvi;
    }

    public void setSt_vvi(List<HashMap<String, Object>> st_vvi) {
        this.st_vvi = st_vvi;
    }

    public List<HashMap<String, Object>> getSt_pva() {
        return st_pva;
    }

    public void setSt_pva(List<HashMap<String, Object>> st_pva) {
        this.st_pva = st_pva;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }
}
