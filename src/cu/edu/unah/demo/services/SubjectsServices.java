package cu.edu.unah.demo.services;

import cu.edu.unah.demo.exceptions.BadRequestException;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectsServices {

    @Autowired
    private SubjectsRepository subjectsrepository;

    public List<Subjects> findAll() {
        return subjectsrepository.findAll();
    }

    public Subjects getSubjects(String nombre) {
        for (Subjects subjects : findAll()) {
            if (subjects.getName().equals(nombre)) {
                return subjects;
            }
        }
        return null;
    }

    public boolean existeSubjects(String nombre) {
        return getSubjects(nombre) != null;
    }

    public boolean existeOtro(Subjects original) {
        String nombre=original.getName();
        Subjects subjects = getSubjects(nombre);
        if (subjects != null && subjects.getId().equals(original.getId())) {
            return false;
        }
        return subjects != null;
    }

    public Subjects findById(Integer id) {
        return subjectsrepository.findById(id).get();
    }

    public Subjects save(Subjects subjects) {
        if (subjects.getId() != null) {
            if (subjectsrepository.existsById(subjects.getId())) {
                throw new EntityExistsException("There is already existing entity with such ID in the database.");
            }
            if (existeSubjects(subjects.getName())) {
                throw new BadRequestException("Ya existe otra asignatura con ese nombre");
            }
        }

        return subjectsrepository.save(subjects);
    }

    public Subjects update(Subjects subjects) {

        if (subjects.getId() != null) {
            if (!subjectsrepository.existsById(subjects.getId())) {
                throw new EntityExistsException("There is no entity with such ID in the database.");
            }
            if (existeOtro(subjects)) {
                throw new BadRequestException("Ya existe otra asignatura con ese nombre");
            }
        }
        return subjectsrepository.save(subjects);
    }

    public void delete(Integer id) {
        subjectsrepository.deleteById(id);
    }
}
