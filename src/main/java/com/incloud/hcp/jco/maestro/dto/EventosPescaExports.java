package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class EventosPescaExports {
    private List<HashMap<String, Object>> st_ccp;
    private List<HashMap<String, Object>> st_cep;

    public List<HashMap<String, Object>> getSt_ccp() {
        return st_ccp;
    }

    public void setSt_ccp(List<HashMap<String, Object>> st_ccp) {
        this.st_ccp = st_ccp;
    }

    public List<HashMap<String, Object>> getSt_cep() {
        return st_cep;
    }

    public void setSt_cep(List<HashMap<String, Object>> st_cep) {
        this.st_cep = st_cep;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private String mensaje;
}
