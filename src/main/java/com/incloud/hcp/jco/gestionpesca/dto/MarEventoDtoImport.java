package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class MarEventoDtoImport {
    private String p_user;
    private String p_indir;
    private String p_newpr;
    private String p_name1;
    private String p_stcd1;
    private String p_stras;
    private String p_orto2;
    private String p_orto1;
    private String p_regio;
    private String p_dsmma;
    private List<HashMap<String, Object>> str_marea;
    private List<HashMap<String, Object>> str_evento;
    private List<HashMap<String, Object>> str_horom;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_indir() {
        return p_indir;
    }

    public void setP_indir(String p_indir) {
        this.p_indir = p_indir;
    }

    public String getP_newpr() {
        return p_newpr;
    }

    public void setP_newpr(String p_newpr) {
        this.p_newpr = p_newpr;
    }

    public String getP_name1() {
        return p_name1;
    }

    public void setP_name1(String p_name1) {
        this.p_name1 = p_name1;
    }

    public String getP_stcd1() {
        return p_stcd1;
    }

    public void setP_stcd1(String p_stcd1) {
        this.p_stcd1 = p_stcd1;
    }

    public String getP_stras() {
        return p_stras;
    }

    public void setP_stras(String p_stras) {
        this.p_stras = p_stras;
    }

    public String getP_orto2() {
        return p_orto2;
    }

    public void setP_orto2(String p_orto2) {
        this.p_orto2 = p_orto2;
    }

    public String getP_orto1() {
        return p_orto1;
    }

    public void setP_orto1(String p_orto1) {
        this.p_orto1 = p_orto1;
    }

    public String getP_regio() {
        return p_regio;
    }

    public void setP_regio(String p_regio) {
        this.p_regio = p_regio;
    }

    public String getP_dsmma() {
        return p_dsmma;
    }

    public void setP_dsmma(String p_dsmma) {
        this.p_dsmma = p_dsmma;
    }

    public List<HashMap<String, Object>> getStr_marea() {
        return str_marea;
    }

    public void setStr_marea(List<HashMap<String, Object>> str_marea) {
        this.str_marea = str_marea;
    }

    public List<HashMap<String, Object>> getStr_evento() {
        return str_evento;
    }

    public void setStr_evento(List<HashMap<String, Object>> str_evento) {
        this.str_evento = str_evento;
    }

    public List<HashMap<String, Object>> getStr_horom() {
        return str_horom;
    }

    public void setStr_horom(List<HashMap<String, Object>> str_horom) {
        this.str_horom = str_horom;
    }
}
