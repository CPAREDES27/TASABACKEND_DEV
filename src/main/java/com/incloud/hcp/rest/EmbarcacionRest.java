package com.incloud.hcp.rest;


import com.incloud.hcp.jco.gestionpesca.dto.Atributos;
import com.incloud.hcp.jco.gestionpesca.dto.EmbarcacionDto;
import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.gestionpesca.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.gestionpesca.dto.TipoEmbarcacionDto;
import com.incloud.hcp.jco.gestionpesca.service.JCOTipoEmbarcacionService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/embarcacion")
public class EmbarcacionRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JCOEmbarcacionService jcoEmbarcacionService;
    @Autowired
    private JCOTipoEmbarcacionService jcoTipoEmbarcacionService;





    //@ApiOperation(value = "Lista Embarcacion x", produces = "application/json")
    @GetMapping(value = "/listaEmbarcacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmbarcacionDto>> listaEmbarcacion(
    ) {
        //Parametro dto = new Parametro();
        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.listaEmbarcacion("CDEMB = '0000004529'"))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @RequestMapping(value = "/listaTipoEmbarcacion", method = RequestMethod.POST)
    public ResponseEntity<List<TipoEmbarcacionDto>> listaTipoEmbarcacion(@RequestBody List<Options> options
    ) {
        //Parametro dto = new Parametro();
        try {
            return Optional.ofNullable(this.jcoTipoEmbarcacionService.listaTipoEmbarcacion(options))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/metodo")
    public List<Options> modelParam(@RequestBody List<Options> person) {

        List<Options> lista = new ArrayList<Options>();
        person.stream().forEach(persona ->{
            lista.add(persona);
        });

        return lista;
    }

}
