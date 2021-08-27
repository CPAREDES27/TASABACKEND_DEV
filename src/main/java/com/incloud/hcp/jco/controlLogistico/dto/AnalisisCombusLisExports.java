package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class AnalisisCombusLisExports {

    private String mensaje;
    private List<HashMap<String, Object>> Str_csmar;
    private List<HashMap<String, Object>> T_mensaje;


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getStr_csmar() {
        return Str_csmar;
    }

    public void setStr_csmar(List<HashMap<String, Object>> str_csmar) {
        Str_csmar = str_csmar;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return T_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        T_mensaje = t_mensaje;
    }
}
