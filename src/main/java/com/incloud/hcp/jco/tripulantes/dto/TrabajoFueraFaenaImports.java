package com.incloud.hcp.jco.tripulantes.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class TrabajoFueraFaenaImports {

    private String ip_nrtff;
    private String ip_cdgfl;
    private String ip_werks;
    private String ip_tope;
    private String ip_canti;
    private String ip_tiptr;
    private String ip_sepes;
    private String ip_esreg;
    private String ip_fecin;
    private String ip_fecfn;
    private String[] fieldst_trabff;
    private String[] fieldst_trabaj;
    private String[] fieldst_fechas;
    private String[] fieldst_textos;
    private List<Options> t_opcion;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;

    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
    }

    public String getIp_nrtff() {
        return ip_nrtff;
    }

    public void setIp_nrtff(String ip_nrtff) {
        this.ip_nrtff = ip_nrtff;
    }

    public String getIp_cdgfl() {
        return ip_cdgfl;
    }

    public void setIp_cdgfl(String ip_cdgfl) {
        this.ip_cdgfl = ip_cdgfl;
    }

    public String getIp_werks() {
        return ip_werks;
    }

    public void setIp_werks(String ip_werks) {
        this.ip_werks = ip_werks;
    }

    public String getIp_tope() {
        return ip_tope;
    }

    public void setIp_tope(String ip_tope) {
        this.ip_tope = ip_tope;
    }

    public String getIp_canti() {
        return ip_canti;
    }

    public void setIp_canti(String ip_canti) {
        this.ip_canti = ip_canti;
    }

    public String getIp_tiptr() {
        return ip_tiptr;
    }

    public void setIp_tiptr(String ip_tiptr) {
        this.ip_tiptr = ip_tiptr;
    }

    public String getIp_sepes() {
        return ip_sepes;
    }

    public void setIp_sepes(String ip_sepes) {
        this.ip_sepes = ip_sepes;
    }

    public String getIp_esreg() {
        return ip_esreg;
    }

    public void setIp_esreg(String ip_esreg) {
        this.ip_esreg = ip_esreg;
    }

    public String getIp_fecin() {
        return ip_fecin;
    }

    public void setIp_fecin(String ip_fecin) {
        this.ip_fecin = ip_fecin;
    }

    public String getIp_fecfn() {
        return ip_fecfn;
    }

    public void setIp_fecfn(String ip_fecfn) {
        this.ip_fecfn = ip_fecfn;
    }

    public String[] getFieldst_trabff() {
        return fieldst_trabff;
    }

    public void setFieldst_trabff(String[] fieldst_trabff) {
        this.fieldst_trabff = fieldst_trabff;
    }

    public String[] getFieldst_trabaj() {
        return fieldst_trabaj;
    }

    public void setFieldst_trabaj(String[] fieldst_trabaj) {
        this.fieldst_trabaj = fieldst_trabaj;
    }

    public String[] getFieldst_fechas() {
        return fieldst_fechas;
    }

    public void setFieldst_fechas(String[] fieldst_fechas) {
        this.fieldst_fechas = fieldst_fechas;
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
