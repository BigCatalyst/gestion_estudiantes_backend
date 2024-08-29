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

@RequestMapping("/StudentCareer")
@RestController
public class StudentCareerController {

    @Autowired
    private StudentCareerServices studentcareerservices;

    @GetMapping(path = {"/findAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<StudentCareer>> findAll() {
        try {
            return new ResponseEntity<List<StudentCareer>>(studentcareerservices.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = {"/find/{studentCi}/{careerId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StudentCareer> findById(@PathVariable String studentCi, @PathVariable Integer careerId) {
        try {
            return new ResponseEntity<StudentCareer>(studentcareerservices.findById(studentCi, careerId), HttpStatus.OK);
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StudentCareer> createStudentCareer(
            @RequestBody StudentCareer studentcareer) throws URISyntaxException {
        try {
            StudentCareer result = studentcareerservices.save(studentcareer);
            return new ResponseEntity<StudentCareer>(result, HttpStatus.CREATED);
        } catch (EntityNotFoundException | EntityExistsException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = {"/update"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StudentCareer> update(@RequestBody StudentCareer studentcareer) throws URISyntaxException {
        if (studentcareer.getStudentCareerPK() == null) {
            return new ResponseEntity<StudentCareer>(HttpStatus.NOT_FOUND);
        }
        try {
            StudentCareer result = studentcareerservices.update(studentcareer);
            return new ResponseEntity<StudentCareer>(result, HttpStatus.OK);
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = {"/delete/{studentCi}/{careerId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable String studentCi, @PathVariable Integer careerId) {
        try {
            studentcareerservices.delete(studentCi, careerId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
