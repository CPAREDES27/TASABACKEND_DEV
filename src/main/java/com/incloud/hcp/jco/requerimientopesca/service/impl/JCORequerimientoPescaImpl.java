package com.incloud.hcp.jco.requerimientopesca.service.impl;

import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaDto;
import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaOptions;
import com.incloud.hcp.jco.requerimientopesca.dto.RequerimientoPesca;
import com.incloud.hcp.jco.requerimientopesca.service.JCORequerimientoPescaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCORequerimientoPescaImpl implements JCORequerimientoPescaService {

    @Override
    public ReqPescaDto ListarRequerimientoPesca(ReqPescaOptions importsParam) throws Exception {

        ReqPescaDto req_p = new ReqPescaDto();
        List<RequerimientoPesca> listaReqPesca = new ArrayList<RequerimientoPesca>();

        try {


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CARGA_REQ_PESCA);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            JCoParameterList imports = function.getImportParameterList();
            imports.setValue("IP_TPOPE", importsParam.getIp_tpope());
           // imports.setValue("IP_FINIT", formato.parse(importsParam.getIp_finit()));
           // imports.setValue("IP_FFINT", formato.parse(importsParam.getIp_ffint()));
            imports.setValue("IP_FINIT", importsParam.getIp_finit());
            imports.setValue("IP_FFINT", importsParam.getIp_ffint());
            imports.setValue("IP_ZONA", importsParam.getIp_zona());

            //JCoParameterList jcoTables = function.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();

            function.execute(destination);

            Metodos metodo = new Metodos();
            JCoParameterList export=function.getExportParameterList();
            JCoTable s_reqPesca = export.getTable(Tablas.ET_ZFLRPS);
            for (int i = 0; i < s_reqPesca.getNumRows(); i++) {
                s_reqPesca.setRow(i);
                RequerimientoPesca rp = new RequerimientoPesca();
                rp.setNrreq(s_reqPesca.getString("NRREQ"));
                rp.setCdpta(s_reqPesca.getString("CDPTA"));
                rp.setZdszar(s_reqPesca.getString("ZDSZAR"));
                rp.setFhreq(s_reqPesca.getString("FHREQ"));
                rp.setHrreq(s_reqPesca.getString("HRREQ"));
                rp.setCnprq(s_reqPesca.getString("CNPRQ"));
                rp.setCnpcm(s_reqPesca.getString("CNPCM"));
                rp.setAufnr(s_reqPesca.getString("AUFNR"));

                listaReqPesca.add(rp);
            }
            //List<HashMap<String, Object>> ListarS_MAREA= metodo.ObtenerListObjetos(s_reqPesca,importsParam.getFieldReqPesca());
            
            req_p.setS_reqpesca(listaReqPesca);
            req_p.setMensaje("Ok");
        }catch (Exception e){
            req_p.setMensaje(e.getMessage());
        }
        return req_p;
    }

    public ReqPescaDto RegistrarRequerimientoPesca(ReqPescaOptions importsParam) throws Exception {

        ReqPescaDto req_p = new ReqPescaDto();

        try {


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CARGA_REQ_PESCA);
            JCoParameterList jcoTables = function.getImportParameterList();

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("IP_TPOPE", importsParam.getIp_tpope());
            imports.put("IP_FINIT", importsParam.getIp_finit());
            imports.put("IP_FFINT", importsParam.getIp_ffint());
            imports.put("IP_ZONA", importsParam.getIp_zona());


            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.IT_ZFLRPS, importsParam.getIt_zflrps());

            function.execute(destination);

            Metodos metodo = new Metodos();
            JCoParameterList exports = function.getExportParameterList();
            JCoTable s_mensaje = exports.getTable(Tablas.ET_MENSJ);

            List<HashMap<String, Object>> mensaje = metodo.ListarObjetos(s_mensaje);

            req_p.setS_mensaje(mensaje);
            req_p.setMensaje("Ok");
        }catch (Exception e){
            req_p.setMensaje(e.getMessage());
        }
        return req_p;
    }

}
