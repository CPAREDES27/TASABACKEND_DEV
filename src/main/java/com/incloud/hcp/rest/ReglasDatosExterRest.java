package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterEditImports;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterNuevImports;
import com.incloud.hcp.jco.maestro.service.JCOReglasDatosExterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/reglasdatosexternos")
public class ReglasDatosExterRest {

    @Autowired
    JCOReglasDatosExterService jcoReglasDatosExterService;

    @PostMapping(value = "/Crear/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MensajeDto>> Crear(@RequestBody ReglasDatosExterNuevImports imports){

        try {
            return Optional.ofNullable(this.jcoReglasDatosExterService.Crear(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Editar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MensajeDto>> Editar(@RequestBody ReglasDatosExterEditImports imports){

        try {
            return Optional.ofNullable(this.jcoReglasDatosExterService.Editar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }


}
