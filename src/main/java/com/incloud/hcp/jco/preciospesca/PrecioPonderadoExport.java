package com.incloud.hcp.jco.preciospesca;

import java.util.HashMap;
import java.util.List;

public class PrecioPonderadoExport {
    private List<HashMap<String, Object>> T_PRCPESCPTA;
    private String mensaje;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<HashMap<String, Object>> getT_PRCPESCPTA() {
        return T_PRCPESCPTA;
    }

    public void setT_PRCPESCPTA(List<HashMap<String, Object>> t_PRCPESCPTA) {
        T_PRCPESCPTA = t_PRCPESCPTA;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
