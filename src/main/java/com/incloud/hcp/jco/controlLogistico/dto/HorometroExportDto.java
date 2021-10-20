package com.incloud.hcp.jco.controlLogistico.dto;

public class HorometroExportDto {
    private String fecha;
    private String motorPrincipal;
    private String motorAuxiliar;
    private String MotorAuxiliar2;
    private String MotorAuxiliar3;
    private String MotorAuxiliar4;
    private String MotorAuxiliar5;
    private String panga;
    private String flujometro;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotorPrincipal() {
        return motorPrincipal;
    }

    public void setMotorPrincipal(String motorPrincipal) {
        this.motorPrincipal = motorPrincipal;
    }

    public String getMotorAuxiliar() {
        return motorAuxiliar;
    }

    public void setMotorAuxiliar(String motorAuxiliar) {
        this.motorAuxiliar = motorAuxiliar;
    }

    public String getMotorAuxiliar2() {
        return MotorAuxiliar2;
    }

    public void setMotorAuxiliar2(String motorAuxiliar2) {
        MotorAuxiliar2 = motorAuxiliar2;
    }

    public String getMotorAuxiliar3() {
        return MotorAuxiliar3;
    }

    public void setMotorAuxiliar3(String motorAuxiliar3) {
        MotorAuxiliar3 = motorAuxiliar3;
    }

    public String getMotorAuxiliar4() {
        return MotorAuxiliar4;
    }

    public void setMotorAuxiliar4(String motorAuxiliar4) {
        MotorAuxiliar4 = motorAuxiliar4;
    }

    public String getMotorAuxiliar5() {
        return MotorAuxiliar5;
    }

    public void setMotorAuxiliar5(String motorAuxiliar5) {
        MotorAuxiliar5 = motorAuxiliar5;
    }

    public String getPanga() {
        return panga;
    }

    public void setPanga(String panga) {
        this.panga = panga;
    }

    public String getFlujometro() {
        return flujometro;
    }

    public void setFlujometro(String flujometro) {
        this.flujometro = flujometro;
    }
}
