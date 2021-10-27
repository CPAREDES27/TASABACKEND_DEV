package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class CargaEmbProduceImports {

    private String p_tope;
    private List<HashMap<String,Object>> it_zflemb;
    private String []fields_itzflemb;

    public String[] getFields_itzflemb() {
        return fields_itzflemb;
    }

    public void setFields_itzflemb(String[] fields_itzflemb) {
        this.fields_itzflemb = fields_itzflemb;
    }

    public String getP_tope() {
        return p_tope;
    }

    public void setP_tope(String p_tope) {
        this.p_tope = p_tope;
    }

    public List<HashMap<String, Object>> getIt_zflemb() {
        return it_zflemb;
    }

    public void setIt_zflemb(List<HashMap<String, Object>> it_zflemb) {
        this.it_zflemb = it_zflemb;
    }
}
