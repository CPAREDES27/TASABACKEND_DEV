package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.tolvas.dto.IngresoDesManualExports;
import com.incloud.hcp.jco.tolvas.dto.IngresoDescManualImports;
import com.incloud.hcp.jco.tolvas.dto.RegistroTolvasImports;
import com.incloud.hcp.jco.tolvas.service.JCOIngresoDescManualService;
import com.incloud.hcp.jco.tolvas.service.JCORegistroTolvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/tolvas")
public class TolvasRest {

    @Autowired
    private JCORegistroTolvasService jcoRegistroTolvasService;
    @Autowired
    private JCOIngresoDescManualService jcoIngresoDescManualService;

    @PostMapping(value = "/registrotolvas_listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> Listar(@RequestBody RegistroTolvasImports imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoRegistroTolvasService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/ingresodescargamanual_guardar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IngresoDesManualExports> Guardar(@RequestBody IngresoDescManualImports imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoIngresoDescManualService.Guardar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
