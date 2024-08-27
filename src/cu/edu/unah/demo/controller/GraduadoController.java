
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
@RequestMapping("/Graduado")
@RestController
public class GraduadoController {
	@Autowired
	private GraduadoServices graduadoservices;
	@GetMapping(path = { "/findAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Graduado>> findAll() {
		try {
			return new ResponseEntity<List<Graduado>>(graduadoservices.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(path = { "/find/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Graduado> findById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Graduado>(graduadoservices.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping(path = { "/create" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Graduado> createGraduado(
			@RequestBody Graduado graduado) throws URISyntaxException {
		Graduado result = graduadoservices.save(graduado);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
		//return ResponseEntity.created(new URI("/Graduado/create/" + result.getId())).body(result);
	}
	@PutMapping(path = { "/update" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Graduado> update(@RequestBody Graduado graduado) throws URISyntaxException {
		if (graduado.getId()==null) {
			return new ResponseEntity<Graduado>(HttpStatus.NOT_FOUND);
		}
		try {
			Graduado result = graduadoservices.update(graduado);
                        return new ResponseEntity<>(result, HttpStatus.OK);
			//return ResponseEntity.created(new URI("/Graduado/updated/" + result.getId())).body(result);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping(path = { "/delete/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		graduadoservices.delete(id);
		return ResponseEntity.ok().build();
	}
}
