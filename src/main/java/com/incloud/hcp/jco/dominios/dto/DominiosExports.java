package com.incloud.hcp.jco.dominios.dto;

import java.util.HashMap;
import java.util.List;

public class DominiosExports {
    private String dominio;
    private List<DominioExportsData> data;

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public List<DominioExportsData> getData() {
        return data;
    }

    public void setData(List<DominioExportsData> data) {
        this.data = data;
    }
}
