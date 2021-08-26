package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class LogRegCombusExports {

    private List<HashMap<String, Object>> str_csmar;
    private List<HashMap<String, Object>> str_csmaj;
    private List<HashMap<String, Object>> str_lgcco;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;


    public List<HashMap<String, Object>> getStr_csmar() {
        return str_csmar;
    }

    public void setStr_csmar(List<HashMap<String, Object>> str_csmar) {
        this.str_csmar = str_csmar;
    }

    public List<HashMap<String, Object>> getStr_csmaj() {
        return str_csmaj;
    }

    public void setStr_csmaj(List<HashMap<String, Object>> str_csmaj) {
        this.str_csmaj = str_csmaj;
    }

    public List<HashMap<String, Object>> getStr_lgcco() {
        return str_lgcco;
    }

    public void setStr_lgcco(List<HashMap<String, Object>> str_lgcco) {
        this.str_lgcco = str_lgcco;
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
