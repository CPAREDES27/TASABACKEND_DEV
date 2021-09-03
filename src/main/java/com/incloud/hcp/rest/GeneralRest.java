package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.util.EjecutarRFC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @PostMapping(value = "/Read_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro(@RequestBody MaestroImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerMaestro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }


    @PostMapping(value = "/Read_Table2/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro2(@RequestBody MaestroImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerMaestro2(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Read_Table3/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro3(@RequestBody MaestroImportsKey imports){

        try {
            MaestroExport me=new MaestroExport();
            MaestroOptionsKey me2 = new MaestroOptionsKey();

            HashMap<String, Object> importz = new HashMap<String, Object>();
            importz.put("QUERY_TABLE", imports.getTabla());
            importz.put("DELIMITER", imports.getDelimitador());
            importz.put("NO_DATA", imports.getNo_data());
            importz.put("ROWSKIPS", imports.getRowskips());
            importz.put("ROWCOUNT", imports.getRowcount());
            importz.put("P_USER", imports.getP_user());
            importz.put("P_ORDER", imports.getOrder());
            String control="";

            List<MaestroOptionsKey> options = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptionsKey mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                if(mo.getControl().equals("INPUT"))
                {
                    control="LIKE";
                }
                if (mo.getControl().equals("COMBOBOX")) {
                    control="=";
                }
                if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals("") )){
                    control="BETWEEN";
                }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    control="=";
                }



                if(mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put("WA",mo.getKey() +" "+ control+ " "+ "'%"+mo.getValueLow()+"'%");
                }else if(mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put("WA",mo.getKey() +" "+ control+ " "+ "'"+mo.getValueLow()+"'");
                }else if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))){
                    record.put("WA", mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" +" AND "+ "'"+mo.getValueHigh()+"'");
                }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put("WA",mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" );
                }


                if(i>0){
                    if(mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                        record.put("WA","AND"+" "+ mo.getKey() +" "+ control+ " "+ "'%"+mo.getValueLow()+"'%");
                    }else if(mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                        record.put("WA","AND"+" "+ mo.getKey() +" "+ control+ " "+ "'"+mo.getValueLow()+"'");
                    }else if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))){
                        record.put("WA","AND"+" "+  mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" +" AND "+ "'"+mo.getValueHigh()+"'");
                    }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                        record.put("WA","AND"+" "+  mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'");
                    }

                }
                tmpOptions.add(record);

            }
            String []fields=imports.getFields();
            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(importz, tmpOptions, fields);
            return Optional.ofNullable(me)
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
}
