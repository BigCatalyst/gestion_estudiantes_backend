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
import java.util.ArrayList;

@RequestMapping("/Notes")
@RestController
public class NotesController {

    @Autowired
    private NotesServices notesservices;

    @GetMapping(path = {"/findAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Notes>> findAll() {
        try {
//                        System.out.println("entro");
            List<Notes> notas = notesservices.findAll();
//                        System.out.println("tiene la lista");
//                        for (int i = 0; i < notas.size(); i++) {
//                            System.out.println(notas.get(i).getStudents().getCi());
//                    }

            return new ResponseEntity<>(notas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = {"/find/{studentCi}/{subjectId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Notes> findById(@PathVariable String studentCi, @PathVariable Integer subjectId) {
        try {
            return new ResponseEntity<Notes>(notesservices.findById(studentCi, subjectId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Notes> createNotes(
            @RequestBody Notes notes) throws URISyntaxException {
        Notes result = notesservices.save(notes);
        return new ResponseEntity<Notes>(result, HttpStatus.CREATED);
    }

    @PutMapping(path = {"/update"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Notes> update(@RequestBody Notes notes) throws URISyntaxException {
        if (notes.getNotesPK() == null) {
            return new ResponseEntity<Notes>(HttpStatus.NOT_FOUND);
        }
        try {
            Notes result = notesservices.update(notes);
            return new ResponseEntity<Notes>(result, HttpStatus.OK);
            //return ResponseEntity.created(new URI("/Notes/updated/" + result.getId())).body(result);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = {"/delete/{studentCi}/{subjectId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable String studentCi, @PathVariable Integer subjectId) {
        notesservices.delete(studentCi, subjectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = {"/notasestudiantes"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity findAllNotasConEstudiante() {
        try {
            return new ResponseEntity<>(notesservices.getNotasConEstudiante(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
