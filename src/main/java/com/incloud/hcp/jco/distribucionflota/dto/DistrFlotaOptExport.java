package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.HashMap;
import java.util.List;

public class DistrFlotaOptExport {
    private String mensaje;
    private List<HashMap<String, Object>> s_zonas;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getS_zonas() {
        return s_zonas;
    }

    public void setS_zonas(List<HashMap<String, Object>> s_zonas) {
        this.s_zonas = s_zonas;
    }
}
