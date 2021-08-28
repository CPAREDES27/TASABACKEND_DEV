package com.incloud.hcp.jco.preciospesca.dto;

import java.util.HashMap;
import java.util.List;

public class PrecioProbPescaExports {
    private String mensaje;
    private List<HashMap<String, Object>> str_app;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getStr_app() {
        return str_app;
    }

    public void setStr_app(List<HashMap<String, Object>> str_app) {
        this.str_app = str_app;
    }
}
