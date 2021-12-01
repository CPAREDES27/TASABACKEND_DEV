package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.ReabrirMareaImports;
import com.incloud.hcp.jco.gestionpesca.service.JCOConsultaMareasService;
import com.incloud.hcp.jco.maestro.dto.CampoTablaExports;
import com.incloud.hcp.jco.maestro.dto.CampoTablaImports;
import com.incloud.hcp.jco.maestro.dto.SetDto;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateTimeNow = dtf.format(now);
            String date = dateTimeNow.substring(0, 9);
            String time = dateTimeNow.substring(11);

            date.replace("/", "");
            time.replace(":", "");

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
}
