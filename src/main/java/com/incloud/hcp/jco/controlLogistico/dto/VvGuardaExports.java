package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class VvGuardaExports {

    private String mensaje;
    private List<HashMap<String, Object>> t_mensaje;
    private String p_orden;
    private String p_merc;
    private String p_vale;

    public String getP_orden() {
        return p_orden;
    }

    public void setP_orden(String p_orden) {
        this.p_orden = p_orden;
    }

    public String getP_merc() {
        return p_merc;
    }

    public void setP_merc(String p_merc) {
        this.p_merc = p_merc;
    }

    public String getP_vale() {
        return p_vale;
    }

    public void setP_vale(String p_vale) {
        this.p_vale = p_vale;
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
