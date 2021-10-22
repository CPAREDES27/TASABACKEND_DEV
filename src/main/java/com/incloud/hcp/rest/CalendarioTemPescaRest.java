package com.incloud.hcp.rest;

import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusLisExports;
import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusLisImports;
import com.incloud.hcp.jco.maestro.dto.CalenTempPescaExports;
import com.incloud.hcp.jco.maestro.dto.CalenTempPescaImports;
import com.incloud.hcp.jco.maestro.service.JCOCalendTempPescaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/calendariotemporadapesca")
public class CalendarioTemPescaRest {

    @Autowired
    private JCOCalendTempPescaService jcoCalendTempPescaService;
    @PostMapping(value = "/Eliminar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CalenTempPescaExports> Eliminar(@RequestBody CalenTempPescaImports imports) {

        try {
            return Optional.ofNullable(this.jcoCalendTempPescaService.BorrarRegistro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

}
