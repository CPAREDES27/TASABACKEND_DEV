package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.HashMap;
import java.util.List;

public class PescaDeclaradaExports {

    private List<HashMap<String, Object>> str_tp;
    private List<HashMap<String, Object>> str_te;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;


    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> getStr_tp() {
        return str_tp;
    }

    public void setStr_tp(List<HashMap<String, Object>> str_tp) {
        this.str_tp = str_tp;
    }

    public List<HashMap<String, Object>> getStr_te() {
        return str_te;
    }

    public void setStr_te(List<HashMap<String, Object>> str_te) {
        this.str_te = str_te;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
