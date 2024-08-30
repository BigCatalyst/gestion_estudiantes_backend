package cu.edu.unah.demo.controller;

import cu.edu.unah.demo.exceptions.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.reportes.ReportesUtiles;
import cu.edu.unah.demo.services.*;
import java.util.HashMap;
import javax.persistence.EntityExistsException;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/AltasBajas")
@RestController
public class AltasBajasController {

    @Autowired
    private AltasBajasServices altasbajasservices;

    @GetMapping(path = {"/findAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AltasBajas>> findAll() {
        try {
            return new ResponseEntity<List<AltasBajas>>(altasbajasservices.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = {"/find/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AltasBajas> findById(@PathVariable String id) {
        try {
            return new ResponseEntity<AltasBajas>(altasbajasservices.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = {"/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AltasBajas> createAltasBajas(
            @RequestBody AltasBajas altasbajas) throws URISyntaxException {
        try {
            AltasBajas result = altasbajasservices.save(altasbajas);
            return new ResponseEntity<AltasBajas>(result, HttpStatus.CREATED);
        } catch (EntityNotFoundException | EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path = {"/update"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AltasBajas> update(@RequestBody AltasBajas altasbajas) throws URISyntaxException {
        if (altasbajas.getCi() == null) {
            return new ResponseEntity<AltasBajas>(HttpStatus.NOT_FOUND);
        }
        try {
            AltasBajas result = altasbajasservices.update(altasbajas);
            //return ResponseEntity.created(new URI("/AltasBajas/updated/" + result.getCi())).body(result);
            return new ResponseEntity<AltasBajas>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = {"/delete/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            altasbajasservices.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = {"/reporte"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity reporte() {
        try {
            return ReportesUtiles.generarReporte(null);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
