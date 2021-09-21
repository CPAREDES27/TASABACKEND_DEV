package com.incloud.hcp.jco.tripulantes.dto;

public class PDFProtestosDto {

    private String trato;
    private String cargoUno;
    private String nombreCargoUno;
    private String domicilioLegal;
    private String cargoDos;
    private String nombreCargoDos;
    private String dni;
    private String segundoParrafo;
    private String puerto;
    private String fecha;
    private String nombreEmbarcacion;
    private String matricula;


    public static String capitaniaGuardacostas="CAPITANIA GUARDACOSTAS MARÍTIMA DE CALLAO";
    public static String primerParrafo1="Tecnólogica de Alimentos con RUC. 20100971772, con domicilio legal en ";
    public static String primerParrafo2=" debidamente representada por el señor BAHIA DE PUERTO, ";
    public static String primerParrafo3=" identificado con DNI N° ";
    public static String primerParrafo4=" y representante de la Embarcación ";
    public static String primerParrafo5=" de matrícula ";
    public static String primerParrafo6=" de propiedad de nuestra empresa, con el debido respeto tengo a bien direigirme a Ud. para informar lo siguiente:";

    public static String textoFinal1="Lo que cumplimos con informar para los fines que estime vuestro despacho reservándonos";
    public static String textoFinal2="el derecho de ampliar el presente protesto las veces que se estime conveniente";
    public static String textoFinal3="Aprovecho la oportunidad para reiterar a Ud. los sentmientos de mi consideración y estima.";
    public static String atentamente="Atentamente,";
    public static String nProtesto="N° Protesto: ";

    public String getNombreEmbarcacion() {
        return nombreEmbarcacion;
    }

    public void setNombreEmbarcacion(String nombreEmbarcacion) {
        this.nombreEmbarcacion = nombreEmbarcacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getTrato() {
        return trato;
    }

    public void setTrato(String trato) {
        this.trato = trato;
    }

    public String getCargoUno() {
        return cargoUno;
    }

    public void setCargoUno(String cargoUno) {
        this.cargoUno = cargoUno;
    }

    public String getNombreCargoUno() {
        return nombreCargoUno;
    }

    public void setNombreCargoUno(String nombreCargoUno) {
        this.nombreCargoUno = nombreCargoUno;
    }

    public String getDomicilioLegal() {
        return domicilioLegal;
    }

    public void setDomicilioLegal(String domicilioLegal) {
        this.domicilioLegal = domicilioLegal;
    }

    public String getCargoDos() {
        return cargoDos;
    }

    public void setCargoDos(String cargoDos) {
        this.cargoDos = cargoDos;
    }

    public String getNombreCargoDos() {
        return nombreCargoDos;
    }

    public void setNombreCargoDos(String nombreCargoDos) {
        this.nombreCargoDos = nombreCargoDos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSegundoParrafo() {
        return segundoParrafo;
    }

    public void setSegundoParrafo(String segundoParrafo) {
        this.segundoParrafo = segundoParrafo;
    }
}
