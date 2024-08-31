package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.exceptions.*;
import cu.edu.unah.demo.serializadores.UbicacionEscalafonResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private SubjectsServices subjectsservices;

    public List<Students> findAll() {
        return studentsrepository.findAll();
    }

    public List<Students> findAll(int grado) {
        List<Students> students = new ArrayList<>();
        for (Students student : findAll()) {
            if (student.getGrade() == grado) {
                students.add(student);
            }

        }
        return students;
    }

    public Students findById(String id) {
        if (!studentsrepository.existsById(id)) {
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
                if (nota.getFinalExam() == null
                        || nota.getFinalNote() == null
                        || nota.getTcp1() == null) {
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
            graduado = graduadoservices.save(graduado);

            for (Notes nota : notas) {
                Notagraduado notagraduado = new Notagraduado();
                notagraduado.setAsnota((double) nota.getAcs());
                notagraduado.setNotafinal((double) nota.getFinalNote());
                notagraduado.setNombreasignatura(nota.getSubjects().getName());
                notagraduado.setTcp1((double) nota.getTcp1());
                notagraduado.setIdgraduado(graduado.getId());
                if (nota.getTcp2() != null) {
                    notagraduado.setTcp2((double) nota.getTcp2());
                }

                notagraduado = notagraduadoservices.save(notagraduado);
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

    public List<UbicacionEscalafonResponse> obtenerEscalafon() {
        List<Students> estudiantes = findAll(9);
        for (Students estudiante : estudiantes) {
            List<Notes> list_notes = notesservices.findAll(estudiante.getCi());
            for (Notes nota : list_notes) {
                if (nota.getFinalExam() == null
                        || nota.getTcp1() == null) {
                    System.out.println(estudiante.getCi());
                    throw new BadRequestException("No puede tener notas vacias");
                }
            }
        }

        HashMap<Students, Double> estudiantes_notas = new HashMap<>();

        for (Students estudiante : estudiantes) {
            List<Notes> list_notes = notesservices.findAll(estudiante.getCi());
            double suma_notas = 0;
            for (Notes note : list_notes) {
                Subjects asignatura = subjectsservices.findById(note.getNotesPK().getSubjectId());
                boolean es_de_noveno = asignatura.getGrade() == 9;
                if (es_de_noveno) {
                    if (note.getFinalNote() == null) {
                        double prom_asc = note.getAcs(); //en base a 10
                        double prom_tcp = note.getTcp1() * 0.4; //en base a 40
                        if (note.getTcp2() != null) {
                            prom_tcp += note.getTcp2() * 0.4;
                            prom_tcp /= 2;
                        }
                        double acumulado_base_50 = prom_asc + prom_tcp;
                        double pf_base_50 = note.getFinalExam() / 2;
                        double nota_final = acumulado_base_50 + pf_base_50;
                        note.setFinalNote(new Float(nota_final));
                        notesservices.save(note);
                    }
                    suma_notas += note.getFinalNote();
                } else {
                    double prom_asc = note.getAcs(); //en base a 10
                    double prom_tcp = note.getTcp1() * 0.4; //en base a 40
                    if (note.getTcp2() != null) {
                        prom_tcp += note.getTcp2() * 0.4;
                        prom_tcp /= 2;
                    }
                    double acumulado_base_50 = prom_asc + prom_tcp;
                    suma_notas += acumulado_base_50*2;
                }

            }
            double promedio_notas = (suma_notas > 0 && (!list_notes.isEmpty())) ? suma_notas / list_notes.size() : 0;
            estudiantes_notas.put(estudiante, promedio_notas);
        }

        List<Map.Entry<Students, Double>> listaNotas = new ArrayList<>(estudiantes_notas.entrySet());

        // Ordenar la lista utilizando un comparador
        listaNotas.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Ordena de mayor a menor

        // Crear una lista para almacenar los estudiantes ordenados
        List<UbicacionEscalafonResponse> estudiantesOrdenados = new ArrayList<>();
        int ubicacion=1;
        for (Map.Entry<Students, Double> entry : listaNotas) {
            UbicacionEscalafonResponse puesto=new UbicacionEscalafonResponse();
            puesto.setEstudiante(entry.getKey());
            puesto.setLugar(ubicacion++);
            puesto.setPromedio(entry.getValue());
            estudiantesOrdenados.add(puesto);//
        }
        
        return estudiantesOrdenados;
    }
    
    public String[][] getDatosEscalafon(List<UbicacionEscalafonResponse> ubicaciones) {
        String[] titulos = new String[]{"Ci", "Nombre", "Apellido", "Promedio", "No."};
        String[][] datos = new String[ubicaciones.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "3", "3", "1", "1"};
        int row = 2;
        for (UbicacionEscalafonResponse ubicacion : ubicaciones) {
            Students estudiante=ubicacion.getEstudiante();
            datos[row][0] = estudiante.getCi();
            datos[row][1] = estudiante.getName();
            datos[row][2] = estudiante.getLastName();
            datos[row][3] = ubicacion.getPromedio()+"";
            datos[row][4] = ubicacion.getLugar()+"";
            
            row++;
        }
        return datos;
    }
    public String[][] getDatosEstudiantes(int grade) {
        return getDatosEstudiantes(findAll(grade));
    }
    
    public String[][] getDatosEstudiantes() {
        return getDatosEstudiantes(findAll());
    }
    private String[][] getDatosEstudiantes(List<Students> estudiantes) {
        String[] titulos = new String[]{"Ci", "Nombre", "Apellido", "Grado", "No.Reg","Genero","direccion"};
        String[][] datos = new String[estudiantes.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "2", "2", "1", "1","1","2"};
        int row = 2;
        for (Students estudiante : estudiantes) {
            datos[row][0] = estudiante.getCi();
            datos[row][1] = estudiante.getName();
            datos[row][2] = estudiante.getLastName();
            datos[row][3] = estudiante.getGrade()+"";
            datos[row][4] = estudiante.getRegNumber()+"";
            datos[row][5] = estudiante.getSex()+"";
            datos[row][6] = estudiante.getAddress()+"";
            
            row++;
        }
        return datos;
    }
}
