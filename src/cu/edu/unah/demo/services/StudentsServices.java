package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.exceptions.*;
import cu.edu.unah.demo.serializadores.BoletaEstudiante;
import cu.edu.unah.demo.serializadores.UbicacionEscalafonResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentsServices {

    @Autowired
    private CareersServices careersServices;

    @Autowired
    private StudentCareerServices studentCareerServices;

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

    @Autowired
    private OtorgamientoService otorgamientoService;

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

    public void subirDeGrado(String ci, String carrera, String Nodematricula, Double notaescalafon, Integer noescalafon) {
        if (!studentsrepository.existsById(ci)) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }

        Students estudiante = findById(ci);
        List<Notes> notas_comprobadas = notesservices.findByGrade(estudiante.getGrade(), ci);
        if (notas_comprobadas.isEmpty()) {
            throw new BadRequestException("Este estudiante no tiene notas");
        }
        for (Notes nota : notas_comprobadas) {
            if (nota.getFinalExam() == null
                    || nota.getFinalNote() == null
                    || nota.getTcp1() == null) {
                throw new BadRequestException("No puede tener notas vacias");
            }
            if (nota.getFinalExam() < (30)) {
                throw new BadRequestException("La nota final es menor a 30");
            }
        }

        if (estudiante.getGrade() >= 9) {

            Graduado graduado = graduadoservices.findByCi(ci);
            if (graduado == null) {
                graduado = new Graduado();
            }
            graduado.setApellidos(estudiante.getLastName());
            graduado.setCarrera(carrera);
            graduado.setCi(estudiante.getCi());
            graduado.setDireccion(estudiante.getAddress());
            graduado.setFechagraduacion(new Date());
            graduado.setNodematricula(noescalafon + "");
            graduado.setNombre(estudiante.getName());
            graduado.setSexo(estudiante.getSex());
            graduado.setNotaescalafon(notaescalafon);
            graduado.setNoescalafon(noescalafon);

            if (graduado.getId() == null) {
                graduado = graduadoservices.save(graduado);
            } else {
                graduadoservices.update(graduado);
            }

            for (Notes nota : notas_comprobadas) {
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
            List<Notes> notas8y7 = notesservices.findAll(ci);
            for (Notes nota : notas8y7) {
                notesservices.delete(nota);
            }
            AltasBajas altasBajas = altasbajasservices.findByIdIfExist(ci);
            if (altasBajas != null) {
                altasbajasservices.delete(altasBajas.getCi());
            }
            studentCareerServices.delete(ci);
            Otorgamiento otorgamiento = otorgamientoService.findById(ci);
            if (otorgamiento != null) {
                otorgamientoService.delete(otorgamiento.getId());
            }

            delete(ci);
        } else {
            estudiante.setGrade(estudiante.getGrade() + 1);
            update(estudiante);
        }
    }

    public void subirDeGradoAll() {
        for (Otorgamiento otorgamiento : otorgamientoService.findAll()) {
            String ci = otorgamiento.getCi();
            String carrera = otorgamiento.getCarrera();
            String Nodematricula = otorgamiento.getNoescalafon() + "";
            Double notaescalafon = otorgamiento.getNotaescalafon();
            Integer noescalafon = otorgamiento.getNoescalafon();
            try {
                subirDeGrado(ci, carrera, Nodematricula, notaescalafon, noescalafon);
            } catch (BadRequestException ex) {
            }

            //otorgamientoService.delete(otorgamiento.getId());
        }
        for (Students students : findAll(8)) {
            try {
                subirDeGrado(students.getCi(), null, null, null, null);
            } catch (BadRequestException ex) {
            }
        }
        for (Students students : findAll(7)) {
            try {
                subirDeGrado(students.getCi(), null, null, null, null);
            } catch (BadRequestException ex) {
            }
        }
    }

    public void realizarOtorgamiento() {
        List<Careers> carrerasDisponibles = careersServices.findAll();
        List<UbicacionEscalafonResponse> ubicacionEscalafon = obtenerEscalafon();
        List<BoletaEstudiante> boletasEstudiantes = new ArrayList<>();

        List<HashMap<String, Object>> rawBoletasFormato = studentCareerServices.findAllBoletasFormato();
        for (HashMap<String, Object> hashMap : rawBoletasFormato) {
            BoletaEstudiante boletaEstudiante = new BoletaEstudiante();
            boletaEstudiante.setCi(hashMap.get("ci").toString());
            boletaEstudiante.setNombre_carreras((List<String>) hashMap.get("carreras"));
            boletasEstudiantes.add(boletaEstudiante);
        }
        otorgarCarreras(carrerasDisponibles, boletasEstudiantes, ubicacionEscalafon);
    }

    public List<UbicacionEscalafonResponse> obtenerEscalafon() {
        List<Students> estudiantes_a_validar = findAll(9);

        List<Students> estudiantes = new ArrayList<>();

        for (Students estudiante : estudiantes_a_validar) {
            boolean estudiante_aprobado = false;
            List<Notes> list_notes = notesservices.findByGrade(estudiante.getCi());
            for (Notes nota : list_notes) {
                if (nota.getFinalExam() == null
                        || nota.getTcp1() == null
                        || nota.getAcs() == null) {
                    System.out.println(estudiante.getCi());
                    continue;
                    //throw new BadRequestException("No puede tener notas vacias");
                }
                if (nota.getFinalExam() < 30) {
                    continue;
                }
                estudiante_aprobado = true;

            }
            if (estudiante_aprobado) {
                estudiantes.add(estudiante);
            }
        }

        HashMap<Students, Double> estudiantes_notas = new HashMap<>();

        for (Students estudiante : estudiantes) {
            List<Notes> list_notes = notesservices.findByGrade(estudiante.getCi());
            double suma_notas = 0;
            for (Notes note : list_notes) {
                Subjects asignatura = subjectsservices.findById(note.getNotesPK().getSubjectId());
                boolean es_de_noveno = asignatura.getGrade() == 9;
                if (es_de_noveno) {
                    if (note.getFinalNote() == null) {
                        double prom_asc = note.getAcs(); //en base a 10
                        double prom_tcp = note.getTcp1();//* 0.4; //en base a 40
                        if (note.getTcp2() != null && asignatura.getTcp2()) {
                            prom_tcp += note.getTcp2();//* 0.4;
                            prom_tcp /= 2;
                        }
                        double acumulado_base_50 = prom_asc + prom_tcp;
                        double pf_base_50 = note.getFinalExam() / 2;
                        double nota_final = acumulado_base_50 + pf_base_50;
                        note.setFinalNote(new Float(nota_final));
                        notesservices.update(note);
                    }
                    suma_notas += note.getFinalNote();
                } else {
                    double prom_asc = note.getAcs(); //en base a 10
                    double prom_tcp = note.getTcp1();// * 0.4; //en base a 40
                    if (note.getTcp2() != null && asignatura.getTcp2()) {
                        prom_tcp += note.getTcp2();// * 0.4;
                        prom_tcp /= 2;
                    }
                    double acumulado_base_50 = prom_asc + prom_tcp;
                    suma_notas += acumulado_base_50 * 2;
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
        int ubicacion = 1;
        for (Map.Entry<Students, Double> entry : listaNotas) {
            UbicacionEscalafonResponse puesto = new UbicacionEscalafonResponse();
            puesto.setEstudiante(entry.getKey());
            puesto.setLugar(ubicacion++);
            puesto.setPromedio(entry.getValue());
            estudiantesOrdenados.add(puesto);//
        }
        Date date = new Date();
        for (UbicacionEscalafonResponse estudiantesOrdenado : estudiantesOrdenados) {
            String ci = estudiantesOrdenado.getEstudiante().getCi();
            Otorgamiento otorgamiento = otorgamientoService.findById(ci);
            if (otorgamiento == null) {
                otorgamiento = new Otorgamiento();
                otorgamiento.setCi(ci);
                otorgamiento.setNoescalafon(estudiantesOrdenado.getLugar());
                otorgamiento.setYeargraduacion(date.getYear() + 1900);
                System.out.println(otorgamiento.getYeargraduacion());
                otorgamiento.setNotaescalafon(estudiantesOrdenado.getPromedio());
                otorgamientoService.save(otorgamiento);
            }
        }
        return estudiantesOrdenados;
    }

    public void otorgarCarreras(List<Careers> carrerasDisponibles,
            List<BoletaEstudiante> boletasEstudiantes,
            List<UbicacionEscalafonResponse> ubicacionEscalafon) {
        Date date = new Date();

        // Mapa para llevar el control de las plazas disponibles por carrera
        Map<String, Integer> plazasDisponibles = carrerasDisponibles.stream()
                .collect(Collectors.toMap(Careers::getName, Careers::getAmount));

        List<Otorgamiento> otorgamientos = new ArrayList<>();

        // Asignar carreras, considerando la priorización de escalafón y deseos
        for (UbicacionEscalafonResponse ubicacion : ubicacionEscalafon) {
            String ciEstudiante = ubicacion.getEstudiante().getCi();
            BoletaEstudiante boleta = boletasEstudiantes.stream()
                    .filter(b -> b.getCi().equals(ciEstudiante))
                    .findFirst()
                    .orElse(null);

            if (boleta != null) {
                // Buscar la carrera que puede ser asignada según los deseos y disponibilidad
                for (String deseo : boleta.nombre_carreras) {
                    Integer plazas = plazasDisponibles.get(deseo);
                    if (plazas != null && plazas > 0) {
                        Otorgamiento otorgamiento = otorgamientoService.findById(ciEstudiante);
                        if (otorgamiento == null) {
                            otorgamiento = new Otorgamiento();
                            otorgamiento.setCi(ciEstudiante);
                            otorgamiento.setNoescalafon(ubicacion.getLugar());
                            otorgamiento.setYeargraduacion(date.getYear() + 1900);
                            System.out.println(otorgamiento.getYeargraduacion());
                            otorgamiento.setNotaescalafon(ubicacion.getPromedio());
                            otorgamiento.setCarrera(deseo);
                            otorgamientoService.save(otorgamiento);
                        } else {
                            otorgamiento.setCarrera(deseo);
                            otorgamientoService.update(otorgamiento);
                        }

//                        // Asignamos la carrera
//                        otorgamientos.add(new Otorgamiento(ciEstudiante, deseo));
                        // Reducimos la cantidad de plazas disponibles
                        plazasDisponibles.put(deseo, plazas - 1);
                        break; // Salimos del bucle ya que el estudiante ha sido asignado
                    }
                }
            }
        }

    }

    public String[][] getDatosEscalafon(List<UbicacionEscalafonResponse> ubicaciones) {
        String[] titulos = new String[]{"Ci", "Nombre", "Apellido", "Promedio", "No."};
        String[][] datos = new String[ubicaciones.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "3", "3", "1", "1"};
        int row = 2;
        for (UbicacionEscalafonResponse ubicacion : ubicaciones) {
            Students estudiante = ubicacion.getEstudiante();
            datos[row][0] = estudiante.getCi();
            datos[row][1] = estudiante.getName();
            datos[row][2] = estudiante.getLastName();
            datos[row][3] = String.format("%.2f", ubicacion.getPromedio());//ubicacion.getPromedio()+"";
            datos[row][4] = ubicacion.getLugar() + "";

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
        String[] titulos = new String[]{"Ci", "Nombre", "Apellido", "Grado", "No.Reg", "Genero", "direccion"};
        String[][] datos = new String[estudiantes.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "2", "2", "1", "1", "1", "2"};
        int row = 2;
        for (Students estudiante : estudiantes) {
            datos[row][0] = estudiante.getCi();
            datos[row][1] = estudiante.getName();
            datos[row][2] = estudiante.getLastName();
            datos[row][3] = estudiante.getGrade() + "";
            datos[row][4] = estudiante.getRegNumber() + "";
            datos[row][5] = estudiante.getSex() + "";
            datos[row][6] = estudiante.getAddress() + "";

            row++;
        }
        return datos;
    }
}
