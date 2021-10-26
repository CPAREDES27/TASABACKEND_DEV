package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class CargaEmbProduceExports {

    private String mensaje;
    private List<HashMap<String, Object>>et_zflemb;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getEt_zflemb() {
        return et_zflemb;
    }

    public void setEt_zflemb(List<HashMap<String, Object>> et_zflemb) {
        this.et_zflemb = et_zflemb;
    }
}
