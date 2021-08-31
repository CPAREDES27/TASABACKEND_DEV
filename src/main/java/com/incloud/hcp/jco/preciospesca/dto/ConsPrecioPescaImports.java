package com.incloud.hcp.jco.preciospesca.dto;

import java.util.List;

public class ConsPrecioPescaImports {
    private String ip_canti;
    private List<MaestroOptionsConsPrecioPesca> t_opcion;

    public String getIp_canti() {
        return ip_canti;
    }

    public void setIp_canti(String ip_canti) {
        this.ip_canti = ip_canti;
    }

    public List<MaestroOptionsConsPrecioPesca> getT_opcion() {
        return t_opcion;
    }

    public void setT_opcion(List<MaestroOptionsConsPrecioPesca> t_opcion) {
        this.t_opcion = t_opcion;
    }
}
