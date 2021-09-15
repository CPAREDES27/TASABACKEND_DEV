package com.incloud.hcp.jco.preciospesca.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class ConsPrecioPescaImports {
    private String ip_canti;
    private List<MaestroOptions> p_option;
    private List<MaestroOptionsKey> p_options;

    public String getIp_canti() {
        return ip_canti;
    }

    public void setIp_canti(String ip_canti) {
        this.ip_canti = ip_canti;
    }

    public List<MaestroOptions> getP_option() {
        return p_option;
    }

    public void setP_option(List<MaestroOptions> p_option) {
        this.p_option = p_option;
    }

    public List<MaestroOptionsKey> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsKey> p_options) {
        this.p_options = p_options;
    }
}
