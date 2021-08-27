package com.incloud.hcp.rest;
import com.incloud.hcp.jco.distribucionflota.dto.DistribucionFlotaExports;
import com.incloud.hcp.jco.distribucionflota.dto.DistribucionFlotaImports;
import com.incloud.hcp.jco.distribucionflota.service.JCODistribucionFlotaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/distribucionflota")
public class ListaDistribucionFlotaRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCODistribucionFlotaService jcoDistribucionFlotaService;

    @PostMapping(value = "/listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DistribucionFlotaExports> Listar(@RequestBody DistribucionFlotaImports imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoDistribucionFlotaService.ListarDistribucionFlota(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
