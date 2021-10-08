package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.impl.JCODominiosImpl;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.jco.reportepesca.dto.MaestroOptionsMarea;
import com.incloud.hcp.jco.reportepesca.dto.MareaExports;
import com.incloud.hcp.jco.reportepesca.dto.MareaImports;
import com.incloud.hcp.jco.reportepesca.service.JCOMareasService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JCOMareasServiceImpl implements JCOMareasService {
    @Override
    public MareaExports ObtenerMareas(MareaImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        Metodos metodo = new Metodos();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("ROWCOUNT", imports.getRowcount());

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options2 = imports.getOptions();

        List<HashMap<String, Object>> tmpOptions = metodo.ValidarOptions(option, options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_MAREA);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC ejecutarRFC = new EjecutarRFC();
        ejecutarRFC.setImports(function, importParams);
        ejecutarRFC.setTable(paramsTable, "OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblS_MAREA = tables.getTable(Tablas.S_MAREA);

        List<HashMap<String, Object>> listS_MAREA = metodo.ListarObjetos(tblS_MAREA);

        /**
         * Búsqueda de descripciones de campos: Ind. propiedad, motivo de marea
         * */
        ArrayList<String> listDomNames = new ArrayList<>();
        listDomNames.add("ZINPRP");
        listDomNames.add("ZCDMMA");

        DominiosHelper helper = new DominiosHelper();
        ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

        DominiosExports detalleIndPropiedad = listDescipciones.stream().filter(d -> d.getDominio().equals("ZINPRP")).findFirst().orElse(null);
        DominiosExports detalleMotMarea = listDescipciones.stream().filter(d -> d.getDominio().equals("ZCDMMA")).findFirst().orElse(null);

        /**
         * Enlace de los detqlles de los campos
         * */
        List<HashMap<String, Object>> listS_MAREA_details = listS_MAREA.stream().map(m -> {
            String inprp = m.get("INPRP").toString();
            String cdmma = m.get("CDMMA").toString();

            // Buscar los detalles
            DominioExportsData dataINPRP = detalleIndPropiedad.getData().stream().filter(d -> d.getId().equals(inprp)).findFirst().orElse(null);
            DominioExportsData dataCDMMA = detalleMotMarea.getData().stream().filter(d -> d.getId().equals(cdmma)).findFirst().orElse(null);
            if (dataINPRP != null) {
                String descInprp = dataINPRP.getDescripcion();
                m.put("DESC_INPRP", descInprp);
            } else {
                m.put("DESC_INPRP", "");
            }

            if (dataCDMMA != null) {
                String descCdmma = dataCDMMA.getDescripcion();
                m.put("DESC_CDMMA", descCdmma);
            } else {
                m.put("DESC_CDMMA", "");
            }

            return m;
        }).collect(Collectors.toList());


        MareaExports dto = new MareaExports();
        dto.setS_marea(listS_MAREA_details);
        dto.setMensaje("Ok");

        return dto;
    }
}
