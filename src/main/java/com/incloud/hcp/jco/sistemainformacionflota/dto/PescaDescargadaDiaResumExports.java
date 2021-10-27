package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.HashMap;
import java.util.List;

public class PescaDescargadaDiaResumExports {
    private List<HashMap<String, Object>> str_pta;
    private List<HashMap<String, Object>> str_dsd;
    private List<HashMap<String, Object>> str_dsddia;
    private List<HashMap<String, Object>> str_dsdtot;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_pta() {
        return str_pta;
    }

    public void setStr_pta(List<HashMap<String, Object>> str_pta) {
        this.str_pta = str_pta;
    }

    public List<HashMap<String, Object>> getStr_dsd() {
        return str_dsd;
    }

    public void setStr_dsd(List<HashMap<String, Object>> str_dsd) {
        this.str_dsd = str_dsd;
    }

    public List<HashMap<String, Object>> getStr_dsddia() {
        return str_dsddia;
    }

    public void setStr_dsddia(List<HashMap<String, Object>> str_dsddia) {
        this.str_dsddia = str_dsddia;
    }

    public List<HashMap<String, Object>> getStr_dsdtot() {
        return str_dsdtot;
    }

    public void setStr_dsdtot(List<HashMap<String, Object>> str_dsdtot) {
        this.str_dsdtot = str_dsdtot;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
