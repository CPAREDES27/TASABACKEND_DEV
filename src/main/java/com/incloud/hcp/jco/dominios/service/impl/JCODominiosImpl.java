package com.incloud.hcp.jco.dominios.service.impl;

import com.incloud.hcp.jco.dominios.dto.DominioDto;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.dominios.dto.DominiosImports;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCODominiosImpl implements JCODominiosService {

    public DominioDto Listar(DominiosImports imports)throws Exception{

        DominioDto domDto= new DominioDto();
        List<DominiosExports> ListDom=new ArrayList<DominiosExports>();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GET_LISTAED);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("STATUS", imports.getStatus());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable domtab = tables.getTable(Tablas.DOMTAB);
            domtab.appendRow();
            domtab.setValue("DOMNAME", imports.getDomname());
            stfcConnection.execute(destination);
            JCoTable lis_out = tables.getTable(Tablas.LIST_OUT);

            for(int i=0; i<lis_out.getNumRows(); i++) {
                lis_out.setRow(i);
                DominiosExports dom= new DominiosExports();

                dom.setDdtext(lis_out.getString("DDTEXT"));
                dom.setDomvalue_l(lis_out.getString("DOMVALUE_L"));
                ListDom.add(dom);
            }
            domDto.setData(ListDom);
            domDto.setMensaje("Ok");
        }catch (Exception e){
            domDto.setMensaje(e.getMessage());
        }

        return  domDto;
    }



}
