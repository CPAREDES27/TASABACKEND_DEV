package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class UpdateTableExports {

    private String mensaje;
    private List<HashMap<String, Object>> t_mensaje;

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
}
