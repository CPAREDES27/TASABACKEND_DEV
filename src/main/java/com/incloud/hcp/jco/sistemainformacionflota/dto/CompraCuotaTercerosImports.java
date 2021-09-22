package com.incloud.hcp.jco.sistemainformacionflota.dto;

import java.util.HashMap;
import java.util.List;

public class CompraCuotaTercerosImports {

    private String p_tcons;
    private String p_cdusr;
    private List<HashMap<String, Object>> str_cet;

    public List<HashMap<String, Object>> getStr_cet() {
        return str_cet;
    }

    public void setStr_cet(List<HashMap<String, Object>> str_cet) {
        this.str_cet = str_cet;
    }

    public String getP_tcons() {
        return p_tcons;
    }

    public void setP_tcons(String p_tcons) {
        this.p_tcons = p_tcons;
    }

    public String getP_cdusr() {
        return p_cdusr;
    }

    public void setP_cdusr(String p_cdusr) {
        this.p_cdusr = p_cdusr;
    }


}
