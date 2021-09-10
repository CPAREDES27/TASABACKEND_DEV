package com.incloud.hcp.jco.tolvas.dto;

import org.apache.commons.collections4.map.HashedMap;

import java.util.HashMap;
import java.util.List;

public class PescaCompetenciaRExports {

    private List<HashMap<String, Object>> str_zlt;
    private List<HashMap<String, Object>> str_pto;
    private List<HashMap<String, Object>> str_pcp;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;


    public List<HashMap<String, Object>> getStr_zlt() {
        return str_zlt;
    }

    public void setStr_zlt(List<HashMap<String, Object>> str_zlt) {
        this.str_zlt = str_zlt;
    }

    public List<HashMap<String, Object>> getStr_pto() {
        return str_pto;
    }

    public void setStr_pto(List<HashMap<String, Object>> str_pto) {
        this.str_pto = str_pto;
    }

    public List<HashMap<String, Object>> getStr_pcp() {
        return str_pcp;
    }

    public void setStr_pcp(List<HashMap<String, Object>> str_pcp) {
        this.str_pcp = str_pcp;
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
