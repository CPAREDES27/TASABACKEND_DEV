package com.incloud.hcp.rest;


import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaExports;
import com.incloud.hcp.jco.controlLogistico.service.JCOValeVivereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/valeviveres")
public class ValeViveresRest {

    @Autowired
    private JCOValeVivereService jcoValeVivereService;

    @PostMapping(value = "/Listar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ControlLogExports> Editar(@RequestBody ControlLogImports imports){

        try {
            return Optional.ofNullable(this.jcoValeVivereService.ListarValeViveres(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Guardar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VvGuardaExports> Guardar(@RequestBody ControlLogImports imports){

        try {
            return Optional.ofNullable(this.jcoValeVivereService.GuardarValeViveres(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
