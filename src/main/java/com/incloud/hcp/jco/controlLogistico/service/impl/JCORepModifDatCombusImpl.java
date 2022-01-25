package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.CallBAPI;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusImports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusRegExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusRegImports;
import com.incloud.hcp.jco.controlLogistico.service.JCORepModifDatCombusService;
import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JCORepModifDatCombusImpl implements JCORepModifDatCombusService {
    private Logger logger = LoggerFactory.getLogger(CallBAPI.class);
    public RepModifDatCombusExports Listar(RepModifDatCombusImports imports)throws Exception{

        RepModifDatCombusExports rmdc= new RepModifDatCombusExports();

        try {
            Metodos metodos = new Metodos();
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            List<MaestroOptions> option = imports.getOption();
            List<MaestroOptionsKey> options2 = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodos.ValidarOptions(option,options2,"DATA");
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_COMB_CONS_MODIF_DATOS);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FASE", imports.getP_fase());
            importx.setValue("P_CANT", imports.getP_cant());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC ejecutarRFC = new EjecutarRFC();

            ejecutarRFC.setTable(tables, "T_OPCIONES", tmpOptions);

            stfcConnection.execute(destination);
            JCoParameterList export=stfcConnection.getExportParameterList();
            rmdc.setP_nmob(export.getValue("P_NMOB").toString());
            rmdc.setP_nmar(export.getValue("P_NMAR").toString());

            double p_nmob= Double.parseDouble(rmdc.getP_nmob());
            double p_mar= Double.parseDouble(rmdc.getP_nmar());
            double total = Math.round((p_nmob/p_mar)*100.0)/100.0;
            total = total*100;
            logger.error("TOTAL"+ total);
            logger.error("P_NMOB"+p_nmob);
            logger.error("p_mar"+p_mar);
            logger.error("P_NMOB", rmdc.getP_nmob());
            logger.error("P_NMOB", rmdc.getP_nmar());
            JCoTable T_FLOCC = tables.getTable(Tablas.T_FLOCC);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable T_OPCIONES= tables.getTable(Tablas.T_OPCIONES);



            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            String[] fieldsT_flocc=imports.getFieldsT_flocc();

            String[] fieldsT_mensaje=imports.getFieldsT_mensaje();

            List<HashMap<String, Object>> t_flocc = metodo.ListarObjetosLazy(T_FLOCC);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetosLazy(T_OPCIONES);

            //Dominios
            ArrayList<String> listDomNames = new ArrayList<>();
            listDomNames.add(Dominios.CDFAS);
            listDomNames.add(Dominios.CDMMA);

            DominiosHelper helper = new DominiosHelper();
            ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

            DominiosExports cdfasDom = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CDFAS)).findFirst().orElse(null);
            DominiosExports cdmmaDom = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CDMMA)).findFirst().orElse(null);

            t_flocc.stream().map(s -> {
                String cdfas = s.get("CDFAS").toString();
                String cdmma = s.get("CDMMA").toString();

                DominioExportsData dataCdfas = cdfasDom.getData().stream().filter(d -> d.getId().equals(cdfas)).findFirst().orElse(null);
                DominioExportsData dataCdmma = cdmmaDom.getData().stream().filter(d -> d.getId().equals(cdmma)).findFirst().orElse(null);

                s.put("DESC_CDFAS", dataCdfas != null ? dataCdfas.getDescripcion() : "");
                s.put("DESC_CDMMA", dataCdmma != null ? dataCdmma.getDescripcion() : "");

                return s;
            }).collect(Collectors.toList());

            rmdc.setT_mensaje(t_mensaje);
            rmdc.setIndicadorPorc(total);
            rmdc.setT_flocc(t_flocc);
            rmdc.setMensaje("Ok");
        }catch (Exception e){
            rmdc .setMensaje(e.getMessage());
        }

        return rmdc;
    }

    @Override
    public RepModifDatCombusRegExports Exportar(RepModifDatCombusRegImports imports) throws Exception {
        RepModifDatCombusRegExports exports = new RepModifDatCombusRegExports();
        try {
            // Lista de campos
            HashMap<String, String> titulosField = new HashMap<>();
            titulosField.put("NMEMB", "Embarcación");
            titulosField.put("NRMAR", "Marea");
            titulosField.put("DESC_CDFAS", "Fase");
            titulosField.put("DESC_CDMMA", "Motivo de marea");
            titulosField.put("FECCONMOV", "Fec. producción");
            titulosField.put("FCMOD", "Fec. modificación");
            titulosField.put("ATMOD", "Usuario");
            titulosField.put("CNPDS", "Descarga (TN)");
            titulosField.put("OBCOM", "Texto Explicativo");

            Workbook repModifDatosCombusBook = new HSSFWorkbook();
            Sheet exportRepSheet = repModifDatosCombusBook.createSheet("Exportación SAPUI5");

            int startTableColumn = 1;
            int startTableRow = 4;
            int startTableData = startTableRow + 1;

            Font fuenteTitulo = repModifDatosCombusBook.createFont();
            fuenteTitulo.setBold(true);

            CellStyle styleTituloGeneral = repModifDatosCombusBook.createCellStyle();
            styleTituloGeneral.setFont(fuenteTitulo);
            styleTituloGeneral.setVerticalAlignment(VerticalAlignment.CENTER);

            // Título general
            Row rowTituloGeneral = exportRepSheet.createRow(1);
            Cell cellTituloGeneral = rowTituloGeneral.createCell(1);
            cellTituloGeneral.setCellValue("REPORTE DE MODIFICACIONES DE DATOS SOBRE COMBUSTIBLE");
            cellTituloGeneral.setCellStyle(styleTituloGeneral);

            // % Mod
            Row rowPorcMod = exportRepSheet.createRow(2);
            Cell cellPorcMod = rowPorcMod.createCell(1);
            cellPorcMod.setCellValue("INDICADOR DE MODIFICACIÓN: " + imports.getPorcIndMod() + "%");
            cellPorcMod.setCellStyle(styleTituloGeneral);

            // Combinación de celdas
            CellRangeAddress cellRangeTituloGeneral = CellRangeAddress.valueOf("B2:J2");
            CellRangeAddress cellRangePorcMod = CellRangeAddress.valueOf("B3:J3");

            exportRepSheet.addMergedRegion(cellRangeTituloGeneral);
            exportRepSheet.addMergedRegion(cellRangePorcMod);

            // Títulos de columnas
            int cellIndexTitulos = startTableColumn;
            Row rowTitulos = exportRepSheet.createRow(startTableRow);
            for (Map.Entry<String, String> titulosFieldsEntry: titulosField.entrySet()) {
                String titulo = titulosFieldsEntry.getValue();

                Cell cellTitulo = rowTitulos.createCell(cellIndexTitulos);

                CellStyle styleTitulo = repModifDatosCombusBook.createCellStyle();
                styleTitulo.setBorderTop(BorderStyle.THIN);
                styleTitulo.setBorderBottom(BorderStyle.THIN);
                styleTitulo.setBorderRight(BorderStyle.THIN);
                styleTitulo.setBorderLeft(BorderStyle.THIN);
                styleTitulo.setFont(fuenteTitulo);
                styleTitulo.setVerticalAlignment(VerticalAlignment.CENTER);

                cellTitulo.setCellValue(titulo);
                cellTitulo.setCellStyle(styleTitulo);

                cellIndexTitulos++;
            }

            // Datos
            int rowIndex = startTableData;
            String dataStr = "";
            for (HashMap<String, Object> itemData : imports.getData()) {
                Row row = exportRepSheet.createRow(rowIndex);
                int cellIndex = startTableColumn;

                for (Map.Entry<String, String> titulosFieldEntry: titulosField.entrySet()) {
                    String value = itemData.get(titulosFieldEntry.getKey()).toString();
                    Cell cell = row.createCell(cellIndex);
                    cell.setCellValue(value);
                    dataStr += value;

                    cellIndex++;
                }

                rowIndex++;
            }

            logger.info(dataStr);

            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Reporte de modificación de datos de combustible.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            repModifDatosCombusBook.write(fileOutputStream);
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

}
