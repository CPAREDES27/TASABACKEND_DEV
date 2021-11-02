package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class EmbarcacionExports {

    private String mensaje;
    private String p_totalpag;
    private List<HashMap<String, Object>> data;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getP_totalpag() {
        return p_totalpag;
    }

    public void setP_totalpag(String p_totalpag) {
        this.p_totalpag = p_totalpag;
    }

    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }
}
