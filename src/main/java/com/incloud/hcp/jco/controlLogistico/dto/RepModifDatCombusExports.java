package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class RepModifDatCombusExports {

    private String mensaje;
    private String p_nmob;
    private String p_nmar;
    private double indicadorPorc;

    public double getIndicadorPorc() {
        return indicadorPorc;
    }

    public void setIndicadorPorc(double indicadorPorc) {
        this.indicadorPorc = indicadorPorc;
    }

    private List<HashMap<String, Object>> t_flocc;
    private List<HashMap<String, Object>> t_mensaje;
    private List<HashMap<String, Object>> t_opciones;

    public List<HashMap<String, Object>> getT_opciones() {
        return t_opciones;
    }

    public void setT_opciones(List<HashMap<String, Object>> t_opciones) {
        this.t_opciones = t_opciones;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getP_nmob() {
        return p_nmob;
    }

    public void setP_nmob(String p_nmob) {
        this.p_nmob = p_nmob;
    }

    public String getP_nmar() {
        return p_nmar;
    }

    public void setP_nmar(String p_nmar) {
        this.p_nmar = p_nmar;
    }

    public List<HashMap<String, Object>> getT_flocc() {
        return t_flocc;
    }

    public void setT_flocc(List<HashMap<String, Object>> t_flocc) {
        this.t_flocc = t_flocc;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }
}
