package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.List;

public class CostoRacionValevImports {

    private String p_user;
    private String p_centro;
    private String p_proveedor;
    private String p_code;
    private List<Options> options;
    private String[] fieldS_data;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_centro() {
        return p_centro;
    }

    public void setP_centro(String p_centro) {
        this.p_centro = p_centro;
    }

    public String getP_proveedor() {
        return p_proveedor;
    }

    public void setP_proveedor(String p_proveedor) {
        this.p_proveedor = p_proveedor;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public String[] getFieldS_data() {
        return fieldS_data;
    }

    public void setFieldS_data(String[] fieldS_data) {
        this.fieldS_data = fieldS_data;
    }
}
