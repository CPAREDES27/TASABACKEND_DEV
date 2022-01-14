package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.*;
import com.incloud.hcp.jco.reportepesca.service.JCOCalasService;
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
public class JCOCalasServiceImpl implements JCOCalasService {
    @Override
    public CalaExports ObtenerCalas(CalaImports imports) throws Exception {
        Metodos metodo = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("ROWCOUNT", imports.getRowcount());

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options2 = imports.getOptions();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions = metodo.ValidarOptions(option, options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_CALAS);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblS_CALA = tables.getTable(Tablas.S_CALA);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listS_CALA = metodos.ListarObjetos(tblS_CALA);

        /**
         * Búsqueda de descripciones de campos: Ind. propiedad, motivo de marea
         * */
        ArrayList<String> listDomNames = new ArrayList<>();
        listDomNames.add("ZINPRP");
        listDomNames.add("ZDO_TIPOMAREA");

        DominiosHelper helper = new DominiosHelper();
        ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

        DominiosExports detalleIndPropiedad = listDescipciones.stream().filter(d -> d.getDominio().equals("ZINPRP")).findFirst().orElse(null);
        DominiosExports detalleTipoMarea = listDescipciones.stream().filter(d -> d.getDominio().equals("ZDO_TIPOMAREA")).findFirst().orElse(null);

        /**
         * Enlace de los detqlles de los campos
         * */
        listS_CALA.stream().map(m -> {
            String inprp = m.get("INPRP").toString();
            String cdmma = m.get("CDMMA").toString();

            // Buscar los detalles
            DominioExportsData dataINPRP = detalleIndPropiedad.getData().stream().filter(d -> d.getId().equals(inprp)).findFirst().orElse(null);
            DominioExportsData dataTipoMarea = detalleTipoMarea.getData().stream().filter(d -> d.getId().equals(cdmma)).findFirst().orElse(null);
            if (dataINPRP != null) {
                String descInprp = dataINPRP.getDescripcion();
                m.put("DESC_INPRP", descInprp);
            } else {
                m.put("DESC_INPRP", "");
            }

            if (dataTipoMarea != null) {
                String descCdmma = dataTipoMarea.getDescripcion();
                m.put("DESC_TIPOMAREA", descCdmma);
            } else {
                m.put("DESC_TIPOMAREA", "");
            }

            String strCppms = String.valueOf(m.get("CPPMS"));
            m.put("CPPMS", strCppms);

            String strTemar = String.valueOf(m.get("TEMAR"));
            m.put("TEMAR", strTemar);

            String strCnpcm = String.valueOf(m.get("CNPCM"));
            m.put("CNPCM", strCnpcm);

            String strZmoda = String.valueOf(m.get("ZMODA"));
            m.put("ZMODA", strZmoda);

            String strCnpju = String.valueOf(m.get("CNPJU"));
            m.put("CNPJU", strCnpju);

            String strZmoju = String.valueOf(m.get("ZMOJU"));
            m.put("ZMOJU", strZmoju);

            String strPorju = String.valueOf(m.get("PORJU"));
            m.put("PORJU", strPorju);

            String strCnpca = String.valueOf(m.get("CNPCA"));
            m.put("CNPCA", strCnpca);

            String strZmoca = String.valueOf(m.get("ZMOCA"));
            m.put("ZMOCA", strZmoca);

            String strPorca = String.valueOf(m.get("PORCA"));
            m.put("PORCA", strPorca);

            String strCnpot = String.valueOf(m.get("CNPOT"));
            m.put("CNPOT", strCnpot);

            String strZmoot = String.valueOf(m.get("ZMOOT"));
            m.put("ZMOOT", strZmoot);

            String strPorot = String.valueOf(m.get("POROT"));
            m.put("POROT", strPorot);

            String strNrmar = String.valueOf(m.get("NRMAR"));
            m.put("NRMAR", strNrmar);

            return m;
        }).collect(Collectors.toList());

        CalaExports dto = new CalaExports();
        dto.setS_cala(listS_CALA);
        dto.setMensaje("OK");

        return dto;
    }
}
