package com.incloud.hcp.jco.tripulantes.dto;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.dto.Options;

import java.util.HashMap;
import java.util.List;

public class ProtestosImports {

    private String ip_tope;
    private String ip_cdprt;
    private String ip_canti;
    private String ip_pernr;
    private String[] fieldst_baprt;
    private String[] fieldst_textos;
    private List<Options> t_opcion;
    private List<HashMap<String, Object>> t_baprt;
    private List<HashMap<String, Object>> t_textos;

    public List<HashMap<String, Object>> getT_baprt() {
        return t_baprt;
    }

    public void setT_baprt(List<HashMap<String, Object>> t_baprt) {
        this.t_baprt = t_baprt;
    }

    public List<HashMap<String, Object>> getT_textos() {
        return t_textos;
    }

    public void setT_textos(List<HashMap<String, Object>> t_textos) {
        this.t_textos = t_textos;
    }

    public String getIp_tope() {
        return ip_tope;
    }

    public void setIp_tope(String ip_tope) {
        this.ip_tope = ip_tope;
    }

    public String getIp_cdprt() {
        return ip_cdprt;
    }

    public void setIp_cdprt(String ip_cdprt) {
        this.ip_cdprt = ip_cdprt;
    }

    public String getIp_canti() {
        return ip_canti;
    }

    public void setIp_canti(String ip_canti) {
        this.ip_canti = ip_canti;
    }

    public String getIp_pernr() {
        return ip_pernr;
    }

    public void setIp_pernr(String ip_pernr) {
        this.ip_pernr = ip_pernr;
    }

    public String[] getFieldst_baprt() {
        return fieldst_baprt;
    }

    public void setFieldst_baprt(String[] fieldst_baprt) {
        this.fieldst_baprt = fieldst_baprt;
    }

    public String[] getFieldst_textos() {
        return fieldst_textos;
    }

    public void setFieldst_textos(String[] fieldst_textos) {
        this.fieldst_textos = fieldst_textos;
    }

    public List<Options> getT_opcion() {
        return t_opcion;
    }

    public void setT_opcion(List<Options> t_opcion) {
        this.t_opcion = t_opcion;
    }
}
