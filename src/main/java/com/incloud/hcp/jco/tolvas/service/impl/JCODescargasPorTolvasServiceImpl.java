package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImportsKey;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.jco.tolvas.service.JCODescargasPorTolvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JCODescargasPorTolvasServiceImpl implements JCODescargasPorTolvasService {
    @Autowired
    private JCOMaestrosService jcoMaestrosService;

    @Override
    public MaestroExport buscarDescargasPorTolvas(MaestroImportsKey imports) throws Exception {
        MaestroExport exports = new MaestroExport();
        try {
            //Sentencia propia
            String strOptionsWA = "(TPROG = 'G' OR (TPROG = 'A' AND CMIN = 'E'))";
            MaestroOptions optionWA = new MaestroOptions();
            optionWA.setWa(strOptionsWA);

            imports.getOption().add(optionWA);

            //Read_Table
            exports = jcoMaestrosService.obtenerMaestro2(imports);
        } catch (Exception ex) {
            exports.setMensaje(ex.getMessage());
        }

        return exports;
    }
}
