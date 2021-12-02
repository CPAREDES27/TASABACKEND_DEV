package com.incloud.hcp.jco.tripulantes.dto;

import java.util.List;

public class SeguimientoTripuImports {

   private String ip_perner;
   private String ip_cdpcn;
   private String ip_tired;
   private String[] fieldst_dstrip;
   private List<com.incloud.hcp.jco.controlLogistico.dto.Options> t_opcion;

    public String getIp_perner() {
        return ip_perner;
    }

    public void setIp_perner(String ip_perner) {
        this.ip_perner = ip_perner;
    }

    public String getIp_cdpcn() {
        return ip_cdpcn;
    }

    public void setIp_cdpcn(String ip_cdpcn) {
        this.ip_cdpcn = ip_cdpcn;
    }

    public String getIp_tired() {
        return ip_tired;
    }

    public void setIp_tired(String ip_tired) {
        this.ip_tired = ip_tired;
    }

    public String[] getFieldst_dstrip() {
        return fieldst_dstrip;
    }

    public void setFieldst_dstrip(String[] fieldst_dstrip) {
        this.fieldst_dstrip = fieldst_dstrip;
    }

    public List<com.incloud.hcp.jco.controlLogistico.dto.Options> getT_opcion() {
        return t_opcion;
    }

    public void setT_opcion(List<com.incloud.hcp.jco.controlLogistico.dto.Options> t_opcion) {
        this.t_opcion = t_opcion;
    }
}
