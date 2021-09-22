package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.HashMap;
import java.util.List;

public class PescaDeclaradaDifeExports {

    private List<HashMap<String, Object>> str_ptd;
    private List<HashMap<String, Object>> str_ptr;
    private List<HashMap<String, Object>> str_emd;
    private List<HashMap<String, Object>> str_emr;
    private String mensaje;


    public List<HashMap<String, Object>> getStr_ptd() {
        return str_ptd;
    }

    public void setStr_ptd(List<HashMap<String, Object>> str_ptd) {
        this.str_ptd = str_ptd;
    }

    public List<HashMap<String, Object>> getStr_ptr() {
        return str_ptr;
    }

    public void setStr_ptr(List<HashMap<String, Object>> str_ptr) {
        this.str_ptr = str_ptr;
    }

    public List<HashMap<String, Object>> getStr_emd() {
        return str_emd;
    }

    public void setStr_emd(List<HashMap<String, Object>> str_emd) {
        this.str_emd = str_emd;
    }

    public List<HashMap<String, Object>> getStr_emr() {
        return str_emr;
    }

    public void setStr_emr(List<HashMap<String, Object>> str_emr) {
        this.str_emr = str_emr;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
