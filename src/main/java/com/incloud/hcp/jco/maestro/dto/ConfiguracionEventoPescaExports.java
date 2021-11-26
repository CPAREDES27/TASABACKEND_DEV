package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class ConfiguracionEventoPescaExports {
    private List<HashMap<String, Object>> List_EventosPesca;
    private List<HashMap<String, Object>> Listar_ConsCombEve;
    private String EspeUniMedTiempExt;
    private String CalaDescEspecieCHI;
    private String CalaUMTiemMaximoExt;
    private String CalaUMTiemMinEntreExt;
    private boolean EspeUMExtValido;
    private boolean CalaUMTiemMaxValido;
    private boolean CalaUMTMinEntreValido;
    private double EspeMiliLimMinimo;
    private double EspeMiliLimMaximo;
    private double CalaTiemMaximo;
    private double CalaMiliTiemMaximo;
    private double CalaTiemMinEntre;
    private double CalaMiliTiemMinEntre;
    private String  CalaDescUMPescaDeclDesc;
    private String CalaDescUMPescaDecl;

    public String getCalaDescUMPescaDecl() {
        return CalaDescUMPescaDecl;
    }

    public void setCalaDescUMPescaDecl(String calaDescUMPescaDecl) {
        CalaDescUMPescaDecl = calaDescUMPescaDecl;
    }

    public String getCalaDescUMPescaDeclDesc() {
        return CalaDescUMPescaDeclDesc;
    }

    public void setCalaDescUMPescaDeclDesc(String calaDescUMPescaDeclDesc) {
        CalaDescUMPescaDeclDesc = calaDescUMPescaDeclDesc;
    }

    public double getCalaMiliTiemMinEntre() {
        return CalaMiliTiemMinEntre;
    }

    public void setCalaMiliTiemMinEntre(double calaMiliTiemMinEntre) {
        CalaMiliTiemMinEntre = calaMiliTiemMinEntre;
    }

    public double getCalaTiemMinEntre() {
        return CalaTiemMinEntre;
    }

    public void setCalaTiemMinEntre(double calaTiemMinEntre) {
        CalaTiemMinEntre = calaTiemMinEntre;
    }

    public double getCalaMiliTiemMaximo() {
        return CalaMiliTiemMaximo;
    }

    public void setCalaMiliTiemMaximo(double calaMiliTiemMaximo) {
        CalaMiliTiemMaximo = calaMiliTiemMaximo;
    }

    public double getCalaTiemMaximo() {
        return CalaTiemMaximo;
    }

    public void setCalaTiemMaximo(double calaTiemMaximo) {
        CalaTiemMaximo = calaTiemMaximo;
    }

    public double getEspeMiliLimMinimo() {
        return EspeMiliLimMinimo;
    }

    public void setEspeMiliLimMinimo(double espeMiliLimMinimo) {
        EspeMiliLimMinimo = espeMiliLimMinimo;
    }

    public double getEspeMiliLimMaximo() {
        return EspeMiliLimMaximo;
    }

    public void setEspeMiliLimMaximo(double espeMiliLimMaximo) {
        EspeMiliLimMaximo = espeMiliLimMaximo;
    }

    public boolean isEspeUMExtValido() {
        return EspeUMExtValido;
    }

    public void setEspeUMExtValido(boolean espeUMExtValido) {
        EspeUMExtValido = espeUMExtValido;
    }

    public boolean isCalaUMTiemMaxValido() {
        return CalaUMTiemMaxValido;
    }

    public void setCalaUMTiemMaxValido(boolean calaUMTiemMaxValido) {
        CalaUMTiemMaxValido = calaUMTiemMaxValido;
    }

    public boolean isCalaUMTMinEntreValido() {
        return CalaUMTMinEntreValido;
    }

    public void setCalaUMTMinEntreValido(boolean calaUMTMinEntreValido) {
        CalaUMTMinEntreValido = calaUMTMinEntreValido;
    }

    public String getEspeUniMedTiempExt() {
        return EspeUniMedTiempExt;
    }

    public void setEspeUniMedTiempExt(String espeUniMedTiempExt) {
        EspeUniMedTiempExt = espeUniMedTiempExt;
    }

    public String getCalaUMTiemMaximoExt() {
        return CalaUMTiemMaximoExt;
    }

    public void setCalaUMTiemMaximoExt(String calaUMTiemMaximoExt) {
        CalaUMTiemMaximoExt = calaUMTiemMaximoExt;
    }

    public String getCalaUMTiemMinEntreExt() {
        return CalaUMTiemMinEntreExt;
    }

    public void setCalaUMTiemMinEntreExt(String calaUMTiemMinEntreExt) {
        CalaUMTiemMinEntreExt = calaUMTiemMinEntreExt;
    }

    public String getCalaDescEspecieCHI() {
        return CalaDescEspecieCHI;
    }

    public void setCalaDescEspecieCHI(String calaDescEspecieCHI) {
        CalaDescEspecieCHI = calaDescEspecieCHI;
    }

    public List<HashMap<String, Object>> getListar_ConsCombEve() {
        return Listar_ConsCombEve;
    }

    public void setListar_ConsCombEve(List<HashMap<String, Object>> listar_ConsCombEve) {
        Listar_ConsCombEve = listar_ConsCombEve;
    }

    public List<HashMap<String, Object>> getList_EventosPesca() {
        return List_EventosPesca;
    }

    public void setList_EventosPesca(List<HashMap<String, Object>> list_EventosPesca) {
        List_EventosPesca = list_EventosPesca;
    }
}
