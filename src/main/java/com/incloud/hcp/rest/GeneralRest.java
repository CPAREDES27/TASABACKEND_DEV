package com.incloud.hcp.rest;


import com.incloud.hcp.jco.consultaGeneral.dto.ConsultaGeneralImports;
import com.incloud.hcp.jco.consultaGeneral.service.JCOConsultaGeneralService;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import com.incloud.hcp.jco.maestro.service.JCODeleteRecordService;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/General")
public class GeneralRest {

    @Autowired
    private JCOMaestrosService MaestroService;

    @Autowired
    private JCOCampoTablaService jcoCampoTablaService;

    @Autowired
    private JCOConsultaGeneralService jcoConsultaGeneralService;

    @Autowired
    private JCODeleteRecordService  jcoDeleteRecordService;

    @PostMapping(value = "/Read_Table2/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro(@RequestBody MaestroImportsKey imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerMaestro3(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }


    @PostMapping(value = "/Read_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro2(@RequestBody MaestroImportsKey imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerMaestro2(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }


    @PostMapping(value = "/Armador/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro2(@RequestBody BusquedaArmadorDTO codigo){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerArmador(codigo))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Update_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> EditarMaestro(@RequestBody MaestroEditImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.editarMaestro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Update_Table2/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> EditarMaestro2(@RequestBody MaestroEditImport imports){

        try {
            return Optional.ofNullable(this.MaestroService.editarMaestro2(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/AppMaestros/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AppMaestrosExports> AppMaestros(@RequestBody AppMaestrosImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.appMaestros(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Update_Camp_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampoTablaExports> ActualizarCampoTabla(@RequestBody CampoTablaImports imports){

        try {
            return Optional.ofNullable(this.jcoCampoTablaService.Actualizar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/AyudasBusqueda/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AyudaBusquedaExports> AyudasBusqueda(@RequestBody AyudaBusquedaImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.AyudasBusqueda(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/ConsultaGeneral/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<com.incloud.hcp.jco.consultaGeneral.dto.ConsultaGeneralExports> ConsultaGeneral(@RequestBody ConsultaGeneralImports imports){

        try {
            return Optional.ofNullable(this.jcoConsultaGeneralService.ConsultaGeneral(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/Delete_Records/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteRecordExports> DELETE_RECORDS(@RequestBody DeleteRecordImports imports){

        try {
            return Optional.ofNullable(this.jcoDeleteRecordService.BorrarRegistro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/UpdateEmbarcacionMasivo/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampoTablaExports> UpdateEmbarcacionMasivo(@RequestBody UpdateEmbarcaMasivoImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.UpdateEmbarcacionMasivo(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/UpdateTripulantesMasivo/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampoTablaExports> UpdateTripulantesMasivo(@RequestBody UpdateTripuMasivoImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.UpdateTripulantesMasivo(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }



}
