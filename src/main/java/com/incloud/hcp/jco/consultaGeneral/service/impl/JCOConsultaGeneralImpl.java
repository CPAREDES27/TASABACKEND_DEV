package com.incloud.hcp.jco.consultaGeneral.service.impl;

import com.incloud.hcp.jco.consultaGeneral.dto.*;
import com.incloud.hcp.jco.consultaGeneral.service.JCOConsultaGeneralService;
import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.util.EjecutarRFC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOConsultaGeneralImpl implements JCOConsultaGeneralService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JCODominiosService jcoDominiosService;

    public ConsultaGeneralExports ConsultaGeneral(ConsultaGeneralImports importsParam)throws Exception{
        logger.error("CONSULTA GENERAL");
        ConsultaGeneralExports dto=new ConsultaGeneralExports();

        MaestroExport me;

        try {
            String tabla=(Buscartabla(importsParam.getNombreConsulta()));

            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", tabla);
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "200");
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_ORDER", "");
            //setear mapeo de tabla options

            List<MaestroOptions> options =
                    BuscarOptions(importsParam.getNombreConsulta(), importsParam.getParametro1(), importsParam.getParametro2()
                            , importsParam.getParametro3(), importsParam.getParametro4(), importsParam.getParametro5());
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }

            String []fields= BuscarFields(importsParam.getNombreConsulta());

            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

            if(importsParam.getNombreConsulta().equals("CONSGENPESDESC")) {
                List<HashMap<String, Object>> data = ListaDataConDominio(me, importsParam.getNombreConsulta());
                dto.setData(data);
            }else{
                dto.setData(me.getData());
            }

            dto.setMensaje("Ok");
        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    public String Buscartabla(String nombreConsultaG){

        String tabla="";

        switch (nombreConsultaG){
            case "CONSGENCODTIPRE":
                tabla= ConsultaGeneralTablas.CONSGENCODTIPRE;
                break;
            case "CONSGENLISTEQUIP":
                tabla= ConsultaGeneralTablas.CONSGENLISTEQUIP;
                break;
            case "CONSGENCOOZONPES":
                tabla= ConsultaGeneralTablas.CONSGENCOOZONPES;
                break;
            case "CONSGENPESDECLA":
                tabla= ConsultaGeneralTablas.CONSGENPESDECLA;
                break;
            case "CONSGENLISTBODE":
                tabla= ConsultaGeneralTablas.CONSGENLISTBODE;
                break;
            case "CONSGENPESBODE":
                tabla= ConsultaGeneralTablas.CONSGENPESBODE;
                break;
            case "CONSGENPUNTDES":
                tabla= ConsultaGeneralTablas.CONSGENPUNTDES;
                break;
            case "CONSGENPESDESC":
               tabla= ConsultaGeneralTablas.CONSGENPESDESC;

                break;


        }
        logger.error("tabla= "+tabla);
        return tabla;
    }

    public String[] BuscarFields(String nombreConsultaG){

        String[]fields={};

        switch (nombreConsultaG){
            case "CONSGENCODTIPRE":
                fields= ConsultaGeneralFields.CONSGENCODTIPRE;
                break;
            case "CONSGENLISTEQUIP":
                fields= ConsultaGeneralFields.CONSGENLISTEQUIP;
                break;
            case "CONSGENCOOZONPES":
                fields= ConsultaGeneralFields.CONSGENCOOZONPES;
                break;
            case "CONSGENPESDECLA":
                fields= ConsultaGeneralFields.CONSGENPESDECLA;
                break;
            case "CONSGENLISTBODE":
                fields= ConsultaGeneralFields.CONSGENLISTBODE;
                break;
            case "CONSGENPESBODE":
                fields= ConsultaGeneralFields.CONSGENPESBODE;
                break;
            case "CONSGENPUNTDES":
                fields= ConsultaGeneralFields.CONSGENPUNTDES;
                break;
            case "CONSGENPESDESC":
                fields= ConsultaGeneralFields.CONSGENPESDESC;
                 break;


        }


        return fields;
    }

    public List<MaestroOptions> BuscarOptions(String nombreAyuda, String parametro1, String parametro2,
                                              String parametro3, String parametro4, String parametro5){

        List<MaestroOptions> options= new ArrayList<>();

        MaestroOptions opt= new MaestroOptions();


        if(nombreAyuda.equals("CONSGENCODTIPRE") || nombreAyuda.equals("CONSGENLISTEQUIP") || nombreAyuda.equals("CONSGENCOOZONPES")
                ||nombreAyuda.equals("CONSGENPESDECLA") || nombreAyuda.equals("CONSGENLISTBODE") || nombreAyuda.equals("CONSGENPESBODE ")
                || nombreAyuda.equals("CONSGENPUNTDES") || nombreAyuda.equals("CONSGENPESDESC")){


            String condicion="";

            switch (nombreAyuda){
                case "CONSGENCODTIPRE":
                    condicion= ConsultaGeneralOptions.CONSGENCODTIPRE + parametro1+"'";
                    opt.setWa(condicion);
                    break;
                case "CONSGENLISTEQUIP":
                    condicion= ConsultaGeneralOptions.CONSGENLISTEQUIP + parametro1+ ConsultaGeneralOptions.CONSGENLISTEQUIP_ ;
                    opt.setWa(condicion);
                    break;
                case "CONSGENCOOZONPES":
                    condicion= ConsultaGeneralOptions.CONSGENCOOZONPES + parametro1+"'";
                    opt.setWa(condicion);
                    break;
                case "CONSGENPESDECLA":
                    condicion= ConsultaGeneralOptions.CONSGENPESDECLA + parametro1+ ConsultaGeneralOptions.CONSGENPESDECLA_+ parametro2+"'";
                    opt.setWa(condicion);
                    break;
                case "CONSGENLISTBODE":
                    condicion= ConsultaGeneralOptions.CONSGENLISTBODE + parametro1+ ConsultaGeneralOptions.CONSGENLISTBODE_;
                    opt.setWa(condicion);
                    break;
                case "CONSGENPESBODE":
                    condicion= ConsultaGeneralOptions.CONSGENPESBODE + parametro1+ ConsultaGeneralOptions.CONSGENPESBODE_+ parametro2+"'";
                    opt.setWa(condicion);
                    break;
                case "CONSGENPUNTDES":
                    condicion= ConsultaGeneralOptions.CONSGENPUNTDES +parametro1+"'";
                    opt.setWa(condicion);
                    break;
                case "CONSGENPESDESC":
                    condicion= ConsultaGeneralOptions.CONSGENPESDESC +parametro1+"'";
                    opt.setWa(condicion);
                    break;



            }
            logger.error("option= "+opt.getWa());
            options.add(opt);
        }




        return options;
    }

    public HashMap<String, Object> BuscarNombreDominio(String nomConsulta){
        logger.error("BuscarNombreDominio");
        HashMap<String, Object>data= new HashMap<>();

        switch (nomConsulta){
            case "CONSGENPESDESC":
                data.put("DOMINIO", "ZCDLDS");
                data.put("CAMPO", "CDLDS");
                break;
        }
      //  logger.error("data1: "+data.get(0).toString());

        return data;
    }

    public String BuscarDominio(String nomDomino, String valor)throws Exception{
        logger.error("BuscarDominio");

        String descripcion="";

        DominioParams dominioParams= new DominioParams();
        dominioParams.setDomname(nomDomino);
        dominioParams.setStatus("A");

        List<DominioParams> ListDominioParams= new ArrayList<>();
        ListDominioParams.add(dominioParams);

        DominiosImports dominiosImports=new DominiosImports();
        dominiosImports.setDominios(ListDominioParams);

        DominioDto dominioDto=jcoDominiosService.Listar(dominiosImports);

        List<DominiosExports>ListaDomExports=dominioDto.getData();

        for(int i=0; i<ListaDomExports.size();i++){
            DominiosExports dominiosExports=ListaDomExports.get(i);
            List<DominioExportsData> ListaDomExportData=dominiosExports.getData();

            for(int j=0; j<ListaDomExportData.size();j++){
                DominioExportsData dominioExportsData=ListaDomExportData.get(j);

                if(valor.equals(dominioExportsData.getId())){
                   descripcion=dominioExportsData.getDescripcion();

                }
            }

        }
        logger.error("descripcion: "+descripcion);
        return descripcion;
    }

    public List<HashMap<String, Object>> ListaDataConDominio(MaestroExport me, String nomConsulta)throws Exception{

        List<HashMap<String, Object>> ndata= new ArrayList<>();
        HashMap<String, Object> nombreDominio=BuscarNombreDominio(nomConsulta);
        String campo="";
        String dom="";
        for (Map.Entry<String, Object> entry :nombreDominio.entrySet()) {

            if(entry.getKey().equals("DOMINIO")){
                dom=entry.getValue().toString();

            }else if(entry.getKey().equals("CAMPO")){
                campo=entry.getValue().toString();

            }
        }

        String valor="";

        List<HashMap<String, Object>> data= me.getData();

        for(int i=0; i<data.size();i++){
            HashMap<String, Object> registro=data.get(i);

            for (Map.Entry<String, Object> entry :registro.entrySet()) {

                if(entry.getKey().equals(campo)){

                    valor=entry.getValue().toString();

                }
            }

            String descripcion="";
            if(!valor.equals("")){
                 descripcion=BuscarDominio(dom, valor);
            }


            String field="DESC_"+campo;

            registro.put(field, descripcion);

            ndata.add(registro);
        }

        return ndata;
    }

}
