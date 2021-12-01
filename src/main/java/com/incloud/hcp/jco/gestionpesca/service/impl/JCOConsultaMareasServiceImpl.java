package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.AnularMareaExports;
import com.incloud.hcp.jco.gestionpesca.dto.AnularMareaImports;
import com.incloud.hcp.jco.gestionpesca.dto.ReabrirMareaImports;
import com.incloud.hcp.jco.gestionpesca.service.JCOConsultaMareasService;
import com.incloud.hcp.jco.maestro.dto.CampoTablaExports;
import com.incloud.hcp.jco.maestro.dto.CampoTablaImports;
import com.incloud.hcp.jco.maestro.dto.SetDto;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class JCOConsultaMareasServiceImpl implements JCOConsultaMareasService {
    @Autowired
    private JCOCampoTablaService jcoCampoTablaService;

    @Override
    public CampoTablaExports reabrirMarea(ReabrirMareaImports imports) throws Exception {
        CampoTablaExports dto = new CampoTablaExports();

        try {
            CampoTablaImports params = new CampoTablaImports();
            params.setP_user(imports.getUser());


            SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd HHmmss");
            dtf.setTimeZone(TimeZone.getTimeZone("America/Lima"));

            Date now = new Date();
            String dateTimeNow = dtf.format(now);
            String date = dateTimeNow.substring(0, 8);
            String time = dateTimeNow.substring(9);

            String setStr = "ATMOD = '" + imports.getUser() + "' ";
            setStr += "FCMOD = '" + date + "' ";
            setStr += "HRMOD = '" + time + "' ";
            setStr += "ESMAR = 'A'";

            String opt = "NRMAR = " + imports.getNrmar();

            List<SetDto> listSets = new ArrayList<>();
            SetDto set = new SetDto();
            set.setCmopt(opt);
            set.setCmset(setStr);
            set.setNmtab("ZFLMAR");

            listSets.add(set);
            params.setStr_set(listSets);

            dto = this.jcoCampoTablaService.Actualizar(params);
        } catch (Exception ex) {
            dto.setMensaje(ex.getMessage());
        }

        return dto;
    }

    @Override
    public AnularMareaExports anularMarea(AnularMareaImports imports) throws Exception {
        AnularMareaExports dto = new AnularMareaExports();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ANULA_MAREA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_MAREA", imports.getP_marea());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            Metodos me = new Metodos();
            List<HashMap<String, Object>> t_mensaje = me.ListarObjetos(T_MENSAJE);

            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");
        } catch (Exception ex) {
            dto.setMensaje(ex.getMessage());
        }
        return dto;
    }
}
