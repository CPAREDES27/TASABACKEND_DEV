package com.incloud.hcp.jco.preciospesca.dto;

import java.util.HashMap;
import java.util.List;

public class PrecioPescaExports {
    private List<HashMap<String, Object>> str_ppc;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_ppc() {
        return str_ppc;
    }

    public void setStr_ppc(List<HashMap<String, Object>> str_ppc) {
        this.str_ppc = str_ppc;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
