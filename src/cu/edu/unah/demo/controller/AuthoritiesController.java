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

import cu.edu.unah.demo.model.Authorities;
import cu.edu.unah.demo.model.AuthoritiesPK;
import cu.edu.unah.demo.services.AuthoritiesServices;

@RequestMapping("/Authorities")
@RestController
public class AuthoritiesController {
	@Autowired
	private AuthoritiesServices authoritiesServices;

	@GetMapping(path = { "/findAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Authorities>> findAll() {
		try {
			return new ResponseEntity<List<Authorities>>(authoritiesServices.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = { "/find" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Authorities> findById(@RequestBody AuthoritiesPK id) {
		try {
			return new ResponseEntity<Authorities>(authoritiesServices.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = { "/findByUsername/{username}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Authorities>> findById(@PathVariable String username) {
		try {
                    //System.out.println("aqui");
			return new ResponseEntity<List<Authorities>>(authoritiesServices.findByUsername(username), HttpStatus.OK);
		} catch (Exception e) {
                    e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping(path = { "/create" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Authorities> createAuthorities(
			@RequestBody Authorities authorities) throws URISyntaxException {
		Authorities result = authoritiesServices.save(authorities);
		return ResponseEntity.created(new URI("/Authorities/create/" + result.getAuthoritiesPK().getAuthority())).body(result);
		//return ResponseEntity.created(new URI("/Authorities/create/" + result.getAuthoritiesPK())).body(result);
	}

	@PutMapping(path = { "/update" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Authorities> updateCarrera(@RequestBody Authorities authorities) throws URISyntaxException {
		if (authorities.getAuthoritiesPK()==null) {
			return new ResponseEntity<Authorities>(HttpStatus.NOT_FOUND);
		}
		try {
			Authorities result = authoritiesServices.update(authorities);
			//return ResponseEntity.created(new URI("/Authorities/updated/" + result.getAuthoritiesPK())).body(result);
			return ResponseEntity.created(new URI("/Authorities/updated/" + result.getAuthoritiesPK().getAuthority())).body(result);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = { "/delete" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> delete(@RequestBody AuthoritiesPK id) {
		authoritiesServices.delete(id);
		return ResponseEntity.ok().build();
	}

}
