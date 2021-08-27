package com.incloud.hcp.rest;


import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusImports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOAnalisisCombustibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/analisiscombustible")
public class AnalisisCombustibleRest {

    @Autowired
    private JCOAnalisisCombustibleService jcoAnalisisCombustibleService;

    @PostMapping(value = "/Listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ControlLogExports> Editar(@RequestBody ControlLogImports imports) {

        try {
            return Optional.ofNullable(this.jcoAnalisisCombustibleService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/Detalle", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ControlLogExports> Detalle(@RequestBody AnalisisCombusImports imports) {

        try {
            return Optional.ofNullable(this.jcoAnalisisCombustibleService.Detalle(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }


}
