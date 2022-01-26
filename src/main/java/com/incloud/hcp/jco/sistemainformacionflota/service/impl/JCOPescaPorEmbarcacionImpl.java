package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaRepExports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaPorEmbarcacionService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
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
import java.text.DecimalFormat;
import java.util.*;

@Service
public class JCOPescaPorEmbarcacionImpl implements JCOPescaPorEmbarcacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PescaPorEmbarcaExports PescaPorEmbarcacion(PescaPorEmbarcaImports imports) throws Exception {

        PescaPorEmbarcaExports ppe= new PescaPorEmbarcaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_EMBARCA_BTP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FCINI", imports.getP_fcini());
            importx.setValue("P_FCFIN", imports.getP_fcfin());
            importx.setValue("P_CDTEM", imports.getP_cdtem());
            importx.setValue("P_CDPCN", imports.getP_cdpcn());
            importx.setValue("P_CDEMB", imports.getP_cdemb());
            importx.setValue("P_PAG", imports.getP_pag());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoParameterList export    = stfcConnection.getExportParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_PEM = tables.getTable(Tablas.STR_PEM);
            ppe.setP_totalpag(export.getString("P_TOTALPAG"));

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_pem = metodo.ListarObjetosLazy(STR_PEM);

            ppe.setStr_pem(str_pem);
            ppe.setMensaje("Ok");

        }catch (Exception e){
            ppe.setMensaje(e.getMessage());
        }

        return ppe;
    }

    @Override
    public PescaPorEmbarcaRepExports ExportPescaPorEmbarcacion(PescaPorEmbarcaImports imports) throws Exception {
        PescaPorEmbarcaRepExports exportsFile = new PescaPorEmbarcaRepExports();

        try {
            PescaPorEmbarcaExports exports = PescaPorEmbarcacion(imports);
            boolean isTemporada = !imports.getP_cdpcn().equals("") ? true : false;
            ArrayList<HashMap<String, Object>> exportsFormatted = new ArrayList<>();
            String formatDecimal = String.format("%.1f");

            Workbook reporteBook = new HSSFWorkbook();
            Sheet pescaEmbSheet = reporteBook.createSheet("Hoja 1");
            int numTableRow = 5;
            int startColumn = 1;

            Font fuenteTitulo = reporteBook.createFont();
            fuenteTitulo.setBold(true);

            CellStyle styleTitulo = reporteBook.createCellStyle();
            styleTitulo.setBorderTop(BorderStyle.THIN);
            styleTitulo.setBorderBottom(BorderStyle.THIN);
            styleTitulo.setBorderRight(BorderStyle.THIN);
            styleTitulo.setBorderLeft(BorderStyle.THIN);
            styleTitulo.setFont(fuenteTitulo);

            LinkedHashMap<String, String> titulosField = new LinkedHashMap<>();
            LinkedHashMap<String, Object> totalesField = new LinkedHashMap<>();

            int numRow = 1;
            float totCppms = 0;
            float totCprnc = 0;
            float totCndcn = 0;
            float totCndsu = 0;
            float totCndhd = 0;
            float totCndto = 0;
            float totDipcn = 0;
            float totDipsu = 0;
            float totDiphd = 0;
            float totDspsu = 0;
            float totDsphd = 0;
            float totDived = 0;
            float totTotdi = 0;
            float totDifal = 0;
            float totRenem = 0;

            for (HashMap<String, Object> pescaEmb: exports.getStr_pem()) {
                HashMap<String, Object> pescaEmbFormatted = new HashMap<>();
                String nmemb = pescaEmb.get("NMEMB").toString();
                float cppms = Float.parseFloat(pescaEmb.get("CPPMS").toString());
                float cprnc = Float.parseFloat(pescaEmb.get("CPRNC").toString());
                float cavnc = Float.parseFloat(pescaEmb.get("CAVNC").toString()) * 100;
                float cndcn = Float.parseFloat(pescaEmb.get("CNDCN").toString());
                float cndsu = Float.parseFloat(pescaEmb.get("CNDSU").toString());
                float cndhd = Float.parseFloat(pescaEmb.get("CNDHD").toString());
                float cndto = Float.parseFloat(pescaEmb.get("CNDTO").toString());
                float dipcn = Float.parseFloat(pescaEmb.get("DIPCN").toString());
                float dipsu = Float.parseFloat(pescaEmb.get("DIPSU").toString());
                float diphd = Float.parseFloat(pescaEmb.get("DIPHD").toString());
                float dspsu = Float.parseFloat(pescaEmb.get("DSPSU").toString());
                float dsphd = Float.parseFloat(pescaEmb.get("DSPHD").toString());
                float dived = Float.parseFloat(pescaEmb.get("DIVED").toString());
                float totdi = Float.parseFloat(pescaEmb.get("TOTDI").toString());
                float difal = Float.parseFloat(pescaEmb.get("DIFAL").toString());
                float renem = Float.parseFloat(pescaEmb.get("RENEM").toString()) * 100;

                pescaEmbFormatted.put("ROW", numRow);
                pescaEmbFormatted.put("NMEMB", nmemb);
                pescaEmbFormatted.put("CPPMS", isTemporada ? cppms : Math.round(cppms));
                pescaEmbFormatted.put("CPRNC", cprnc);
                pescaEmbFormatted.put("CAVNC", cavnc);
                pescaEmbFormatted.put("CNDCN", cndcn);
                pescaEmbFormatted.put("CNDSU", cndsu);
                pescaEmbFormatted.put("CNDHD", cndhd);
                pescaEmbFormatted.put("CNDTO", cndto);
                pescaEmbFormatted.put("DIPCN", dipcn);
                pescaEmbFormatted.put("DIPSU", dipsu);
                pescaEmbFormatted.put("DIPHD", diphd);
                pescaEmbFormatted.put("DSPSU", dspsu);
                pescaEmbFormatted.put("DSPHD", dsphd);
                pescaEmbFormatted.put("DIVED", dived);
                pescaEmbFormatted.put("TOTDI", totdi);
                pescaEmbFormatted.put("DIFAL", difal);
                pescaEmbFormatted.put("RENEM", renem);

                exportsFormatted.add(pescaEmbFormatted);
                numRow++;

                // Cálculo de totales
                totCppms += cppms;
                totCprnc += cprnc;
                totCndcn += cndcn;
                totCndsu += cndsu;
                totCndhd += cndhd;
                totCndto += cndto;
                totDipcn += dipcn;
                totDipsu += dipsu;
                totDiphd += diphd;
                totDspsu += dspsu;
                totDsphd += dsphd;
                totDived += dived;
                totTotdi += totdi;
                totDifal += difal;
                totRenem += renem;
            }

            Row rowTitulosCombined = pescaEmbSheet.createRow(numTableRow - 1);
            CellStyle styleTituloCombined = reporteBook.createCellStyle();
            styleTituloCombined.setFont(fuenteTitulo);

            if (isTemporada) { // Renderizado para temporadas
                titulosField.put("ROW", "");
                titulosField.put("NMEMB", "Embarcacion");
                titulosField.put("CPPMS", "CBOD");
                titulosField.put("CPRNC", "Periodo");
                titulosField.put("CAVNC", "Avance(%)");
                titulosField.put("CNDSU", "Pesca Sur");
                titulosField.put("DIPSU", "Días Sur");
                titulosField.put("DIFAL", "Faltantes");
                titulosField.put("RENEM", "Rend(%)");

                //Títulos combinados
                Cell cellCuota = rowTitulosCombined.createCell(4);
                cellCuota.setCellStyle(styleTituloCombined);
                cellCuota.setCellValue("CUOTA");

                Cell cellPescaTM = rowTitulosCombined.createCell(6);
                cellPescaTM.setCellStyle(styleTitulo);
                cellPescaTM.setCellValue("Pesca TM");

                Cell cellDiasPesca = rowTitulosCombined.createCell(7);
                cellDiasPesca.setCellStyle(styleTitulo);
                cellDiasPesca.setCellValue("Días de Pesca");

                Cell cellDias = rowTitulosCombined.createCell(7);
                cellDias.setCellStyle(styleTitulo);
                cellDias.setCellValue("Días");

                CellRangeAddress cellRangeCuota = CellRangeAddress.valueOf("E5:F5");
                pescaEmbSheet.addMergedRegion(cellRangeCuota);

                RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeCuota, pescaEmbSheet);
                RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeCuota, pescaEmbSheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeCuota, pescaEmbSheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeCuota, pescaEmbSheet);

                // Datos para fila de totales
                totalesField.put("ROW", "");
                totalesField.put("NMEMB", "Total:");
                totalesField.put("CPPMS", totCppms);
                totalesField.put("CPRNC", totCprnc);
                totalesField.put("CAVNC", "");
                totalesField.put("CNDSU", totCndsu);
                totalesField.put("DIPSU", totDipsu);
                totalesField.put("DIFAL", totDifal);
                totalesField.put("RENEM", totRenem);
            } else { // Renderizado por fechas
                titulosField.put("ROW", "");
                titulosField.put("NMEMB", "Embarcacion");
                titulosField.put("CPPMS", "CBOD");
                titulosField.put("CNDCN", "Pesca Centro Norte");
                titulosField.put("CNDSU", "Pesca Sur");
                titulosField.put("CNDHD", "Pesca CHD");
                titulosField.put("CNDTO", "Total Pesca");
                titulosField.put("DIPCN", "Días Centro Norte");
                titulosField.put("DIPSU", "Días Sur");
                titulosField.put("DIPHD", "Días CHD");
                titulosField.put("DSPHD", "Sin Pesca CHD");
                titulosField.put("DSPSU", "Sin Pesca Sur");
                titulosField.put("DIVED", "Días Veda");
                titulosField.put("TOTDI", "Total Días");

                //Títulos combinados
                Cell cellPescaTM = rowTitulosCombined.createCell(4);
                cellPescaTM.setCellStyle(styleTituloCombined);
                cellPescaTM.setCellValue("Pesca TM");

                Cell cellDiasPesca = rowTitulosCombined.createCell(7);
                cellDiasPesca.setCellStyle(styleTituloCombined);
                cellDiasPesca.setCellValue("Días de Pesca");

                CellRangeAddress cellRangePescaTM = CellRangeAddress.valueOf("E5:H5");
                pescaEmbSheet.addMergedRegion(cellRangePescaTM);

                CellRangeAddress cellRangeDiasPesca = CellRangeAddress.valueOf("I5:O5");
                pescaEmbSheet.addMergedRegion(cellRangeDiasPesca);

                RegionUtil.setBorderTop(BorderStyle.THIN, cellRangePescaTM, pescaEmbSheet);
                RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangePescaTM, pescaEmbSheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, cellRangePescaTM, pescaEmbSheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangePescaTM, pescaEmbSheet);

                RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeDiasPesca, pescaEmbSheet);
                RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeDiasPesca, pescaEmbSheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeDiasPesca, pescaEmbSheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeDiasPesca, pescaEmbSheet);

                // Datos para fila de totales
                totalesField.put("ROW", "");
                totalesField.put("NMEMB", "Total:");
                totalesField.put("CPPMS", totCppms);
                totalesField.put("CNDCN", totCndcn);
                totalesField.put("CNDSU", totCndsu);
                totalesField.put("CNDHD", totCndhd);
                totalesField.put("CNDTO", totCndto);
                totalesField.put("DIPCN", totDipcn);
                totalesField.put("DIPSU", totDipsu);
                totalesField.put("DIPHD", totDiphd);
                totalesField.put("DSPHD", totDsphd);
                totalesField.put("DSPSU", totDspsu);
                totalesField.put("DIVED", totDived);
                totalesField.put("TOTDI", totTotdi);
            }

            // Títulos generales
            String fechaIni = imports.getP_fcini();
            String fechaFin = imports.getP_fcfin();
            String fechaIniFormat = fechaIni.substring(6) + "/" + fechaIni.substring(4, 6) + "/" + fechaIni.substring(0, 4);
            String fechaFinFormat = fechaFin.substring(6) + "/" + fechaFin.substring(4, 6) + "/" + fechaFin.substring(0, 4);

            Row rowTituloGen = pescaEmbSheet.createRow(1);
            Cell cellTituloGen1 = rowTituloGen.createCell(1);
            cellTituloGen1.setCellStyle(styleTituloCombined);
            cellTituloGen1.setCellValue("PESCA POR EMBARCACIÓN");

            Cell cellTituloGen2 = rowTituloGen.createCell(2);
            cellTituloGen2.setCellStyle(styleTituloCombined);
            cellTituloGen2.setCellValue("DEL " + fechaIniFormat + " AL " + fechaFinFormat);

            CellRangeAddress cellRangeTituloGen1 = CellRangeAddress.valueOf(isTemporada ? "B2:J2" : "B2:O2");
            pescaEmbSheet.addMergedRegion(cellRangeTituloGen1);

            CellRangeAddress cellRangeTituloGen2 = CellRangeAddress.valueOf(isTemporada ? "B3:J3" : "B3:O3");
            pescaEmbSheet.addMergedRegion(cellRangeTituloGen2);

            // Títulos de columnas
            int rowIndex = numTableRow;
            int cellIndexTitulos = startColumn;

            Row rowTitulos = pescaEmbSheet.createRow(rowIndex);

            for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                String titulo = titulosFieldEntry.getValue();
                Cell cellTitulo = rowTitulos.createCell(cellIndexTitulos);
                cellTitulo.setCellValue(titulo);
                cellTitulo.setCellStyle(styleTitulo);

                cellIndexTitulos++;
            }

            // Llenado de datos
            int rowIndexData = numTableRow + 1;
            for (HashMap<String, Object> pescaEmb: exportsFormatted) {
                Row row = pescaEmbSheet.createRow(rowIndexData);
                int cellIndexData = startColumn;

                for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                    String value = pescaEmb.get(titulosFieldEntry.getKey()).toString();
                    Cell cell = row.createCell(cellIndexData);
                    cell.setCellValue(value);

                    cellIndexData++;
                }

                rowIndexData++;
            }

            // Fila de totales
            Row rowTotales = pescaEmbSheet.createRow(rowIndexData);
            int cellIndexTotal = startColumn;
            for (Map.Entry<String, Object> totalesFieldEntry: totalesField.entrySet()) {
                Cell cellTotal = rowTotales.createCell(cellIndexTotal);
                cellTotal.setCellValue(totalesFieldEntry.getValue().toString());

                cellIndexTotal++;
            }

            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "PescaEmbarcacion.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            reporteBook.write(fileOutputStream);
            fileOutputStream.close();

            File file = new File(path);
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            String base64Encoded = new String(encoded, StandardCharsets.UTF_8);
            exportsFile.setBase64(base64Encoded);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return exportsFile;
    }
}
