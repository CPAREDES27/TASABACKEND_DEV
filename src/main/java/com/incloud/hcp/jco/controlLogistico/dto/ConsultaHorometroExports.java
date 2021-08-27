package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class ConsultaHorometroExports {

    private String mensaje;
    private List<HashMap<String, Object>> str_emb;
    private List<HashMap<String, Object>> str_evn;
    private List<HashMap<String, Object>> str_lho;
    private List<HashMap<String, Object>> t_mensaje;


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getStr_emb() {
        return str_emb;
    }

    public void setStr_emb(List<HashMap<String, Object>> str_emb) {
        this.str_emb = str_emb;
    }

    public List<HashMap<String, Object>> getStr_evn() {
        return str_evn;
    }

    public void setStr_evn(List<HashMap<String, Object>> str_evn) {
        this.str_evn = str_evn;
    }

    public List<HashMap<String, Object>> getStr_lho() {
        return str_lho;
    }

    public void setStr_lho(List<HashMap<String, Object>> str_lho) {
        this.str_lho = str_lho;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }
}
