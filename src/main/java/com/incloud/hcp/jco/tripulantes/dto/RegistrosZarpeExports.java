package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class RegistrosZarpeExports {

    private List<HashMap<String, Object>> t_zatrp;
    private List<HashMap<String, Object>> t_nzatr;
    private List<HashMap<String, Object>> t_dzatr;
    private List<HashMap<String, Object>> t_vgcer;
    private String mensaje;
    private List<HashMap<String, Object>> t_mensaje;

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> getT_zatrp() {
        return t_zatrp;
    }

    public void setT_zatrp(List<HashMap<String, Object>> t_zatrp) {
        this.t_zatrp = t_zatrp;
    }

    public List<HashMap<String, Object>> getT_nzatr() {
        return t_nzatr;
    }

    public void setT_nzatr(List<HashMap<String, Object>> t_nzatr) {
        this.t_nzatr = t_nzatr;
    }

    public List<HashMap<String, Object>> getT_dzatr() {
        return t_dzatr;
    }

    public void setT_dzatr(List<HashMap<String, Object>> t_dzatr) {
        this.t_dzatr = t_dzatr;
    }

    public List<HashMap<String, Object>> getT_vgcer() {
        return t_vgcer;
    }

    public void setT_vgcer(List<HashMap<String, Object>> t_vgcer) {
        this.t_vgcer = t_vgcer;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
