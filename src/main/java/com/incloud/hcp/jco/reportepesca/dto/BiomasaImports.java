package com.incloud.hcp.jco.reportepesca.dto;

import java.util.List;

public class BiomasaImports {
    private String ip_oper;
    private List<BiomasaMarea> it_marea;
    private String ip_cdmma;

    public String getIp_oper() {
        return ip_oper;
    }

    public void setIp_oper(String ip_oper) {
        this.ip_oper = ip_oper;
    }

    public List<BiomasaMarea> getIt_marea() {
        return it_marea;
    }

    public void setIt_marea(List<BiomasaMarea> it_marea) {
        this.it_marea = it_marea;
    }

    public String getIp_cdmma() {
        return ip_cdmma;
    }

    public void setIp_cdmma(String ip_cdmma) {
        this.ip_cdmma = ip_cdmma;
    }
}
