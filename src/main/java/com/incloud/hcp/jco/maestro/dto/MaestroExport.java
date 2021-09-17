package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MaestroExport {

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

    public MaestroExport(List<HashMap<String, Object>> data, String mensaje) {
        this.data = data;
        this.mensaje = mensaje;
    }

    public MaestroExport(){

    }

    public List<HashMap<String, Object>> getFields() {
        return fields;
    }

    public void setFields(List<HashMap<String, Object>> fields) {
        this.fields = fields;
    }

    private List<HashMap<String, Object>> data;
    private List<HashMap<String, Object>> fields;
    private String mensaje;

}
