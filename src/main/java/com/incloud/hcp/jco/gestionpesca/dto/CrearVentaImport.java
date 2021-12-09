package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class CrearVentaImport {

    public String p_user;
    public int p_nrmar;
    public String p_cdemb;
    public String p_werks;
    public String p_lgort;
    public int p_nrevn;
    public String p_fhrsv;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public int getP_nrmar() {
        return p_nrmar;
    }

    public void setP_nrmar(int p_nrmar) {
        this.p_nrmar = p_nrmar;
    }

    public String getP_cdemb() {
        return p_cdemb;
    }

    public void setP_cdemb(String p_cdemb) {
        this.p_cdemb = p_cdemb;
    }

    public String getP_werks() {
        return p_werks;
    }

    public void setP_werks(String p_werks) {
        this.p_werks = p_werks;
    }

    public String getP_lgort() {
        return p_lgort;
    }

    public void setP_lgort(String p_lgort) {
        this.p_lgort = p_lgort;
    }

    public int getP_nrevn() {
        return p_nrevn;
    }

    public void setP_nrevn(int p_nrevn) {
        this.p_nrevn = p_nrevn;
    }

    public String getP_fhrsv() {
        return p_fhrsv;
    }

    public void setP_fhrsv(String p_fhrsv) {
        this.p_fhrsv = p_fhrsv;
    }

    public List<HashMap<String, Object>> getStr_rcb() {
        return str_rcb;
    }

    public void setStr_rcb(List<HashMap<String, Object>> str_rcb) {
        this.str_rcb = str_rcb;
    }

    public List<HashMap<String, Object>> str_rcb;



}
