package com.incloud.hcp.jco.sistemainformacionflota.dto;



import java.util.HashMap;
import java.util.List;

public class PescaCompetenciaRadialExports {

    private List<HashMap<String, Object>> str_zlt;
    private List<HashMap<String, Object>> str_pto;
    private List<HashMap<String, Object>> str_gre;
    private List<HashMap<String, Object>> str_emp;
    private List<HashMap<String, Object>> str_pge;
    private List<HashMap<String, Object>> str_epp;
    private String mensaje;

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

    public List<HashMap<String, Object>> getStr_gre() {
        return str_gre;
    }

    public void setStr_gre(List<HashMap<String, Object>> str_gre) {
        this.str_gre = str_gre;
    }

    public List<HashMap<String, Object>> getStr_emp() {
        return str_emp;
    }

    public void setStr_emp(List<HashMap<String, Object>> str_emp) {
        this.str_emp = str_emp;
    }

    public List<HashMap<String, Object>> getStr_pge() {
        return str_pge;
    }

    public void setStr_pge(List<HashMap<String, Object>> str_pge) {
        this.str_pge = str_pge;
    }

    public List<HashMap<String, Object>> getStr_epp() {
        return str_epp;
    }

    public void setStr_epp(List<HashMap<String, Object>> str_epp) {
        this.str_epp = str_epp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
