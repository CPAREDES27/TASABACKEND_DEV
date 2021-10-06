package com.incloud.hcp.rest;


import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.controlLogistico.service.JCOValeVivereService;
import com.incloud.hcp.jco.maestro.dto.DetalleViveresExports;
import com.incloud.hcp.jco.maestro.dto.DetalleViveresImports;
import com.incloud.hcp.jco.maestro.service.JCODetalleViveresService;
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

    @Autowired
    private JCODetalleViveresService jcoDetalleViveresService;

    @PostMapping(value = "/Listar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ValeViveresExports> Editar(@RequestBody ValeViveresImports imports) {

        try {
            return Optional.ofNullable(this.jcoValeVivereService.ListarValeViveres(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Guardar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VvGuardaExports> Guardar(@RequestBody VvGuardaImports imports) {

        try {
            return Optional.ofNullable(this.jcoValeVivereService.GuardarValeViveres(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/DetalleImpresionViveres", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DetalleViveresExports> DetalleImpresionViveres(@RequestBody DetalleViveresImports imports) {

        try {
            return Optional.ofNullable(this.jcoDetalleViveresService.DetalleImpresionViveres(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }


    }
}
