package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;

public class EventosPesca2Exports {
    HashMap<String, Object> st_ccp;
    HashMap<String, Object> st_cep;
    HashMap<String, Object> st_cmap;
    private String mensaje;

    public HashMap<String, Object> getSt_ccp() {
        return st_ccp;
    }

    public void setSt_ccp(HashMap<String, Object> st_ccp) {
        this.st_ccp = st_ccp;
    }

    public HashMap<String, Object> getSt_cep() {
        return st_cep;
    }

    public void setSt_cep(HashMap<String, Object> st_cep) {
        this.st_cep = st_cep;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public HashMap<String, Object> getSt_cmap() {
        return st_cmap;
    }

    public void setSt_cmap(HashMap<String, Object> st_cmap) {
        this.st_cmap = st_cmap;
    }
}
