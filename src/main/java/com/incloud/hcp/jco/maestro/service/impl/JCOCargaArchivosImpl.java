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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
            ArrayList<String> fields = new ArrayList<>(dataFieldsFile.keySet());
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
                    for (String key : data.keySet()) {
                        if (!fieldsToCompare.contains(key)) {
                            data.remove(key);
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

            /*
            switch (imports.getIp_tpcarga()) {
                case "E": //Mapear la lista de embarcaciones
                    for (HashMap<String, Object> embarcacion : imports.getListData()) {
                        HashMap<String, Object> embarcacionMatch = new HashMap<>();
                        String nombreEmbarcacion = embarcacion.get("EMBARCACION") != null ? embarcacion.get("EMBARCACION").toString() : "";
                        String matricula = embarcacion.get("MATRICULA") != null ? embarcacion.get("MATRICULA").toString() : "";
                        String casco = embarcacion.get("CASCO") != null ? embarcacion.get("CASCO").toString() : "";
                        String regimen = embarcacion.get("REGIMEN") != null ? embarcacion.get("REGIMEN").toString() : "";
                        String tipoPreservacion = embarcacion.get("TIPO PRESEVACION") != null ? embarcacion.get("TIPO PRESEVACION").toString() : "";

                        Double eslora = embarcacion.get("ESLORA") != null ? Double.parseDouble(embarcacion.get("ESLORA").toString()) : 0;
                        String esloraFormat = decimalFormat.format(eslora);

                        Double manga = embarcacion.get("MANGA") != null ? Double.parseDouble(embarcacion.get("MANGA").toString()) : 0;
                        String mangaFormat = decimalFormat.format(manga);

                        Double puntual = embarcacion.get("PUNTAL") != null ? Double.parseDouble(embarcacion.get("PUNTAL").toString()) : 0;
                        String puntualFormat = decimalFormat.format(puntual);

                        String marcaMotor = embarcacion.get("MARCA MOTOR") != null ? embarcacion.get("MARCA MOTOR").toString() : "";
                        String modeloMotor = embarcacion.get("MODELO MOTOR") != null ? embarcacion.get("MODELO MOTOR").toString() : "";
                        String nroSerieMotor = embarcacion.get("NRO SERIE MOTOR") != null ? embarcacion.get("NRO SERIE MOTOR").toString() : "";

                        Double potenciaMotor = embarcacion.get("POTENCIA MOTOR") != null ? Double.parseDouble(embarcacion.get("POTENCIA MOTOR").toString()) : 0;
                        String potenciaMotorFormat = decimalFormat.format(potenciaMotor);

                        Double arqueoNeto = embarcacion.get("ARQUEO NETO") != null ? Double.parseDouble(embarcacion.get("ARQUEO NETO").toString()) : 0;
                        String arqueoNetoFormat = decimalFormat.format(arqueoNeto);

                        Double arqueoBruto = embarcacion.get("ARQUEO BRUTO") != null ? Double.parseDouble(embarcacion.get("ARQUEO BRUTO").toString()) : 0;
                        String arqueoBrutoFormat = decimalFormat.format(arqueoBruto);

                        Double capbodM3 = embarcacion.get("CAPBOD_M3") != null ? Double.parseDouble(embarcacion.get("CAPBOD_M3").toString()) : 0;
                        String capbodM3Format = decimalFormat.format(capbodM3);

                        Double capbodTM = embarcacion.get("CAPBOD_TM") != null ? Double.parseDouble(embarcacion.get("CAPBOD_TM").toString()) : 0;
                        String capbodTMFormat = decimalFormat.format(capbodTM);

                        String resolucion = embarcacion.get("RESOLUCION") != null ? embarcacion.get("RESOLUCION").toString() : "";
                        String permisoPesca = embarcacion.get("PERMISO PESCA") != null ? embarcacion.get("PERMISO PESCA").toString() : "";
                        String permisoZarpe = embarcacion.get("PERMISO ZARPE") != null ? embarcacion.get("PERMISO ZARPE").toString() : "";
                        String codigoPago = embarcacion.get("CODIGO PAGO") != null ? embarcacion.get("CODIGO PAGO").toString() : "0";
                        String transmisor = embarcacion.get("TRANSMISOR") != null ? embarcacion.get("TRANSMISOR").toString() : "0";
                        String armador = embarcacion.get("ARMADOR") != null ? embarcacion.get("ARMADOR").toString() : "";
                        String ruc = embarcacion.get("RUC") != null ? embarcacion.get("RUC").toString() : "";
                        String precinto = embarcacion.get("PRECINTO") != null ? embarcacion.get("PRECINTO").toString() : "";
                        String aparejo = embarcacion.get("APAREJO") != null ? embarcacion.get("APAREJO").toString() : "";
                        String especieChi = embarcacion.get("ESPECIE CHI") != null ? embarcacion.get("ESPECIE CHI").toString() : "";
                        String especieChd = embarcacion.get("ESPECIE CHD") != null ? embarcacion.get("ESPECIE CHD").toString() : "";
                        String especieChiVigentes = embarcacion.get("ESPECIE CHI-VIGENTES") != null ? embarcacion.get("ESPECIE CHI-VIGENTES").toString() : "";
                        String especieChdVigentes = embarcacion.get("ESPECIE CHD-VIGENTES") != null ? embarcacion.get("ESPECIE CHD-VIGENTES").toString() : "";
                        String pmceNorteCentro = embarcacion.get("PMCE NORTE-CENTRO") != null ? embarcacion.get("PMCE NORTE-CENTRO").toString() : "0";
                        String pmceSur = embarcacion.get("PMCE SUR") != null ? embarcacion.get("PMCE SUR").toString() : "0";
                        String nominadaNorteCentro = embarcacion.get("NOMINADA NORTE-CENTRO") != null ? embarcacion.get("NOMINADA NORTE-CENTRO").toString() : "";
                        String nominadaSur = embarcacion.get("NOMINADA SUR") != null ? embarcacion.get("NOMINADA SUR").toString() : "";
                        String convenioNorteCentro = embarcacion.get("CONVENIO NORTE-CENTRO") != null ? embarcacion.get("CONVENIO NORTE-CENTRO").toString() : "";
                        String convenioSur = embarcacion.get("CONVENIO SUR") != null ? embarcacion.get("CONVENIO SUR").toString() : "";
                        String grupoNorteCentro = embarcacion.get("GRUPO NORTE-CENTRO") != null ? embarcacion.get("GRUPO NORTE-CENTRO").toString() : "";
                        String grupoSur = embarcacion.get("GRUPO SUR") != null ? embarcacion.get("GRUPO SUR").toString() : "";

                        embarcacionMatch.put("NMEMB", nombreEmbarcacion);
                        embarcacionMatch.put("MATRI", matricula);
                        embarcacionMatch.put("CASCO", casco);
                        embarcacionMatch.put("REGIM", regimen);
                        embarcacionMatch.put("TPRES", tipoPreservacion);
                        embarcacionMatch.put("ESLOR", esloraFormat);
                        embarcacionMatch.put("MANGA", mangaFormat);
                        embarcacionMatch.put("PNTAL", puntualFormat);
                        embarcacionMatch.put("MAMOTR", marcaMotor);
                        embarcacionMatch.put("MOMOTR", modeloMotor);
                        embarcacionMatch.put("SEMOTR", nroSerieMotor);
                        embarcacionMatch.put("POMOTR", potenciaMotorFormat);
                        embarcacionMatch.put("ARNETO", arqueoNetoFormat);
                        embarcacionMatch.put("ARBRUTO", arqueoBrutoFormat);
                        embarcacionMatch.put("CAPBOM3", capbodM3Format);
                        embarcacionMatch.put("CAPBOTM", capbodTMFormat);
                        embarcacionMatch.put("RESOL", resolucion);
                        embarcacionMatch.put("PERPESC", permisoPesca);
                        embarcacionMatch.put("PERZARP", permisoZarpe);
                        embarcacionMatch.put("CODPAGO", codigoPago);
                        embarcacionMatch.put("TRANSMI", transmisor);
                        embarcacionMatch.put("ARMDOR", armador);
                        embarcacionMatch.put("PRECNT", precinto);
                        embarcacionMatch.put("APAREJO", aparejo);
                        embarcacionMatch.put("ESPECHI", especieChi);
                        embarcacionMatch.put("ESPECHD", especieChd);
                        embarcacionMatch.put("PNORCEN", pmceNorteCentro);
                        embarcacionMatch.put("PMCESUR", pmceSur);
                        embarcacionMatch.put("NNORCEN", nominadaNorteCentro);
                        embarcacionMatch.put("NOMISUR", nominadaSur);
                        embarcacionMatch.put("CONOCEN", convenioNorteCentro);
                        embarcacionMatch.put("CONVSUR", convenioSur);
                        embarcacionMatch.put("GNORCEN", grupoNorteCentro);
                        embarcacionMatch.put("GRUPSUR", grupoSur);

                        listEmbarcaciones.add(embarcacionMatch);
                    }

                    exec.setTable(importx, Tablas.IT_ZFLEMB, listEmbarcaciones);
                    break;
                case "C": // Mapear cuotas armadores
                    for (HashMap<String, Object> cuotaArmador : imports.getListData()) {
                        HashMap<String, Object> cuotaArmadorMatch = new HashMap<>();

                        if (!cuotaArmador.containsKey("MATRICULA")) {
                            cuotaArmador.put("MATRICULA", "");
                        }

                        if (!cuotaArmador.containsKey("TEMPORADA")) {
                            cuotaArmador.put("TEMPORADA", "");
                        }

                        if (!cuotaArmador.containsKey("NOMBEP")) {
                            cuotaArmador.put("NOMBEP", "");
                        }

                        if (cuotaArmador.containsKey("CAPBOD")) {
                            Double capacidadBodega = Double.parseDouble(cuotaArmador.get("CAPBOD").toString());
                            cuotaArmador.replace("CAPBOD", decimalFormat2.format(capacidadBodega));
                        } else {
                            cuotaArmador.put("CAPBOD", "0");
                        }

                        if (!cuotaArmador.containsKey("GPOEMPRESA")) {
                            cuotaArmador.put("GPOEMPRESA", "");
                        }

                        if (cuotaArmador.containsKey("PMCE")) {
                            Double pmce = Double.parseDouble(cuotaArmador.get("PMCE").toString());
                            cuotaArmador.replace("PMCE", decimalFormat2.format(pmce));
                        } else {
                            cuotaArmador.put("PMCE", "0");
                        }

                        if (cuotaArmador.containsKey("LMCE")) {
                            Double lmce = Double.parseDouble(cuotaArmador.get("LMCE").toString());
                            cuotaArmador.replace("LMCE", decimalFormat2.format(lmce));
                        } else {
                            cuotaArmador.put("LMCE", "0");
                        }

                        if (!cuotaArmador.containsKey("CLEP")) {
                            cuotaArmador.put("CLEP", "");
                        }

                        if (!cuotaArmador.containsKey("CODARMD")) {
                            cuotaArmador.put("CODARMD", "");
                        }
                    }
                    exec.setTable(importx, Tablas.IT_CUOTAARM, listCuotasArmadores);
                    break;
            }

             */


            return dto;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
