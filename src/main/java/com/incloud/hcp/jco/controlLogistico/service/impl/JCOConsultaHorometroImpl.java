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

            //List<HashMap<String, Object>> str_emb = metodo.ObtenerListObjetos(STR_EMB, imports.getFieldStr_emb());
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
                    if(key.equals("CDEMB")){
                        horo.setCDEMB(value.toString());
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

            List<HorometroExportDto> listaHorometro = new ArrayList<HorometroExportDto>();
            for(int i=0;i<lista.size();i++){
                HorometroExportDto obj = new HorometroExportDto();
                for(int j=0;j<lista.get(i).getLista().size();j++){

                    String cdemb=lista.get(i).getCDEMB();
                    String[]detalleEmbar=BuscarDetalleEmbarcacion(STR_EMB, cdemb);
                    obj.setMatricula(detalleEmbar[0]);
                    obj.setNombreEmbarcacion(detalleEmbar[1]);
                    obj.setFlota(detalleEmbar[2]);

                    obj.setFecha(lista.get(i).getFIEVN());
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("1")){
                        obj.setMotorPrincipal(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("2")){
                        obj.setMotorAuxiliar(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("3")){
                        obj.setMotorAuxiliar2(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("4")){
                        obj.setMotorAuxiliar3(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("5")){
                        obj.setMotorAuxiliar4(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("6")){
                        obj.setMotorAuxiliar5(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("7")){
                        obj.setPanga(lista.get(i).getLista().get(j).getLCHOR());
                    }
                    if(lista.get(i).getLista().get(j).getCDTHR().equals("8")){
                        obj.setFlujometro(lista.get(i).getLista().get(j).getLCHOR());
                    }

                }
                listaHorometro.add(obj);
            }


            //ch.setStr_emb(str_emb);
            ch.setListaHorometro(listaHorometro);
            ch.setT_mensaje(t_mensaje);
            ch.setMensaje("Ok");
        }catch (Exception e){
            ch .setMensaje(e.getMessage());
        }

        return ch;
    }

    public String[] BuscarDetalleEmbarcacion(JCoTable str_emb, String codEmbarca){

        String[]stremb=new String[3];
        for(int i=0; i<str_emb.getNumRows();i++){
            str_emb.setRow(i);

            String codEmb=str_emb.getString("CDEMB");
            if(codEmb.equals(codEmbarca)){
                String matricula=str_emb.getString("MREMB");
                String nombreEmbarca=str_emb.getString("NMEMB");
                String flota=str_emb.getString("DSGFL").replace("\\","");

                stremb[0]=matricula;
                stremb[1]=nombreEmbarca;
                stremb[2]=flota;
            }

        }
        return stremb;
    }

}
