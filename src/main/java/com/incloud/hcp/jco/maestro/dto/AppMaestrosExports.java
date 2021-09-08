package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class AppMaestrosExports {

    private List<HashMap<String, Object>> t_tabapp;
    private List<HashMap<String, Object>> t_tabfield;
    private List<HashMap<String, Object>> t_tabservice;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getT_tabapp() {
        return t_tabapp;
    }

    public void setT_tabapp(List<HashMap<String, Object>> t_tabapp) {
        this.t_tabapp = t_tabapp;
    }

    public List<HashMap<String, Object>> getT_tabfield() {
        return t_tabfield;
    }

    public void setT_tabfield(List<HashMap<String, Object>> t_tabfield) {
        this.t_tabfield = t_tabfield;
    }

    public List<HashMap<String, Object>> getT_tabservice() {
        return t_tabservice;
    }

    public void setT_tabservice(List<HashMap<String, Object>> t_tabservice) {
        this.t_tabservice = t_tabservice;
    }



}
