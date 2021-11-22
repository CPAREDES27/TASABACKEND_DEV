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

    public DominioDto Listar(DominiosImports imports) throws Exception {


        DominioDto domDto = new DominioDto();
        List<DominiosExports> listExports = new ArrayList<>();
        Metodos metodo = new Metodos();
        try {


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            for (DominioParams domParams : imports.getDominios()) {

                if (domParams.getDomname().startsWith("Z")) {
                    DominiosExports exports = new DominiosExports();

                    List<DominioExportsData> listDatas = new ArrayList<>();
                    JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GET_LISTAED);
                    JCoParameterList importx = stfcConnection.getImportParameterList();
                    importx.setValue("STATUS", domParams.getStatus());

                    JCoParameterList tables = stfcConnection.getTableParameterList();
                    JCoTable domtab = tables.getTable(Tablas.DOMTAB);
                    domtab.appendRow();
                    if(domParams.getDomname().equals("ZCDMMACOM")){
                        domtab.setValue("DOMNAME", "ZCDMMA");
                    }else{
                        domtab.setValue("DOMNAME", domParams.getDomname());
                    }

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
                    List<DominioExportsData> listDatas2 = new ArrayList<>();
                    if(domParams.getDomname().equals("ZCDMMACOM")){
                        for(int i=0;i<listDatas.size();i++){
                            if(listDatas.get(i).getId().equals("1") || listDatas.get(i).getId().equals("2") || listDatas.get(i).getId().equals("7") || listDatas.get(i).getId().equals("8") ){
                                DominioExportsData data = new DominioExportsData();
                                data.setDescripcion(listDatas.get(i).getDescripcion());
                                data.setId(listDatas.get(i).getId());
                                listDatas2.add(data);

                            }
                        }
                        exports.setData(listDatas2);
                    }else{
                        exports.setData(listDatas);
                    }


                    listExports.add(exports);
                    domDto.setData(listExports);
                    domDto.setMensaje("Ok");

                } else {
                    /**
                     * Objetos especiales
                     */
                    DominiosExports exports = new DominiosExports();
                    exports.setDominio(domParams.getDomname());

                    List<DominioExportsData> listDatas = new ArrayList<>();

                    switch (domParams.getDomname()) {
                        case "MOTIVOMAREA_RPDC":
                            DominioExportsData dataMotivoMareaRpdc1 = new DominioExportsData();
                            DominioExportsData dataMotivoMareaRpdc2 = new DominioExportsData();
                            dataMotivoMareaRpdc1.setId("A");
                            dataMotivoMareaRpdc1.setDescripcion("Todos");

                            dataMotivoMareaRpdc2.setId("1");
                            dataMotivoMareaRpdc2.setDescripcion("CHD");

                            listDatas.add(dataMotivoMareaRpdc1);
                            listDatas.add(dataMotivoMareaRpdc2);
                            break;
                        case "OPCIONFECHA_RPEB":
                            DominioExportsData dataOpcionFechaRfeb1 = new DominioExportsData();
                            DominioExportsData dataOpcionFechaRfeb2 = new DominioExportsData();

                            dataOpcionFechaRfeb1.setId("T");
                            dataOpcionFechaRfeb1.setDescripcion("Temporadas");

                            dataOpcionFechaRfeb2.setId("F");
                            dataOpcionFechaRfeb2.setDescripcion("Fechas");

                            listDatas.add(dataOpcionFechaRfeb1);
                            listDatas.add(dataOpcionFechaRfeb2);
                            break;
                        case "ALMACEN":
                            Metodos me = new Metodos();
                            listDatas=me.obtenerAlmacen();

                            break;
                        default:
                            /**
                             * Leer tablas en base al dominio
                             */
                            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
                            JCoParameterList importx = stfcConnection.getImportParameterList();
                            String TABLE_READ_TABLE = metodo.returnTable(domParams.getDomname());
                            String WA_READ_TABLE = metodo.returnWA(domParams.getDomname());
                            String[] fieldname = metodo.returnField(domParams.getDomname());
                            importx.setValue("QUERY_TABLE", TABLE_READ_TABLE);
                            importx.setValue("DELIMITER", "|");
                            importx.setValue("P_USER", "FGARCIA");

                            logger.error("TABLA= "+TABLE_READ_TABLE);
                            logger.error("WA= "+WA_READ_TABLE);
                            logger.error("fieldname[0]= "+fieldname[0]);

                            JCoParameterList tables = stfcConnection.getTableParameterList();
                            JCoTable tableImport = tables.getTable("OPTIONS");
                            tableImport.appendRow();

                            tableImport.setValue("WA", WA_READ_TABLE);
                            stfcConnection.execute(destination);
                            JCoTable lis_out = tables.getTable("DATA");
                            JCoTable FIELDS = tables.getTable("FIELDS");


                            for (int i = 0; i < lis_out.getNumRows(); i++) {
                                lis_out.setRow(i);
                                DominioExportsData data = new DominioExportsData();
                                String ArrayResponse[] = lis_out.getString().split("\\|");
                                for (int j = 0; j < FIELDS.getNumRows(); j++) {
                                    FIELDS.setRow(j);
                                    String key = (String) FIELDS.getValue("FIELDNAME");

                                    if (key.equals(fieldname[0])) {

                                        Object value = ArrayResponse[j];
                                        data.setId(value.toString().trim());


                                    }
                                    if (key.equals(fieldname[1])) {
                                        Object values = ArrayResponse[j];
                                        data.setDescripcion(values.toString().trim());
                                    }


                                }
                                listDatas.add(data);
                            }
                            break;
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
