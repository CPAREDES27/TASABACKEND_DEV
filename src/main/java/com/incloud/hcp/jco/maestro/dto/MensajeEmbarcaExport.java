package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class MensajeEmbarcaExport {

    List<HashMap<String, Object>> t_mensaje;
    private String message;

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
