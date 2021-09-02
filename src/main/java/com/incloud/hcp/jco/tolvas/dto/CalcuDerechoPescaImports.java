package com.incloud.hcp.jco.tolvas.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;

import java.util.HashMap;
import java.util.List;

public class CalcuDerechoPescaImports {

    private String p_user;
    private String Rowcount;
    private String p_indtr;
    private String p_moned;
    private String[] fields_derecho;
    private String[] fieldt_mensaje;
    private String[] fieldstr_dps;
    private List<HashMap<String, Object>> str_dps;
    private List<HashMap<String, Object>> s_derecho;
    private List<MaestroOptions> options;

    public List<MaestroOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptions> options) {
        this.options = options;
    }

    public String[] getFieldstr_dps() {
        return fieldstr_dps;
    }

    public void setFieldstr_dps(String[] fieldstr_dps) {
        this.fieldstr_dps = fieldstr_dps;
    }

    public List<HashMap<String, Object>> getS_derecho() {
        return s_derecho;
    }

    public void setS_derecho(List<HashMap<String, Object>> s_derecho) {
        this.s_derecho = s_derecho;
    }

    public List<HashMap<String, Object>> getStr_dps() {
        return str_dps;
    }

    public void setStr_dps(List<HashMap<String, Object>> str_dps) {
        this.str_dps = str_dps;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getRowcount() {
        return Rowcount;
    }

    public void setRowcount(String rowcount) {
        Rowcount = rowcount;
    }

    public String getP_indtr() {
        return p_indtr;
    }

    public void setP_indtr(String p_indtr) {
        this.p_indtr = p_indtr;
    }

    public String getP_moned() {
        return p_moned;
    }

    public void setP_moned(String p_moned) {
        this.p_moned = p_moned;
    }



    public String[] getFields_derecho() {
        return fields_derecho;
    }

    public void setFields_derecho(String[] fields_derecho) {
        this.fields_derecho = fields_derecho;
    }

    public String[] getFieldt_mensaje() {
        return fieldt_mensaje;
    }

    public void setFieldt_mensaje(String[] fieldt_mensaje) {
        this.fieldt_mensaje = fieldt_mensaje;
    }


}
