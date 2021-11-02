package com.incloud.hcp.rest;

import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosExports;
import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOAceitesUsadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/aceitesusados")
public class AceitesUsadosRest {

    @Autowired
    private JCOAceitesUsadosService jcoAceitesUsadosService;

    @PostMapping(value = "/Listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AceitesUsadosExports> Editar(@RequestBody AceitesUsadosImports imports) {

        try {
            return Optional.ofNullable(this.jcoAceitesUsadosService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/Nuevo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AceitesUsadosExports> Nuevo(@RequestBody AceitesUsadosImports imports) {

        try {
            return Optional.ofNullable(this.jcoAceitesUsadosService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }


}
