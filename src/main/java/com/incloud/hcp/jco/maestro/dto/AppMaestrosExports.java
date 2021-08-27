package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class AppMaestrosExports {

    private List<HashMap<String, Object>> t_approles;
    private List<HashMap<String, Object>> t_tabcolumna;
    private List<HashMap<String, Object>> t_tabselec;
    private List<HashMap<String, Object>> t_tabform;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getT_approles() {
        return t_approles;
    }

    public void setT_approles(List<HashMap<String, Object>> t_approles) {
        this.t_approles = t_approles;
    }

    public List<HashMap<String, Object>> getT_tabcolumna() {
        return t_tabcolumna;
    }

    public void setT_tabcolumna(List<HashMap<String, Object>> t_tabcolumna) {
        this.t_tabcolumna = t_tabcolumna;
    }

    public List<HashMap<String, Object>> getT_tabselec() {
        return t_tabselec;
    }

    public void setT_tabselec(List<HashMap<String, Object>> t_tabselec) {
        this.t_tabselec = t_tabselec;
    }

    public List<HashMap<String, Object>> getT_tabform() {
        return t_tabform;
    }

    public void setT_tabform(List<HashMap<String, Object>> t_tabform) {
        this.t_tabform = t_tabform;
    }

}
