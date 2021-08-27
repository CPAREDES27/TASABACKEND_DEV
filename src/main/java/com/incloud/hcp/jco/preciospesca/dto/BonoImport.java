package com.incloud.hcp.jco.preciospesca.dto;

import java.util.List;

public class BonoImport {
    private String p_user;
    private String p_tcons;
    private String p_nrmar;
    private String p_nrdes;
    private String p_cdspc;
    private List<Bpm> str_bpm;
    private List<Act> str_act;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_tcons() {
        return p_tcons;
    }

    public void setP_tcons(String p_tcons) {
        this.p_tcons = p_tcons;
    }

    public String getP_nrmar() {
        return p_nrmar;
    }

    public void setP_nrmar(String p_nrmar) {
        this.p_nrmar = p_nrmar;
    }

    public String getP_nrdes() {
        return p_nrdes;
    }

    public void setP_nrdes(String p_nrdes) {
        this.p_nrdes = p_nrdes;
    }

    public String getP_cdspc() {
        return p_cdspc;
    }

    public void setP_cdspc(String p_cdspc) {
        this.p_cdspc = p_cdspc;
    }

    public List<Bpm> getStr_bpm() {
        return str_bpm;
    }

    public void setStr_bpm(List<Bpm> str_bpm) {
        this.str_bpm = str_bpm;
    }

    public List<Act> getStr_act() {
        return str_act;
    }

    public void setStr_act(List<Act> str_act) {
        this.str_act = str_act;
    }
}
