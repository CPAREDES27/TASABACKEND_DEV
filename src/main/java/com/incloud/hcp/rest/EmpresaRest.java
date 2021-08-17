package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.EmpresaImports;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.service.JCOEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/empresa")
public class EmpresaRest {

    @Autowired
    private JCOEmpresaService jcoEmpresaService;


    @PostMapping(value = "/ConsultarEmpresa/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ListarEmpresas(@RequestBody EmpresaImports imports){

        try {
            return Optional.ofNullable(this.jcoEmpresaService.ListarEmpresas(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
