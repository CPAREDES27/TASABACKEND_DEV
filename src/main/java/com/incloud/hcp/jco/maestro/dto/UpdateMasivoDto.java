package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class UpdateMasivoDto {

    private List<HashMap<String, Object>> values;
    private List<HashMap<String, Object>> options;

    public List<HashMap<String, Object>> getValues() {
        return values;
    }

    public void setValues(List<HashMap<String, Object>> values) {
        this.values = values;
    }

    public List<HashMap<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(List<HashMap<String, Object>> options) {
        this.options = options;
    }


}
