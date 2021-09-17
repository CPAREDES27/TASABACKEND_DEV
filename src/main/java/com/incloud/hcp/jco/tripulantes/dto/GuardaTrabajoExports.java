package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class GuardaTrabajoExports {

    private String ep_nrtff;
    private List<HashMap<String, Object>> t_mensaj;
    private String mensaje;


    public String getEp_nrtff() {
        return ep_nrtff;
    }

    public void setEp_nrtff(String ep_nrtff) {
        this.ep_nrtff = ep_nrtff;
    }

    public List<HashMap<String, Object>> getT_mensaj() {
        return t_mensaj;
    }

    public void setT_mensaj(List<HashMap<String, Object>> t_mensaj) {
        this.t_mensaj = t_mensaj;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
