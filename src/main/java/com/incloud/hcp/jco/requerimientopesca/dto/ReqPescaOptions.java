package com.incloud.hcp.jco.requerimientopesca.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;

import java.util.List;

public class ReqPescaOptions {
    private String ip_tpope;
    private String ip_finit;
    private String ip_ffint;
    private String ip_zona;
    private List<RequerimientoPesca> reqList;
    private String[] fieldReqPesca;

    public List<RequerimientoPesca> getReqList() {
        return reqList;
    }

    public void setReqList(List<RequerimientoPesca> reqList) {
        this.reqList = reqList;
    }

    public String getIp_tpope() {
        return ip_tpope;
    }

    public void setIp_tpope(String ip_tpope) {
        this.ip_tpope = ip_tpope;
    }

    public String getIp_finit() {
        return ip_finit;
    }

    public void setIp_finit(String ip_finit) {
        this.ip_finit = ip_finit;
    }

    public String getIp_ffint() {
        return ip_ffint;
    }

    public void setIp_ffint(String ip_ffint) {
        this.ip_ffint = ip_ffint;
    }

    public String getIp_zona() {
        return ip_zona;
    }

    public void setIp_zona(String ip_zona) {
        this.ip_zona = ip_zona;
    }

    public String[] getFieldReqPesca() {
        return fieldReqPesca;
    }

    public void setFieldReqPesca(String[] fieldReqPesca) {
        this.fieldReqPesca = fieldReqPesca;
    }
}
