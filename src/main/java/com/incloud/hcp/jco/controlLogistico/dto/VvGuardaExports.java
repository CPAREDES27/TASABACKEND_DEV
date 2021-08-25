package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class VvGuardaExports {

    private String mensaje;
    private List<HashMap<String, Object>> st_vvi;
    private List<HashMap<String, Object>> st_pva;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getSt_vvi() {
        return st_vvi;
    }

    public void setSt_vvi(List<HashMap<String, Object>> st_vvi) {
        this.st_vvi = st_vvi;
    }

    public List<HashMap<String, Object>> getSt_pva() {
        return st_pva;
    }

    public void setSt_pva(List<HashMap<String, Object>> st_pva) {
        this.st_pva = st_pva;
    }
}
