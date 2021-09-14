package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.reportepesca.dto.BiometriaExports;
import com.incloud.hcp.jco.reportepesca.dto.BiometriaImports;
import com.incloud.hcp.jco.reportepesca.service.JCOBiometriaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

            JCoParameterList paramExport = function.getExportParameterList();
            function.execute(destination);

            JCoTable ET_BIOM = paramExport.getTable(Tablas.ET_BIOM);
            JCoTable ET_ESPE = paramExport.getTable(Tablas.ET_ESPE);
            JCoTable ET_PSCINC = paramExport.getTable(Tablas.ET_PSCINC);
            double min=Double.parseDouble(paramExport.getValue("EP_MMIN").toString());
            double max=Double.parseDouble(paramExport.getValue("EP_MMAX").toString());

            dto.setEp_mmin(paramExport.getValue("EP_MMIN").toString());
            dto.setEp_mmax(paramExport.getValue("EP_MMAX").toString());

            Metodos metodos = new Metodos();
            List<HashMap<String, Object>> et_biom = metodos.ListarObjetos(ET_BIOM);
            List<HashMap<String, Object>> et_espe = metodos.ListarObjetos(ET_ESPE);
            List<HashMap<String, Object>> et_pscinc = metodos.ListarObjetos(ET_PSCINC);

            String path=CrearExcel(ET_BIOM, ET_ESPE, ET_PSCINC, min, max, imports.getIp_cdmma());
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

    private String CrearExcel(JCoTable et_biom, JCoTable et_espe, JCoTable et_pscinc, double min, double max, String codigoMotivoMarea){
        String path="";
        Workbook workbook = new HSSFWorkbook();
        //Crea hoja nueva
        Sheet sheet = workbook.createSheet("Reporte Biometria");
        //sheet.setColumnWidth();

        String[]CellMedidas= CellMedidas(min, max, codigoMotivoMarea);
        Object[] fields= ListarFields(et_biom, CellMedidas);
        String[] field= ListarFields(et_biom, CellMedidas);
        List<String[]> pscinc=ListarEt_pscinc(et_pscinc);
        List<String[]> registros=ListarRegistros(et_biom,  CellMedidas);
        List<String[]> medidas=ListarMedidas(et_espe);

        List<String[]> registrosTotal=registrosTotal(registros, medidas, pscinc,field, CellMedidas);

        Map<String, Object[]> datos = new TreeMap<String, Object[]>();
        //Object[] header=new Object[]{"REPORTE BIOMETRIA"};
        //datos.put("1", header);
        datos.put("1", fields);

        int keys=3;
        for(int i=0; i<registrosTotal.size(); i++){
            Object[] obj= registrosTotal.get(i);

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

    private List<String[]> ListarRegistros(JCoTable et_biom ,  String []CellMedidas){

        List<String[]> campos= new ArrayList<String[]>();
        JCoFieldIterator iter = et_biom.getFieldIterator();

        int con=0;
        while (iter.hasNextField()) {
            JCoField field = iter.nextField();
            con++;
        }
        int cantidadRegistros=con+CellMedidas.length;;
        for (int i = 0; i < et_biom.getNumRows(); i++) {
            et_biom.setRow(i);
            String[] registros = new String[cantidadRegistros];
            JCoFieldIterator ite = et_biom.getFieldIterator();
            int j=0;
            while (ite.hasNextField()) {
                JCoField field = ite.nextField();
                String key = (String) field.getName();
                String value ="";
                try {
                    value = et_biom.getValue(key).toString();
                }catch (Exception e){
                    value="";
                }

                if (key.equals("HIEVN") || key.equals("HFEVN")) {
                    value = value.substring(11,19);
                }
                try {
                    if (key.equals("FIEVN") || key.equals("FFEVN")) {

                           Date date=field.getDate();
                        SimpleDateFormat dia = new SimpleDateFormat("yyyy-MM-dd");
                        String fecha = dia.format(date);
                        value = fecha;


                    }
                }catch (Exception e){
                    value=String.valueOf(value);
                }
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

    private List<String[]> registrosTotal(List<String[]> et_biom, List<String[]> et_espe, List<String[]> et_pscinc,String[]fields, String[] CellMedidas){

        List<String[]> registrosTotal= new ArrayList<String[]>();

        for(int i=0; i<et_biom.size(); i++){

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

                            }

                    }
                }
            }

            registrosTotal.add(listaBiom);
        }
        int cantidadRegistros=fields.length;
        for(int i=0; i<et_pscinc.size(); i++){

            String[]pscinc= et_pscinc.get(i);
            String[] registros = new String[cantidadRegistros];
            registros[0]="0";
            registros[3]=pscinc[0];
            registros[16]=pscinc[1];
            registros[20]=pscinc[2];
            registros[21] = pscinc[3];

            registrosTotal.add(registros);
        }

        for(int i=0; i<registrosTotal.size(); i++){


            String[] registro=registrosTotal.get(i);
            int empiezaceldasMedidas=registro.length-(CellMedidas.length-1);
            for(int l=empiezaceldasMedidas; l<registro.length; l++){
                if(registro[l]==null){
                    registro[l]="0";
                }
            }

        }
        sumarMuestra(registrosTotal, CellMedidas);

        return registrosTotal;
    }

    private List<String[]> ListarEt_pscinc(JCoTable jcoTable){

        /**ET_PSCINC:
         * DSSPC
         * PCSPC
         * NREVN
         * NRMAR
         */
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
                if(key.compareTo("DSSPC")==0 ||key.compareTo("PCSPC")==0 ||
                        key.compareTo("NREVN")==0 ||key.compareTo("NRMAR")==0 ){

                    registros[j] = value;
                    j++;
                }
            }
            campos.add(registros);
        }



        return campos;
    }

    private void sumarMuestra(List<String[]> registroTotal, String [] cellMedidas){




        for (int i=0; i<registroTotal.size(); i++){

            String[]registro=registroTotal.get(i);

            int cellTallas=registro.length-cellMedidas.length+2;
            logger.error("ID: "+registro[0]);

            int muestra=0;
            for(int j=cellTallas; j<registro.length; j++){
                logger.error("registro: "+registro[j]);

                int talla= Integer.parseInt(registro[j]);
                if(talla>0){

                    muestra+=talla;
                    logger.error("muestra: "+muestra);
                }
                logger.error("muestraTotal: "+muestra);
                if(muestra>0) {
                    registro[17] = String.valueOf(muestra);
                }
            }

        }


    }
}
