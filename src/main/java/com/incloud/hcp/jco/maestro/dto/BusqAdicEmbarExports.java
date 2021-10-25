package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class BusqAdicEmbarExports {
    private List<HashMap<String, Object>> s_pe;
    private List<HashMap<String, Object>> s_ps;
    private List<HashMap<String, Object>> s_ee;
    private List<HashMap<String, Object>> s_be;
    private List<HashMap<String, Object>> str_hor;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_hor() {
        return str_hor;
    }

    public void setStr_hor(List<HashMap<String, Object>> str_hor) {
        this.str_hor = str_hor;
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

    public List<HashMap<String, Object>> getS_pe() {
        return s_pe;
    }

    public void setS_pe(List<HashMap<String, Object>> s_pe) {
        this.s_pe = s_pe;
    }

    public List<HashMap<String, Object>> getS_ps() {
        return s_ps;
    }

    public void setS_ps(List<HashMap<String, Object>> s_ps) {
        this.s_ps = s_ps;
    }

    public List<HashMap<String, Object>> getS_ee() {
        return s_ee;
    }

    public void setS_ee(List<HashMap<String, Object>> s_ee) {
        this.s_ee = s_ee;
    }

    public List<HashMap<String, Object>> getS_be() {
        return s_be;
    }

    public void setS_be(List<HashMap<String, Object>> s_be) {
        this.s_be = s_be;
    }



}
