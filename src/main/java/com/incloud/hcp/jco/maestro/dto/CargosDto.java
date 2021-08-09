package com.incloud.hcp.jco.maestro.dto;

public class CargosDto {
    private String CDCRG;    //Codigo de cargo
    private String DSCRG;    //Descripcion de cargo
    private String ESREG;    //Estado registro


    public String getCDCRG() {
        return CDCRG;
    }

    public void setCDCRG(String CDCRG) {
        this.CDCRG = CDCRG;
    }

    public String getDSCRG() {
        return DSCRG;
    }

    public void setDSCRG(String DSCRG) {
        this.DSCRG = DSCRG;
    }

    public String getESREG() {
        return ESREG;
    }

    public void setESREG(String ESREG) {
        this.ESREG = ESREG;
    }

}
