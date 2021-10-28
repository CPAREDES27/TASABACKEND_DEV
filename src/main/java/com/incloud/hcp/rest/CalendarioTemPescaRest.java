package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.CalendarioTemporadaExports;
import com.incloud.hcp.jco.maestro.dto.CalendarioTemporadaImports;
import com.incloud.hcp.jco.maestro.dto.DeleteRecordExports;
import com.incloud.hcp.jco.maestro.dto.DeleteRecordImports;
import com.incloud.hcp.jco.maestro.service.JCOCalendarioTemporadaService;
import com.incloud.hcp.jco.maestro.service.JCODeleteRecordService;
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
    private JCODeleteRecordService jcoDeleteRecordService;

    @Autowired
    private JCOCalendarioTemporadaService jcoCalendarioTemporadaService;

    @PostMapping(value = "/Eliminar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteRecordExports> Eliminar(@RequestBody DeleteRecordImports imports) {

        try {
            return Optional.ofNullable(this.jcoDeleteRecordService.BorrarRegistro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/Guardar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarioTemporadaExports> Guardar(@RequestBody CalendarioTemporadaImports imports) {

        try {
            return Optional.ofNullable(this.jcoCalendarioTemporadaService.Guadar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }


}
