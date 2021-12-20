package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.dominios.dto.DominioDto;
import com.incloud.hcp.jco.dominios.dto.DominioParams;
import com.incloud.hcp.jco.dominios.dto.DominiosImports;
import com.incloud.hcp.jco.dominios.service.impl.JCODominiosImpl;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOEventosPescaService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JCOEventosPescaImpl implements JCOEventosPescaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());




   public EventosPescaExports ListarEventoPesca(EventosPescaImports imports)throws Exception{
       EventosPescaExports dto= new EventosPescaExports();
       try {
           logger.error("ListarEventosPesca_1");
           ;
           JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
           logger.error("ListarEventosPesca_2");
           ;
           JCoRepository repo = destination.getRepository();
           logger.error("ListarEventosPesca_3");
           ;
           JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
           JCoParameterList importx = stfcConnection.getImportParameterList();
           importx.setValue("I_FLAG", imports.getI_flag());
           importx.setValue("P_USER", imports.getP_user());
           logger.error("ListarEventosPesca_4");
           ;
           JCoParameterList tables = stfcConnection.getTableParameterList();
           stfcConnection.execute(destination);
           logger.error("ListarEventosPesca_5");

           JCoTable tableST_CCP = tables.getTable(Tablas.ST_CCP);
           logger.error("ListarEventosPesca_6");
           JCoTable tableST_CEP = tables.getTable(Tablas.ST_CEP);

           logger.error("ListarEventosPesca_7");

           Metodos metodo = new Metodos();
           List<HashMap<String, Object>> ListarST_CEP = metodo.ListarObjetos(tableST_CEP);
           List<HashMap<String, Object>> ListarST_CCP = metodo.ListarObjetos(tableST_CCP);


           dto.setSt_cep(ListarST_CEP);
           dto.setSt_ccp(ListarST_CCP);
           dto.setMensaje("Ok");
       }catch (Exception e){
           dto.setMensaje(e.getMessage());
       }

       return dto;
   }

   public Mensaje EditarEventosPesca(EventosPescaEditImports impor)throws Exception{
        Mensaje msj= new Mensaje();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_FLAG", impor.getI_flag());
            imports.put("P_USER", impor.getP_user());


            logger.error("ListarEventosPesca_1");
            ;
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
            JCoParameterList jcoTables = function.getTableParameterList();

            logger.error("ListarEventosPesca_4");
            ;

            List<HashMap<String, Object>> estcce = impor.getEstcce();
            List<HashMap<String, Object>> estcep = impor.getEstcep();

            for (Object o : estcce) {
                logger.error("RECORRER LISTA: " + o.toString());
            }
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.ESTCCE, estcce);
            exec.setTable(jcoTables, Tablas.ESTCEP, estcep);
            function.execute(destination);
            msj.setMensaje("Ok");

        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }



        return msj;
    }

    @Override
    public ConfiguracionEventoPescaExports ObtenerConfEventosPesca() throws Exception {

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repo = destination.getRepository();
        ;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE_BTP);
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("DELIMITER","|");
        importx.setValue("QUERY_TABLE",Tablas.ZFLCEP);

        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();

        tableImport.setValue("WA", "CLSIS = '01'");
        String[] field = {"QMART", "MNESP", "MXESP", "NTESP", "UMPDC", "PRCHI", "TMXCA", "UMTMX", "TMICA", "UMTMI",
                "UMPDS", "BAUGR", "HERKUNFT", "BSART", "EKORG", "EKGRP", "AUART", "VKORG", "BWART1", "VTWEG", "HRCOR"};
        stfcConnection.execute(destination);


        JCoTable tableExport = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");
        Metodos me = new Metodos();
        List<HashMap<String, Object>> ListarST_CEP =me.obtenerDataEventosPesca(tableExport,FIELDS,field);

        ConfiguracionEventoPescaExports list = new ConfiguracionEventoPescaExports();
        list.setList_EventosPesca(ListarST_CEP);

        //SEGUNDO

        JCoDestination destinations = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repos = destinations.getRepository();
        ;
        JCoFunction stfcConnections = repos.getFunction(Constantes.ZFL_RFC_READ_TABLE_BTP);
        JCoParameterList importxs = stfcConnections.getImportParameterList();

        importxs.setValue("DELIMITER","|");
        importxs.setValue("QUERY_TABLE",Tablas.ZFLCCE);

        JCoParameterList tabless = stfcConnections.getTableParameterList();
        JCoTable tableImports = tabless.getTable("OPTIONS");
        String[] fieldz = {"CDTEV", "BWART", "MATNR", "UNICB", "ATLHO"};
        stfcConnections.execute(destination);


        JCoTable tableExports = tabless.getTable("DATA");
        JCoTable FIELDSs = tabless.getTable("FIELDS");
        List<HashMap<String, Object>> Listar_ConsCombEve =me.obtenerDataEventosPesca2(tableExports,FIELDSs,fieldz);

        int contador=0;


        for(Map<String,Object> datas: ListarST_CEP){
            for(Map.Entry<String,Object> entry: datas.entrySet()){
                contador++;
            }
        }
        String[] data=new String[contador];
        logger.error("TAMANIO DATA:" +data.length);
        logger.error("TAMANIO LISTA:" + contador);

        int contador2=0;
        for(Map<String,Object> datas: ListarST_CEP){
            for(Map.Entry<String,Object> entry: datas.entrySet()){
                String key= entry.getKey();
                Object value= entry.getValue();
                data[contador2]=value.toString();
                contador2++;
            }

        }

        for(int j=0;j<data.length;j++){
            logger.error(data[j]);
        }
        list.setListar_ConsCombEve(Listar_ConsCombEve);


        //tercero

        String tabla="";
        String[] optionsEspe={"CDUMD = '"+data[8]+"'"};
        String[] optionsCHI={"CDSPC = '"+data[9]+"'"};
        String[] optionsoptionsCalaMax={"CDUMD = '"+data[16]+"'"};
        String[] optionsoptionsCalaMin={"CDUMD = '"+data[5]+"'"};
        String[]  CalaDescUMPescaDeclDesc   ={"CDUMD = '"+data[4]+"'"};
        String[]  CalaDescUMPescaDscgDesc   ={"CDUMD = '"+data[15]+"'"};
        logger.error("data[10]= "+data[15]);

        String CalaDescUMPescaDecl = data[4];
        String fields="";
        String EspeUniMedTiempExt = me.getFieldData(Tablas.ZFLUMD,optionsEspe,"MEINS");
        String CalaDescEspecieCHI = me.getFieldData(Tablas.ZFLSPC,optionsCHI,"DSSPC");
        String CalaUMTiemMaximoExt = me.getFieldData(Tablas.ZFLUMD,optionsoptionsCalaMax,"MEINS");
        String CalaUMTiemMinEntreExt = me.getFieldData(Tablas.ZFLUMD,optionsoptionsCalaMin,"MEINS");
        String CalaDescUMPescaDeclDescFinal = me.getFieldData(Tablas.ZFLUMD,CalaDescUMPescaDeclDesc,"DSUMD");
        String CalaDescUMPescaDeclDscgFinal = me.getFieldData(Tablas.ZFLUMD,CalaDescUMPescaDscgDesc,"DSUMD");

        logger.error("ERROR 1");
        //8,9,16,5
        logger.error("CADENA CALA: "+EspeUniMedTiempExt);
        list.setCalaDescUMPescaDecl(CalaDescUMPescaDecl);
        list.setCalaDescUMPescaDeclDesc(CalaDescUMPescaDeclDescFinal);
        list.setCalaDescUMPescaDscgDesc(CalaDescUMPescaDeclDscgFinal);
        list.setEspeUniMedTiempExt(EspeUniMedTiempExt);
        list.setCalaDescEspecieCHI(CalaDescEspecieCHI);
        list.setCalaUMTiemMaximoExt(CalaUMTiemMaximoExt);
        list.setCalaUMTiemMinEntreExt(CalaUMTiemMinEntreExt);
        list.setEspeUMExtValido(true);
        list.setCalaUMTiemMaxValido(true);
        list.setCalaUMTMinEntreValido(true);
        list.setCalaTiemMaximo(Double.parseDouble(data[18]));
        list.setCalaCodEspecieCHI(data[9]);
        double espeLimMin = Double.parseDouble(data[0]);
        double espeLimMax = Double.parseDouble(data[13]);
        logger.error("ERROR 2");
        if(!list.getEspeUniMedTiempExt().equals(null)){
            if (!list.getEspeUniMedTiempExt().equalsIgnoreCase("MIN")) {
                if (list.getEspeUniMedTiempExt().equalsIgnoreCase("H")) {
                    espeLimMin *= 60;
                    espeLimMax *= 60;
                } else {
                    list.setEspeUMExtValido(false);
                }
            }
        }
        logger.error("ERROR 3");
        list.setEspeMiliLimMinimo(espeLimMin*60*1000);
        list.setEspeMiliLimMaximo(espeLimMax*60*1000);
        list.setCalaTiemMinEntre(Double.parseDouble(data[7]));
        double calaTiemMax = list.getCalaTiemMaximo();
        logger.error("ERROR 4");

        if (!list.getCalaUMTiemMaximoExt().equalsIgnoreCase("MIN")) {
            if (list.getCalaUMTiemMaximoExt().equalsIgnoreCase("H")) {
                calaTiemMax *= 60;
            } else {
                list.setCalaUMTiemMaxValido(false);
            }
        }
        list.setCalaMiliTiemMaximo(calaTiemMax*60*1000);
        logger.error("ERROR 5");
        double calaTMinEntre = list.getCalaTiemMinEntre();
        if (!list.getCalaUMTiemMinEntreExt().equalsIgnoreCase("MIN")) {
            if (list.getCalaUMTiemMinEntreExt().equalsIgnoreCase("H")) {
                calaTMinEntre *= 60;
            } else {
                list.setCalaUMTMinEntreValido(false);
            }
        }
        logger.error("ERROR 6");

        list.setCalaMiliTiemMinEntre(calaTMinEntre*60*1000);
        return list;
    }
    @Override
    public HorometrosExport obtenerHorometros(HorometrosImportDto evento) throws Exception{

       HorometrosExport obj = new HorometrosExport();
       Metodos me =new Metodos();
       String[] horometros= me.obtenerHoroEvento(evento.getEvento());
       for(int i=0;i<horometros.length;i++){
           logger.error("HOROMETROS : "+horometros[i]);

       }
       String centro= "T059";
       String table="ZFLHOR";
       String[] fields ={"CDTHR"};
       String[] options = {"WERKS = '"+evento.getCentro()+"'"," AND ESREG = 'S'"," AND POINT NE ''"," "};
       String horoEve = "";
       if(horometros != null && horometros.length>0){
           horoEve += "AND (";
           for (int i = 0; i < horometros.length; i++) {
               horoEve += "CDTHR = '" + horometros[i] +  "'";
               if (i < horometros.length - 1) {
                   horoEve += " OR ";
               }
           }
           horoEve += ")";
       }
        options[3]=horoEve;

        logger.error("OPTIONS : "+horoEve);
       String[] dataAdvance = me.getFieldDataArray(table,options,fields);
        DominioParams domi = new DominioParams();
        domi.setDomname("ZCDTHR");
        domi.setStatus("A");
        List<DominioParams> lsita= new ArrayList<DominioParams>();
        lsita.add(domi);
        DominiosImports dom = new DominiosImports();
        dom.setDominios(lsita);


        DominioDto domDto = new DominioDto();
        JCODominiosImpl objs = new JCODominiosImpl();
        domDto=objs.Listar(dom);
        logger.error("DOMIDOMI:"+ domDto.getData().size());
        List<DominioHorometro> objHoro = new ArrayList<DominioHorometro>();
        for(int i=0;i<domDto.getData().size();i++){
            for(int j=0;j<domDto.getData().get(i).getData().size();j++){
                DominioHorometro objDomi = new DominioHorometro();
                objDomi.setId(domDto.getData().get(i).getData().get(j).getId());
                objDomi.setDescripcion(domDto.getData().get(i).getData().get(j).getDescripcion());
                objHoro.add(objDomi);
            }
        }
        for(int k=0;k<objHoro.size();k++){
            logger.error("DATA HOROMETRO:" + objHoro.get(k).getDescripcion());
        }

        List<HorometrosDto> listaHoro = new ArrayList<HorometrosDto>();
        logger.error("tamanioAdvance : "+dataAdvance.length);
       for(int i=0;i<dataAdvance.length;i++){
           HorometrosDto horo = new HorometrosDto();
           int data= Integer.parseInt(dataAdvance[i]);
           logger.error("entró next");
           horo.setIndicador(Constant.CARACTERNUEVO);
           logger.error("itertor: "+data);
           horo.setTipoHorometro(dataAdvance[i]);
           horo.setDescTipoHorom(objHoro.get(data-1).getDescripcion());
           listaHoro.add(horo);
       }

       //3er read table
        logger.error("PASO 01");
        String[] opcions={"NRMAR = "+evento.getMarea(),"AND NREVN = "+evento.getNroEvento()};
        JCoDestination destinations = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;logger.error("PASO 02");
        JCoRepository repos = destinations.getRepository();
        ;logger.error("PASO 03");
        JCoFunction stfcConnections = repos.getFunction(Constantes.ZFL_RFC_READ_TABLE_BTP);
        JCoParameterList importxs = stfcConnections.getImportParameterList();
        logger.error("PASO 04");
        importxs.setValue("DELIMITER","|");
        importxs.setValue("QUERY_TABLE","ZFLLHO");
        logger.error("PASO 05");
        JCoParameterList tabless = stfcConnections.getTableParameterList();
        JCoTable tableImports = tabless.getTable("OPTIONS");
        for(int i=0;i<opcions.length;i++){
            tableImports.appendRow();
            tableImports.setValue("WA",opcions[i]);
        }

        stfcConnections.execute(destinations);
        String[] fieldz = {"CDTHR","LCHOR", "NORAV"};
        logger.error("PASO 00");
        JCoTable tableExports = tabless.getTable("DATA");
        JCoTable FIELDSs = tabless.getTable("FIELDS");
        List<HashMap<String, Object>> Listar_ConsCombEve =me.obtenerLectHormoetros(tableExports,FIELDSs,fieldz);
        List<HorometroExportDto> listHoro = new ArrayList<HorometroExportDto>();
        logger.error("PASO 0");
        int contador=0;
        for(Map<String,Object> datas: Listar_ConsCombEve){
            for(Map.Entry<String,Object> entry: datas.entrySet()){
                contador++;
            }
        }
        logger.error("PASO1");
        String[] data=new String[contador];
        int contador2=0;
        logger.error("tamanio lista inicio:" +listaHoro.size());

        for(Map<String,Object> datas: Listar_ConsCombEve){
            HorometroExportDto objHorom = new HorometroExportDto();
            for(Map.Entry<String,Object> entry: datas.entrySet()){
                String key= entry.getKey();
                Object value= entry.getValue();
                logger.error("KEYCITO:" + key + " " + value);
                if(key.equals("LCHOR")){
                    objHorom.setLCHOR(value.toString());
                }
                if(key.equals("NORAV")){
                    objHorom.setNORAV(value.toString());
                }
                if(key.equals("CDTHR")){
                    objHorom.setCDTHR(value.toString());
                }
            }
            listHoro.add(objHorom);
            logger.error("tamanio lista add:" +listaHoro.size());
        }

        logger.error("tamanio lista final:" +listaHoro.size());
        logger.error("PASO2");
        for(int i=0;i<listHoro.size();i++){
            for(int k=0;k<listaHoro.size();k++){
                if(listaHoro.get(k).getTipoHorometro().equals(listHoro.get(i).getCDTHR())){
                    listaHoro.get(k).setLectura(listHoro.get(i).getLCHOR());
                    listaHoro.get(k).setAveriado(listHoro.get(i).getNORAV());
                    break;
                }
            }
        }

       obj.setLista(listaHoro);
       return obj;
    }

    public AnularDescargaExports AnularDescarga(AnularDescargaImports imports)throws Exception{

        AnularDescargaExports dto= new AnularDescargaExports();

        try{
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ANULA_DESCA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_NRDES", imports.getP_nrdes());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_MENSAJE=tables.getTable(Tablas.T_MENSAJE);
            Metodos me= new Metodos();
            List<HashMap<String , Object>>t_mensaje=me.ListarObjetos(T_MENSAJE);

            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");


        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }

        return dto;

    }


}
