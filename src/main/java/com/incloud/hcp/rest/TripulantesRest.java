package com.incloud.hcp.rest;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.service.JCORegistroZarpeService;
import com.incloud.hcp.jco.tripulantes.service.JCORolTripulacionService;
import com.incloud.hcp.jco.tripulantes.service.JCOSeguimientoTripuService;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.jco.tripulantes.service.JCOPDFZarpeService;
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
    @Autowired
    private JCORolTripulacionService jcoRolTripulacionService;
    @Autowired
    private JCOPDFZarpeService JCOPDFZarpeService;
    @Autowired
    private JCOSeguimientoTripuService jcoSeguimientoTripuService;

    @PostMapping(value = "/RegistroZarpe/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrosZarpeExports> RegistroZarpe(@RequestBody RegistrosZarpeImports imports){

        try {
            return Optional.ofNullable(this.jcoRegistroZarpeService.RegistroZarpe(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/RolTripulacion/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RolTripulacionExports> RolTripulacion(@RequestBody RolTripulacionImports imports){

        try {
            return Optional.ofNullable(this.jcoRolTripulacionService.RolTripulacion(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/PDFZarpe", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFZarpeExports> GenerarPdfZarpe(@RequestBody PDFZarpeImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFZarpeService.GenerarPDFZarpe(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFTravesia", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFZarpeExports> GenerarPdfTravesia() {

        try {
            return Optional.ofNullable(this.JCOPDFZarpeService.GenerarPDFTravesia())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/SeguimientoTripulantes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SeguimientoTripuExports> SeguimientoTripulantes(@RequestBody SeguimientoTripuImports imports) {

        try {
            return Optional.ofNullable(this.jcoSeguimientoTripuService.SeguimientoTripulantes(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
