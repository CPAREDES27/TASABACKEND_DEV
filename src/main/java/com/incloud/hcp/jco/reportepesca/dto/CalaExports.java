package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class CalaExports {
    private List<HashMap<String, Object>> s_cala;
    private String mensaje;

    public List<HashMap<String, Object>> getS_cala() {
        return s_cala;
    }

    public void setS_cala(List<HashMap<String, Object>> s_cala) {
        this.s_cala = s_cala;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
