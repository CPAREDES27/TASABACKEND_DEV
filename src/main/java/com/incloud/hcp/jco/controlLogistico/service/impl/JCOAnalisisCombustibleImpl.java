package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.controlLogistico.service.JCOAnalisisCombustibleService;
import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Dominios;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JCOAnalisisCombustibleImpl implements JCOAnalisisCombustibleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AnalisisCombusLisExports Listar(AnalisisCombusLisImports imports)throws Exception{
        AnalisisCombusLisExports ce= new AnalisisCombusLisExports();
        Metodos metodo = new Metodos();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONT_COMB_MARE_BTP);
            logger.error("stfcConnection: "+stfcConnection.toString());
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_ROW", imports.getP_row());
            String cadena="";
            boolean valida=false;
            if(!imports.getEmbarcacionIni().equals("")){
                valida=true;
                cadena+="CDEMB LIKE '"+imports.getEmbarcacionIni()+"'";
            }
            if(!imports.getMotivoIni().equals("")){
               if(valida==true){
                   cadena+=" AND (CDMMA LIKE '"+imports.getMotivoIni()+"')";
               }else{
                   cadena+="(CDMMA LIKE '"+imports.getMotivoIni()+"')";
                   valida=true;
               }
            }
            if(!imports.getFechaIni().equals("") && !imports.getFechaFin().equals("")){
                if(valida==true){
                    cadena+=" AND (FIEVN BETWEEN '"+imports.getFechaIni()+"'"+" AND "+"'"+imports.getFechaFin()+"')";
                }else{
                    cadena+="(FIEVN BETWEEN '"+imports.getFechaIni()+"'"+" AND "+"'"+imports.getFechaFin()+"')";
                    valida=true;
                }
            }
            if(!imports.getFechaIni().equals("") && imports.getFechaFin().equals("")){
                if(valida==true){
                    cadena+=" AND (FIEVN LIKE '"+imports.getFechaIni()+"')";
                }else{
                    cadena+="FIEVN LIKE '"+imports.getFechaIni()+"'";
                    valida=true;
                }
            }
            if(imports.getFechaIni().equals("") && !imports.getFechaIni().equals("")){
                if(valida==true){
                    cadena+=" AND (FIEVN LIKE '"+imports.getFechaFin()+"')";
                }else{
                    cadena+="(FIEVN LIKE '"+imports.getFechaFin()+"')";
                    valida=true;
                }
            }
            if(!imports.getMotivoIni().equals("")){
                if(imports.getMotivoIni().equals("1") || imports.getMotivoIni().equals("2")){
                    if(valida==true){
                        cadena+=" AND (CDTEV EQ '5')";
                    }else{
                        cadena+="(CDTEV EQ '5')";
                        valida=true;
                    }
                }else if(imports.getMotivoIni().equals("7") || imports.getMotivoIni().equals("8")){
                    if(valida==true){
                        cadena+=" AND CDTEV EQ 'H' OR CDTEV EQ 'T'";
                    }else{
                        cadena+="CDTEV EQ 'H' OR CDTEV EQ 'T'";
                    }
                }

            }else {
                if(valida==true){
                    cadena+=" AND (CDTEV EQ '5' OR CDTEV EQ 'H' OR CDTEV EQ 'T')";
                }else{
                    cadena+="CDTEV EQ '5' OR CDTEV EQ 'H' OR CDTEV EQ 'T'";
                }
            }
            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable tableImport = tables.getTable("P_OPTIONS");
            tableImport.appendRow();
            tableImport.setValue("WA", cadena);
            stfcConnection.execute(destination);
            JCoTable STR_CSMAR = tables.getTable(Tablas.STR_CSMAR);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            List<HashMap<String, Object>> str_csmar = metodo.ListarObjetosLazy(STR_CSMAR);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetosLazy(T_MENSAJE);
            ce.setStr_csmar(str_csmar);
            ce.setT_mensaje(t_mensaje);
            ce.setMensaje("Ok");
        }catch (Exception e){
            ce .setMensaje(e.getMessage());
        }
        return ce;
    }

    public ControlLogExports Detalle(AnalisisCombusImports imports)throws Exception{

        ControlLogExports ce=new ControlLogExports();


        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_COMBUS_VEDA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_NRMAR", imports.getP_nrmar());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable tableExport = tables.getTable(Tablas.STR_DEV);


            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);
            String[] fields=imports.getFields();
           List<HashMap<String, Object>> data = metodo.ObtenerListObjetos(tableExport, fields);

            ce.setData(data);
            ce.setMensaje("Ok");

        }catch (Exception e){
            ce .setMensaje(e.getMessage());
        }

        return ce;
    }


    public ControlDetalleExport Detalles(AnalisisCombusImports imports)throws Exception{

        ControlDetalleExport ce=new ControlDetalleExport();

        logger.error("Analisis Combus Detalles 1");
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_COMB_FASE);
            JCoParameterList importx = stfcConnection.getImportParameterList();

            importx.setValue("P_MAREA", imports.getP_nrmar());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable tableExport = tables.getTable(Tablas.STR_FASE);
            JCoTable tableExport2 = tables.getTable(Tablas.STR_DETF);



            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);
            String[] fields=imports.getFields();
            List<HashMap<String, Object>> data = metodo.ObtenerListObjetos(tableExport, fields);
            List<HashMap<String, Object>> data2 = metodo.ObtenerListObjetos(tableExport2, fields);


            List<HashMap<String, Object>> Datas= new ArrayList<>();

            for (int i=0; i<data.size(); i++){

                HashMap<String, Object> regData=data.get(i);
                String obcom="";
                for(Map.Entry<String, Object> entry:data.get(i).entrySet()){

                    String key=entry.getKey();
                    String value=entry.getValue().toString();

                    if(key.equals("OBCOM")){
                        obcom= value;
                    }

                }
                if(obcom.equals("")){
                    regData.put("request", "sap-icon://request");
                }
                if(!obcom.equals("")){
                    regData.put("request", "sap-icon://detail-view");
                }

                Datas.add(regData);

            }
            ce.setStr_detf(data2);
            ce.setStr_fase(Datas);
            ce.setMensaje("Ok");
        }catch (Exception e){
            ce .setMensaje(e.getMessage());

        }

        return ce;
    }
    public QlikExport QlikView(QlikView imports)throws Exception{

        QlikExport ce=new QlikExport();


        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_QV_COMB_FASE);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FIEVN", imports.getpFievn());
            importx.setValue("P_FFEVN", imports.getpFfevn());
            importx.setValue("P_CDEMB", imports.getpCdemb());
            importx.setValue("P_CDMMA", imports.getpCdmma());
            importx.setValue("P_ROW", imports.getpRow());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable STR_CEF = tables.getTable(Tablas.STR_CEF);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  dataT_MENSAJE = metodo.ListarObjetosLazy(T_MENSAJE);

            List<HashMap<String, Object>> dataSTR_CEF = metodo.ListarObjetosLazy(STR_CEF);


            ce.setT_mensaje(dataT_MENSAJE);
            ce.setStr_cef(dataSTR_CEF);

        }catch (Exception e){
            ce.setMensaje(e.getMessage());
        }

        return ce;
    }

    @Override
    public AnalisisDtoExport AnalisisCombu(AnalisisDtoImport imports) throws Exception {
        AnalisisDtoExport dto = new AnalisisDtoExport();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ANALIS_COMB_FASE);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FIEVN", imports.getFechaIni());
            importx.setValue("P_FFEVN", imports.getFechaFin());
            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable STR_CEF = tables.getTable(Tablas.STR_CEF);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  dataT_MENSAJE = metodo.ListarObjetosLazy(T_MENSAJE);

            List<HashMap<String, Object>> dataSTR_CEF = metodo.ListarObjetosLazy(STR_CEF);


            ArrayList<String> listDomNames = new ArrayList<>();
            listDomNames.add(Dominios.ZCDMMA);

            DominiosHelper helper = new DominiosHelper();
            ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

            DominiosExports detalleMotMarea = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZCDMMA)).findFirst().orElse(null);

            /**
             * Enlace de los detqlles de los campos
             * */
            dataSTR_CEF.stream().map(m -> {
                String cdmma = m.get("CDMMA").toString();

                // Buscar los detalles
                DominioExportsData dataCDMMA = detalleMotMarea.getData().stream().filter(d -> d.getId().equals(cdmma)).findFirst().orElse(null);


                if (dataCDMMA != null) {
                    String descCdmma = dataCDMMA.getDescripcion();
                    m.put("DESC_CDMMA", descCdmma);
                } else {
                    m.put("DESC_CDMMA", "");
                }


                return m;
            }).collect(Collectors.toList());

            ArrayList<String> listMotivos=ObtenerTiposMotivo(dataSTR_CEF);

            List<HashMap<String, Object>> listaReg= new ArrayList<>();
            for(int i=0; i<listMotivos.size();i++){

                double totalNav=0, totalDes=0, totalPue=0, totalMar=0;

                for(int j=0;j<dataSTR_CEF.size();j++){

                    HashMap<String, Object>Reg= dataSTR_CEF.get(j);

                    if(dataSTR_CEF.get(j).get("DSMMA").toString().equals(listMotivos.get(i))){

                        totalNav+=Double.parseDouble(dataSTR_CEF.get(j).get("CONAV").toString());
                        totalDes+=Double.parseDouble(dataSTR_CEF.get(j).get("CODES").toString());
                        totalPue+=Double.parseDouble(dataSTR_CEF.get(j).get("COPUE").toString());
                        totalMar+=Double.parseDouble(dataSTR_CEF.get(j).get("COMAR").toString());

                        listaReg.add(Reg);
                    }

                 }

                HashMap<String, Object>Reg= new HashMap<>();
                Reg.put("DSMMA", "Total");
                Reg.put("CONAV", String.valueOf(totalNav));
                Reg.put("CODES", String.valueOf(totalDes));
                Reg.put("COPUE", String.valueOf(totalPue));
                Reg.put("COMAR", String.valueOf(totalMar));

                listaReg.add(Reg);
            }


            dto.setT_mensaje(dataT_MENSAJE);
            dto.setStr_cef(listaReg);

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;
    }

    @Override
    public AnalisisCombusRegExport ExportarRegistroAnalisisCombu(AnalisisCombusLisImports imports) throws Exception {
        AnalisisCombusRegExport exports = new AnalisisCombusRegExport();

        try {
            AnalisisCombusLisExports exportsData = Listar(imports);

            // Nombres de campos de la tabla
            LinkedHashMap<String, String> titulosField = new LinkedHashMap<>();
            titulosField.put("NRMAR", "Num. Marea");
            titulosField.put("CDEMB", "Cod. de Embarcación");
            titulosField.put("NMEMB", "Nombre de Embarcación");
            titulosField.put("DSMMA", "Motivo");
            titulosField.put("PTOZA", "Puerto de Zarpe");
            titulosField.put("FECZA", "Fecha de Zarpe");
            titulosField.put("HIZAR", "Hora de Zarpe");
            titulosField.put("PTOAR", "Puerto de Arribo");
            titulosField.put("FECAR", "Fecha de Arribo");
            titulosField.put("HIARR", "Hora de Arribo");
            titulosField.put("FECCONMOV", "Fecha de prod.");
            titulosField.put("CNPDS", "Cant. desc. (Tn)");
            titulosField.put("STCMB", "Stock Inicial");
            titulosField.put("CNSUM", "Suministro");
            titulosField.put("CONSU", "Consumo");
            titulosField.put("STFIN", "Stock Final");
            titulosField.put("HOZMP", "MP");
            titulosField.put("HOZA1", "A1");
            titulosField.put("HOZA2", "A2");
            titulosField.put("HOZA3", "A3");
            titulosField.put("HOZA4", "A4");
            titulosField.put("HOZA5", "A5");
            titulosField.put("HOZPA", "PA");
            titulosField.put("HOZFP", "FP");
            titulosField.put("HOAMP", "MP");
            titulosField.put("HOAA1", "A1");
            titulosField.put("HOAA2", "A2");
            titulosField.put("HOAA3", "A3");
            titulosField.put("HOAA4", "A4");
            titulosField.put("HOAA5", "A5");
            titulosField.put("HOAPA", "PA");
            titulosField.put("HOAFP", "FP");
            titulosField.put("HODMP", "MP");
            titulosField.put("HODFP", "FP");
            titulosField.put("HOHMP", "MP");
            titulosField.put("HOHA1", "A1");
            titulosField.put("HOHA2", "A2");
            titulosField.put("HOHA3", "A3");
            titulosField.put("HOHA4", "A4");
            titulosField.put("HOHA5", "A5");
            titulosField.put("HOHPA", "PA");
            titulosField.put("HOHFP", "FP");

            Workbook reporteBook = new HSSFWorkbook();
            Sheet analisisCombusSheet = reporteBook.createSheet("Exportación SAPUI5");

            Font fuenteTitulo = reporteBook.createFont();
            fuenteTitulo.setBold(true);

            CellStyle styleTituloGeneral = reporteBook.createCellStyle();
            styleTituloGeneral.setFont(fuenteTitulo);
            styleTituloGeneral.setAlignment(HorizontalAlignment.CENTER);

            // Título general
            Row rowTituloGeneral = analisisCombusSheet.createRow(1);
            Cell cellTituloGeneral = rowTituloGeneral.createCell(1);
            cellTituloGeneral.setCellValue("CONTROL DE COMBUSTIBLE");
            cellTituloGeneral.setCellStyle(styleTituloGeneral);

            CellRangeAddress cellRangeGeneral = CellRangeAddress.valueOf("B2:AQ2");
            analisisCombusSheet.addMergedRegion(cellRangeGeneral);

            // ------ Títulos de categorías ----------
            Row rowTitulosCategorias = analisisCombusSheet.createRow(3);
            Cell cellTituloZarpe = rowTitulosCategorias.createCell(17);
            cellTituloZarpe.setCellValue("Zarpe");
            cellTituloZarpe.setCellStyle(styleTituloGeneral);

            Cell cellTituloArribo = rowTitulosCategorias.createCell(25);
            cellTituloArribo.setCellValue("Arribo");
            cellTituloArribo.setCellStyle(styleTituloGeneral);

            Cell cellTituloDescarga = rowTitulosCategorias.createCell(33);
            cellTituloDescarga.setCellValue("Descarga");
            cellTituloDescarga.setCellStyle(styleTituloGeneral);

            Cell cellTituloHorometro = rowTitulosCategorias.createCell(35);
            cellTituloHorometro.setCellValue("Horometro");
            cellTituloHorometro.setCellStyle(styleTituloGeneral);

            CellRangeAddress cellRangeZarpe = CellRangeAddress.valueOf("R4:Y4");
            CellRangeAddress cellRangeArribo = CellRangeAddress.valueOf("Z4:AG4");
            CellRangeAddress cellRangeDescarga = CellRangeAddress.valueOf("AH4:AI4");
            CellRangeAddress cellRangeHorometro = CellRangeAddress.valueOf("AJ4:AQ4");

            analisisCombusSheet.addMergedRegion(cellRangeZarpe);
            analisisCombusSheet.addMergedRegion(cellRangeArribo);
            analisisCombusSheet.addMergedRegion(cellRangeDescarga);
            analisisCombusSheet.addMergedRegion(cellRangeHorometro);

            RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeZarpe, analisisCombusSheet);
            RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeZarpe, analisisCombusSheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeZarpe, analisisCombusSheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeZarpe, analisisCombusSheet);

            RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeArribo, analisisCombusSheet);
            RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeArribo, analisisCombusSheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeArribo, analisisCombusSheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeArribo, analisisCombusSheet);

            RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeDescarga, analisisCombusSheet);
            RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeDescarga, analisisCombusSheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeDescarga, analisisCombusSheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeDescarga, analisisCombusSheet);

            RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeHorometro, analisisCombusSheet);
            RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeHorometro, analisisCombusSheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeHorometro, analisisCombusSheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeHorometro, analisisCombusSheet);

            /*CellStyle styleTitulosCategorias = reporteBook.createCellStyle();
            styleTitulosCategorias.setFont(fuenteTitulo);*/

            // ------ Títulos de columnas ----------
            int cellIndexTitulos = 1;
            Row rowTitulos = analisisCombusSheet.createRow(4);

            CellStyle styleTitulo = reporteBook.createCellStyle();
            styleTitulo.setBorderTop(BorderStyle.THIN);
            styleTitulo.setBorderBottom(BorderStyle.THIN);
            styleTitulo.setBorderRight(BorderStyle.THIN);
            styleTitulo.setBorderLeft(BorderStyle.THIN);
            styleTitulo.setFont(fuenteTitulo);

            for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                String titulo = titulosFieldEntry.getValue();
                Cell cellTitulo = rowTitulos.createCell(cellIndexTitulos);
                cellTitulo.setCellValue(titulo);
                cellTitulo.setCellStyle(styleTitulo);

                cellIndexTitulos++;
            }

            // Llenado de datos
            int rowIndex = 5;
            String dataStr = "";
            String[] fieldsNumbers = {"NRMAR", "CNPDS", "STCMB", "CNSUM", "CONSU", "STFIN", "HOZMP", "HOZA1", "HOZA2", "HOZA3", "HOZA4", "HOZA5", "HOZPA", "HOZFP", "HOAMP", "HOAA1", "HOAA2", "HOAA3", "HOAA4", "HOAA5", "HOAPA", "HOAFP", "HODMP", "HODFP", "HOHMP", "HOHA1", "HOHA2", "HOHA3", "HOHA4", "HOHA5", "HOHPA", "HOHFP"};
            String[] fieldsDate = {"FECZA", "FECAR", "FECCONMOV"};

            CellStyle styleNumberFormat = reporteBook.createCellStyle();
            styleNumberFormat.setDataFormat(reporteBook.createDataFormat().getFormat("#,##0"));

            for (HashMap<String, Object> itemData : exportsData.getStr_csmar()) {
                Row row = analisisCombusSheet.createRow(rowIndex);
                int cellIndex = 1;

                for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                    String value = itemData.get(titulosFieldEntry.getKey()).toString();
                    Cell cell = row.createCell(cellIndex);
                    if (Arrays.asList(fieldsDate).contains(titulosFieldEntry.getKey()) && !value.equals("")) {
                        String dia = value.substring(0, 2);
                        String mes = value.substring(3, 5);
                        String anho = value.substring(6);

                        value = anho + "-" + mes + "-" + dia;
                        cell.setCellValue(value);
                    } else if (Arrays.asList(fieldsNumbers).contains(titulosFieldEntry.getKey())) {
                        double doubleValue = Double.parseDouble(value);
                        cell.setCellStyle(styleNumberFormat);
                        cell.setCellValue(doubleValue);
                    } else {
                        cell.setCellValue(value);
                    }

                    dataStr += value;

                    cellIndex++;
                }

                rowIndex++;
            }

            // Autoajuste de ancho de columnas
            int indexColumn = 1;
            for (Map.Entry<String, String> titulosFieldsEntry: titulosField.entrySet()) {
                analisisCombusSheet.autoSizeColumn(indexColumn);
                indexColumn++;
            }

            logger.info(dataStr);

            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Reporte Análisis de Combustuible.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            reporteBook.write(fileOutputStream);
            fileOutputStream.close();

            File file = new File(path);
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            String base64Encoded = new String(encoded, StandardCharsets.UTF_8);
            exports.setBase64(base64Encoded);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return exports;
    }

    public AnalisisCombusRegExport ExportarQlikView(QlikView imports) throws Exception {
        AnalisisCombusRegExport exports = new AnalisisCombusRegExport();

        try {
            QlikExport exportsData = QlikView(imports);
            LinkedHashMap<String, String> titulosField = new LinkedHashMap<>();
            titulosField.put("CDEMB", "Cdemb");
            titulosField.put("NRMAR", "Nmar");
            titulosField.put("NMEMB", "Nmemb");
            titulosField.put("CDMMA", "Cdmma");
            titulosField.put("DSMMA", "Dsmma");
            titulosField.put("CNPDS", "Cnpds");
            titulosField.put("HONAV", "Honav");
            titulosField.put("HODES", "Hodes");
            titulosField.put("HOPUE", "Hopue");
            titulosField.put("HOMAR", "Homar");
            titulosField.put("CONAV", "Conav");
            titulosField.put("CODES", "Codes");
            titulosField.put("COPUE", "Copue");
            titulosField.put("COMAR", "Comar");
            titulosField.put("RRNAV", "Rrnav");
            titulosField.put("RRDES", "Rrdes");
            titulosField.put("RRPUE", "Rrpue");
            titulosField.put("RRMAR", "Rrmar");
            titulosField.put("RPNAV", "Rpnav");
            titulosField.put("RPDES", "Rpdes");
            titulosField.put("RPPUE", "Rppue");
            titulosField.put("RPMAR", "Rpmar");
            titulosField.put("FEPRD", "Feprd");
            Workbook reporteBook = new HSSFWorkbook();
            Sheet analisisCombusSheet = reporteBook.createSheet("Exportación SAPUI5");
            Font fuenteTitulo = reporteBook.createFont();
            fuenteTitulo.setBold(true);
            CellStyle styleTituloGeneral = reporteBook.createCellStyle();
            styleTituloGeneral.setFont(fuenteTitulo);
            styleTituloGeneral.setAlignment(HorizontalAlignment.CENTER);
            Row rowTituloGeneral = analisisCombusSheet.createRow(1);
            Cell cellTituloGeneral = rowTituloGeneral.createCell(1);
            cellTituloGeneral.setCellValue("CONTROL DE COMBUSTIBLE - QLIKVIEW");
            cellTituloGeneral.setCellStyle(styleTituloGeneral);

            CellRangeAddress cellRangeGeneral = CellRangeAddress.valueOf("B2:X2");
            analisisCombusSheet.addMergedRegion(cellRangeGeneral);

            int cellIndexTitulos = 1;
            Row rowTitulos = analisisCombusSheet.createRow(3);

            CellStyle styleTitulo = reporteBook.createCellStyle();
            styleTitulo.setBorderTop(BorderStyle.THIN);
            styleTitulo.setBorderBottom(BorderStyle.THIN);
            styleTitulo.setBorderRight(BorderStyle.THIN);
            styleTitulo.setBorderLeft(BorderStyle.THIN);
            styleTitulo.setFont(fuenteTitulo);

            for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                String titulo = titulosFieldEntry.getValue();
                Cell cellTitulo = rowTitulos.createCell(cellIndexTitulos);
                cellTitulo.setCellValue(titulo);
                cellTitulo.setCellStyle(styleTitulo);

                cellIndexTitulos++;
            }
            int rowIndex = 4;
            String dataStr = "";
            String[] fieldsNumbers ={"CNPDS","NRMAR","HONAV", "HODES", "HOPUE", "HOMAR", "CONAV", "CODES", "COPUE",
                    "COMAR", "RRNAV", "RRDES", "RRPUE","RRMAR","RPNAV", "RPDES", "RPPUE","RPMAR"};
            String[] fieldsDate = {"FEPRD"};

            CellStyle styleNumberFormat = reporteBook.createCellStyle();
            styleNumberFormat.setDataFormat(reporteBook.createDataFormat().getFormat("#,##0"));
            CellStyle styleNumberFormat1 = reporteBook.createCellStyle();
            styleNumberFormat1.setDataFormat(reporteBook.createDataFormat().getFormat("#,##0.000"));
            for (HashMap<String, Object> itemData : exportsData.getStr_cef()) {
                Row row = analisisCombusSheet.createRow(rowIndex);
                int cellIndex = 1;
                for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                    String key=titulosFieldEntry.getKey();
                    String value = itemData.get(titulosFieldEntry.getKey()).toString();
                    Cell cell = row.createCell(cellIndex);
                    if (Arrays.asList(fieldsDate).contains(titulosFieldEntry.getKey()) && !value.equals("")) {
                        String dia = value.substring(0, 2);
                        String mes = value.substring(3, 5);
                        String anho = value.substring(6);
                        value = anho + "-" + mes + "-" + dia;
                        cell.setCellValue(value);
                    } else if (Arrays.asList(fieldsNumbers).contains(titulosFieldEntry.getKey())) {
                        double doubleValue = Double.parseDouble(value);
                        cell.setCellStyle(styleNumberFormat1);
                        cell.setCellValue(doubleValue);
                    }else if(key.equals("NRMAR")){
                        double doubleValue = Double.parseDouble(value);
                        cell.setCellStyle(styleNumberFormat);
                        String nrmar=String.valueOf(doubleValue).concat(".000");
                        cell.setCellValue(nrmar);
                    }else {
                        cell.setCellValue(value);
                    }
                    dataStr += value;
                    cellIndex++;
                }
                rowIndex++;
            }
            int indexColumn = 1;
            for (Map.Entry<String, String> titulosFieldsEntry: titulosField.entrySet()) {
                analisisCombusSheet.autoSizeColumn(indexColumn);
                indexColumn++;
            }
            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "ControlCombustible_QlikView.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            reporteBook.write(fileOutputStream);
            fileOutputStream.close();
            File file = new File(path);
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            String base64Encoded = new String(encoded, StandardCharsets.UTF_8);
            exports.setBase64(base64Encoded);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            exports.setBase64(ex.getMessage());
        }
        return exports;
    }

    public ArrayList<String> ObtenerTiposMotivo(List<HashMap<String, Object>> listaReg)throws Exception{

        ArrayList<String>motivos=new ArrayList<>();
        String motivo="";

        for(int i=0;i<listaReg.size();i++){

            String mot=listaReg.get(i).get("DSMMA").toString();

            if(!mot.equals(motivo)){

                logger.error("mot: "+mot);
                logger.error("motivo: "+motivo);
                motivo=mot;
                motivos.add(motivo);
            }

        }

        Set<String> set = new HashSet<>(motivos);
        motivos.clear();
        motivos.addAll(set);

        return motivos;
    }

}
