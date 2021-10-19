package com.incloud.hcp.jco.consultaGeneral.dto;

import java.util.HashMap;
import java.util.List;

public class ConsultaGeneralExports {
    private List<HashMap<String, Object>> data;
    private String mensaje;

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
}
