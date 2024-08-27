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

import cu.edu.unah.demo.model.Users;
import cu.edu.unah.demo.services.UsersServices;

@RequestMapping("/Users")
@RestController
public class UsersController {
	@Autowired
	private UsersServices usersServices;

	@GetMapping(path = { "/findAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Users>> findAll() {
		try {
			return new ResponseEntity<List<Users>>(usersServices.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = { "/find/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Users> findById(@PathVariable String id) {
		try {
			return new ResponseEntity<Users>(usersServices.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = { "/create" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Users> createUsers(
			@RequestBody Users users) throws URISyntaxException {
		Users result = usersServices.save(users);
		return ResponseEntity.created(new URI("/Users/create/" + result.getUsername())).body(result);
	}

	@PutMapping(path = { "/update" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Users> updateCarrera(@RequestBody Users users) throws URISyntaxException {
		if (users.getUsername()==null) {
			return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
		}
		try {
			Users result = usersServices.update(users);
			return ResponseEntity.created(new URI("/Users/updated/" + result.getUsername())).body(result);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = { "/delete/{id}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		usersServices.delete(id);
		return ResponseEntity.ok().build();
	}

}
