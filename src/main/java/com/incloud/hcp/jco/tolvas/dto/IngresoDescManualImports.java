package com.incloud.hcp.jco.tolvas.dto;

import java.util.HashMap;
import java.util.List;

public class IngresoDescManualImports {

    private String p_user;
    private List<HashMap<String, Object>> str_des;
    private String[] fieldst_mensaje;

    public String[] getFieldst_mensaje() {
        return fieldst_mensaje;
    }

    public void setFieldst_mensaje(String[] fieldst_mensaje) {
        this.fieldst_mensaje = fieldst_mensaje;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<HashMap<String, Object>> getStr_des() {
        return str_des;
    }

    public void setStr_des(List<HashMap<String, Object>> str_des) {
        this.str_des = str_des;
    }
}
