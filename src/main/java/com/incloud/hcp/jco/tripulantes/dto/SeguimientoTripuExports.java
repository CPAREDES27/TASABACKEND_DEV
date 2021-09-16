package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class SeguimientoTripuExports {

    private List<HashMap<String, Object>> t_dstrip;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public List<HashMap<String, Object>> getT_dstrip() {
        return t_dstrip;
    }

    public void setT_dstrip(List<HashMap<String, Object>> t_dstrip) {
        this.t_dstrip = t_dstrip;
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
