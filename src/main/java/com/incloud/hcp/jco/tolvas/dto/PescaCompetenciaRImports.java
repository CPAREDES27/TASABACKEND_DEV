package com.incloud.hcp.jco.tolvas.dto;

import org.apache.commons.collections4.map.HashedMap;

import java.util.HashMap;
import java.util.List;

public class PescaCompetenciaRImports {



    private String p_user;
    private String p_fecha;
    private String p_cdtpc;
    private String p_tipo;
    private List<HashMap<String, Object>> str_zlt;
    private List<HashMap<String, Object>> str_pto;
    private List<HashMap<String, Object>> str_pcp;
    private String[] fieldStr_zlt;
    private String[] fieldStr_pto;
    private String[] fieldStr_pcp;

    public String[] getFieldStr_zlt() {
        return fieldStr_zlt;
    }

    public void setFieldStr_zlt(String[] fieldStr_zlt) {
        this.fieldStr_zlt = fieldStr_zlt;
    }

    public String[] getFieldStr_pto() {
        return fieldStr_pto;
    }

    public void setFieldStr_pto(String[] fieldStr_pto) {
        this.fieldStr_pto = fieldStr_pto;
    }

    public String[] getFieldStr_pcp() {
        return fieldStr_pcp;
    }

    public void setFieldStr_pcp(String[] fieldStr_pcp) {
        this.fieldStr_pcp = fieldStr_pcp;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }
    public String getP_fecha() {
        return p_fecha;
    }

    public void setP_fecha(String p_fecha) {
        this.p_fecha = p_fecha;
    }

    public String getP_cdtpc() {
        return p_cdtpc;
    }

    public void setP_cdtpc(String p_cdtpc) {
        this.p_cdtpc = p_cdtpc;
    }

    public String getP_tipo() {
        return p_tipo;
    }

    public void setP_tipo(String p_tipo) {
        this.p_tipo = p_tipo;
    }

    public List<HashMap<String, Object>> getStr_zlt() {
        return str_zlt;
    }

    public void setStr_zlt(List<HashMap<String, Object>> str_zlt) {
        this.str_zlt = str_zlt;
    }

    public List<HashMap<String, Object>> getStr_pto() {
        return str_pto;
    }

    public void setStr_pto(List<HashMap<String, Object>> str_pto) {
        this.str_pto = str_pto;
    }

    public List<HashMap<String, Object>> getStr_pcp() {
        return str_pcp;
    }

    public void setStr_pcp(List<HashMap<String, Object>> str_pcp) {
        this.str_pcp = str_pcp;
    }
}
