package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class MoverEmbarcaImports {

    private String p_user;
    private String p_cdpta;
    private List<HashMap<String, Object>> data;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_cdtpa() {
        return p_cdpta;
    }

    public void setP_cdtpa(String p_cdtpa) {
        this.p_cdpta = p_cdtpa;
    }

    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }


}
