package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class EmpresaExports {
    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }

    private List<HashMap<String, Object>> data;

}
