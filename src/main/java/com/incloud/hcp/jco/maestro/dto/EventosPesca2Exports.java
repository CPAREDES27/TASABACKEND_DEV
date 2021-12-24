package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;

public class EventosPesca2Exports {
    HashMap<String, Object> st_cmap;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public HashMap<String, Object> getSt_cmap() {
        return st_cmap;
    }

    public void setSt_cmap(HashMap<String, Object> st_cmap) {
        this.st_cmap = st_cmap;
    }
}
