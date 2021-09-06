package com.incloud.hcp.rest;

import com.incloud.hcp.jco.dominios.dto.DominioDto;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.dominios.dto.DominiosImports;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/dominios")
public class DominiosRest {

    @Autowired
    private JCODominiosService jcoDominiosService;

    @PostMapping(value = "/Listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String, Object>>> Listar(@RequestBody DominiosImports imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoDominiosService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
