package com.incloud.hcp.jco.tripulantes.dto;

import java.util.List;

public class AvisoAveriaImports {

    private String ip_tope;
    private String ip_nravi;
    private String[] fieldst_zpmavi;
    private String[] fieldst_textos;
    private List<Options> t_opcion;

    public List<Options> getT_opcion() {
        return t_opcion;
    }

    public void setT_opcion(List<Options> t_opcion) {
        this.t_opcion = t_opcion;
    }

    public String getIp_tope() {
        return ip_tope;
    }

    public void setIp_tope(String ip_tope) {
        this.ip_tope = ip_tope;
    }

    public String getIp_nravi() {
        return ip_nravi;
    }

    public void setIp_nravi(String ip_nravi) {
        this.ip_nravi = ip_nravi;
    }

    public String[] getFieldst_zpmavi() {
        return fieldst_zpmavi;
    }

    public void setFieldst_zpmavi(String[] fieldst_zpmavi) {
        this.fieldst_zpmavi = fieldst_zpmavi;
    }

    public String[] getFieldst_textos() {
        return fieldst_textos;
    }

    public void setFieldst_textos(String[] fieldst_textos) {
        this.fieldst_textos = fieldst_textos;
    }
}
