package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.List;

public class AceitesUsadosImports {

    private String ip_tope;
    private String ip_nrrnv;
    private String ip_cdemb;
    private String ip_fhrnvi;
    private String ip_fhrnvf;
    private String ip_cdalm;
    private String ip_esrnv;
    private String FHRNV;
    private String[] fieldsT_rnv;
    private String[] fieldsT_rpn;
    private String[] fieldsEt_mensj;
    private List<RegistroAceiteDto> t_rpn;

    public String getFHRNV() {
        return FHRNV;
    }

    public void setFHRNV(String FHRNV) {
        this.FHRNV = FHRNV;
    }

    public List<RegistroAceiteDto> getT_rpn() {
        return t_rpn;
    }

    public void setT_rpn(List<RegistroAceiteDto> t_rpn) {
        this.t_rpn = t_rpn;
    }

    public String[] getFieldsEt_mensj() {
        return fieldsEt_mensj;
    }

    public void setFieldsEt_mensj(String[] fieldsEt_mensj) {
        this.fieldsEt_mensj = fieldsEt_mensj;
    }

    public String getIp_tope() {
        return ip_tope;
    }

    public void setIp_tope(String ip_tope) {
        this.ip_tope = ip_tope;
    }

    public String getIp_nrrnv() {
        return ip_nrrnv;
    }

    public void setIp_nrrnv(String ip_nrrnv) {
        this.ip_nrrnv = ip_nrrnv;
    }

    public String getIp_cdemb() {
        return ip_cdemb;
    }

    public void setIp_cdemb(String ip_cdemb) {
        this.ip_cdemb = ip_cdemb;
    }

    public String getIp_fhrnvi() {
        return ip_fhrnvi;
    }

    public void setIp_fhrnvi(String ip_fhrnvi) {
        this.ip_fhrnvi = ip_fhrnvi;
    }

    public String getIp_fhrnvf() {
        return ip_fhrnvf;
    }

    public void setIp_fhrnvf(String ip_fhrnvf) {
        this.ip_fhrnvf = ip_fhrnvf;
    }

    public String getIp_cdalm() {
        return ip_cdalm;
    }

    public void setIp_cdalm(String ip_cdalm) {
        this.ip_cdalm = ip_cdalm;
    }

    public String getIp_esrnv() {
        return ip_esrnv;
    }

    public void setIp_esrnv(String ip_esrnv) {
        this.ip_esrnv = ip_esrnv;
    }

    public String[] getFieldsT_rnv() {
        return fieldsT_rnv;
    }

    public void setFieldsT_rnv(String[] fieldsT_rnv) {
        this.fieldsT_rnv = fieldsT_rnv;
    }

    public String[] getFieldsT_rpn() {
        return fieldsT_rpn;
    }

    public void setFieldsT_rpn(String[] fieldsT_rpn) {
        this.fieldsT_rpn = fieldsT_rpn;
    }
}
