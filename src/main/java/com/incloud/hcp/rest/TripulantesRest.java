package com.incloud.hcp.rest;

import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeExports;
import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeImports;
import com.incloud.hcp.jco.tripulantes.service.JCORegistroZarpeService;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/tripulantes")
public class TripulantesRest {

    @Autowired
    private JCORegistroZarpeService jcoRegistroZarpeService;

    @PostMapping(value = "/RegistroZarpe/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrosZarpeExports> RegistroZarpe(@RequestBody RegistrosZarpeImports imports){

        try {
            return Optional.ofNullable(this.jcoRegistroZarpeService.RegistroZarpe(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
