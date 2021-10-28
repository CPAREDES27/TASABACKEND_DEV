package com.incloud.hcp.util.Mail;

import java.util.List;

public class CorreoDto {

    private String subject;
    private String body;
    private List<String> sendTo;
    private String  planta;
    private String[]  data;

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getSendTo() {
        return sendTo;
    }

    public void setSendTo(List<String> sendTo) {
        this.sendTo = sendTo;
    }
}
