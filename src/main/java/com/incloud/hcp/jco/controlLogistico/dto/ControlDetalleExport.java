package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class ControlDetalleExport {

    private String mensaje;
    private List<HashMap<String, Object>> str_fase;
    private List<HashMap<String, Object>> str_detf;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getStr_fase() {
        return str_fase;
    }

    public void setStr_fase(List<HashMap<String, Object>> str_fase) {
        this.str_fase = str_fase;
    }

    public List<HashMap<String, Object>> getStr_detf() {
        return str_detf;
    }

    public void setStr_detf(List<HashMap<String, Object>> str_detf) {
        this.str_detf = str_detf;
    }
}
