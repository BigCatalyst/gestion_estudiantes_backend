package cu.edu.unah.demo.controller;

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
import cu.edu.unah.demo.services.*;
import java.util.HashMap;
import javax.persistence.EntityExistsException;

@RequestMapping("/Notagraduado")
@RestController
public class NotagraduadoController {

    @Autowired
    private NotagraduadoServices notagraduadoservices;

    @GetMapping(path = {"/findAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Notagraduado>> findAll() {
        try {
            return new ResponseEntity<List<Notagraduado>>(notagraduadoservices.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = {"/find/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Notagraduado> findById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<Notagraduado>(notagraduadoservices.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Notagraduado> createNotagraduado(
            @RequestBody Notagraduado notagraduado) throws URISyntaxException {
        try {
            Notagraduado result = notagraduadoservices.save(notagraduado);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (EntityNotFoundException | EntityExistsException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = {"/update"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Notagraduado> update(@RequestBody Notagraduado notagraduado) throws URISyntaxException {
        if (notagraduado.getId() == null) {
            return new ResponseEntity<Notagraduado>(HttpStatus.NOT_FOUND);
        }
        try {
            Notagraduado result = notagraduadoservices.update(notagraduado);
            return new ResponseEntity<>(result, HttpStatus.OK);
            //return ResponseEntity.created(new URI("/Notagraduado/updated/" + result.getId())).body(result);
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = {"/delete/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            notagraduadoservices.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
