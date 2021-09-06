package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class BiometriaImports {

    private String ip_oper;
    private List<HashMap<String, Object>> it_marea;
    private String ip_cdmma;

    public String getIp_oper() {
        return ip_oper;
    }

    public void setIp_oper(String ip_oper) {
        this.ip_oper = ip_oper;
    }

    public List<HashMap<String, Object>> getIt_marea() {
        return it_marea;
    }

    public void setIt_marea(List<HashMap<String, Object>> it_marea) {
        this.it_marea = it_marea;
    }

    public String getIp_cdmma() {
        return ip_cdmma;
    }

    public void setIp_cdmma(String ip_cdmma) {
        this.ip_cdmma = ip_cdmma;
    }
}
