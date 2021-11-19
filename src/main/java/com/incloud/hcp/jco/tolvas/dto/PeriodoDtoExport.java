package com.incloud.hcp.jco.tolvas.dto;

import java.util.HashMap;
import java.util.List;

public class PeriodoDtoExport {
    private boolean valido;
    private List<HashMap<String, Object>> data;
    private String mensaje;

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

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }
}
