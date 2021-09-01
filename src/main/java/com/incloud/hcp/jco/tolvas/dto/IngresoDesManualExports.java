package com.incloud.hcp.jco.tolvas.dto;

import java.util.HashMap;
import java.util.List;

public class IngresoDesManualExports {

    private List<HashMap<String, Object>> t_mensaje;
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

}
