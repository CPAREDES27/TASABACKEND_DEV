package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class ReglasDatosExterEditImports {

    private ReglasDatosExterDto params;
    private List<HashMap<String, Object>> t_rrz;
    private List<HashMap<String, Object>> t_ref;
    private List<HashMap<String, Object>> t_eod;

    public ReglasDatosExterDto getParams() {
        return params;
    }

    public void setParams(ReglasDatosExterDto params) {
        this.params = params;
    }

    public List<HashMap<String, Object>> getT_rrz() {
        return t_rrz;
    }

    public void setT_rrz(List<HashMap<String, Object>> t_rrz) {
        this.t_rrz = t_rrz;
    }

    public List<HashMap<String, Object>> getT_ref() {
        return t_ref;
    }

    public void setT_ref(List<HashMap<String, Object>> t_ref) {
        this.t_ref = t_ref;
    }

    public List<HashMap<String, Object>> getT_eod() {
        return t_eod;
    }

    public void setT_eod(List<HashMap<String, Object>> t_eod) {
        this.t_eod = t_eod;
    }
}
