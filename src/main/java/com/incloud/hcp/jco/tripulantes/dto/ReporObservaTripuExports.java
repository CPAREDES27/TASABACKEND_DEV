package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class ReporObservaTripuExports {

    private List<HashMap<String, Object>> t_triobs;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public List<HashMap<String, Object>> getT_triobs() {
        return t_triobs;
    }

    public void setT_triobs(List<HashMap<String, Object>> t_triobs) {
        this.t_triobs = t_triobs;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
