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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JCOCargaArchivosImpl implements JCOCargaArchivosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mensaje CargaArchivo(CargaArchivoImports importsParam) throws Exception {

        Mensaje msj= new Mensaje();

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
           String value= jcoTablesExport.getValue(0).toString();
            msj.setMensaje(value);


        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }

    @Override
    public CargaDescargaArchivosExports CargaDescargaArchivos(CargaDescargaArchivosImports importsParam) throws Exception {

        CargaDescargaArchivosExports cda=new CargaDescargaArchivosExports();


        try {
            logger.error("base64INI");


            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TRAMA", importsParam.getI_trama());
            imports.put("I_DIRECTORIO", importsParam.getI_directorio());
            imports.put("I_FILENAME", importsParam.getI_filename());
            imports.put("I_ACCION", importsParam.getI_accion());
            imports.put("I_PROCESOBTP",importsParam.getI_procesobtp());
            imports.put("I_USER",importsParam.getI_user());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CRG_DESCRG_ARCH);
            JCoParameterList export = function.getExportParameterList();

            JCoParameterList tablas = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);
            JCoTable T_MENSAJE= tablas.getTable(Tablas.T_MENSAJE);

            Metodos me=new Metodos();
            List<HashMap<String, Object>> t_mensaje= me.ListarObjetos(T_MENSAJE);

            cda.setT_mensaje(t_mensaje);
            cda.setE_trama(export.getValue(Tablas.E_TRAMA).toString());
            cda.setMensaje("Ok");

            logger.error("base64fin");
        }catch (Exception e){
            cda.setMensaje(e.getMessage());
        }


        return cda;
    }

    @Override
    public ImpoBtpExports CargaDinamicaArchivos(ImpoBtpImports imports) throws Exception {
        try {
            ImpoBtpExports dto=new ImpoBtpExports();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.###", symbols);

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_IMPO_BTP);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPER", "C");
            importx.setValue("IP_TPCARGA", imports.getIp_tpcarga());
            //mportx.setTa("IT_CUOTAARM", imports.getIt_cuotaarm());
            //importx.setValue("IT_ZFLEMB", imports.getIt_zflemb());

            //Mapear la lista de embarcaciones
            List<HashMap<String, String>> listMatchEmbarcacionesKey = new ArrayList<>();
            List<HashMap<String, Object>> listEmbarcaciones = new ArrayList<>();

            //Obtener los nombres de los campos originales (excel)
            List<String> listOriginalKeys = imports.getEmbarcaciones().get(0).keySet().stream().collect(Collectors.toList());

            // Campos de las tablas de SAP
            String[] listKeys={"INDEX", "NMEMB", "MATRI", "CASCO", "REGIM", "TPRES", "ESLOR", "MANGA", "PNTAL", "MAMOTR", "MOMOTR", "SEMOTR", "POMOTR", "ARNETO", "ARBRUTO", "CAPBOM3", "CAPBOTM", "RESOL", "PERPESC", "PERZARP", "CODPAGO", "TRANSMI", "ARMDOR", "PRECNT", "APAREJO", "ESPECHI", "ESPECHD", "PNORCEN", "PMCESUR", "NNORCEN", "NOMISUR", "CONOCEN", "CONVSUR", "GNORCEN", "GRUPSUR"};

            for (String key: listKeys) {

            }

            for (int i = 0; i < imports.getEmbarcaciones().size(); i++) {
                HashMap<String,Object> embarcacion = imports.getEmbarcaciones().get(i);

                String index = String.valueOf(i + 1);
                HashMap<String, Object> embarcacionMatch = new HashMap<>();
                String nombreEmbarcacion = embarcacion.get("EMBARCACION").toString();
                String matricula = embarcacion.get("MATRICULA").toString();
                String casco = embarcacion.get("CASCO").toString();
                String regimen = embarcacion.get("REGIMEN").toString();
                String tipoPreservacion = embarcacion.get("TIPO PRESEVACION").toString();

                Double eslora = Double.parseDouble(embarcacion.get("ESLORA").toString());
                String esloraFormat = decimalFormat.format(eslora);

                Double manga = Double.parseDouble(embarcacion.get("MANGA").toString());
                String mangaFormat = decimalFormat.format(manga);

                Double puntual = Double.parseDouble(embarcacion.get("PUNTAL").toString());
                String puntualFormat = decimalFormat.format(puntual);

                String marcaMotor = embarcacion.get("MARCA MOTOR").toString();
                String modeloMotor = embarcacion.get("MODELO MOTOR").toString();
                String nroSerieMotor = embarcacion.get("NRO SERIE MOTOR").toString();
                String potenciaMotor = embarcacion.get("POTENCIA MOTOR").toString();
                String arqueoNeto = embarcacion.get("ARQUEO NETO").toString();
                String arqueoBruto = embarcacion.get("ARQUEO BRUTO").toString();
                String capbodM3 = embarcacion.get("CAPBOD_M3").toString();
                String capbodTM = embarcacion.get("CAPBOD_TM").toString();
                String resolucion = embarcacion.get("RESOLUCION").toString();
                String permisoPesca = embarcacion.get("PERMISO PESCA").toString();
                String permisoZarpe = embarcacion.get("PERMISO ZARPE").toString();
                String codigoPago = embarcacion.get("CODIGO PAGO").toString();
                String transmisor = embarcacion.get("TRANSMISOR").toString();
                String armador = embarcacion.get("ARMADOR").toString();
                String ruc = embarcacion.get("RUC").toString();
                String precinto = embarcacion.get("PRECINTO").toString();
                String aparejo = embarcacion.get("APAREJO").toString();
                String especieChi = embarcacion.get("ESPECIE CHI").toString();
                String especieChd = embarcacion.get("ESPECIE CHD").toString();
                String especieChiVigentes = embarcacion.get("ESPECIE CHI-VIGENTES").toString();
                String especieChdVigentes = embarcacion.get("ESPECIE CHD-VIGENTES").toString();
                String pmceNorteCentro = embarcacion.get("PMCE NORTE-CENTRO").toString();
                String pmceSur = embarcacion.get("PMCE SUR").toString();
                String nominadaNorteCentro = embarcacion.get("NOMINADA NORTE-CENTRO").toString();
                String nominadaSur = embarcacion.get("NOMINADA SUR").toString();
                String convenioNorteCentro = embarcacion.get("CONVENIO NORTE-CENTRO").toString();
                String convenioSur = embarcacion.get("CONVENIO SUR").toString();
                String grupoNorteCentro = embarcacion.get("GRUPO NORTE-CENTRO").toString();
                String grupoSur = embarcacion.get("GRUPO SUR").toString();

                embarcacionMatch.put("INDEX", index);
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
                embarcacionMatch.put("POMOTR", potenciaMotor);
                embarcacionMatch.put("ARNETO", arqueoNeto);
                embarcacionMatch.put("ARBRUTO", arqueoBruto);
                embarcacionMatch.put("CAPBOM3", capbodM3);
                embarcacionMatch.put("CAPBOTM", capbodTM);
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

            /*
            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            Metodos me = new Metodos();
            List<HashMap<String, Object>> t_mensaje = me.ListarObjetos(T_MENSAJE);*/

            dto.setT_mensaje(listEmbarcaciones);

            return dto;
        } catch (Exception ex) {
            throw new  Exception(ex.getMessage());
        }
    }
}
