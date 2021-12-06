package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class ImpoBtpExports {
    private List<HashMap<String, Object>> t_mensaje;
    private String e_fieldName;
    private String mensaje;

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public String getE_fieldName() {
        return e_fieldName;
    }

    public void setE_fieldName(String e_fieldName) {
        this.e_fieldName = e_fieldName;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
