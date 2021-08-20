package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class DescargasExports {
    private List<HashMap<String,Object>> str_des;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_des() {
        return str_des;
    }

    public void setStr_des(List<HashMap<String, Object>> str_des) {
        this.str_des = str_des;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
