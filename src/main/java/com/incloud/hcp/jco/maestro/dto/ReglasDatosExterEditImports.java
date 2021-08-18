package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class ReglasDatosExterEditImports {

    private ReglasDatosExterDto params;
    private List<HashMap<String, Object>> s_update;
    private List<HashMap<String, Object>> s_eod;

    public ReglasDatosExterDto getParams() {
        return params;
    }

    public void setParams(ReglasDatosExterDto params) {
        this.params = params;
    }

    public List<HashMap<String, Object>> getS_update() {
        return s_update;
    }

    public void setS_update(List<HashMap<String, Object>> s_update) {
        this.s_update = s_update;
    }



    public List<HashMap<String, Object>> getS_eod() {
        return s_eod;
    }

    public void setS_eod(List<HashMap<String, Object>> s_eod) {
        this.s_eod = s_eod;
    }
}
