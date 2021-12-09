package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class AnularVentaImport {
    public List<HashMap<String, Object>> getP_ventas() {
        return p_ventas;
    }

    public void setP_ventas(List<HashMap<String, Object>> p_ventas) {
        this.p_ventas = p_ventas;
    }

    public List<HashMap<String, Object>> p_ventas;
}
