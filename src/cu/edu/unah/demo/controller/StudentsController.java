
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
@RequestMapping("/Students")
@RestController
public class StudentsController {
	@Autowired
	private StudentsServices studentsservices;
	@GetMapping(path = { "/findAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Students>> findAll() {
		try {
			return new ResponseEntity<List<Students>>(studentsservices.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(path = { "/find/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Students> findById(@PathVariable String id) {
		try {
			return new ResponseEntity<Students>(studentsservices.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping(path = { "/create" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Students> createStudents(
			@RequestBody Students students) throws URISyntaxException {
		Students result = studentsservices.save(students);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	@PutMapping(path = { "/update" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Students> update(@RequestBody Students students) throws URISyntaxException {
		if (students.getCi()==null) {
			return new ResponseEntity<Students>(HttpStatus.NOT_FOUND);
		}
		try {
			Students result = studentsservices.update(students);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping(path = { "/delete/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		studentsservices.delete(id);
		return ResponseEntity.ok().build();
	}
}
