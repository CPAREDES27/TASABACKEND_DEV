package com.incloud.hcp.jco.tripulantes.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.HashMap;
import java.util.List;

public class RegistrosZarpeImports {

    private String p_tope;
    private String p_cdzat;
    private String p_werks;
    private String p_werkp;
    private String p_canti;
    private String p_cdmma;
    private String p_pernr;
    private List<MaestroOptions> t_opciones;
    private List<MaestroOptionsKey> p_options;
    private List<HashMap<String, Object>> t_zatrp;
    private List<HashMap<String, Object>> t_dzatr;
    private List<HashMap<String, Object>> t_nzatr;



    public List<HashMap<String, Object>> getT_nzatr() {
        return t_nzatr;
    }

    public void setT_nzatr(List<HashMap<String, Object>> t_nzatr) {
        this.t_nzatr = t_nzatr;
    }

    public List<HashMap<String, Object>> getT_zatrp() {
        return t_zatrp;
    }

    public void setT_zatrp(List<HashMap<String, Object>> t_zatrp) {
        this.t_zatrp = t_zatrp;
    }

    public List<HashMap<String, Object>> getT_dzatr() {
        return t_dzatr;
    }

    public void setT_dzatr(List<HashMap<String, Object>> t_dzatr) {
        this.t_dzatr = t_dzatr;
    }

    public List<MaestroOptionsKey> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsKey> p_options) {
        this.p_options = p_options;
    }

    private String[] fieldst_zatrp;
    private String[] fieldst_nzatr;
    private String[] fieldst_dzatr;
    private String[] fieldst_vgcer;
    private String[] fieldst_archivo;

    public String[] getFieldst_archivo() {
        return fieldst_archivo;
    }

    public void setFieldst_archivo(String[] fieldst_archivo) {
        this.fieldst_archivo = fieldst_archivo;
    }



    public String getP_tope() {
        return p_tope;
    }

    public void setP_tope(String p_tope) {
        this.p_tope = p_tope;
    }

    public String getP_cdzat() {
        return p_cdzat;
    }

    public void setP_cdzat(String p_cdzat) {
        this.p_cdzat = p_cdzat;
    }

    public String getP_werks() {
        return p_werks;
    }

    public void setP_werks(String p_werks) {
        this.p_werks = p_werks;
    }

    public String getP_werkp() {
        return p_werkp;
    }

    public void setP_werkp(String p_werkp) {
        this.p_werkp = p_werkp;
    }

    public String getP_canti() {
        return p_canti;
    }

    public void setP_canti(String p_canti) {
        this.p_canti = p_canti;
    }

    public String getP_cdmma() {
        return p_cdmma;
    }

    public void setP_cdmma(String p_cdmma) {
        this.p_cdmma = p_cdmma;
    }

    public String getP_pernr() {
        return p_pernr;
    }

    public void setP_pernr(String p_pernr) {
        this.p_pernr = p_pernr;
    }

    public List<MaestroOptions> getT_opciones() {
        return t_opciones;
    }

    public void setT_opciones(List<MaestroOptions> t_opciones) {
        this.t_opciones = t_opciones;
    }

    public String[] getFieldst_zatrp() {
        return fieldst_zatrp;
    }

    public void setFieldst_zatrp(String[] fieldst_zatrp) {
        this.fieldst_zatrp = fieldst_zatrp;
    }

    public String[] getFieldst_nzatr() {
        return fieldst_nzatr;
    }

    public void setFieldst_nzatr(String[] fieldst_nzatr) {
        this.fieldst_nzatr = fieldst_nzatr;
    }

    public String[] getFieldst_dzatr() {
        return fieldst_dzatr;
    }

    public void setFieldst_dzatr(String[] fieldst_dzatr) {
        this.fieldst_dzatr = fieldst_dzatr;
    }

    public String[] getFieldst_vgcer() {
        return fieldst_vgcer;
    }

    public void setFieldst_vgcer(String[] fieldst_vgcer) {
        this.fieldst_vgcer = fieldst_vgcer;
    }
}
