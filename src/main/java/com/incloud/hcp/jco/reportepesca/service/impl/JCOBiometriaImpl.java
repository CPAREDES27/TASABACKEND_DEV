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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
            JCoParameterList paramImport = function.getImportParameterList();
            logger.error("lista import: "+function.getImportParameterList());
            logger.error("lista tablas: "+function.getTableParameterList());
            logger.error("lista export: "+function.getExportParameterList());
            logger.error("lista export: "+paramImport.getTable("IT_MAREA"));

            HashMap<String, Object> importParams = new HashMap<>();
            importParams.put("IP_OPER", imports.getIp_oper());
            // importParams.put("IT_MAREA", imports.getIt_marea());
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
            dto.setEp_mmin(paramExport.getValue("EP_MMIN").toString());
            dto.setEp_mmax(paramExport.getValue("EP_MMAX").toString());

            Metodos metodos = new Metodos();
            List<HashMap<String, Object>> et_biom = metodos.ListarObjetos(ET_BIOM);
            List<HashMap<String, Object>> et_espe = metodos.ListarObjetos(ET_ESPE);
            List<HashMap<String, Object>> et_pscinc = metodos.ListarObjetos(ET_PSCINC);


            String path=CrearExcel(ET_BIOM);
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

    private String CrearExcel(JCoTable jCoTable){
        String path="";
        Workbook workbook = new HSSFWorkbook();
        //Crea hoja nueva
        Sheet sheet = workbook.createSheet("Reporte Biometria");
        //Por cada línea se crea un arreglo de objetos (Object[])
        Map<String, Object[]> datos = new TreeMap<String, Object[]>();
        Object[] fields= ListarFields(jCoTable);
        //datos.put("1", new Object[]{"Identificador", "Nombre", "Apellidos"});
        datos.put("1", fields);



        datos.put("2", new Object[]{1, "María", "Remen"});
        datos.put("3", new Object[]{2, "David", "Allos"});
        datos.put("4", new Object[]{3, "Carlos", "Caritas"});
        datos.put("5", new Object[]{4, "Luisa", "Vitz"});
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

    private Object[] ListarFields(JCoTable jcoTable){

        JCoFieldIterator iter = jcoTable.getFieldIterator();
        int con=0;
        while (iter.hasNextField()) {
            JCoField field = iter.nextField();
            con++;
        }
        Object[] fields= new Object[con];
        int i=0;
        while (iter.hasNextField()) {
            JCoField field = iter.nextField();
            fields[i]=field.getName();
            con++;
        }

        return fields;

    }

    private List<Object[]> ListarRegistros(JCoTable jcoTable){

        List<Object[]> campos= new ArrayList<Object[]>();

        JCoFieldIterator iter = jcoTable.getFieldIterator();

        int con=0;
        while (iter.hasNextField()) {
            JCoField field = iter.nextField();
            con++;
        }
        for (int i = 0; i < jcoTable.getNumRows(); i++) {
            jcoTable.setRow(i);
            Object[] registros = new Object[con];
            int j = 0;
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = jcoTable.getValue(key);
                registros[j] = value;
                j++;
            }
            campos.add(registros);
        }



        return campos;
    }
}
