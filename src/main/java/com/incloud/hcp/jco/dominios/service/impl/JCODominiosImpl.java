package com.incloud.hcp.jco.dominios.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCODominiosImpl implements JCODominiosService {

    public DominioDto Listar(DominiosImports imports) throws Exception {

        DominioDto domDto = new DominioDto();
        List<DominiosExports> listExports = new ArrayList<>();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            for (DominioParams domParams : imports.getDominios()) {
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
                    data.setDdtext(lis_out.getString("DDTEXT"));
                    data.setDomvalue_l(lis_out.getString("DOMVALUE_L"));
                    listDatas.add(data);
                }
                exports.setData(listDatas);
                listExports.add(exports);
            }

            domDto.setData(listExports);
            domDto.setMensaje("Ok");
        } catch (Exception e) {
            domDto.setMensaje(e.getMessage());
        }

        return domDto;
    }


}
