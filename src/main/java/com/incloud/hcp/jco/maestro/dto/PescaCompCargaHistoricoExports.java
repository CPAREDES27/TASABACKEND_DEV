package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class PescaCompCargaHistoricoExports {
    private String mensaje;
    private List<HashMap<String, Object>> data;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }
}
