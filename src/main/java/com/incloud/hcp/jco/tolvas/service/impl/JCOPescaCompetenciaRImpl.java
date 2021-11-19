package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.tolvas.dto.*;
import com.incloud.hcp.jco.tolvas.service.JCOPescaCompetenciaRService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOPescaCompetenciaRImpl implements JCOPescaCompetenciaRService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public PescaCompetenciaRExports PescaCompetencia(PescaCompetenciaRImports imports) throws Exception {

        PescaCompetenciaRExports pc=new PescaCompetenciaRExports();
        List<HashMap<String, Object>> t_mensaje =new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> str_ztl =new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> str_pto =new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> str_pcp=new ArrayList<HashMap<String, Object>>();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESC_DECLA_COMPE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FECHA", imports.getP_fecha());
            importx.setValue("P_CDTPC", imports.getP_cdtpc());
            importx.setValue("P_TIPO", imports.getP_tipo());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.STR_ZLT,imports.getStr_zlt());
            exec.setTable(tables, Tablas.STR_PTO,imports.getStr_pto());
            exec.setTable(tables, Tablas.STR_PCP,imports.getStr_pcp());

            stfcConnection.execute(destination);
            Metodos metodo = new Metodos();

            if(imports.getP_tipo().compareTo("G")==0){
                JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
                t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            } else {

                JCoTable STR_ZLT = tables.getTable(Tablas.STR_ZLT);
                JCoTable STR_PTO = tables.getTable(Tablas.STR_PTO);
                JCoTable STR_PCP = tables.getTable(Tablas.STR_PCP);
                JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


                str_ztl = metodo.ObtenerListObjetos(STR_ZLT, imports.getFieldStr_zlt());
                str_pto = metodo.ObtenerListObjetos(STR_PTO, imports.getFieldStr_pto());
                str_pcp = metodo.ObtenerListObjetos(STR_PCP, imports.getFieldStr_pcp());
                 t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            }

            pc.setStr_zlt(str_ztl);
            pc.setStr_pto(str_pto);
            pc.setStr_pcp(str_pcp);
            pc.setT_mensaje(t_mensaje);
            pc.setMensaje("Ok");
        }catch (Exception e){
            pc.setMensaje(e.getMessage());
        }

        return pc;

    }

    @Override
    public DescargaExportDto ejecutarPrograma(DescargaImportDto imports) throws Exception {
        DescargaExportDto dto = new DescargaExportDto();

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();

        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_JOB_DESCARGA);

        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_USER", imports.getP_user());
        importx.setValue("P_NRDES", imports.getP_nrdes());
        importx.setValue("P_CNDES", "");
        importx.setValue("P_CREA", imports.getP_crea());
        importx.setValue("P_ANUL", imports.getP_anul());
        importx.setValue("P_REIN", 0);
        stfcConnection.execute(destination);
        JCoParameterList tables = stfcConnection.getExportParameterList();
        String code = tables.getValue("W_MENSAJE").toString();

        dto.setW_mensaje(code);

        return dto;
    }

    @Override
    public PeriodoDtoExport validarPeriodo(PeriodoDtoImport imports) throws Exception {
        PeriodoDtoExport dto = new PeriodoDtoExport();
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        String[] opcions={"MJAHR = '2021'","AND RDPCA = '4'"};
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getUsuario());
            importx.setValue("QUERY_TABLE", Tablas.ZV_FLDC1);
            importx.setValue("DELIMITER", Constantes.delimiter);
            importx.setValue("NO_DATA","");
            importx.setValue("ROWSKIPS",0);
            importx.setValue("WORCOUNT",0);
            importx.setValue("P_ORDER","");
            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable tableImport = tables.getTable("OPTIONS");
            for(int i=0;i<opcions.length;i++){
                tableImport.appendRow();
                tableImport.setValue("WA",opcions[i]);
            }
            stfcConnection.execute(destination);
            JCoTable tableExport = tables.getTable("DATA");
            Metodos me= new Metodos();
            data=me.ObtenerListObjetosSinField(tableExport);
            int contador=0;


            for(Map<String,Object> datas: data){
                for(Map.Entry<String,Object> entry: datas.entrySet()){
                    contador++;
                }
            }
            String[] dataFinal=new String[contador];


            int contador2=0;
            for(Map<String,Object> datas: data){
                for(Map.Entry<String,Object> entry: datas.entrySet()){
                    String key= entry.getKey();
                    Object value= entry.getValue();
                    dataFinal[contador2]=value.toString();
                    contador2++;
                }

            }
            for(int j=0;j<dataFinal.length;j++){
                logger.error("DATAFINAL"+dataFinal[j]);
            }

        }catch (Exception e){

        }
        dto.setData(data);
        return dto;
    }
}
