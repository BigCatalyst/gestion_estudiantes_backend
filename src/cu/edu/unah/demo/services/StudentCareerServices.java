package cu.edu.unah.demo.services;

import cu.edu.unah.demo.exceptions.BadRequestException;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.serializadores.EleccionBoleta;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCareerServices {

    @Autowired
    private StudentCareerRepository studentcareerrepository;

    @Autowired
    private SubjectsServices subjectsservices;

    @Autowired
    private CareersServices careersservices;

    @Autowired
    private StudentsServices studentsservices;

    public List<StudentCareer> findAll() {
        return studentcareerrepository.findAll();
    }

    public List<StudentCareer> findAll(String studentCi) {
        ArrayList<StudentCareer> lista = new ArrayList<>();
        for (StudentCareer entidadPadre : findAll()) {
            StudentCareerPK entidad = entidadPadre.getStudentCareerPK();
            if (entidad.getStudentCi().equals(studentCi)) {
                lista.add(entidadPadre);
            }

        }
        return lista;
        //return studentcareerrepository.findById(id).get();
    }

    public StudentCareer findById(String studentCi, int careerId) {
        for (StudentCareer entidadPadre : findAll()) {
            StudentCareerPK entidad = entidadPadre.getStudentCareerPK();
            if (entidad.getCareerId() == careerId) {
                if (entidad.getStudentCi().equals(studentCi)) {
                    return entidadPadre;
                }
            }

        }
        return null;
        //return studentcareerrepository.findById(id).get();
    }

    public boolean existStudentCareer(StudentCareer studentCareer) {
        if (studentCareer.getStudentCareerPK() == null) {
            return false;
        }
        StudentCareerPK studentcareerpk = studentCareer.getStudentCareerPK();
        if (studentcareerpk.getStudentCi() != null) {
            String ci = studentcareerpk.getStudentCi();
            int id = studentcareerpk.getCareerId();
            boolean existe = findById(ci, id) != null;
            return existe;
        }

        return false;

    }

    public StudentCareer save(StudentCareer studentcareer) {
        if (existStudentCareer(studentcareer)) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        //StudentCareerPK pk=new StudentCareerPK();
        //pk.setCareerId(studentcareer.getCareers().getId());

        //studentcareer.setStudentCareerPK();
        return studentcareerrepository.save(studentcareer);
    }

    public StudentCareer update(StudentCareer studentcareer) {
        if (!existStudentCareer(studentcareer)) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return studentcareerrepository.save(studentcareer);
    }

    public void delete(String studentCi, int careerId) {
        StudentCareer studentCareer = findById(studentCi, careerId);
        if (studentCareer == null) {
            throw new EntityExistsException("There is no entity with such ID in the database.");
        }
        studentcareerrepository.delete(studentCareer);
    }

    public void delete(String studentCi) {
        List<StudentCareer> studentCareers = findAll(studentCi);
        for (StudentCareer studentCareer : studentCareers) {
            studentcareerrepository.delete(studentCareer);
        }

    }

    public void crearBoleta(String ci, List<Integer> ids_carreras) {
        Students student = studentsservices.findById(ci);

        ArrayList<Careers> carreras = new ArrayList<>();
        HashSet<Integer> ids_validados = new HashSet<>();
        for (Integer ids_carrera : ids_carreras) {
            if (ids_validados.contains(ids_carrera)) {
                throw new BadRequestException("Existen ids de carreras repetidos");
            } else {
                ids_validados.add(ids_carrera);
            }
            carreras.add(careersservices.findById(ids_carrera));
        }
        for (int i = 0; i < carreras.size(); i++) {
            Careers carrera = carreras.get(i);
            StudentCareerPK studentCareerPK = new StudentCareerPK();
            studentCareerPK.setCareerId(carrera.getId());
            studentCareerPK.setStudentCi(student.getCi());
            StudentCareer studentCareer = new StudentCareer();
            studentCareer.setIndex(i);
            studentCareer.setStudentCareerPK(studentCareerPK);
            save(studentCareer);
        }

    }

    public List<EleccionBoleta> findAllBoletas() {
        List<EleccionBoleta> elecciones = new ArrayList<>();
        for (StudentCareer estudiantecarrera : findAll()) {
            EleccionBoleta eleccion = new EleccionBoleta();
            eleccion.setCarrera(careersservices.findById(estudiantecarrera.getStudentCareerPK().getCareerId()).getName());
            eleccion.setCi(estudiantecarrera.getStudentCareerPK().getStudentCi());
            eleccion.setLugar(estudiantecarrera.getIndex());
            elecciones.add(eleccion);
        }
        elecciones.sort(new Comparator<EleccionBoleta>() {
            @Override
            public int compare(EleccionBoleta o1, EleccionBoleta o2) {
                int response = o1.getCi().compareTo(o2.getCi());
                return response != 0 ? response : new Integer(o1.getLugar()).compareTo(o2.getLugar());
            }
        });
        return elecciones;
    }

    public String[][] getEleccionBoleta() {
        return getEleccionBoleta(findAllBoletas());
    }

    private String[][] getEleccionBoleta(List<EleccionBoleta> elecciones) {
        String[] titulos = new String[]{"Ci", "Carrera", "Puesto"};
        String[][] datos = new String[elecciones.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "2", "1"};
        int row = 2;
        for (EleccionBoleta eleccion : elecciones) {
            datos[row][0] = eleccion.getCi();
            datos[row][1] = eleccion.getCarrera();
            datos[row][2] = eleccion.getLugar() + "";
            row++;
        }
        return datos;
    }
}
