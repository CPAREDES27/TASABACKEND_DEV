package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class AnalisisCombusRegImport {
    private ArrayList<String> titulos;
    private ArrayList<HashMap<String, Object>> data;

    public ArrayList<String> getTitulos() {
        return titulos;
    }

    public void setTitulos(ArrayList<String> titulos) {
        this.titulos = titulos;
    }

    public ArrayList<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
    }
}
