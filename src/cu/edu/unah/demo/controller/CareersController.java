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
import org.hibernate.id.IdentifierGenerationException;

@RequestMapping("/Careers")
@RestController
public class CareersController {

    @Autowired
    private CareersServices careersservices;

    @GetMapping(path = {"/findAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Careers>> findAll() {
        try {
            return new ResponseEntity<List<Careers>>(careersservices.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = {"/find/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Careers> findById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<Careers>(careersservices.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = {"/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Careers> createCareers(
            @RequestBody Careers careers) throws URISyntaxException {
        try {
            Careers result = careersservices.save(careers);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (EntityNotFoundException | EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = {"/update"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Careers> update(@RequestBody Careers careers) throws URISyntaxException {
        if (careers.getId() == null) {
            return new ResponseEntity<Careers>(HttpStatus.NOT_FOUND);
        }
        try {
            Careers result = careersservices.update(careers);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = {"/delete/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            careersservices.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = {"/reporte"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity reporte() {
        try {
            return ReportesUtiles.generarReporte(careersservices.getDatosCarreras());
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException | BadRequestException | IdentifierGenerationException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
