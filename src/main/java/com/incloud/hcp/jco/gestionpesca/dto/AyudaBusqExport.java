package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class AyudaBusqExport {
    private List<HashMap<String, Object>> str_emb;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_emb() {
        return str_emb;
    }

    public void setStr_emb(List<HashMap<String, Object>> str_emb) {
        this.str_emb = str_emb;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
