package com.incloud.hcp.rest;

import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaDto;
import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaOptions;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDeclaradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/sistemainformacionflota")
public class SistemaInformacionFlotaRest {

    @Autowired
    JCOPescaDeclaradaService jcoPescaDeclaradaService;

    @PostMapping(value = "/PescaDeclarada", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaDeclaradaExports> PescaDeclarada(@RequestBody PescaDeclaradaImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaDeclaradaService.PescaDeclarada(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
