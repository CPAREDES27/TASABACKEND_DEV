package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class HorometroExport {

    private List<HashMap<String, Object>> t_marea;
    private List<HashMap<String, Object>> t_event;
    private List<HashMap<String, Object>> t_lechor;
    private List<HashMap<String, Object>> t_mensaje;


    public List<HashMap<String, Object>> getT_marea() {
        return t_marea;
    }

    public void setT_marea(List<HashMap<String, Object>> t_marea) {
        this.t_marea = t_marea;
    }

    public List<HashMap<String, Object>> getT_event() {
        return t_event;
    }

    public void setT_event(List<HashMap<String, Object>> t_event) {
        this.t_event = t_event;
    }

    public List<HashMap<String, Object>> getT_lechor() {
        return t_lechor;
    }

    public void setT_lechor(List<HashMap<String, Object>> t_lechor) {
        this.t_lechor = t_lechor;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }


}
