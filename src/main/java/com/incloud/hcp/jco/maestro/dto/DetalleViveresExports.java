package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class DetalleViveresExports {

    private String mensaje;
    private List<HashMap<String, Object>>  s_posicion;
    private List<HashMap<String, Object>>  t_mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getS_posicion() {
        return s_posicion;
    }

    public void setS_posicion(List<HashMap<String, Object>> s_posicion) {
        this.s_posicion = s_posicion;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }
}
