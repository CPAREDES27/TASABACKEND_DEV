package com.incloud.hcp.rest;

import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusImports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusRegExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusRegImports;
import com.incloud.hcp.jco.controlLogistico.service.JCORepModifDatCombusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/reportesmodifdatoscombustible")
public class ReporModifDatosCombusRest {

    @Autowired
    private JCORepModifDatCombusService jcoRepModifDatCombusService;

    @PostMapping(value = "/Listar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RepModifDatCombusExports> Listar(@RequestBody RepModifDatCombusImports imports){

        try {
            return Optional.ofNullable(this.jcoRepModifDatCombusService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Exportar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RepModifDatCombusRegExports> Exportar(@RequestBody RepModifDatCombusImports imports){

        try {
            return Optional.ofNullable(this.jcoRepModifDatCombusService.Exportar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
