package com.incloud.hcp.util.Mail;

public class CorreoConAdjuntoDto {


    private String from;
    private String subject;
    private String text;
    private String rutaArchivo;
    private String nombreArchivo;
    private String[] to;
    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

}
