package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class AceitesUsadosExports {
    private String mensaje;
    private List<HashMap<String, Object>> t_rnv;
    private List<HashMap<String, Object>> t_rpn;
    private List<HashMap<String, Object>> et_mensj;
    private String ep_nrrnv;

    public List<HashMap<String, Object>> getEt_mensj() {
        return et_mensj;
    }

    public void setEt_mensj(List<HashMap<String, Object>> et_mensj) {
        this.et_mensj = et_mensj;
    }

    public String getEp_nrrnv() {
        return ep_nrrnv;
    }

    public void setEp_nrrnv(String ep_nrrnv) {
        this.ep_nrrnv = ep_nrrnv;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getT_rnv() {
        return t_rnv;
    }

    public void setT_rnv(List<HashMap<String, Object>> t_rnv) {
        this.t_rnv = t_rnv;
    }

    public List<HashMap<String, Object>> getT_rpn() {
        return t_rpn;
    }

    public void setT_rpn(List<HashMap<String, Object>> t_rpn) {
        this.t_rpn = t_rpn;
    }
}
