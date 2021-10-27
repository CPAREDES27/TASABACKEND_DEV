package com.incloud.hcp.jco.tripulantes.dto;

public class PDFProduceDto {

    private String armadorORespresentante;//ARMAD
    private String documentoIdentidad;   //DNIAR
    private String nombreEP;   //NMEMB
    private String matriculaEP;   //MREMB
    private String permisoPesca;   //RSPMS
    private String capacidadBodegaM3;   //CVPMS
    private String capacidadBodegaTM;   //CPPMS
    private String nroTripulantes;   //NRTRI
    private String fecha;   //FIDES
    private String hora;   //HIDES
    private String nombre;   //DSPDG
    private String inicioDescarga;   //HIDES
    private String finDescarga;   //HFDES
    private String TMDescargadas;   //CNPDS
    private String comercianteRecibeProd;   //

    public String getArmadorORespresentante() {
        return armadorORespresentante;
    }

    public void setArmadorORespresentante(String armadorORespresentante) {
        this.armadorORespresentante = armadorORespresentante;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombreEP() {
        return nombreEP;
    }

    public void setNombreEP(String nombreEP) {
        this.nombreEP = nombreEP;
    }

    public String getMatriculaEP() {
        return matriculaEP;
    }

    public void setMatriculaEP(String matriculaEP) {
        this.matriculaEP = matriculaEP;
    }

    public String getPermisoPesca() {
        return permisoPesca;
    }

    public void setPermisoPesca(String permisoPesca) {
        this.permisoPesca = permisoPesca;
    }

    public String getCapacidadBodegaM3() {
        return capacidadBodegaM3;
    }

    public void setCapacidadBodegaM3(String capacidadBodegaM3) {
        this.capacidadBodegaM3 = capacidadBodegaM3;
    }

    public String getCapacidadBodegaTM() {
        return capacidadBodegaTM;
    }

    public void setCapacidadBodegaTM(String capacidadBodegaTM) {
        this.capacidadBodegaTM = capacidadBodegaTM;
    }

    public String getNroTripulantes() {
        return nroTripulantes;
    }

    public void setNroTripulantes(String nroTripulantes) {
        this.nroTripulantes = nroTripulantes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicioDescarga() {
        return inicioDescarga;
    }

    public void setInicioDescarga(String inicioDescarga) {
        this.inicioDescarga = inicioDescarga;
    }

    public String getFinDescarga() {
        return finDescarga;
    }

    public void setFinDescarga(String finDescarga) {
        this.finDescarga = finDescarga;
    }

    public String getTMDescargadas() {
        return TMDescargadas;
    }

    public void setTMDescargadas(String TMDescargadas) {
        this.TMDescargadas = TMDescargadas;
    }

    public String getComercianteRecibeProd() {
        return comercianteRecibeProd;
    }

    public void setComercianteRecibeProd(String comercianteRecibeProd) {
        this.comercianteRecibeProd = comercianteRecibeProd;
    }
}
