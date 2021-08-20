package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.*;

public class FlotaDto {
    private List<HashMap<String, Object>> str_zlt;
    private List<HashMap<String, Object>> str_di;
    private List<HashMap<String, Object>> str_pta;
    private List<HashMap<String, Object>> str_dp;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_zlt() {
        return str_zlt;
    }

    public void setStr_zlt(List<HashMap<String, Object>> str_zlt) {
        this.str_zlt = str_zlt;
    }

    public List<HashMap<String, Object>> getStr_di() {
        return str_di;
    }

    public void setStr_di(List<HashMap<String, Object>> str_di) {
        this.str_di = str_di;
    }

    public List<HashMap<String, Object>> getStr_pta() {
        return str_pta;
    }

    public void setStr_pta(List<HashMap<String, Object>> str_pta) {
        this.str_pta = str_pta;
    }

    public List<HashMap<String, Object>> getStr_dp() {
        return str_dp;
    }

    public void setStr_dp(List<HashMap<String, Object>> str_dp) {
        this.str_dp = str_dp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
