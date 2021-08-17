package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class ReglasDatosExterNuevImports {

    private ReglasDatosExterDto params;
    private List<HashMap<String, Object>> s_insert;
    private List<HashMap<String, Object>> s_eod;

    public ReglasDatosExterDto getParams() {
        return params;
    }

    public void setParams(ReglasDatosExterDto params) {
        this.params = params;
    }

    public List<HashMap<String, Object>> getS_insert() {
        return s_insert;
    }

    public void setS_insert(List<HashMap<String, Object>> s_insert) {
        this.s_insert = s_insert;
    }

    public List<HashMap<String, Object>> getS_eod() {
        return s_eod;
    }

    public void setS_eod(List<HashMap<String, Object>> s_eod) {
        this.s_eod = s_eod;
    }
}
