package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class AnalisisCombusExports {

    private String mensaje;
    private List<HashMap<String, Object>> str_csmar;
    private List<HashMap<String, Object>> t_mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getStr_csmar() {
        return str_csmar;
    }

    public void setStr_csmar(List<HashMap<String, Object>> str_csmar) {
        this.str_csmar = str_csmar;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }
}
