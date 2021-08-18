package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class CapacidadTanquesImports {

    private  List<HashMap<String, Object>> data;
    private String p_user;

    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }
}
