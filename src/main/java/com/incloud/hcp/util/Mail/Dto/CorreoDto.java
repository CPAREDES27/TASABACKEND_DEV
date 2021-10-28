package com.incloud.hcp.util.Mail.Dto;

import java.util.List;

public class CorreoDto {

    private String subject;
    private String body;
    private List<String> sendTo;


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
