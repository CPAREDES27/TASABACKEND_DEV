package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class MaestroExportKey {
    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public MaestroExportKey(List<HashMap<String, Object>> data, String mensaje) {
        this.data = data;
        this.mensaje = mensaje;
    }

    public MaestroExportKey(){

    }

    private List<HashMap<String, Object>> data;
    private String mensaje;
}
