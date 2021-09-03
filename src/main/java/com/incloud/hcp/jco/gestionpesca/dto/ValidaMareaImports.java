package com.incloud.hcp.jco.gestionpesca.dto;

public class ValidaMareaImports {

    private String p_codemb;
    private String p_codpta;
    private String[] fieldt_mensaje;

    public String[] getFieldt_mensaje() {
        return fieldt_mensaje;
    }

    public void setFieldt_mensaje(String[] fieldt_mensaje) {
        this.fieldt_mensaje = fieldt_mensaje;
    }

    public String getP_codemb() {
        return p_codemb;
    }

    public void setP_codemb(String p_codemb) {
        this.p_codemb = p_codemb;
    }

    public String getP_codpta() {
        return p_codpta;
    }

    public void setP_codpta(String p_codpta) {
        this.p_codpta = p_codpta;
    }
}
