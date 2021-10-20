package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.controlLogistico.service.JCOConsultaHorometroService;
import com.incloud.hcp.jco.gestionpesca.dto.HorometroExport;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOConsultaHorometroImpl implements JCOConsultaHorometroService {

    public ConsultaHorometroExports Listar(ConsultaHorometroImports imports)throws Exception{

        ConsultaHorometroExports ch= new ConsultaHorometroExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_HORO);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FIEVN", imports.getP_fievn());
            importx.setValue("P_FFEVN", imports.getP_ffevn());
            importx.setValue("P_CDEMB", imports.getP_cdemb());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);


            JCoTable STR_EMB = tables.getTable(Tablas.STR_EMB);
            JCoTable STR_EVN = tables.getTable(Tablas.STR_EVN);
            JCoTable STR_LHO= tables.getTable(Tablas.STR_LHO);
            JCoTable T_MENSAJE= tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();

            List<HashMap<String, Object>> str_emb = metodo.ObtenerListObjetos(STR_EMB, imports.getFieldStr_emb());
            List<HashMap<String, Object>> str_evn = metodo.ObtenerListObjetos(STR_EVN, imports.getFieldStr_evn());
            List<HashMap<String, Object>> str_lho = metodo.ObtenerListObjetos(STR_LHO, imports.getFieldStr_lho());
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldT_mensaje());
            List<HorometroListDto> lista =new ArrayList<HorometroListDto>();
            for(Map<String,Object> datas: str_evn) {
                HorometroListDto horo = new HorometroListDto();
                for (Map.Entry<String, Object> entry : datas.entrySet()) {
                    String key= entry.getKey();
                    Object value= entry.getValue();
                    if(key.equals("NRMAR")){
                        horo.setNRMAR(value.toString());
                    }
                    if(key.equals("NREVN")){
                        horo.setNREVN(value.toString());
                    }
                    if(key.equals("FIEVN")){
                        horo.setFIEVN(value.toString());
                    }
                }
                lista.add(horo);
            }

            List<HorometroStrDto> listaStr = new ArrayList<HorometroStrDto>();
            for(Map<String,Object> datas: str_lho) {
                HorometroStrDto horoStr = new HorometroStrDto();
                for (Map.Entry<String, Object> entry : datas.entrySet()) {
                    String key= entry.getKey();
                    Object value= entry.getValue();
                    if(key.equals("NRMAR")){
                        horoStr.setNRMAR(value.toString());
                    }
                    if(key.equals("NREVN")){
                        horoStr.setNREVN(value.toString());
                    }
                    if(key.equals("CDTHR")){
                        horoStr.setCDTHR(value.toString());
                    }
                    if(key.equals("LCHOR")){
                        horoStr.setLCHOR(value.toString());
                    }
                }
                listaStr.add(horoStr);
            }

            for(int i=0;i<lista.size();i++){
                List<MotorDto> motorobj = new ArrayList<MotorDto>();
                for(int j=0;j<listaStr.size();j++){
                    MotorDto obj = new MotorDto();
                    if(lista.get(i).getNRMAR().equals(listaStr.get(j).getNRMAR()) && lista.get(i).getNREVN().equals(listaStr.get(j).getNREVN())){
                        obj.setLCHOR(listaStr.get(j).getLCHOR());
                        obj.setCDTHR(listaStr.get(j).getCDTHR());
                        motorobj.add(obj);
                    }
                }
                lista.get(i).setLista(motorobj);
            }


            ch.setListaHorometro(lista);
            ch.setT_mensaje(t_mensaje);
            ch.setMensaje("Ok");
        }catch (Exception e){
            ch .setMensaje(e.getMessage());
        }

        return ch;
    }

}
