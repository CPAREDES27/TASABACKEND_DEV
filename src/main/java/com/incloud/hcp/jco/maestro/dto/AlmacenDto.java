package com.incloud.hcp.jco.maestro.dto;

public class AlmacenDto {
    public String CDALM;        //CDALM
    public String CDPTA;       //CDPTA
    public String DSALM;      //DSALM
    public String WERKS;           //WERKS
    public String NEWKO;       //NEWKO
    public String CDALE;       //CDALE
    public String ESREG;        //ESREG

    public String getCDALM() { return CDALM;  }

    public void setCDALM(String CDALM) {
        this.CDALM = CDALM;}

    public String getCDPTA() {return CDPTA;  }

    public void setCDPTA(String CDPTA) {
        this.CDPTA = CDPTA;}

    public String getDSALM() {return DSALM;}

    public void setDSALM(String DSALM) {
        this.DSALM = DSALM;}

    public String getWERKS() {return WERKS;}

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS; }

    public String getCDALE() {return CDALE;}

    public void setCDALE(String CDALE) {
        this.CDALE = CDALE;}

    public String getESREG() {return ESREG;}

    public void setESREG(String ESREG) {
        this.ESREG = ESREG;}

    public String getNEWKO() {
        return NEWKO;
    }

    public void setNEWKO(String NEWKO) {
        this.NEWKO = NEWKO;
    }
}
