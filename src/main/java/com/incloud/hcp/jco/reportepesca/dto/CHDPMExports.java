package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class CHDPMExports {
    private List<HashMap<String, Object>> t_DetailsReport;
    private List<HashMap<String, Object>> t_bodeg;
    private String mensaje;

    public List<HashMap<String, Object>> getT_DetailsReport() {
        return t_DetailsReport;
    }

    public void setT_DetailsReport(List<HashMap<String, Object>> t_DetailsReport) {
        this.t_DetailsReport = t_DetailsReport;
    }

    public List<HashMap<String, Object>> getT_bodeg() {
        return t_bodeg;
    }

    public void setT_bodeg(List<HashMap<String, Object>> t_bodeg) {
        this.t_bodeg = t_bodeg;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
