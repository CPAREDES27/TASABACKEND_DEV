package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class EventosPescaDto {

    private List<ST_CCPDto> Lista_st_cpp;
    private ST_CEPDto st_cep;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ST_CCPDto> getLista_st_cpp() {
        return Lista_st_cpp;
    }

    public void setLista_st_cpp(List<ST_CCPDto> lista_st_cpp) {
        Lista_st_cpp = lista_st_cpp;
    }

    public ST_CEPDto getSt_cep() {
        return st_cep;
    }

    public void setSt_cep(ST_CEPDto st_cep) {
        this.st_cep = st_cep;
    }
}
