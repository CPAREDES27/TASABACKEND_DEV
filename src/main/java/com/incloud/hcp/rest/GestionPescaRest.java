package com.incloud.hcp.rest;

import com.incloud.hcp.jco.gestionpesca.dto.ReabrirMareaImports;
import com.incloud.hcp.jco.gestionpesca.service.JCOConsultaMareasService;
import com.incloud.hcp.jco.maestro.dto.CampoTablaExports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api/gestionpesca")
public class GestionPescaRest {
    @Autowired
    private JCOConsultaMareasService jcoConsultaMareasService;

    @PostMapping(value = "/reabrir-marea/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampoTablaExports> ConsultarMaestro(@RequestBody ReabrirMareaImports imports) {

        try {
            return Optional.ofNullable(this.jcoConsultaMareasService.reabrirMarea(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
