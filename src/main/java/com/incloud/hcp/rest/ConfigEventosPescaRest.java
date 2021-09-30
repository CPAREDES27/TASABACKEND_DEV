package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOConfigEventosPesca;
import com.incloud.hcp.jco.maestro.service.JCOEventosPescaService;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api/configeventospesca")
public class ConfigEventosPescaRest {
    @Autowired
    private JCOConfigEventosPesca jcoConfigEventosPesca;

    @PostMapping(value = "/Listar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EventosPesca2Exports> ListarEventoPesca(@RequestBody EventosPesca2Imports imports) {

        try {
            return Optional.ofNullable(this.jcoConfigEventosPesca.ListarEventoPesca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Editar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EditarEventosPesca(@RequestBody EventosPescaEdit2Imports imports) {

        try {
            return Optional.ofNullable(this.jcoConfigEventosPesca.EditarEventosPesca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
