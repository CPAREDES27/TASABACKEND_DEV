package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOCargaArchivosService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JCOCargaArchivosImpl implements JCOCargaArchivosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mensaje CargaArchivo(CargaArchivoImports importsParam) throws Exception {

        Mensaje msj = new Mensaje();

        try {

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_CODE", importsParam.getP_code());
            imports.put("P_CHANGE", importsParam.getP_change());
            imports.put("P_VALIDA", importsParam.getP_valida());


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_JOB_FUENT_EXTERN);
            JCoParameterList jcoTablesExport = function.getExportParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);
            String value = jcoTablesExport.getValue(0).toString();
            msj.setMensaje(value);


        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }

    @Override
    public CargaDescargaArchivosExports CargaDescargaArchivos(CargaDescargaArchivosImports importsParam) throws Exception {

        CargaDescargaArchivosExports cda = new CargaDescargaArchivosExports();


        try {
            logger.error("base64INI");


            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TRAMA", importsParam.getI_trama());
            imports.put("I_DIRECTORIO", importsParam.getI_directorio());
            imports.put("I_FILENAME", importsParam.getI_filename());
            imports.put("I_ACCION", importsParam.getI_accion());
            imports.put("I_PROCESOBTP", importsParam.getI_procesobtp());
            imports.put("I_USER", importsParam.getI_user());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CRG_DESCRG_ARCH);
            JCoParameterList export = function.getExportParameterList();

            JCoParameterList tablas = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);
            JCoTable T_MENSAJE = tablas.getTable(Tablas.T_MENSAJE);

            Metodos me = new Metodos();
            List<HashMap<String, Object>> t_mensaje = me.ListarObjetos(T_MENSAJE);

            cda.setT_mensaje(t_mensaje);
            cda.setE_trama(export.getValue(Tablas.E_TRAMA).toString());
            cda.setMensaje("Ok");

            logger.error("base64fin");
        } catch (Exception e) {
            cda.setMensaje(e.getMessage());
        }


        return cda;
    }

    @Override
    public ImpoBtpExports CargaDinamicaArchivos(ImpoBtpImports imports) throws Exception {
        try {
            ImpoBtpExports dto = new ImpoBtpExports();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.###", symbols);
            DecimalFormat decimalFormat2 = new DecimalFormat("#.##########", symbols);

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_IMPO_BTP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            JCoParameterList tables = stfcConnection.getTableParameterList();

            importx.setValue("IP_TOPER", "T"); // Leer tablas
            importx.setValue("IP_TPCARGA", imports.getIp_tpcarga());

            EjecutarRFC exec = new EjecutarRFC();

            // Obtener los nombre de los campos
            stfcConnection.execute(destination);
            JCoParameterList exports = stfcConnection.getExportParameterList();

            String fieldNames = exports.getValue("E_FIELDNAME").toString();
            ArrayList<String> fieldsToCompare = new ArrayList<>(Arrays.asList(fieldNames.split("\\|")));

            // Guardar datos
            HashMap<String, Object> dataFieldsFile = imports.getListData().remove(0); // Se elimina el primer elemento que contiene los títulos, pero se utilizará para validar los campos
            importx.setValue("IP_TOPER", "C");

            String nombreLista = "";

            // Mapear los campos
            switch (imports.getIp_tpcarga()) {
                case "E":
                    nombreLista = "Embarcaciones";
                    break;
                case "C":
                    nombreLista = "Cuotas por armadores";
                    break;
            }

            //Evaluar que todos los campos indicados por el RFC existan en el import
            boolean fieldsAreCorrect = true;
            for (String fieldToCompare : fieldsToCompare) {
                if (!dataFieldsFile.containsKey(fieldToCompare)) {
                    fieldsAreCorrect = false;
                    break;
                }
            }

            if (fieldsAreCorrect) {
                for (HashMap<String, Object> data : imports.getListData()) {
                    // Borrar los campos de los imports que no pertenezcan a la tabla
                    Iterator<Map.Entry<String, Object>> iteratorData = data.entrySet().iterator();
                    while (iteratorData.hasNext()) {
                        Map.Entry<String, Object> entryData = iteratorData.next();
                        if (!fieldsToCompare.contains(entryData.getKey())) {
                            iteratorData.remove();
                        }
                    }

                    // Mapear campos vacíos
                    for (String fieldToCompare : fieldsToCompare) {
                        if (!data.containsKey(fieldToCompare)) {
                            data.put(fieldToCompare, "");
                        }
                    }

                    // Mapear algunos campos específicos
                    switch (imports.getIp_tpcarga()) {
                        case "E":
                            Double eslora = data.get("ESLOR").toString().equals("") ? Double.parseDouble(data.get("ESLOR").toString()) : 0;
                            Double manga = data.get("MANGA").toString().equals("") ? Double.parseDouble(data.get("MANGA").toString()) : 0;
                            Double puntual = data.get("PNTAL").toString().equals("") ? Double.parseDouble(data.get("PNTAL").toString()) : 0;
                            Double potenciaMotor = data.get("POMOTR").toString().equals("") ? Double.parseDouble(data.get("POMOTR").toString()) : 0;
                            Double arqueoNeto = data.get("ARNETO").toString().equals("") ? Double.parseDouble(data.get("ARNETO").toString()) : 0;
                            Double arqueoBruto = data.get("ARBRUTO").toString().equals("") ? Double.parseDouble(data.get("ARBRUTO").toString()) : 0;
                            Double capbodM3 = data.get("CAPBOM3").toString().equals("") ? Double.parseDouble(data.get("CAPBOM3").toString()) : 0;
                            Double capbodTM = data.get("CAPBOTM").toString().equals("") ? Double.parseDouble(data.get("CAPBOTM").toString()) : 0;

                            data.replace("ESLOR", decimalFormat.format(eslora));
                            data.replace("MANGA", decimalFormat.format(manga));
                            data.replace("PNTAL", decimalFormat.format(puntual));
                            data.replace("POMOTR", decimalFormat.format(potenciaMotor));
                            data.replace("ARNETO", decimalFormat.format(arqueoNeto));
                            data.replace("ARBRUTO", decimalFormat.format(arqueoBruto));
                            data.replace("CAPBOM3", decimalFormat.format(capbodM3));
                            data.replace("CAPBOTM", decimalFormat.format(capbodTM));

                            break;
                        case "C":
                            Double capacidadBodega = data.get("CAPBOD").toString().equals("") ? Double.parseDouble(data.get("CAPBOD").toString()) : 0;
                            Double pmce = data.get("PMCE").toString().equals("") ? Double.parseDouble(data.get("PMCE").toString()) : 0;
                            Double lmce = data.get("LMCE").toString().equals("") ? Double.parseDouble(data.get("LMCE").toString()) : 0;

                            data.replace("CAPBOD", decimalFormat2.format(capacidadBodega));
                            data.replace("PMCE", decimalFormat2.format(pmce));
                            data.replace("LMCE", decimalFormat2.format(lmce));
                            break;
                    }
                }

                //Establecer la lista para mandar
                switch (imports.getIp_tpcarga()) {
                    case "E":
                        exec.setTable(importx, Tablas.IT_ZFLEMB, imports.getListData());
                        break;
                    case "C":
                        exec.setTable(importx, Tablas.IT_CUOTAARM, imports.getListData());
                        break;
                }

                stfcConnection.execute(destination);

                JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
                Metodos me = new Metodos();
                List<HashMap<String, Object>> t_mensaje = me.ListarObjetos(T_MENSAJE);

                dto.setT_mensaje(t_mensaje);

                dto.setMensaje("Ok");
            } else {
                dto.setMensaje("Las columnas de " + nombreLista + " son incorrectas");
            }

            return dto;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
