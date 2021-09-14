package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class RolTripulacionExports {

    private List<HashMap<String, Object>> t_zartr;
    private List<HashMap<String, Object>> t_dzart;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public List<HashMap<String, Object>> getT_zartr() {
        return t_zartr;
    }

    public void setT_zartr(List<HashMap<String, Object>> t_zartr) {
        this.t_zartr = t_zartr;
    }

    public List<HashMap<String, Object>> getT_dzart() {
        return t_dzart;
    }

    public void setT_dzart(List<HashMap<String, Object>> t_dzart) {
        this.t_dzart = t_dzart;
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
