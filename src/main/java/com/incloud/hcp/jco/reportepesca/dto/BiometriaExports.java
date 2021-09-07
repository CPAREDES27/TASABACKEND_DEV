package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class BiometriaExports {

    private String ep_mmin;
    private String ep_mmax;
    private List<HashMap<String, Object>> et_biom;
    private List<HashMap<String, Object>> et_espe;
    private List<HashMap<String, Object>> et_pscinc;
    private String base64;
    private String mensaje;

    public String getEp_mmin() {
        return ep_mmin;
    }

    public void setEp_mmin(String ep_mmin) {
        this.ep_mmin = ep_mmin;
    }

    public String getEp_mmax() {
        return ep_mmax;
    }

    public void setEp_mmax(String ep_mmax) {
        this.ep_mmax = ep_mmax;
    }

    public List<HashMap<String, Object>> getEt_biom() {
        return et_biom;
    }

    public void setEt_biom(List<HashMap<String, Object>> et_biom) {
        this.et_biom = et_biom;
    }

    public List<HashMap<String, Object>> getEt_espe() {
        return et_espe;
    }

    public void setEt_espe(List<HashMap<String, Object>> et_espe) {
        this.et_espe = et_espe;
    }

    public List<HashMap<String, Object>> getEt_pscinc() {
        return et_pscinc;
    }

    public void setEt_pscinc(List<HashMap<String, Object>> et_pscinc) {
        this.et_pscinc = et_pscinc;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
