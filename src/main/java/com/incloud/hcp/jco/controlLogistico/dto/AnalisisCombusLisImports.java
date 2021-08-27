package com.incloud.hcp.jco.controlLogistico.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;

import java.util.HashMap;
import java.util.List;

public class AnalisisCombusLisImports {
    private String p_user;
    private String p_row;
    private String[]  fieldsStr_csmar;
    private String[]  fieldsT_mensaje;
    private List<MaestroOptions> options;

    public List<MaestroOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptions> options) {
        this.options = options;
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

    public String[] getFieldsStr_csmar() {
        return fieldsStr_csmar;
    }

    public void setFieldsStr_csmar(String[] fieldsStr_csmar) {
        this.fieldsStr_csmar = fieldsStr_csmar;
    }

    public String[] getFieldsT_mensaje() {
        return fieldsT_mensaje;
    }

    public void setFieldsT_mensaje(String[] fieldsT_mensaje) {
        this.fieldsT_mensaje = fieldsT_mensaje;
    }
}
