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

import cu.edu.unah.demo.model.Users;
import cu.edu.unah.demo.reportes.ReportesUtiles;
import cu.edu.unah.demo.serializadores.ChangePasswordRequestBody;
import cu.edu.unah.demo.services.UsersServices;
import java.util.HashMap;
import javax.persistence.EntityExistsException;
import org.hibernate.id.IdentifierGenerationException;

@RequestMapping("/Users")
@RestController
public class UsersController {

    @Autowired
    private UsersServices usersServices;

    @GetMapping(path = {"/findAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Users>> findAll() {
        try {
            return new ResponseEntity<List<Users>>(usersServices.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch ( EntityExistsException |BadRequestException | IdentifierGenerationException  e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = {"/find/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Users> findById(@PathVariable String id) {
        try {
            return new ResponseEntity<Users>(usersServices.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch ( EntityExistsException |BadRequestException | IdentifierGenerationException  e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = {"/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Users> createUsers(
            @RequestBody Users users) throws URISyntaxException {
        try {
            Users result = usersServices.save(users);
            return new ResponseEntity<Users>(result, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }catch ( EntityExistsException |BadRequestException | IdentifierGenerationException  e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = {"/update"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Users> update(@RequestBody Users users) throws URISyntaxException {
        if (users.getUsername() == null) {
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        try {
            Users result = usersServices.update(users);
            return new ResponseEntity<Users>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch ( EntityExistsException |BadRequestException | IdentifierGenerationException  e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = {"/changepassword"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Users> updatePassword(@RequestBody ChangePasswordRequestBody body) throws URISyntaxException {
        String username = body.getPassword();
        String password = body.getPassword();
        if (username == null || password == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {

            Users result = usersServices.updatePassword(username, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch ( EntityExistsException |BadRequestException | IdentifierGenerationException  e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = {"/delete/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            usersServices.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch ( EntityExistsException |BadRequestException | IdentifierGenerationException  e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping(path = {"/reporte"})
    public ResponseEntity reporte() {
        System.out.println("entro?");
        try {
            String [][]data=usersServices.getDatosUsers();
            System.out.println("paso la obtencion de los datos");
            ResponseEntity response=ReportesUtiles.generarReporte(data);
            System.out.println("paso la creacion del response");
            return response;
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
