package com.incloud.hcp.util;

import com.incloud.hcp.jco.consultaGeneral.service.JCOConsultaGeneralService;
import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.reportepesca.dto.MaestroOptionsDescarga;
import com.sap.conn.jco.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Metodos {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JCODominiosService jcoDominiosService;

    @Autowired
    private JCOConsultaGeneralService jcoConsultaGeneralService;

    public List<HashMap<String, Object>> ListarObjetos(JCoTable tableExport) throws Exception {

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = tableExport.getValue(key);


                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    value = dateFormat.format(value);
                    if(String.valueOf(value).equalsIgnoreCase("00:00")){
                        value = "";
                    }
                }
                if(key.equals("DSMIN")) {
                    value = value.toString();
                }

                try {
                    if(key.equals("LNMAX") || key.equals("LNMIN") ||key.equals("LTMAX") ||key.equals("LTMIN") ){
                        String valor=value.toString();
                        logger.error("valor= "+valor);
                        valor=valor.substring(0,3)+"°"+valor.substring(3,valor.length());
                        logger.error("valor= "+valor);
                        value=valor.substring(0,6)+"'";
                        logger.error("value= "+value);
                    }
                    if (field.getTypeAsString().equals("DATE")) {

                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    }

                    /*if(field.getTypeAsString().equals("BCD")){
                        String strValue = String.valueOf(value);
                        value = strValue;
                    }*/


                    /*if(field.getTypeAsString().equals("DEC")){
                        BigDecimal val = new BigDecimal(String.valueOf(value));
                        value = val.setScale(3, RoundingMode.HALF_UP);

                    }*/


                }catch (Exception e){
                   // value=String.valueOf(value);
                    value="-";

                }
                newRecord.put(key, value);

                if(key.equals("INPRP") || key.equals("ESREG") ||key.equals("WAERS") || key.equals("ESCSG")|| key.equals("ESPRC")
                        || key.equals("CALIDA")|| key.equals("CDLDS")|| key.equals("ESDES")|| key.equals("ESPRO")|| key.equals("CDTPC")||
                        key.equals("CDFAS")||key.equals("CDMMA")|| key.equals("ESRNV")|| key.equals("ESVVI")){
                    HashMap<String, Object>dominio=BuscarNombreDominio(key, value.toString());
                    for (Map.Entry<String, Object> entry:dominio.entrySet() ){
                        String campo=entry.getKey();
                        Object valor=entry.getValue();
                        newRecord.put(campo, valor);
                    }
                }
            }
            data.add(newRecord);
        }

        return data;
    }

    public List<DominioExportsData> obtenerAlmacen() throws Exception{

        String[] optionsCentro={"APLICACION EQ 'FL' AND PROGRAMA EQ 'ZFL_RFC_GEST_ACEITES' AND CAMPO EQ 'WERKS'"};
        String centro=getFieldData("ZTB_CONSTANTES",optionsCentro,"LOW");
        String[] optionPlanta={"WERKS EQ '"+centro+"'"};
        String planta= getFieldData("ZFLPTA",optionPlanta,"CDPTA");

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        String[] fieldname = {"CDALM","DSALM"};
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("QUERY_TABLE", "ZFLALM");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");
        importx.setValue("P_ORDER", "DSALM ASCENDING");

        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();

        tableImport.setValue("WA", "CDPTA EQ '" + planta + "' AND ESREG EQ 'S'");
        stfcConnection.execute(destination);
        JCoTable lis_out = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");
        List<DominioExportsData> listDatas = new ArrayList<>();

        for (int i = 0; i < lis_out.getNumRows(); i++) {
            lis_out.setRow(i);
            DominioExportsData data = new DominioExportsData();
            String ArrayResponse[] = lis_out.getString().split("\\|");
            for (int j = 0; j < FIELDS.getNumRows(); j++) {
                FIELDS.setRow(j);
                String key = (String) FIELDS.getValue("FIELDNAME");

                if (key.equals(fieldname[0])) {

                    Object value = ArrayResponse[j];
                    data.setId(value.toString().trim());


                }
                if (key.equals(fieldname[1])) {
                    Object values = ArrayResponse[j];
                    data.setDescripcion(values.toString().trim());
                }


            }
            listDatas.add(data);
        }
        return listDatas;
    }
    public List<HashMap<String, Object>> ListarObjetosDIR(JCoTable tableExport) throws Exception {

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();

                String key = (String) field.getName();
                Object value = tableExport.getValue(key);

                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }

                if (field.getTypeAsString().equals("DATE") && key.equals("FEARR")) {
                    try {
                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    } catch (Exception e) {
                        value = "";
                    }

                }
                newRecord.put(key, value);
            }
            data.add(newRecord);
            if (newRecord.containsKey("ESMAR")) {
                if (newRecord.containsValue("C") || newRecord.containsValue("A")) {
                    data.add(newRecord);
                }
            }
        }

        return data;
    }

    public String returnTable(String table) {
        String tablita = "";
        if (table.equals("MONEDA")) {
            tablita = "ZFLMND";
        }
        if (table.equals("ALMACEN")) {
            tablita = "ZFLALM";
        }
        if (table.equals("UBICPLANTA")) {
            tablita = "ZFLUPT";
        }
        if (table.equals("LITORAL")) {
            tablita = "ZFLZLT";
        }else if (table.equals("GRUPOEMPRESA")) {
            tablita = "ZFLGRE";

        } else if (table.equals("INCIDENTE")) {
            tablita = "ZFLINC";

        } else if (table.equals("LITORAL")) {
            tablita = "ZFLZLT";

        } else if (table.equals("CLASEAVISOMANT")) {
            tablita = "TQ80_T";

        } else if (table.equals("ORGCOMPRAS")) {
            tablita = "T024E";

        } else if (table.equals("UNIDADMEDIDA")) {
            tablita = "ZFLUMD";

        } else if (table.equals("ORGVENTAS")) {
            tablita = "TVKOT";

        } else if (table.equals("CANALDISTRIBUCION")) {
            tablita = "TVTWT";

        } else if (table.equals("ORIGENDATOS")) {
            tablita = "ZFLORD";

        } else if (table.equals("SISTPESCA")) {
            tablita = "ZFLSPE";
        } else if (table.equals("GRUPFLOTA")) {
            tablita = "ZFLGFL";
        } else if (table.equals("GRUPCAPACIDAD")) {
            tablita = "ZFLGCP";
         } else if (table.equals("BODEGA")) {
            tablita = "ZFLBOD";
        } else if (table.equals("TEMPORADA")) {
            tablita = "ZFLTPO";

        } else if (table.equals("SUMINISTRO")) {

            tablita = "ZFLSUM";

        } else if (table.equals("1ZONAPESCA")) {
            tablita = "ZFLZPC";

        } else if (table.equals("REPERCUSION")) {
            tablita = "ZV_FLRE";

        } else if (table.equals("SINIESTRO")) {
            tablita = "ZFLINC";

        } else if (table.equals("ACCIDENTE")) {
            tablita = "ZFLINC";

        } else if (table.equals("ENCUBICACION")) {
            tablita = "ZFLUBI";

        } else if (table.equals("TIPOEMBARCACION")) {
            tablita = "ZFLTEM";

        } else if (table.equals("ZONALITORAL")) {
            tablita = "ZFLZLT";

        } else if (table.equals("PLANTAPROPIA")) {
            tablita = "ZV_FLPA";

        } else if (table.equals("UBICPLANTA")) {
            tablita = "ZFLUPT";

        } else if (table.equals("MONEDASAP")) {
            tablita = "ZFLMND";

        } else if (table.equals("ESPECIE")) {
            tablita = "ZFLSPC";

        } else if (table.equals("CATEGORIA")) {
            tablita = "ZFLCNS";

        } else if (table.equals("1ZONAAREA")) {
            tablita = "ZFLZAR";
        }
        else if (table.equals("SISTVIRADO")) {
            tablita = "ZTBC_DATA";

        }
        else if (table.equals("FASE")) {
            tablita = "ZFLFAS";
        }
        else if (table.equals("MATERIAL")) {
            tablita = "ZTB_CONSTANTES";
        }
        else if (table.equals("TIPOMATERIAL")) {
            tablita = "ZTB_CONSTANTES";
        }
        else if(table.equals("CENTRO")){
            tablita = "ZTB_CONSTANTES";
        }
        return tablita;
    }

    public String returnWA(String table) {
        String wa = "";
        if (table.equals("MONEDA") || table.equals("ESPECIE") || table.equals("UBICPLANTA") || table.equals("MONEDASAP") || table.equals("1ZONALITORAL") || table.equals("TIPOEMBARCACION") || table.equals("GRUPCAPACIDAD") || table.equals("BODEGA") || table.equals("TEMPORADA") || table.equals("SUMINISTRO") || table.equals("1ZONAPESCA") || table.equals("ORIGENDATOS") || table.equals("SISTPESCA") || table.equals("GRUPFLOTA") || table.equals("UBICPLANTA") || table.equals("LITORAL") || table.equals("INCIDENTE") ||table.equals("UNIDADMEDIDA")) {
            wa = "ESREG = 'S'";
        }else if(table.equals("CLASEAVISOMANT") || table.equals("REPERCUSION") ||table.equals("ORGVENTAS") || table.equals("CANALDISTRIBUCION")){
            wa= "SPRAS EQ 'S'";
        }else if(table.equals("ORGVENTAS")){
            wa= "SPRAS EQ 'S'";
        }else if(table.equals("SINIESTRO")){
            wa = "ESREG = 'S' AND CDTIN = 'S'";
        }else if(table.equals("ACCIDENTE")){
            wa = "ESREG = 'S' AND CDTIN = 'A' ";
        }else if(table.equals("ENCUBICACION")){
            wa = "CDUBI BETWEEN '1' AND '999'";
        }else if(table.equals("PLANTAPROPIA")){
            wa= "ESREG = 'S' AND (WERKS <> 'FP09') AND (INPRP = 'P')";
        }else if(table.equals("CATEGORIA")){
            wa= "DESCR = 'CATEGORIA PESCA COMPETENCIA'";
        }else if(table.equals("1ZONAAREA")){
            wa = "ZESZAR = 'S'";
        }else if(table.equals("SISTVIRADO")){
            wa= "CODIG EQ 'SH' AND STATU EQ '1'";
        }
        else if(table.equals("FASE")){
            wa= "ESREG = 'S'";
        }else if(table.equals("MATERIAL")){
            wa= "APLICACION EQ 'FL' AND (PROGRAMA EQ 'ZFL_RFC_GEST_ACEITES') AND (CORR EQ '0002')";
        }else if(table.equals("TIPOMATERIAL")){
            wa= "APLICACION EQ 'FL' AND (PROGRAMA EQ 'ZFL_RFC_GEST_ACEITES') AND (CORR EQ '0003')";
        }else if(table.equals("CENTRO")){
            wa="APLICACION EQ 'FL' AND PROGRAMA EQ 'ZFL_RFC_GEST_ACEITES' AND CAMPO EQ 'WERKS'";
        }

        return wa;
    }

    public String[] returnField(String table) {

        String[] fields = null;
        if (table.equals("UBICPLANTA")) {
            fields = new String[]{"CDUPT", "DSUPT"};
        }else if (table.equals("MONEDA")) {
            fields = new String[]{"CDMND", "DSMND"};
        }else if (table.equals("LITORAL")) {
            fields = new String[]{"CDZLT", "DSZLT"};
        }else if (table.equals("GRUPOEMPRESA")){
            fields= new String[]{"CDGRE","DSGRE"};
        }
        else if (table.equals("INCIDENTE")){
            fields= new String[]{"CDINC", "DSINC"};
        }
        else if (table.equals("CLASEAVISOMANT")){
            fields= new String[]{"CDGRE","DSGRE"};
        }
        else if (table.equals("GRUPOEMPRESA")){
            fields= new String[]{"QMART", "QMARTX"};
        }
        else if (table.equals("ORGCOMPRAS")){
            fields= new String[]{"EKORG", "EKOTX"};
        }
        else if (table.equals("UNIDADMEDIDA")){
            fields= new String[]{"CDUMD", "DSUMD"};
        }
        else if (table.equals("ORGVENTAS")){
            fields= new String[]{"VKORG", "VTEXT"};
        }
        else if (table.equals("CANALDISTRIBUCION")){
            fields= new String[]{"VTWEG", "VTEXT"};
        }
        else if (table.equals("ORIGENDATOS")){
            fields= new String[]{"CDORD", "DSCRP"};
        }
        else if (table.equals("SISTPESCA")){
            fields= new String[]{"CDSPE", "DSSPE"};
        }
        else if (table.equals("GRUPFLOTA")){
            fields= new String[]{"CDGFL", "DSGFL"};
        }
        else if (table.equals("GRUPCAPACIDAD")){
            fields= new String[]{"CDGCP", "DSGCP"};
        } else if (table.equals("BODEGA")) {
            fields = new String[] {"CDBOD", "DSBOD"};
        }else if (table.equals("TEMPORADA")) {
            fields = new String[] {"CDTPO", "DSTPO"};
        } else if (table.equals("SUMINISTRO")) {
            fields = new String[] {"CDSUM", "DSSUM"};
        } else if (table.equals("1ZONAPESCA")) {
            fields = new String[] {"CDZPC", "DSZPC"};
        } else if (table.equals("REPERCUSION")) {
            fields = new String[] {"AUSWK", "AUSWKT"};
        } else if (table.equals("SINIESTRO")) {
            fields = new String[] {"CDINC", "DSINC"};
        } else if (table.equals("ACCIDENTE")) {
            fields = new String[] {"CDINC", "DSINC"};
        } else if (table.equals("ENCUBICACION")) {
            fields = new String[] {"CDUBI", "DSUBI"};
        } else if (table.equals("TIPOEMBARCACION")) {
            fields = new String[] {"CDTEM", "DESCR"};
        } else if (table.equals("1ZONALITORAL")) {
            fields = new String[] {"CDZLT", "DSZLT"};
        } else if (table.equals("PLANTAPROPIA")) {
            fields = new String[] {"CDPTA", "DESCR"};
        } else if (table.equals("UBICPLANTA")) {
            fields = new String[] {"CDUPT", "DSUPT"};
        } else if (table.equals("MONEDASAP")) {
            fields = new String[] {"MDEXT", "DSMND"};
        } else if (table.equals("ESPECIE")) {
            fields = new String[] {"CDSPC", "DSSPC"};
        } else if (table.equals("CATEGORIA")) {
            fields = new String[] {"VAL01", "VAL02"};
        } else if (table.equals("1ZONAAREA")) {
            fields = new String[] {"ZCDZAR", "ZDSZAR"};
        }
        else if (table.equals("SISTVIRADO")) {
            fields = new String[] {"ARGUM", "DESCR"};
        }
        else if (table.equals("ALLPLANTAPROPIA")) {
            fields = new String[] {"CDPTA", "DESCR"};
        }
        else if (table.equals("FASE")) {
            fields = new String[] {"CDFAS", "DSFAS"};
        }
        else if (table.equals("MATERIAL")) {
            fields = new String[] {"CAMPO", "LOW"};
        }
        else if (table.equals("TIPOMATERIAL")) {
            fields = new String[] {"LOW", "LOW"};
        }else if(table.equals("CENTRO")){
            fields= new String[] {"LOW","LOW"};
        }

        return fields;
    }

    public List<HashMap<String, Object>> ValidarOptions(List<MaestroOptions> option ,List<MaestroOptionsKey> options){
        return ValidateOptions(option,options,"WA");
    }

    public List<HashMap<String, Object>> ValidarOptions(List<MaestroOptions> option ,List<MaestroOptionsKey> options,String optionMane){
        return ValidateOptions(option,options,optionMane);
    }

    public List<ListaWA> GeneraCadena(List<MaestroOptions> option ,List<MaestroOptionsKey> options, String optionName) {

    logger.error("ENTRE METODO GENERAR");
        String control = "";
        String abrir="";
        String cerrar="";
        String cerrarFinal="";
        List<ListaWA> tmpOptions = new ArrayList<ListaWA>();
        List<MaestroOptionsKey> sorted= options.stream().sorted(Comparator.comparing(l->l.getKey())).collect(Collectors.toList());
        for(int i=0;i<sorted.size();i++){
            ListaWA objeto = new ListaWA();
            if(sorted.size()<1){
                abrir="(";
                cerrar=")";
            }
            if(sorted.size()>1){
                abrir="(";
                cerrar="";
                cerrarFinal=")";
            }
            if (i < 1)
            {
                switch (sorted.get(i).getControl()){
                    case "COMBOBOX":
                        control = "=";
                        objeto.setClave(optionName);
                        objeto.setValor(sorted.get(i).getKey() + " " + control + " '" + sorted.get(i).getValueLow());
                        break;
                    case "INPUT":
                        control = "LIKE";
                        objeto.setClave(optionName);
                        objeto.setValor(sorted.get(i).getKey()+" "+control+" '%"+sorted.get(i).getValueLow()+"%'");
                        break;
                    case "RANGEINPUT":
                        if(sorted.get(i).getValueHigh().equals("")){
                            control="=";
                            objeto.setClave(optionName);
                            objeto.setValor(sorted.get(i).getKey()+" "+control+" "+"'"+sorted.get(i).getValueLow()+"'");
                        }else{
                            control="BETWEEN";
                            objeto.setClave(optionName);
                            objeto.setValor(sorted.get(i).getKey()+" "+control+" '"+sorted.get(i).getValueLow()+"'"+" AND "+sorted.get(i).getValueHigh()+"'");
                        }
                        break;
                    case "MULTIINPUT":
                        control="EQ";
                        objeto.setClave(optionName);
                        objeto.setValor(sorted.get(i).getKey()+" "+control+" '"+sorted.get(i).getValueLow()+"'");
                        break;
                    case "MULTICOMBOBOX":
                        control="EQ";
                        objeto.setClave(abrir+optionName);
                        objeto.setValor(sorted.get(i).getKey()+" "+control+" '"+sorted.get(i).getValueLow()+"'"+cerrar);
                        break;

                }
            }
            if(i>0){
                switch (sorted.get(i).getControl()) {
                    case "COMBOBOX":
                        for (int k = 0; k < i; k++) {
                            if (sorted.get(i).getKey().equals(sorted.get(k).getKey())) {
                                objeto.setClave(optionName);
                                objeto.setValor("OR " + sorted.get(i).getKey() + " " + "= " + "'" + sorted.get(i).getValueLow() + "'");
                            } else {
                                objeto.setClave(optionName);
                                objeto.setValor("AND " + sorted.get(i).getKey() + " " + "= '" + sorted.get(i).getValueLow() + "'");
                            }
                        }
                        break;
                    case "INPUT":
                        for (int k = 0; k < i; k++) {
                            if (sorted.get(i).getKey().equals(sorted.get(k).getKey())) {
                                objeto.setClave(optionName);
                                objeto.setValor("OR " + sorted.get(i).getKey() + " " + "LIKE " + "'%" + sorted.get(i).getValueLow() + "%'");
                            } else {
                                objeto.setClave(optionName);
                                objeto.setValor("AND " + sorted.get(i).getKey() + " " + "LIKE " + "'%" + sorted.get(i).getValueLow() + "%'");
                            }
                        }
                        break;
                    case "RANGEINPUT":
                        for (int k = 0; k < i; k++) {
                            if (sorted.get(i).getKey().equals(sorted.get(k).getKey())) {
                                if (sorted.get(i).getValueHigh().equals("")) {
                                    objeto.setClave(optionName);
                                    objeto.setValor("OR " + sorted.get(i).getKey() + " " + "= " + "'" + sorted.get(i).getValueLow() + "'");
                                } else {
                                    objeto.setClave(optionName);
                                    objeto.setValor("OR " + sorted.get(i).getKey() + " " + "BETWEEN " + "'" + sorted.get(i).getValueLow() + "'" + " AND " + "'" + sorted.get(i).getValueHigh() + "'");
                                }
                            } else {
                                if(sorted.get(i).getValueHigh().equals("")){
                                    objeto.setClave(optionName);
                                    objeto.setValor("AND "+sorted.get(i).getKey()+" "+"= "+"'"+sorted.get(i).getValueLow()+"'");
                                }else{
                                    objeto.setClave(optionName);
                                    objeto.setValor("AND "+sorted.get(i).getKey()+" "+"BETWEEN '"+sorted.get(i).getValueLow()+"' AND "+"'"+sorted.get(i).getValueHigh()+"'");
                                }
                            }
                        }
                        break;
                    case "MULTIINPUT":
                        for(int k=0;k<i;k++){
                            if(sorted.get(i).getKey().equals(sorted.get(k).getKey())){
                                objeto.setClave(optionName);
                                objeto.setValor("OR "+sorted.get(i).getKey()+" "+"EQ "+"'"+sorted.get(i).getValueLow()+"'");
                            }else{
                                objeto.setClave(optionName);
                                objeto.setValor("AND "+sorted.get(i).getKey()+" "+"EQ "+"'"+sorted.get(i).getValueLow()+"'");
                            }
                        }
                        break;
                    case "MULTICOMBOBOX":
                        for(int k=0;k<i;k++){
                            if(sorted.get(i).getKey().equals(sorted.get(k).getKey())){
                                objeto.setClave(optionName);
                                objeto.setValor("OR "+sorted.get(i).getKey()+" "+"EQ "+"'"+sorted.get(i).getValueLow()+"'"+cerrarFinal);
                            }else{
                                objeto.setClave(optionName);
                                objeto.setValor("AND "+sorted.get(i).getKey()+" "+"EQ "+"'"+sorted.get(i).getValueLow()+"'");
                            }
                        }
                        break;

                }

            }
            tmpOptions.add(objeto);
        }


        for (ListaWA obje : tmpOptions
             ) {
            logger.error("CADENOTA "+obje.getClave());
            logger.error("CADENOTA "+obje.getValor());
        }
        return tmpOptions;
    }
    public List<HashMap<String, Object>> ValidateOptions(List<MaestroOptions> option ,List<MaestroOptionsKey> options, String optionName){

        String control="";
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

        if(option.size()>0 && options.size()==0){
            for(int j=0;j<option.size();j++){
                MaestroOptions mop = option.get(j);
                HashMap<String, Object> record2 = new HashMap<String, Object>();
                record2.put(optionName,mop.getWa());
                tmpOptions.add(record2);
            }
        }

        if(option.size() >0 && options.size()>0){
            for(int j=0;j<option.size();j++){
                MaestroOptions mop = option.get(j);
                HashMap<String, Object> record2 = new HashMap<String, Object>();
                record2.put(optionName,mop.getWa());
                tmpOptions.add(record2);
            }

            for (int i = 0; i < options.size(); i++) {
                MaestroOptionsKey mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                if(mo.getControl().equals("INPUT")||mo.getControl().equals("INPUT/NUMERIC"))
                {
                    control="LIKE";
                }
                if (mo.getControl().equals("COMBOBOX")) {
                    control="=";
                }
                if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals("") )){
                    control="BETWEEN";
                }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    control="=";
                }
                if(mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))||mo.getControl().equals("INPUT/NUMERIC") && (mo.getValueHigh().equals(""))){
                    record.put(optionName,"AND"+" "+ mo.getKey() +" "+ control+ " "+ "'%"+mo.getValueLow()+"%'");
                }else if(mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put(optionName,"AND"+" "+ mo.getKey() +" "+ control+ " "+ "'"+mo.getValueLow()+"'");
                }else if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))){
                    record.put(optionName,"AND"+" "+ mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" +" AND "+ "'"+mo.getValueHigh()+"'");
                }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put(optionName,"AND"+" "+ mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" );
                }
                tmpOptions.add(record);

            }

        }

        if(options.size()>0 && option.size()==0) {
            String abrir="(";
            String cerrar=")";
            String cerrarFinal="";
            if(options.size()>1){
                cerrar="";
                cerrarFinal=")";
            }
            for (int i = 0; i < options.size(); i++) {
                MaestroOptionsKey mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                if (mo.getControl().equals("INPUT")||mo.getControl().equals("INPUT/NUMERIC")) {
                    control = "LIKE";
                }
                if (mo.getControl().equals("COMBOBOX")) {
                    control = "=";
                }
                if (mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))) {
                    control = "BETWEEN";
                } else if (mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    control = "=";
                }
                else if (mo.getControl().equals("MULTICOMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    control = "=";
                }

                if (mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))||mo.getControl().equals("INPUT/NUMERIC")&& (mo.getValueHigh().equals(""))) {
                    record.put(optionName, mo.getKey() + " " + control + " " + "'%" + mo.getValueLow().toUpperCase().trim() + "%'");
                } else if (mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    record.put(optionName, mo.getKey() + " " + control + " " + "'" + mo.getValueLow().toUpperCase().trim() + "'");
                } else if (mo.getControl().equals("COMBOBOX") && (mo.getValueLow().equals("") || mo.getValueLow().equals(null))){
                    record.put(optionName, mo.getKey() + " " + control + " " + "'" + mo.getValueHigh() + "'");
                }else if (mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))) {
                    record.put(optionName, mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'" + " AND " + "'" + mo.getValueHigh() + "'");
                } else if (mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    record.put(optionName, mo.getKey() + " " + control + " " + "'" + mo.getValueLow().toUpperCase().trim() + "'");
                }else if (mo.getControl().equals("MULTICOMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    record.put(optionName, abrir+mo.getKey() + " " + control + " " + "'" + mo.getValueLow().toUpperCase().trim() + "'"+cerrar);
                }


                if (i > 0) {
                    if (mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))||mo.getControl().equals("INPUT/NUMERIC")&& (mo.getValueHigh().equals(""))){
                        record.put(optionName, "AND" + " " + mo.getKey() + " " + control + " " + "'%" + mo.getValueLow().toUpperCase().trim() + "%'");
                    } else if (mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                        record.put(optionName, "AND" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueLow().toUpperCase().trim() + "'");
                    }else if (mo.getControl().equals("COMBOBOX") && (mo.getValueLow().equals("") || mo.getValueLow().equals(null))) {
                        record.put(optionName, "AND" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueHigh().toUpperCase().trim() + "'");
                    }else if (mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))) {
                        record.put(optionName, "AND" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueLow().toUpperCase().trim() + "'" + " AND " + "'" + mo.getValueHigh() + "'");
                    } else if (mo.getControl().equals("MULTICOMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                        record.put(optionName, "OR" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueLow().toUpperCase().trim() + "'"+cerrarFinal);
                    }
                }
                tmpOptions.add(record);

            }
        }
        logger.error("arreglo: " +tmpOptions);
        return tmpOptions;
    }
    public List<HashMap<String, Object>> ObtenerListObjetos(JCoTable jcoTable,  String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        if(fields.length>=1){
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                JCoFieldIterator iter = jcoTable.getFieldIterator();
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                while (iter.hasNextField()) {
                    JCoField field = iter.nextField();
                    String key = (String) field.getName();
                    Object value = jcoTable.getValue(key);

                    for (int k = 0; k < fields.length; k++) {
                        logger.error("key: " + key + " k: " + fields[k]);
                        ;

                        if (fields[k].trim().equals(key.trim())) {


                            if (field.getTypeAsString().equals("TIME")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                value = dateFormat.format(value);
                            }
                            try {
                                if (field.getTypeAsString().equals("DATE")) {

                                    String date = String.valueOf(value);
                                    SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                                    String fecha = dia.format(value);
                                    value = fecha;
                                }
                            }catch (Exception e){
                                value=String.valueOf(value);
                            }

                            newRecord.put(key, value);
                            if(key.equals("INPRP") || key.equals("ESREG") ||key.equals("WAERS") || key.equals("ESCSG")|| key.equals("ESPRC")
                                    || key.equals("CALIDA")|| key.equals("CDLDS")|| key.equals("ESDES")|| key.equals("ESPRO")|| key.equals("CDTPC")
                                    || key.equals("CDFAS")||key.equals("CDMMA")|| key.equals("ESRNV")|| key.equals("ESVVI")){
                                HashMap<String, Object>dominio=BuscarNombreDominio(key, value.toString());
                                for (Map.Entry<String, Object> entry:dominio.entrySet() ){
                                    String campo=entry.getKey();
                                    Object valor=entry.getValue();
                                    newRecord.put(campo, valor);
                                }
                            }
                        }
                    }


                }
                data.add(newRecord);
            }
        }else {
            data=ObtenerListObjetosSinField(jcoTable);
        }
        return data;

    }

    public String getFieldData(String tabla,String[] option,String field) throws  Exception{
        JCoDestination destinations3 = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repos3 = destinations3.getRepository();
        ;
        JCoFunction stfcConnections3 = repos3.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importxs3 = stfcConnections3.getImportParameterList();
        importxs3.setValue("DELIMITER","|");
        importxs3.setValue("QUERY_TABLE",tabla);

        JCoParameterList tabless3 = stfcConnections3.getTableParameterList();
        JCoTable tableImports3 = tabless3.getTable("OPTIONS");
        tableImports3.appendRow();
        for(int i=0;i<option.length;i++) {
            tableImports3.setValue("WA", option[i]);
        }
        stfcConnections3.execute(destinations3);

        JCoTable tableExports = tabless3.getTable("DATA");
        JCoTable FIELDS = tabless3.getTable("FIELDS");
        String valor = obtenerDataEventosPescaCadena(tableExports,FIELDS,field);

        return valor;
    }
    public String[] getFieldDataArray(String tabla,String[] option,String[] field) throws  Exception{
        JCoDestination destinations3 = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repos3 = destinations3.getRepository();
        ;
        JCoFunction stfcConnections3 = repos3.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importxs3 = stfcConnections3.getImportParameterList();
        importxs3.setValue("DELIMITER","|");
        importxs3.setValue("QUERY_TABLE",tabla);

        JCoParameterList tabless3 = stfcConnections3.getTableParameterList();
        JCoTable tableImports3 = tabless3.getTable("OPTIONS");
        for(int i=0;i<option.length;i++){
            tableImports3.appendRow();
            tableImports3.setValue("WA", option[i]);
        }
        stfcConnections3.execute(destinations3);

        JCoTable tableExports = tabless3.getTable("DATA");
        JCoTable FIELDS = tabless3.getTable("FIELDS");
        String[] valor = obtenerDataArrayEventosPescaCadena(tableExports,FIELDS,field);

        return valor;
    }
    public List<HashMap<String, Object>> ObtenerListObjetosSinField(JCoTable jcoTable)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < jcoTable.getNumRows(); i++) {
            jcoTable.setRow(i);
            JCoFieldIterator iter = jcoTable.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = jcoTable.getValue(key);

                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }
                try {
                    if (field.getTypeAsString().equals("DATE")) {

                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    }
                }catch (Exception e){
                    value=String.valueOf(value);
                }

                newRecord.put(key, value);
                if(key.equals("INPRP") || key.equals("ESREG") ||key.equals("WAERS") || key.equals("ESCSG")|| key.equals("ESPRC")
                        || key.equals("CALIDA")|| key.equals("CDLDS")|| key.equals("ESDES")|| key.equals("ESPRO")|| key.equals("CDTPC")
                        || key.equals("CDFAS")||key.equals("CDMMA") || key.equals("ESRNV") || key.equals("ESVVI")){
                    HashMap<String, Object>dominio=BuscarNombreDominio(key, value.toString());
                    for (Map.Entry<String, Object> entry:dominio.entrySet() ){
                        String campo=entry.getKey();
                        Object valor=entry.getValue();
                        newRecord.put(campo, valor);
                    }
                }
            }
            data.add(newRecord);
        }
        return data;

    }

    public String ObtenerCampo(JCoTable tableExport, JCoTable FIELDS,String validador){
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");

            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                if(key.equals(validador)){
                    value=ArrayResponse[j].trim();
                    campo=value.toString();

                }
            }

        }
        return campo;
    }
    public List<HashMap<String, Object>> obtenerDataEventosPesca2(JCoTable tableExport, JCoTable FIELDS,String fields[]){
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Constantes c = new Constantes();
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            logger.error("array response: "+ArrayResponse);
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");

                for(int k=0;k<fields.length;k++) {

                    if (key.equals(fields[k])) {
                        value = ArrayResponse[j].trim();
                        campo = value.toString();
                        if(key.equals("CDTEV")){
                            String sufijo = "";
                            if (campo.equals(c.CODTIPOEVENTOZARPE)) {
                                sufijo = "Zarp";
                            } else if (campo.equals(c.CODTIPOEVENTOARRIBO)) {
                                sufijo = "Arri";
                            } else if (campo.equals(c.CODTIPOEVENTODESCARGA)) {
                                sufijo = "Desc";
                            }
                            newRecord.put(sufijo + "ClaseMovComb", ArrayResponse[3]);
                            newRecord.put(sufijo + "MatCombus", ArrayResponse[6]);
                            newRecord.put(sufijo + "UniMedComb", ArrayResponse[5]);
                            newRecord.put(sufijo + "AutorLectHoro", ArrayResponse[4]);
                        }


                    }
                }
            }
            data.add(newRecord);
        }
        return data;
    }
    public List<HashMap<String, Object>> obtenerLectHormoetros(JCoTable tableExport, JCoTable FIELDS,String fields[]){
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Constantes c = new Constantes();
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            logger.error("array response: "+ArrayResponse.length);

            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                logger.error("Entré");

                for(int k=0;k<fields.length;k++) {
                    logger.error("key+"+ key + "field "+ fields[k]);
                    if (key.equals(fields[k])) {
                        try{
                            value = ArrayResponse[j].trim();
                        }catch (Exception e){
                            value="";
                        }


                        logger.error("J"+ j);
                        logger.error("value"+ value);
                        campo = value.toString();
                        newRecord.put(key, campo);
                    }
                }
            }
            data.add(newRecord);
        }
        return data;
    }
    public List<HashMap<String, Object>> obtenerDataEventosPesca(JCoTable tableExport, JCoTable FIELDS,String fields[]){
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                for(int k=0;k<fields.length;k++) {
                    if (key.equals(fields[k])) {
                        value = ArrayResponse[j].trim();
                        campo = value.toString();
                        newRecord.put(key, campo);
                    }
                }
            }
            data.add(newRecord);
        }
        return data;
    }

    public String[] obtenerDataArrayEventosPescaCadena(JCoTable tableExport, JCoTable FIELDS,String[] validador){
        String[] campo=new String[tableExport.getNumRows()];
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");

            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                logger.error("KEYS2021: "+key);
                for(int k=0;k<validador.length;k++){
                    if(key.equals(validador[k])){
                        value=ArrayResponse[j].trim();
                        campo[i]=value.toString();
                    }
                }


            }

        }

        return campo;
    }
    public String[] obtenerHoroEvento(String evento) throws Exception{

        HorometrosExport obj = new HorometrosExport();
        String centro = "TCO";
        String table = "ZFLTHE";
        String[] fields = {"CDTHR"};
        String[] options = {"CDTEV = '"+evento+"'"};
        Metodos me = new Metodos();
        List<HashMap<String, Object>> datas= new ArrayList<HashMap<String, Object>>();
        String data[]= me.getFieldDataArray(table,options,fields);


        return data;
    }
    public String obtenerDataEventosPescaCadena(JCoTable tableExport, JCoTable FIELDS,String validador){
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");

            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                logger.error("KEYS2021: "+key);
                    if(key.equals(validador)){
                        value=ArrayResponse[j].trim();
                        campo=value.toString();
                    }



            }

        }

        return campo;
    }
    public String ConvertirABase64(String fileName)throws IOException {
        File file = new File(fileName);
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.UTF_8);
    }


    public List<MaestroOptions> convertMaestroOptions(List<MaestroOptionsDescarga> options){
        return options.stream().map(o->{
            MaestroOptions maestroOptions=new MaestroOptions();
            maestroOptions.setWa(o.getData());

            return maestroOptions;
        }).collect(Collectors.toList());
    }

    public HashMap<String, Object> BuscarNombreDominio(String campo, String valor)throws Exception{
        String descripcion="";
        String dom="";

        HashMap<String, Object> dominio= new HashMap<>();

        if(campo.equals("CALIDA")){
            campo="DESC_"+campo;
            descripcion=ObtenerCalidad(valor);

        }else{
            if(campo.equals("INPRP") ){
                dom="ZINPRP";
                campo="DESC_"+campo;
            }else
            if(campo.equals("ESREG") ){
                dom="ZESREG";
                campo="DESC_"+campo;
            }
            if(campo.equals("WAERS") ){
                dom="MONEDA";
                campo="DESC_"+campo;
                if(valor.equals("PEN")){
                    valor="00001";
                }else if(valor.equals("USD")){
                    valor="00002";
                }else if(valor.equals("EUR")){
                    valor="00003";
                }
            }
            if(campo.equals("ESCSG")){
                dom="ZESCSG";
                campo="DESC_"+campo;
            }
            if(campo.equals("ESPRC")){
                dom="ZESPRC";
                campo="DESC_"+campo;
            }if(campo.equals("CDLDS")){
                dom="ZCDLDS";
                campo="DESC_"+campo;
            }
            if(campo.equals("ESDES")){
                dom="ZESDES";
                campo="DESC_"+campo;
            }
            if(campo.equals("ESPRO")){
                dom="ZDO_ESREGC";
                campo="DESC_"+campo;
            }
            if(campo.equals("CDTPC")){
                dom="ZCDTPC";
                campo="DESC_"+campo;
            }
            if(campo.equals("CDFAS")){
                dom="FASE";
                campo="DESC_"+campo;
            }
            if(campo.equals("CDMMA")){
                dom="ZCDMMA";
                campo="DESC_"+campo;
            }
            if(campo.equals("ESRNV")){
                dom="ZD_FLESRNV";
                campo="DESC_"+campo;
            }
            if(campo.equals("ESVVI")){
                dom="ZESREG";
                campo="DESC_"+campo;
            }
            descripcion=ObtenerDominio(dom,valor);
       }



        dominio.put(campo, descripcion);

        return dominio;
    }
    public String ObtenerDominio(String nomDominio, String valor)throws Exception{

        String descripcion="";
        try {



            DominioParams dominioParams = new DominioParams();
            dominioParams.setDomname(nomDominio);
            dominioParams.setStatus("A");


            List<DominioParams> ListDominioParams = new ArrayList<>();
            ListDominioParams.add(dominioParams);



            DominiosImports dominiosImports = new DominiosImports();
            dominiosImports.setDominios(ListDominioParams);

            DominioDto dominioDto= new DominioDto();
            dominioDto = ListarDominio(dominiosImports);


            List<DominiosExports> ListaDomExports = dominioDto.getData();

            for (int i = 0; i < ListaDomExports.size(); i++) {

                DominiosExports dominiosExports = ListaDomExports.get(i);
                List<DominioExportsData> ListaDomExportData = dominiosExports.getData();

                for (int j = 0; j < ListaDomExportData.size(); j++) {
                    DominioExportsData dominioExportsData = ListaDomExportData.get(j);

                    if (valor.equals(dominioExportsData.getId())) {
                        descripcion = dominioExportsData.getDescripcion();

                    }
                }

            }
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());

        }
        return descripcion;
    }

    public DominioDto ListarDominio(DominiosImports imports) throws Exception {


        DominioDto domDto = new DominioDto();
        List<DominiosExports> listExports = new ArrayList<>();
        Metodos metodo = new Metodos();
        try {


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            for (DominioParams domParams : imports.getDominios()) {

                if (domParams.getDomname().startsWith("Z")) {
                    DominiosExports exports = new DominiosExports();

                    List<DominioExportsData> listDatas = new ArrayList<>();
                    JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GET_LISTAED);
                    JCoParameterList importx = stfcConnection.getImportParameterList();
                    importx.setValue("STATUS", domParams.getStatus());

                    JCoParameterList tables = stfcConnection.getTableParameterList();
                    JCoTable domtab = tables.getTable(Tablas.DOMTAB);
                    domtab.appendRow();
                    domtab.setValue("DOMNAME", domParams.getDomname());
                    stfcConnection.execute(destination);
                    JCoTable lis_out = tables.getTable(Tablas.LIST_OUT);

                    exports.setDominio(domParams.getDomname());
                    for (int i = 0; i < lis_out.getNumRows(); i++) {

                        lis_out.setRow(i);
                        DominioExportsData data = new DominioExportsData();
                        HashMap<String, Object> newRecord = new HashMap<String, Object>();

                        data.setDescripcion(lis_out.getString("DDTEXT"));
                        data.setId(lis_out.getString("DOMVALUE_L"));
                        listDatas.add(data);
                    }
                    exports.setData(listDatas);
                    listExports.add(exports);
                    domDto.setData(listExports);
                    domDto.setMensaje("Ok");

                } else {
                    /**
                     * Objetos especiales
                     */
                    DominiosExports exports = new DominiosExports();
                    exports.setDominio(domParams.getDomname());

                    List<DominioExportsData> listDatas = new ArrayList<>();

                    if (domParams.getDomname().equals("MOTIVOMAREA_RPDC")) {
                        DominioExportsData data1 = new DominioExportsData();
                        DominioExportsData data2 = new DominioExportsData();
                        data1.setId("A");
                        data1.setDescripcion("Todos");

                        data2.setId("1");
                        data2.setDescripcion("CHD");

                        listDatas.add(data1);
                        listDatas.add(data2);
                    }
                    /**
                     * Leer tablas en base al dominio
                     */
                    else {
                        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
                        JCoParameterList importx = stfcConnection.getImportParameterList();
                        String TABLE_READ_TABLE = metodo.returnTable(domParams.getDomname());
                        String WA_READ_TABLE = metodo.returnWA(domParams.getDomname());
                        String[] fieldname = metodo.returnField(domParams.getDomname());
                        importx.setValue("QUERY_TABLE", TABLE_READ_TABLE);
                        importx.setValue("DELIMITER", "|");
                        importx.setValue("P_USER", "FGARCIA");


                        JCoParameterList tables = stfcConnection.getTableParameterList();
                        JCoTable tableImport = tables.getTable("OPTIONS");
                        tableImport.appendRow();

                        tableImport.setValue("WA", WA_READ_TABLE);
                        stfcConnection.execute(destination);
                        JCoTable lis_out = tables.getTable("DATA");
                        JCoTable FIELDS = tables.getTable("FIELDS");


                        for (int i = 0; i < lis_out.getNumRows(); i++) {
                            lis_out.setRow(i);
                            DominioExportsData data = new DominioExportsData();
                            String ArrayResponse[] = lis_out.getString().split("\\|");
                            for (int j = 0; j < FIELDS.getNumRows(); j++) {
                                FIELDS.setRow(j);
                                String key = (String) FIELDS.getValue("FIELDNAME");

                                if (key.equals(fieldname[0])) {

                                    Object value = ArrayResponse[j];
                                    data.setId(value.toString().trim());


                                }
                                if (key.equals(fieldname[1])) {
                                    Object values = ArrayResponse[j];
                                    data.setDescripcion(values.toString().trim());
                                }


                            }
                            listDatas.add(data);
                        }
                    }

                    exports.setData(listDatas);
                    listExports.add(exports);
                    domDto.setData(listExports);
                    domDto.setMensaje("Ok");


                }
            }


        } catch (Exception e) {
            domDto.setMensaje(e.getMessage());
        }

        return domDto;
    }

    public String ObtenerCalidad(String valor)throws Exception{

        String calidad="";

        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("QUERY_TABLE", "ZFLCNS");
        imports.put("DELIMITER", "|");
        imports.put("P_USER", "FGARCIA");

        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        MaestroOptions mo = new MaestroOptions();
        HashMap<String, Object> record = new HashMap<String, Object>();
        record.put("WA", "CDCNS = '39' OR CDCNS = '40' OR CDCNS = '41'");
        tmpOptions.add(record);

        String[]fields={"VAL01", "VAL02"};

        EjecutarRFC exec= new EjecutarRFC();
        MaestroExport me=exec.Execute_ZFL_RFC_READ_TABLE(imports,tmpOptions,fields);

        List<HashMap<String, Object>> data=me.getData();
        boolean b=false;
        for (int i=0; i<data.size();i++){
            HashMap<String, Object> d=data.get(i);


            for (Map.Entry<String, Object> entry :d.entrySet()) {
                String key=entry.getKey();
                String value=entry.getValue().toString();
                if(key.equals("VAL01") && value.equals(valor)){
                    b=true;
                }
                if(b==true && key.equals("VAL02")){
                    calidad=value;
                }
            }
        }

        return calidad;
    }

    public String ConvertirFecha(JCoTable jCoTable, String tabla){

        String fecha="";

        try {
            JCoField fieldF = jCoTable.getField(tabla);
            Date date=fieldF.getDate();
            SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
            fecha = dia.format(date);
        }catch (Exception e){
            fecha="";
        }


        return fecha;
    }
    public String ConvertirHora(JCoTable jCoTable, String tabla){

        String hora="";
        try {
            JCoField fieldH = jCoTable.getField(tabla);
            Date time = fieldH.getTime();
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hora = hour.format(time);
        }catch (Exception e){
            hora="";
        }

        return hora;
    }
}
