package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class GuardaTrabajoImports {

    private String ip_tope;
    private String ip_nrtff;
    private List<HashMap<String, Object>> t_trabff;
    private List<HashMap<String, Object>> t_trabaj;
    private List<HashMap<String, Object>> t_fechas;
    private List<HashMap<String, Object>> t_textos;
    private List<HashMap<String, Object>> t_actcam;

    public String getIp_tope() {
        return ip_tope;
    }

    public void setIp_tope(String ip_tope) {
        this.ip_tope = ip_tope;
    }

    public String getIp_nrtff() {
        return ip_nrtff;
    }

    public void setIp_nrtff(String ip_nrtff) {
        this.ip_nrtff = ip_nrtff;
    }

    public List<HashMap<String, Object>> getT_trabff() {
        return t_trabff;
    }

    public void setT_trabff(List<HashMap<String, Object>> t_trabff) {
        this.t_trabff = t_trabff;
    }

    public List<HashMap<String, Object>> getT_trabaj() {
        return t_trabaj;
    }

    public void setT_trabaj(List<HashMap<String, Object>> t_trabaj) {
        this.t_trabaj = t_trabaj;
    }

    public List<HashMap<String, Object>> getT_fechas() {
        return t_fechas;
    }

    public void setT_fechas(List<HashMap<String, Object>> t_fechas) {
        this.t_fechas = t_fechas;
    }

    public List<HashMap<String, Object>> getT_textos() {
        return t_textos;
    }

    public void setT_textos(List<HashMap<String, Object>> t_textos) {
        this.t_textos = t_textos;
    }

    public List<HashMap<String, Object>> getT_actcam() {
        return t_actcam;
    }

    public void setT_actcam(List<HashMap<String, Object>> t_actcam) {
        this.t_actcam = t_actcam;
    }
}
