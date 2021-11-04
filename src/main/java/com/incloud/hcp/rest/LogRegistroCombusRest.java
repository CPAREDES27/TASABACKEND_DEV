package com.incloud.hcp.rest;

import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOLogRegisCombusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/logregistrocombustible")
public class LogRegistroCombusRest {

    @Autowired
    private JCOLogRegisCombusService jcoLogRegisCombusService;

    @PostMapping(value = "/Listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LogRegCombusExports> Editar(@RequestBody LogRegCombusImports imports) {

        try {
            return Optional.ofNullable(this.jcoLogRegisCombusService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/Nuevo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LogRegCombusExports> Nuevo(@RequestBody LogRegCombusImports imports) {

        try {
            return Optional.ofNullable(this.jcoLogRegisCombusService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
