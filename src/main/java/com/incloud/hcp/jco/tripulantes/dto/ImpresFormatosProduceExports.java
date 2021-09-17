package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class ImpresFormatosProduceExports {

    private List<HashMap<String, Object>> t_rsprce;
    private List<HashMap<String, Object>> t_dtprce;
    private String mensaje;

    public List<HashMap<String, Object>> getT_rsprce() {
        return t_rsprce;
    }

    public void setT_rsprce(List<HashMap<String, Object>> t_rsprce) {
        this.t_rsprce = t_rsprce;
    }

    public List<HashMap<String, Object>> getT_dtprce() {
        return t_dtprce;
    }

    public void setT_dtprce(List<HashMap<String, Object>> t_dtprce) {
        this.t_dtprce = t_dtprce;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
