package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class QlikExport {
    private List<HashMap<String, Object>> t_mensaje;
    private List<HashMap<String, Object>> str_cef;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> getStr_cef() {
        return str_cef;
    }

    public void setStr_cef(List<HashMap<String, Object>> str_cef) {
        this.str_cef = str_cef;
    }
}
