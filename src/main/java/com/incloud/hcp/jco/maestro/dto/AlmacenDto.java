package com.incloud.hcp.jco.maestro.dto;

public class AlmacenDto {
    public String CodPlanta;        //CDALM
    public String CodAlmacen;       //CDPTA
    public String Descripcion;      //DSALM
    public String Centro;           //WERKS
    public String CuentaAsoc;       //NEWKO
    public String AlmacenExt;       //CDALE
    public String EstadoReg;        //ESREG

    public String getCodPlanta() { return CodPlanta;  }

    public void setCodPlanta(String codPlanta) {CodPlanta = codPlanta;}

    public String getCodAlmacen() {return CodAlmacen;  }

    public void setCodAlmacen(String codAlmacen) {CodAlmacen = codAlmacen;}

    public String getDescripcion() {return Descripcion;}

    public void setDescripcion(String descripcion) {Descripcion = descripcion;}

    public String getCentro() {return Centro;}

    public void setCentro(String centro) {Centro = centro; }

    public String getAlmacenExt() {return AlmacenExt;}

    public void setAlmacenExt(String almacenExt) {AlmacenExt = almacenExt;}

    public String getEstadoReg() {return EstadoReg;}

    public void setEstadoReg(String estadoReg) {EstadoReg = estadoReg;}

    public String getCuentaAsoc() {
        return CuentaAsoc;
    }

    public void setCuentaAsoc(String cuentaAsoc) {
        CuentaAsoc = cuentaAsoc;
    }
}
