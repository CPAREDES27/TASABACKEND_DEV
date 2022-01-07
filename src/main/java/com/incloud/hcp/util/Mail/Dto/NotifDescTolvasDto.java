package com.incloud.hcp.util.Mail.Dto;

import java.util.HashMap;
import java.util.List;

public class NotifDescTolvasDto {


    private String planta;
    private List<HashMap<String, Object>> data;



    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }
}
