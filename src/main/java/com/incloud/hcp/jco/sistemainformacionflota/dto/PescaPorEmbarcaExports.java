package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.HashMap;
import java.util.List;

public class PescaPorEmbarcaExports {

    private List<HashMap<String, Object>> str_pem;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_pem() {
        return str_pem;
    }

    public void setStr_pem(List<HashMap<String, Object>> str_pem) {
        this.str_pem = str_pem;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
