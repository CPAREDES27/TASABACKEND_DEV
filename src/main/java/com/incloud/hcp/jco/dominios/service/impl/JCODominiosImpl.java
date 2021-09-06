package com.incloud.hcp.jco.dominios.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.gestionpesca.dto.BodegaExport;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCODominiosImpl implements JCODominiosService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public List<HashMap<String, Object>>  Listar(DominiosImports imports) throws Exception {

        DominioDto domDto = new DominioDto();
        List<DominiosExports> listExports = new ArrayList<>();
        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            for (DominioParams domParams : imports.getDominios()) {

                if (domParams.getDomname().startsWith("ZDO")){
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

                    HashMap<String, Object> newRecord = new HashMap<String, Object>();
                    String key = (String)lis_out.getString("DOTEXT");
                    String valor=(String) lis_out.getString("DOMVALUE_L");
                    logger.error("KEYS NOW "+ " "+key+" "+" "+valor);
                    newRecord.put(key,valor);
                    data.add(newRecord);
                }

            }else{
                    DominiosExports exports = new DominiosExports();

                    List<DominioExportsData> listDatas = new ArrayList<>();
                    JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
                    JCoParameterList importx = stfcConnection.getImportParameterList();

                    importx.setValue("QUERY_TABLE", "ZFLMND");
                    importx.setValue("DELIMITER", "|");
                    importx.setValue("P_USER", "FGARCIA");
                                        ;
                    JCoParameterList tables = stfcConnection.getTableParameterList();
                    JCoTable tableImport = tables.getTable("OPTIONS");
                    tableImport.appendRow();

                    tableImport.setValue("WA", "ESREG = 'S'");
                    //Ejecutar Funcion
                    stfcConnection.execute(destination);

                    JCoTable tableExport = tables.getTable("DATA");
                    JCoTable FIELDS = tables.getTable("FIELDS");
                    exports.setDominio(domParams.getDomname());
                    for (int i = 0; i < tableExport.getNumRows(); i++) {
                        tableExport.setRow(i);
                        String ArrayResponse[] = tableExport.getString().split("\\|");
                        HashMap<String, Object> newRecord = new HashMap<String, Object>();
                        newRecord.put("dominio","MONEDA");

                        for (int j = 0; j < FIELDS.getNumRows(); j++) {
                            FIELDS.setRow(j);
                            String key = (String) FIELDS.getValue("FIELDNAME");
                            Object value = "";
                            value = ArrayResponse[j].trim();
                            newRecord.put(key,value);

                        }
                        data.add(newRecord);

                    }
                }
            }


        } catch (Exception e) {
        domDto.setMensaje(e.getMessage());
        }

        return data;
    }


}
