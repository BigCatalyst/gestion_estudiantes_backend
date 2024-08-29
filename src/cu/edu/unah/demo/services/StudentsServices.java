package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.exceptions.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentsServices {

    @Autowired
    private StudentsRepository studentsrepository;

    @Autowired
    private NotagraduadoServices notagraduadoservices;

    @Autowired
    private NotesServices notesservices;

    @Autowired
    private AltasBajasServices altasbajasservices;

    @Autowired
    private GraduadoServices graduadoservices;

    public List<Students> findAll() {
        return studentsrepository.findAll();
    }

    public Students findById(String id) {
        if(!studentsrepository.existsById(id)){
            throw new EntityExistsException("There is no entity with such ID in the database.");
        }
        return studentsrepository.findById(id).get();
    }

    public Students save(Students students) {
        if (students.getCi() != null && studentsrepository.existsById(students.getCi())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return studentsrepository.save(students);
    }

    public Students update(Students students) {
        if (students.getCi() != null && !studentsrepository.existsById(students.getCi())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return studentsrepository.save(students);
    }

    public void delete(String id) {
        studentsrepository.deleteById(id);
    }

    public void subirDeGrado(String ci, String carrera, String Nodematricula) {
        if (!studentsrepository.existsById(ci)) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        Students estudiante = findById(ci);
        if (estudiante.getGrade() >= 9) {
            List<Notes> notas = notesservices.findAll(ci);
            for (Notes nota : notas) {
                if(nota.getFinalExam()==null
                        ||nota.getFinalNote()==null
                        ||nota.getTcp1()==null
                        ||nota.getTcp2()==null
                        ){
                    throw new BadRequestException("No puede tener notas vacias");
                }
            }
            
            Graduado graduado = new Graduado();
            graduado.setApellidos(estudiante.getLastName());
            graduado.setCarrera(carrera);
            graduado.setCi(estudiante.getCi());
            graduado.setDireccion(estudiante.getAddress());
            graduado.setFechagraduacion(new Date());
            graduado.setNodematricula(carrera);
            graduado.setNombre(estudiante.getName());
            graduado.setSexo(estudiante.getSex());
            graduado=graduadoservices.save(graduado);
            
            
            for (Notes nota : notas) {
                Notagraduado notagraduado=new Notagraduado();
                notagraduado.setAsnota((double)nota.getAcs());
                notagraduado.setNotafinal((double)nota.getFinalNote());
                notagraduado.setNombreasignatura(nota.getSubjects().getName());
                notagraduado.setTcp1((double)nota.getTcp1());
                notagraduado.setTcp2((double)nota.getTcp2());
                notagraduado=notagraduadoservices.save(notagraduado);
                notesservices.delete(nota);
            }
            for (AltasBajas altasBajas : altasbajasservices.findAll()) {
                altasbajasservices.delete(altasBajas.getCi());
            }
            delete(ci);
        } else {
            estudiante.setGrade(estudiante.getGrade() + 1);
            update(estudiante);
        }
    }
}
