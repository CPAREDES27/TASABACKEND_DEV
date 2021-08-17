package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class TripuEmbarcaImports {

    private String p_user;
    private List<HashMap<String, Object>> str_emb;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<HashMap<String, Object>> getStr_emb() {
        return str_emb;
    }

    public void setStr_emb(List<HashMap<String, Object>> str_emb) {
        this.str_emb = str_emb;
    }
}
