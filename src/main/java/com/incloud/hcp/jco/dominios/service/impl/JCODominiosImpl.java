package com.incloud.hcp.jco.dominios.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.gestionpesca.dto.BodegaExport;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCODominiosImpl implements JCODominiosService {

    public DominioDto Listar(DominiosImports imports) throws Exception {

        DominioDto domDto = new DominioDto();
        List<DominiosExports> listExports = new ArrayList<>();
        Metodos metodo = new Metodos();
        try {


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            for (DominioParams domParams : imports.getDominios()) {

            if(domParams.getDomname().equals("ZDO_ZCDTEV")) {
                DominiosExports exports = new DominiosExports();

                List<DominioExportsData> listDatas = new ArrayList<>();
                JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GET_LISTAED);
                JCoParameterList importx = stfcConnection.getImportParameterList();
                importx.setValue("STATUS", domParams.getStatus());

                JCoParameterList tables = stfcConnection.getTableParameterList();
                JCoTable domtab = tables.getTable(Tablas.DOMTAB);
                domtab.appendRow();
                domtab.setValue("DOMNAME", domParams.getDomname());
                stfcConnection.execute(destination);
                JCoTable lis_out = tables.getTable(Tablas.LIST_OUT);

                exports.setDominio(domParams.getDomname());
                for (int i = 0; i < lis_out.getNumRows(); i++) {
                    lis_out.setRow(i);
                    DominioExportsData data = new DominioExportsData();
                    HashMap<String, Object> newRecord = new HashMap<String, Object>();

                    data.setDescripcion(lis_out.getString("DDTEXT"));
                    data.setId(lis_out.getString("DOMVALUE_L"));
                    listDatas.add(data);
                }
                exports.setData(listDatas);
                listExports.add(exports);
                domDto.setData(listExports);
                domDto.setMensaje("Ok");

            }else{
                DominiosExports exports = new DominiosExports();

                List<DominioExportsData> listDatas = new ArrayList<>();
                JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
                JCoParameterList importx = stfcConnection.getImportParameterList();
                String TABLE_READ_TABLE =metodo.returnTable(domParams.getDomname());

                importx.setValue("QUERY_TABLE", "ZFLMND");
                importx.setValue("DELIMITER", "|");
                importx.setValue("P_USER", "FGARCIA");

                JCoParameterList tables = stfcConnection.getTableParameterList();
                JCoTable tableImport = tables.getTable("OPTIONS");
                tableImport.appendRow();

                tableImport.setValue("WA", "ESREG = 'S'");
                stfcConnection.execute(destination);
                JCoTable lis_out = tables.getTable("DATA");
                JCoTable FIELDS = tables.getTable("FIELDS");

                exports.setDominio(domParams.getDomname());
                for (int i = 0; i < lis_out.getNumRows(); i++) {
                    lis_out.setRow(i);
                    DominioExportsData data = new DominioExportsData();
                    String ArrayResponse[] = lis_out.getString().split("\\|");
                    for(int j=0;j<FIELDS.getNumRows();j++){
                        FIELDS.setRow(j);
                        String key = (String) FIELDS.getValue("FIELDNAME");

                        if(key.equals("CDMND")){

                            Object value=ArrayResponse[j];
                            data.setId(value.toString().trim());


                        }
                        if(key.equals("DSMND")){
                            Object values=ArrayResponse[j];
                            data.setDescripcion(values.toString().trim());
                        }


                    }
                    listDatas.add(data);
                }
                exports.setData(listDatas);
                listExports.add(exports);
                domDto.setData(listExports);
                domDto.setMensaje("Ok");

            }
            }


        } catch (Exception e) {
            domDto.setMensaje(e.getMessage());
        }

        return domDto;
    }


}
