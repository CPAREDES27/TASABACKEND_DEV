package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class AnularRerservaExport {

    public List<HashMap<String, Object>> str_rsc;

    public List<HashMap<String, Object>> getStr_rsc() {
        return str_rsc;
    }

    public void setStr_rsc(List<HashMap<String, Object>> str_rsc) {
        this.str_rsc = str_rsc;
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

    public List<HashMap<String, Object>> t_mensaje;
    public String mensaje;
}
