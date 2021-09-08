package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.reportepesca.dto.BiometriaExports;
import com.incloud.hcp.jco.reportepesca.dto.BiometriaImports;
import com.incloud.hcp.jco.reportepesca.service.JCOBiometriaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import javafx.scene.control.Tab;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.codec.binary.Base64;

@Service
public class JCOBiometriaImpl implements JCOBiometriaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BiometriaExports ReporteBiometria(BiometriaImports imports)throws Exception{

        BiometriaExports dto= new BiometriaExports();

        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_BIOM);

            HashMap<String, Object> importParams = new HashMap<>();
            importParams.put("IP_OPER", imports.getIp_oper());
            importParams.put("IP_CDMMA", imports.getIp_cdmma());

            JCoParameterList importx = function.getImportParameterList();

            EjecutarRFC executeRFC = new EjecutarRFC();
            executeRFC.setImports(function, importParams);
            executeRFC.setTable(importx, Tablas.IT_MAREA, imports.getIt_marea());
            logger.error("LOG IMPORT TABLE");
            JCoParameterList paramExport = function.getExportParameterList();
            function.execute(destination);
            logger.error("LOG EXECUTE FUNCTION");
            JCoTable ET_BIOM = paramExport.getTable(Tablas.ET_BIOM);
            JCoTable ET_ESPE = paramExport.getTable(Tablas.ET_ESPE);
            JCoTable ET_PSCINC = paramExport.getTable(Tablas.ET_PSCINC);
            double min=Double.parseDouble(paramExport.getValue("EP_MMIN").toString());
            double max=Double.parseDouble(paramExport.getValue("EP_MMAX").toString());
            logger.error("LOG CONVERTER DOUBLE: "+ min + ", "+ max);
            dto.setEp_mmin(paramExport.getValue("EP_MMIN").toString());
            dto.setEp_mmax(paramExport.getValue("EP_MMAX").toString());

            Metodos metodos = new Metodos();
            List<HashMap<String, Object>> et_biom = metodos.ListarObjetos(ET_BIOM);
            List<HashMap<String, Object>> et_espe = metodos.ListarObjetos(ET_ESPE);
            List<HashMap<String, Object>> et_pscinc = metodos.ListarObjetos(ET_PSCINC);

            String path=CrearExcel(ET_BIOM, ET_ESPE, min, max, imports.getIp_cdmma());
            logger.error("LOG CONVERTER DOUBLE");
            String base64=ConvertirABase64(path);
            dto.setEt_biom(et_biom);
            dto.setEt_espe(et_espe);
            dto.setEt_pscinc(et_pscinc);
            dto.setMensaje("OK");
            dto.setBase64(base64);
        }catch (Exception e){

            dto.setMensaje(e.getMessage());
        }
        return dto;
    }

    private String CrearExcel(JCoTable et_biom, JCoTable et_espe, double min, double max, String codigoMotivoMarea){
        String path="";
        Workbook workbook = new HSSFWorkbook();
        //Crea hoja nueva
        Sheet sheet = workbook.createSheet("Reporte Biometria");


        String[]CellMedidas= CellMedidas(min, max, codigoMotivoMarea);
        Object[] fields= ListarFields(et_biom, CellMedidas);
        String[] field= ListarFields(et_biom, CellMedidas);

        List<String[]> registros=ListarRegistros(et_biom, CellMedidas);
        List<String[]> medidas=ListarMedidas(et_espe);
        List<String[]> registrosTotal=registrosTotal(registros, medidas, field);
        String []a=registrosTotal.get(0);
        for(int i=0; i<a.length; i++){
            logger.error("registrosTotal: "+a[i]);
        }

        Map<String, Object[]> datos = new TreeMap<String, Object[]>();
        datos.put("1", fields);

        int keys=2;
        for(int i=0; i<registrosTotal.size(); i++){
            Object[] obj= registros.get(i);

            datos.put(String.valueOf(keys), obj);
            keys++;
        }


        //Iterar sobre datos para escribir en la hoja
        Set keyset = datos.keySet();
        int numeroRenglon = 0;
        for (Object key : keyset) {
            Row row = sheet.createRow(numeroRenglon++);

            Object[] arregloObjetos = datos.get(key);
            int numeroCelda = 0;
            for (Object obj : arregloObjetos) {
                Cell cell = row.createCell(numeroCelda++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);

                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }

        try {
            //Se genera el documento
            path=Constantes.RUTA_ARCHIVO_IMPORTAR + "/Reporte.xlsx";
            FileOutputStream out = new FileOutputStream(new File(path));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    private String ConvertirABase64(String fileName)throws IOException {
        File file = new File(fileName);
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private String[] ListarFields(JCoTable jcoTable, String[] CellMedidas){

        JCoFieldIterator iter = jcoTable.getFieldIterator();
        int con=0;
        String[] f=new String[100];
        while (iter.hasNextField()) {
            JCoField field = iter.nextField();
            f[con]=field.getName();
            con++;
        }
        int cantidadFields=con+CellMedidas.length;
        String[] fields= new String[cantidadFields];
        for(int i=0; i<con; i++){
            fields[i]=jcoTable.getField(i).getName();

        }
        for(int j=0; j<CellMedidas.length; j++){

            fields[con]=CellMedidas[j];
            con++;
        }

        return fields;

    }

    private List<String[]> ListarRegistros(JCoTable jcoTable , String []CellMedidas){

        List<String[]> campos= new ArrayList<String[]>();
        JCoFieldIterator iter = jcoTable.getFieldIterator();

        int con=0;
        while (iter.hasNextField()) {
            JCoField field = iter.nextField();
            con++;
        }
        int cantidadRegistros=con+CellMedidas.length;;
        for (int i = 0; i < jcoTable.getNumRows(); i++) {
            jcoTable.setRow(i);
            String[] registros = new String[cantidadRegistros];
            JCoFieldIterator ite = jcoTable.getFieldIterator();
            int j=0;
            while (ite.hasNextField()) {
                JCoField field = ite.nextField();
                String key = (String) field.getName();
                String value ="";
                try {
                    value = jcoTable.getValue(key).toString();
                }catch (Exception e){
                    value="";
                }

                if (key.equals("HIEVN") || key.equals("HFEVN")) {
                    value = value.substring(11,19);
                }/*
                if (field.getTypeAsString().equals("DATE")) {
                    String date = String.valueOf(value);
                    SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                    String fecha = dia.format(value);
                    value = fecha;
                }*/
                registros[j] = value;
                j++;
            }
            campos.add(registros);
        }



        return campos;
    }

    private String[] CellMedidas(double min, double max, String codigoMotivoMarea){


        String[] celdas= new String[10];
        double prom=(max-min)+1;
        int con=0;
        int cantidadCeldas=0;

        if(codigoMotivoMarea.compareTo("2")==0){

           for(double i=0; i<prom; i+=0.5){
                con++;
           }
            cantidadCeldas=con+2;
            celdas= new String[cantidadCeldas];
            celdas[0]="DSSPCI";
            celdas[1]="PCSPC";
            int n=2;
            double cel=min;
            for (int j=0; j<con; j++) {
                celdas[n] = String.valueOf(cel);
                cel += 0.5;
                n++;
            }

            return celdas;


        }else if(codigoMotivoMarea.compareTo("1")==0){
            for(int i=0; i<prom; i++){
                con++;
            }
            cantidadCeldas=con+2;
            celdas= new String[cantidadCeldas];
            celdas[0]="DSSPCI";
            celdas[1]="PCSPC";
            int n=2;
            double cel=min;
            for (int j=0; j<con; j++) {
                celdas[n] = String.valueOf(cel);

                cel ++;
                n++;

            }

            return celdas;
        }
       return celdas;
    }

    private List<String[]> ListarMedidas(JCoTable jcoTable){

        logger.error("Listar medidas");
        List<String[]> campos= new ArrayList<String[]>();



        for (int i = 0; i < jcoTable.getNumRows(); i++) {
            jcoTable.setRow(i);
            String[] registros = new String[4];
            JCoFieldIterator ite = jcoTable.getFieldIterator();
            int j=0;
            while (ite.hasNextField()) {
                JCoField field = ite.nextField();
                String key = (String) field.getName();
                String value ="";
                try {
                    value = jcoTable.getValue(key).toString();
                }catch (Exception e){
                    value="";
                }
                if(key.compareTo("NRMAR")==0 ||key.compareTo("NREVN")==0 ||
                        key.compareTo("TMMED")==0 ||key.compareTo("CNSPC")==0 ){

                    registros[j] = value;
                    j++;
                }
            }
            campos.add(registros);
        }



        return campos;
    }

    private List<String[]> registrosTotal(List<String[]> et_biom, List<String[]> et_espe, String[]fields){

        List<String[]> registrosTotal= new ArrayList<String[]>();

        for(int i=0; i<et_biom.size(); i++){
            /**ET_BIOM posicion:
             * NRMAR : 3
             * NREVN:15
             * ET_ESPE posicion:
             * NRMAR: 0
             * NREVN: 1
             * TMMED: 2
             * CNSPC: 3
             */

            String[] listaBiom=et_biom.get(i);

            for(int j=0; j<et_espe.size(); j++){
                String[] listaEspe=et_espe.get(j);

                if(listaBiom[3].trim().compareTo(listaEspe[0].trim())==0 &&
                        listaBiom[16].trim().compareTo(listaEspe[1].trim())==0){
                    for (int k=0; k<fields.length; k++){

                                double med=Double.parseDouble(listaEspe[2]);
                                String x=String.valueOf(med);
                            if(fields[k].trim().compareTo(x.trim())==0){

                                listaBiom[k]=listaEspe[3];

                                logger.error("listabiomCabezera["+k+"]: "+listaBiom[k]);
                            }
                            else if(listaBiom[k]==null){
                                listaBiom[k]="0";

                            }
                    }
                }
            }
            registrosTotal.add(listaBiom);
        }
        String []a=et_biom.get(0);
        for (int i=0; i<a.length; i++){
            logger.error("a: "+a[i]);
        }

        return registrosTotal;
    }
}
