package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.HashMap;
import java.util.List;

public class PescaDeclaradaDiariaExports {

    private String mensaje;
    private List<HashMap<String, Object>> str_dl;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getStr_dl() {
        return str_dl;
    }

    public void setStr_dl(List<HashMap<String, Object>> str_dl) {
        this.str_dl = str_dl;
    }
}
