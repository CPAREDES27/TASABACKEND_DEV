package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class AnularReservaImport {
    public String p_user;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<HashMap<String, Object>> getStr_rsc() {
        return str_rsc;
    }

    public void setStr_rsc(List<HashMap<String, Object>> str_rsc) {
        this.str_rsc = str_rsc;
    }

    public List<HashMap<String, Object>> str_rsc;

}
