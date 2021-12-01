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
    public CampoTablaExports reabrirMarea(ReabrirMareaImports imports) {
        CampoTablaImports params = new CampoTablaImports();
        params.setP_user(imports.getUser());

        CampoTablaExports dto = new CampoTablaExports();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTimeNow = dtf.format(now);

        String setStr = "ATMOD = '" + imports.getUser() + "' ";

        List<SetDto> listSets = new ArrayList<>();
        SetDto set = new SetDto();
        set.setCmopt("");
        //params.getStr_set()
        dto.setMensaje(dateTimeNow);
        return dto;
    }
}
