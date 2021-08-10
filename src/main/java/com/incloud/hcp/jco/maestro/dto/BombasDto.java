package com.incloud.hcp.jco.maestro.dto;

public class BombasDto {


    private String CDBOM;     //Codigo bomba
    private String DSBOM;     //Descripcion de bomba
    private String ESREG;     //estado de registro


    public String getCDBOM() {
        return CDBOM;
    }

    public void setCDBOM(String CDBOM) {
        this.CDBOM = CDBOM;
    }

    public String getDSBOM() {
        return DSBOM;
    }

    public void setDSBOM(String DSBOM) {
        this.DSBOM = DSBOM;
    }

    public String getESREG() {
        return ESREG;
    }

    public void setESREG(String ESREG) {
        this.ESREG = ESREG;
    }


}
