package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class CostoRacionValevExports {

    private List<HashMap<String, Object>> t_mensaje;
    private List<HashMap<String, Object>> s_data;
    private String mensaje;

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> getS_data() {
        return s_data;
    }

    public void setS_data(List<HashMap<String, Object>> s_data) {
        this.s_data = s_data;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
