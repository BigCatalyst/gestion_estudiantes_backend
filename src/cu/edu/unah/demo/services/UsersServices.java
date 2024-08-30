package cu.edu.unah.demo.services;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cu.edu.unah.demo.model.Users;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class UsersServices {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users findById(String id) {
        if (id != null) {
            List<Users> l = findAll();
            for (Users c : l) {
                if (c != null && c.getUsername() != null
                        && c.getUsername().equals(id)) {
                    return c;
                }
            }
        }

        return null;
        //return usersRepository.findById(id).get();
    }

    public Users save(Users users) {
        if (users.getUsername() != null && usersRepository.existsById(users.getUsername())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        users.setPassword(DigestUtils.shaHex(users.getPassword()));

        return usersRepository.save(users);
    }

    public Users update(Users users) {
        if (users.getUsername() != null && !usersRepository.existsById(users.getUsername())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }

        return usersRepository.save(users);
    }

    public Users updatePassword(String username, String password) {
        Users users = findById(username);
        if (users == null) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        users.setPassword(DigestUtils.shaHex(password));
        return usersRepository.save(users);
    }

    public void delete(String id) {
        usersRepository.deleteById(id);
    }

    public String[][] getDatosUsers() {
        return getDatosUsers(findAll());
    }

    private String[][] getDatosUsers(List<Users> usuarios) {
        String[] titulos = new String[]{"Username", "Identi", "nombre", "email", "enabled"};
        String[][] datos = new String[usuarios.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "1", "3", "2", "1"};
        int row = 2;
        for (Users usuario : usuarios) {

            datos[row][0] = usuario.getUsername();
            datos[row][1] = usuario.getIdentificacion();
            datos[row][2] = usuario.getNombre();
            datos[row][3] = usuario.getEmail();
            datos[row][4] = usuario.getEnabled() ? "si" : "no";

            row++;
        }
        return datos;
    }
}
