package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class ProtestosExports {

    private String ep_cdprt;
    private String ep_drpta;
    private List<HashMap<String, Object>> t_baprt;
    private List<HashMap<String, Object>> t_textos;
    private List<HashMap<String, Object>> t_mensaj;
    private String mensaje;

    public String getEp_cdprt() {
        return ep_cdprt;
    }

    public void setEp_cdprt(String ep_cdprt) {
        this.ep_cdprt = ep_cdprt;
    }

    public String getEp_drpta() {
        return ep_drpta;
    }

    public void setEp_drpta(String ep_drpta) {
        this.ep_drpta = ep_drpta;
    }

    public List<HashMap<String, Object>> getT_baprt() {
        return t_baprt;
    }

    public void setT_baprt(List<HashMap<String, Object>> t_baprt) {
        this.t_baprt = t_baprt;
    }

    public List<HashMap<String, Object>> getT_textos() {
        return t_textos;
    }

    public void setT_textos(List<HashMap<String, Object>> t_textos) {
        this.t_textos = t_textos;
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
