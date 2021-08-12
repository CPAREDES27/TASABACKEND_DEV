package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.EmpresaDto;
import com.incloud.hcp.jco.maestro.dto.EmpresaImports;
import com.incloud.hcp.jco.maestro.service.JCOEmpresaService;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/Empresa")
public class EmpresaRest {

    @Autowired
    private JCOEmpresaService jcoEmpresaService;

    @PostMapping(value = "/ConsultarEmpresa/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpresaDto> ObtenerEmpresa(@RequestBody EmpresaImports imports){

        try {
            return Optional.ofNullable(this.jcoEmpresaService.obtenerEmpresa(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
