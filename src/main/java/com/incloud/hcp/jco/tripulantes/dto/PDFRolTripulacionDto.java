package com.incloud.hcp.jco.tripulantes.dto;

public class PDFRolTripulacionDto {

    private String capitania;
    private String nombreNave;
    private String matricula;
    private String arqueoBruto;
    private String nombrePatron;
    private String armador;
    private String arqueoNeto;
    private String fecha;
    private String numTripulantes;

    public String getNumTripulantes() {
        return numTripulantes;
    }

    public void setNumTripulantes(String numTripulantes) {
        this.numTripulantes = numTripulantes;
    }

    public String getCapitania() {
        return capitania;
    }

    public void setCapitania(String capitania) {
        this.capitania = capitania;
    }

    public String getNombreNave() {
        return nombreNave;
    }

    public void setNombreNave(String nombreNave) {
        this.nombreNave = nombreNave;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getArqueoBruto() {
        return arqueoBruto;
    }

    public void setArqueoBruto(String arqueoBruto) {
        this.arqueoBruto = arqueoBruto;
    }

    public String getNombrePatron() {
        return nombrePatron;
    }

    public void setNombrePatron(String nombrePatron) {
        this.nombrePatron = nombrePatron;
    }

    public String getArmador() {
        return armador;
    }

    public void setArmador(String armador) {
        this.armador = armador;
    }

    public String getArqueoNeto() {
        return arqueoNeto;
    }

    public void setArqueoNeto(String arqueoNeto) {
        this.arqueoNeto = arqueoNeto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
