
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
@RequestMapping("/Subjects")
@RestController
public class SubjectsController {
	@Autowired
	private SubjectsServices subjectsservices;
	@GetMapping(path = { "/findAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Subjects>> findAll() {
		try {
			return new ResponseEntity<List<Subjects>>(subjectsservices.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(path = { "/find/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Subjects> findById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Subjects>(subjectsservices.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping(path = { "/create" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Subjects> createSubjects(
			@RequestBody Subjects subjects) throws URISyntaxException {
		Subjects result = subjectsservices.save(subjects);
		return ResponseEntity.created(new URI("/Subjects/create/" + result.getId())).body(result);
	}
	@PutMapping(path = { "/update" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Subjects> update(@RequestBody Subjects subjects) throws URISyntaxException {
		if (subjects.getId()==null) {
			return new ResponseEntity<Subjects>(HttpStatus.NOT_FOUND);
		}
		try {
			Subjects result = subjectsservices.update(subjects);
			return ResponseEntity.created(new URI("/Subjects/updated/" + result.getId())).body(result);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping(path = { "/delete/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		subjectsservices.delete(id);
		return ResponseEntity.ok().build();
	}
}
