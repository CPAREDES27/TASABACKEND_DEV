package com.incloud.hcp.rest;

import com.incloud.hcp.jco.controlLogistico.dto.ConsultaHorometroExports;
import com.incloud.hcp.jco.controlLogistico.dto.ConsultaHorometroImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOConsultaHorometroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/consultahorometro")
public class ConsultaHorometroRest {

    @Autowired
    private JCOConsultaHorometroService jcoConsultaHorometroService;

    @PostMapping(value = "/Listar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultaHorometroExports> Listar(@RequestBody ConsultaHorometroImports imports){

        try {
            return Optional.ofNullable(this.jcoConsultaHorometroService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}