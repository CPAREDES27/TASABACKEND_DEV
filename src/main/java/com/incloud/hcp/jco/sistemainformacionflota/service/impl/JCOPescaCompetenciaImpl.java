package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusRegExport;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.sistemainformacionflota.dto.*;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaCompetenciaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import io.swagger.models.auth.In;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
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
import java.util.*;

@Service
public class JCOPescaCompetenciaImpl implements JCOPescaCompetenciaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public PescaCompetenciaRadialExports PescaCompetenciaRadial(PescaCompetenciaRadialImports imports) throws Exception {

        PescaCompetenciaRadialExports pcr=new PescaCompetenciaRadialExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_DECLA_COMPE_RADIO);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FEINI", imports.getP_feini());
            importx.setValue("P_FEFIN", imports.getP_fefin());
            importx.setValue("P_CDGRE", imports.getP_cdgre());
            importx.setValue("P_CTGRA", imports.getP_ctgra());
            importx.setValue("P_CDTPC", imports.getP_cdtpc());

            List<MaestroOptions> options = imports.getP_options();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }
            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);



            stfcConnection.execute(destination);

            JCoTable STR_ZLT = tables.getTable(Tablas.STR_ZLT);
            JCoTable STR_PTO = tables.getTable(Tablas.STR_PTO);
            JCoTable STR_GRE = tables.getTable(Tablas.STR_GRE);
            JCoTable STR_EMP = tables.getTable(Tablas.STR_EMP);
            JCoTable STR_PGE = tables.getTable(Tablas.STR_PGE);
            JCoTable STR_EPP = tables.getTable(Tablas.STR_EPP);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_zlt = metodo.ObtenerListObjetos(STR_ZLT, imports.getFieldstr_zlt());
            List<HashMap<String, Object>> str_pto = metodo.ObtenerListObjetos(STR_PTO, imports.getFieldstr_pto());
            List<HashMap<String, Object>> str_gre = metodo.ObtenerListObjetos(STR_GRE, imports.getFieldstr_gre());
            List<HashMap<String, Object>> str_emp = metodo.ObtenerListObjetos(STR_EMP, imports.getFieldstr_emp());
            List<HashMap<String, Object>> str_pge = metodo.ObtenerListObjetos(STR_PGE, imports.getFieldstr_pge());
            List<HashMap<String, Object>> str_epp = metodo.ObtenerListObjetos(STR_EPP, imports.getFieldstr_epp());

            pcr.setStr_zlt(str_zlt);
            pcr.setStr_pto(str_pto);
            pcr.setStr_gre(str_gre);
            pcr.setStr_emp(str_emp);
            pcr.setStr_pge(str_pge);
            pcr.setStr_epp(str_epp);
            pcr.setMensaje("Ok");

        }catch (Exception e){
            pcr.setMensaje(e.getMessage());
        }

        return pcr;
    }

    @Override
    public PescaCompetenciaProduceExports PescaCompetenciaProduce(PescaCompetenciaProduceImports imports) throws Exception {

        PescaCompetenciaProduceExports pcp=new PescaCompetenciaProduceExports();

        try {
            Metodos metodo = new Metodos();
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESC_COMP_GNRAL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("CDUSR", imports.getCdusr ());
            importx.setValue("P_TCONS", imports.getP_tcons());
            importx.setValue("P_FEINI", imports.getP_feini());
            importx.setValue("P_FEFIN", imports.getP_fefin());
            importx.setValue("P_CDGRE", imports.getP_cdgre());
            importx.setValue("P_GRUEB", imports.getP_grueb());
            importx.setValue("P_CTGRA", imports.getP_ctgra());
            importx.setValue("P_CDPCN", imports.getP_cdpcn());
            importx.setValue("P_ZCDZAR", imports.getP_zcdzar());
            importx.setValue("P_EMBA", imports.getP_emba());
            importx.setValue("P_EMPEB", imports.getP_empeb());

            List<HashMap<String, Object>> tmpOptions = metodo.ValidarOptions(imports.getP_option(),imports.getP_options());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);
            stfcConnection.execute(destination);

            JCoTable tblSTR_ZLT = tables.getTable(Tablas.STR_ZLT);
            JCoTable tblSTR_PTO = tables.getTable(Tablas.STR_PTO);
            JCoTable tblSTR_GRE = tables.getTable(Tablas.STR_GRE);
            JCoTable tblSTR_PGE = tables.getTable(Tablas.STR_PGE);
            JCoTable tblSTR_GZP = tables.getTable(Tablas.STR_GZP);
            JCoTable tblSTR_EPP = tables.getTable(Tablas.STR_EPP);
            JCoTable tblSTR_ZPL = tables.getTable(Tablas.STR_ZPL);
            JCoTable tblSTR_EMP = tables.getTable(Tablas.STR_EMP);
            JCoTable tblSTR_PEM = tables.getTable(Tablas.STR_PEM);
            JCoTable tblSTR_PED = tables.getTable(Tablas.STR_PED);
            JCoTable tblSTR_GRP = tables.getTable(Tablas.STR_GRP);
            JCoTable tblSTR_PLM = tables.getTable(Tablas.STR_PLM);

            List<HashMap<String, Object>> str_zlt = metodo.ListarObjetosLazy(tblSTR_ZLT);
            List<HashMap<String, Object>> str_pto = metodo.ListarObjetosLazy(tblSTR_PTO);
            List<HashMap<String, Object>> str_gre = metodo.ListarObjetosLazy(tblSTR_GRE);
            List<HashMap<String, Object>> str_pge = metodo.ListarObjetosLazy(tblSTR_PGE);
            List<HashMap<String, Object>> str_gzp = metodo.ListarObjetosLazy(tblSTR_GZP);
            List<HashMap<String, Object>> str_epp = metodo.ListarObjetosLazy(tblSTR_EPP);
            List<HashMap<String, Object>> str_zpl = metodo.ListarObjetosLazy(tblSTR_ZPL);
            List<HashMap<String, Object>> str_emp = metodo.ListarObjetosLazy(tblSTR_EMP);
            List<HashMap<String, Object>> str_pem = metodo.ListarObjetosLazy(tblSTR_PEM);
            List<HashMap<String, Object>> str_ped = metodo.ListarObjetosLazy(tblSTR_PED);
            List<HashMap<String, Object>> str_grp = metodo.ListarObjetosLazy(tblSTR_GRP);
            List<HashMap<String, Object>> str_plm = metodo.ListarObjetosLazy(tblSTR_PLM);

            pcp.setStr_zlt(str_zlt);
            pcp.setStr_pto(str_pto);
            pcp.setStr_gre(str_gre);
            pcp.setStr_pge(str_pge);
            pcp.setStr_gzp(str_gzp);
            pcp.setStr_epp(str_epp);
            pcp.setStr_zpl(str_zpl);
            pcp.setStr_emp(str_emp);
            pcp.setStr_pem(str_pem);
            pcp.setStr_ped(str_ped);
            pcp.setStr_grp(str_grp);
            pcp.setStr_plm(str_plm);
            pcp.setMensaje("Ok");

        }catch (Exception e){
            pcp.setMensaje(e.getMessage());
        }

        return pcp;
    }

    public AnalisisCombusRegExport ExportPescaCompetencia(PesCompetenciaProdImports imports)throws Exception{
        AnalisisCombusRegExport exports = new AnalisisCombusRegExport();
        try {

            logger.error("export pescomprod 1");

            Workbook reporteBook = new HSSFWorkbook();
            Sheet pescaComProdSheet = reporteBook.createSheet("Exportación SAPUI5");

            Font fuenteTitulo = reporteBook.createFont();
            fuenteTitulo.setBold(true);

            CellStyle styleTituloGeneral = reporteBook.createCellStyle();
            styleTituloGeneral.setFont(fuenteTitulo);
            styleTituloGeneral.setAlignment(HorizontalAlignment.CENTER);
            String abc="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            logger.error("export pescomprod 2");


            // Títulos generales
            Row rowTituloGeneral = pescaComProdSheet.createRow(1);
            Cell cellTituloGeneral = rowTituloGeneral.createCell(1);
            cellTituloGeneral.setCellValue("PESCA DESCARGADA DE COMPETENCIA");
            cellTituloGeneral.setCellStyle(styleTituloGeneral);

            logger.error("export pescomprod 2.1");

            int cantCol=imports.getColumnas().size();
            String rangoTitulo="B2:"+abc.charAt(cantCol)+"2";
            String rangoTitulo2="B3:"+abc.charAt(cantCol)+"3";
            String rangoTitulo3="B4:"+abc.charAt(cantCol)+"4";

            logger.error("rangoTitulo: "+rangoTitulo);
            logger.error("rangoTitulo2: "+ rangoTitulo2);
            logger.error("rangoTitulo3: "+ rangoTitulo3);

            CellRangeAddress cellRangeGeneral = CellRangeAddress.valueOf(rangoTitulo);
            pescaComProdSheet.addMergedRegion(cellRangeGeneral);
            logger.error("export pescomprod 2.2");
            Row rowTituloGeneral2 = pescaComProdSheet.createRow(2);
            Cell cellTituloGeneral2 = rowTituloGeneral2.createCell(1);
            cellTituloGeneral2.setCellValue(imports.getTitulo());
            cellTituloGeneral2.setCellStyle(styleTituloGeneral);

            cellRangeGeneral = CellRangeAddress.valueOf(rangoTitulo2);
            pescaComProdSheet.addMergedRegion(cellRangeGeneral);
            logger.error("export pescomprod 2.3");

            Row rowTituloGeneral3 = pescaComProdSheet.createRow(3);
            Cell cellTituloGeneral3 = rowTituloGeneral3.createCell(1);
            cellTituloGeneral3.setCellValue("DEL "+imports.getFechaInicio()+ " AL "+ imports.getFechaFin());
            cellTituloGeneral3.setCellStyle(styleTituloGeneral);
            logger.error("export pescomprod 2.4");

            cellRangeGeneral = CellRangeAddress.valueOf(rangoTitulo3);
            pescaComProdSheet.addMergedRegion(cellRangeGeneral);
            logger.error("export pescomprod 2.5");


            List<LinkedHashMap<String, Object>> columnas=ObtCantColumPorCabecera(imports.getColumnas());


            // ------ Títulos de categorías ----------

            CellStyle styleHeader = reporteBook.createCellStyle();
            styleHeader.setFont(fuenteTitulo);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            int canSubCab=0;
            Row rowTitulosCategorias = pescaComProdSheet.createRow(5);

            int col=1;
            for(int i = 0; i<columnas.size(); i++){

                 canSubCab= Integer.parseInt(columnas.get(i).get("columnas").toString());
                 logger.error("can sub cab: "+canSubCab);

                 if(i!=0){
                     col+=Integer.parseInt(columnas.get(i-1).get("columnas").toString());
                 }


                if(!columnas.get(i).get("cabecera").toString().equals("")) {


                    logger.error("col: "+(col+1));
                    Cell cabeceras = rowTitulosCategorias.createCell(col );
                    String header=columnas.get(i).get("cabecera").toString();
                    logger.error("cabecera: "+header);
                    cabeceras.setCellValue(header);
                    cabeceras.setCellStyle(styleHeader);



                    int celf=(col + canSubCab)-1;
                    String celdaIni = abc.charAt(col) + "6";
                    String celdaFin = abc.charAt(celf) + "6";
                    String celdas = celdaIni + ":" + celdaFin;



                    logger.error(celdaIni);
                    logger.error(celdaFin);
                    logger.error(celdas);

                    CellRangeAddress RangosCeldas = CellRangeAddress.valueOf(celdas);
                    pescaComProdSheet.addMergedRegion(RangosCeldas);

                    RegionUtil.setBorderTop(BorderStyle.THIN, RangosCeldas, pescaComProdSheet);
                    RegionUtil.setBorderBottom(BorderStyle.THIN, RangosCeldas, pescaComProdSheet);
                    RegionUtil.setBorderRight(BorderStyle.THIN, RangosCeldas, pescaComProdSheet);
                    RegionUtil.setBorderLeft(BorderStyle.THIN, RangosCeldas, pescaComProdSheet);
                }
            }
            logger.error("Export pesca comp produce_3");
            // ------ Títulos de columnas ----------

            int canCab=columnas.size();
            int nFilaTitulos=6;
            int nFilaRegistros=7;
            if(canCab==1){
                nFilaTitulos=5;
                nFilaRegistros=6;
            }
            int cellIndexTitulos = 1;
            Row rowTitulos = pescaComProdSheet.createRow(nFilaTitulos);

            CellStyle styleTitulo = reporteBook.createCellStyle();
            styleTitulo.setBorderTop(BorderStyle.THIN);
            styleTitulo.setBorderBottom(BorderStyle.THIN);
            styleTitulo.setBorderRight(BorderStyle.THIN);
            styleTitulo.setBorderLeft(BorderStyle.THIN);
            styleTitulo.setFont(fuenteTitulo);

            for(int i = 0; i<imports.getColumnas().size(); i++) {

                String titulo = imports.getColumnas().get(i).get("titulo").toString();
                Cell cellTitulo = rowTitulos.createCell(cellIndexTitulos);
                cellTitulo.setCellValue(titulo);
                cellTitulo.setCellStyle(styleTitulo);

                pescaComProdSheet.autoSizeColumn(cellIndexTitulos);
                cellIndexTitulos++;

            }

            cellIndexTitulos = 1;


            for(int i = 0; i<imports.getFilas().size(); i++) {

                Row rowRegistros = pescaComProdSheet.createRow(nFilaRegistros);

                for(Map.Entry<String, Object> entry: imports.getFilas().get(i).entrySet()){

                    String valor = entry.getValue().toString();
                    Cell cellTitulo = rowRegistros.createCell(cellIndexTitulos);
                    cellTitulo.setCellValue(valor);

                    pescaComProdSheet.autoSizeColumn(cellIndexTitulos);
                    cellIndexTitulos++;
                }


                nFilaRegistros++;
            }

            logger.error("Export pesca comp produce_5");
            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Reporte_Pesca_Competencia_Produce.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            reporteBook.write(fileOutputStream);
            fileOutputStream.close();
            logger.error("Export pesca comp produce_6");
            File file = new File(path);
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            String base64Encoded = new String(encoded, StandardCharsets.UTF_8);
            exports.setBase64(base64Encoded);

        } catch (Exception ex) {
            logger.error("causa: "+ex.getCause());
            logger.error("error: "+ex.getMessage());
            exports.setBase64(ex.getMessage());
        }
        return exports;

    }

    public  List<LinkedHashMap<String, Object>>   ObtCantColumPorCabecera(List<LinkedHashMap<String, Object>> columnas){


        logger.error("ObtCantColumPorCabecera ");
        List<LinkedHashMap<String, Object>> column= new ArrayList<>();

        logger.error("ObtCantColumPorCabecera  column size:"+ columnas.size());

        /*for(int i=0; i<column.size();i++){
            logger.error("ObtCantColumPorCabecera  primer for: "+i);
            LinkedHashMap<String, Object> col= new LinkedHashMap<>();
            String cabecera=columnas.get(i).get("cabecera").toString();
            logger.error("ObtCantColumPorCabecera  cabecera: "+ cabecera);
            int con=0;

            for(int j=0; j<column.size();j++) {
                logger.error("ObtCantColumPorCabecera  segundo for: "+ cabecera+":"+columnas.get(j).get("cabecera").toString());

                if (cabecera.equals(columnas.get(j).get("cabecera").toString())) {
                    con++;
                }else{
                    break;
                }
            }
                col.put("cabecera", cabecera);
                col.put("columnas", con);
                column.add(col);


        }*/

        for(int i=0; i<columnas.size();i++){
            LinkedHashMap<String, Object> col= new LinkedHashMap<>();
            String cabecera=columnas.get(i).get("cabecera").toString();
            int con=0;
            if(i==0){
                for(int j=0; j<columnas.size(); j++){

                    if(cabecera.equals(columnas.get(j).get("cabecera"))){
                        con++;
                    }else{
                        if(con>0){
                            break;
                        }
                    }
                }

                col.put("cabecera", cabecera);
                col.put("columnas", con);
                column.add(col);
            }else{
                if(!cabecera.equals(columnas.get(i-1).get("cabecera").toString())){
                    for(int j=0; j<columnas.size(); j++){

                        if(cabecera.equals(columnas.get(j).get("cabecera"))){
                            con++;
                        }else{
                            if(con>0){
                                break;
                            }
                        }
                    }

                    col.put("cabecera", cabecera);
                    col.put("columnas", con);
                    column.add(col);
                }

            }

        }

        for(int i=0;  i<column.size();i++){
            for (Map.Entry<String, Object> entry: column.get(i).entrySet())
            {
                logger.error(entry.getKey()+" : "+entry.getValue().toString());
            }
        }

        return column;
    }
}
