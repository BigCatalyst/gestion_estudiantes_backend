package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.security.auth.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesServices {

    @Autowired
    private NotesRepository notesrepository;

    @Autowired
    private StudentsServices studentsservices;

    @Autowired
    private SubjectsServices subjectsservices;

    public List<Notes> findAll() {
        return notesrepository.findAll();
    }

    public List<Notes> findAll(String studentCi) {
        ArrayList<Notes> notas = new ArrayList<>();
        for (Notes note : findAll()) {
            NotesPK notepk = note.getNotesPK();
            if (notepk.getStudentCi().equals(studentCi)) {
                notas.add(note);
            }
        }
        return notas;
    }

    public List<Notes> findAll(int subjectId) {
        ArrayList<Notes> notas = new ArrayList<>();
        for (Notes note : findAll()) {
            NotesPK notepk = note.getNotesPK();
            if (notepk.getSubjectId() == subjectId) {
                notas.add(note);
            }
        }
        return notas;
    }

    public List<Notes> findByGrade(int grade, int subjectId) {
        ArrayList<Notes> notas = new ArrayList<>();
        for (Notes note : findAll()) {
            NotesPK notepk = note.getNotesPK();

            if (notepk.getSubjectId() == subjectId) {
                Subjects asignatura = subjectsservices.findById(subjectId);
                if (asignatura.getGrade() == grade) {
                    notas.add(note);
                }

            }
        }
        return notas;
    }

    public List<Notes> findByGrade(int grade, String studentCi) {
        ArrayList<Notes> notas = new ArrayList<>();
        for (Notes note : findAll()) {
            NotesPK notepk = note.getNotesPK();
            if (notepk.getStudentCi().equals(studentCi)) {

                int subjectId = notepk.getSubjectId();
                Subjects asignatura = subjectsservices.findById(subjectId);
                if (asignatura.getGrade() == grade) {
                    notas.add(note);
                }

            }

        }
        return notas;
    }

    public List<Notes> findByGrade(int grade) {
        ArrayList<Notes> notas = new ArrayList<>();
        for (Notes note : findAll()) {
            NotesPK notepk = note.getNotesPK();

            int subjectId = notepk.getSubjectId();
            Subjects asignatura = subjectsservices.findById(subjectId);
            if (asignatura.getGrade() == grade) {
                notas.add(note);
            }

        }
        return notas;
    }

    public Notes findById(String studentCi, int subjectId) {
        for (Notes note : findAll()) {
            NotesPK notepk = note.getNotesPK();
            if (notepk.getStudentCi().equals(studentCi) && notepk.getSubjectId() == subjectId) {
                return note;
            }
        }
        return null;
        //return notesrepository.findById(id).get();
    }

    public boolean existNote(Notes note) {
        NotesPK notespk = note.getNotesPK();
        return notespk.getStudentCi() != null && findById(notespk.getStudentCi(), notespk.getSubjectId()) != null;
    }

    public Notes save(Notes note) {
        if (existNote(note)) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return notesrepository.save(note);
    }

    public Notes update(Notes note) {
        if (!existNote(note)) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return notesrepository.save(note);
    }

    public void delete(String studentCi, int subjectId) {
        Notes note = findById(studentCi, subjectId);
        if (note == null) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        notesrepository.delete(note);
    }

    public void delete(Notes note) {
        if (note == null) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        notesrepository.delete(note);
    }

    public List<HashMap<String, String>> getNotasConEstudiante() {
        ArrayList<HashMap<String, String>> response = new ArrayList<>();
        for (Notes note : findAll()) {
            HashMap<String, String> data_nota = new HashMap<>();
            NotesPK notepk = note.getNotesPK();
            String ci = notepk.getStudentCi();
            Integer idAsignatura = notepk.getSubjectId();
            Students estudiante = studentsservices.findById(ci);
            Subjects asignatura = subjectsservices.findById(idAsignatura);
            data_nota.put("ci", ci);
            data_nota.put("nombre", estudiante.getName());
            data_nota.put("apellidos", estudiante.getLastName());
            data_nota.put("asignatura", asignatura.getName());
            data_nota.put("id_asignatura", idAsignatura + "");
            data_nota.put("grade", asignatura.getGrade() + "");
            data_nota.put("as", note.getAcs() + "");
            data_nota.put("tcp1", note.getTcp1() + "");
            data_nota.put("tcp2", note.getTcp2() + "");
            data_nota.put("exmane_final", note.getFinalExam() + "");
            data_nota.put("nota_final", note.getFinalNote() + "");
            response.add(data_nota);
        }
        return response;
    }

    public String[][] getDatosReporteNotes() {
        return getDatosReporteNotes(findAll());
    }

    public String[][] getDatosReporteNotes(String ci) {
        List<Notes> notas = findAll(ci);
        return getDatosReporteNotes(notas);
    }

    public String[][] getDatosReporteNotes(int subjectId) {
        return getDatosReporteNotes(findAll(subjectId));
    }

    public String[][] getDatosReporteNotesByGrade(int grade, int subjectId) {
        return getDatosReporteNotes(findByGrade(grade, subjectId));
    }

    public String[][] getDatosReporteNotesByGrade(int grade) {
        return getDatosReporteNotes(findByGrade(grade));
    }

    private String[][] getDatosReporteNotes(List<Notes> notas) {
        String[] titulos = new String[]{"Ci", "Asignatura", "Grado", "AS", "TCP1", "TCP2", "EF", "FN"};
        String[][] datos = new String[notas.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"3", "3", "1", "1", "1", "1", "1", "1"};
        int row = 2;
        for (Notes nota : notas) {
            Subjects asignatura = subjectsservices.findById(nota.getNotesPK().getSubjectId());
            datos[row][0] = studentsservices.findById(nota.getNotesPK().getStudentCi()).getName();
            datos[row][1] = asignatura.getName();
            datos[row][2] = asignatura.getGrade() + "";
            datos[row][3] = nota.getAcs() != null ? nota.getAcs() + "" : "";
            datos[row][4] = nota.getTcp1() != null ? nota.getTcp1() + "" : "";
            datos[row][5] = nota.getTcp2() != null ? nota.getTcp2() + "" : "";
            datos[row][6] = nota.getFinalExam() != null ? nota.getFinalExam() + "" : "";
            datos[row][7] = nota.getFinalNote() != null ? nota.getFinalNote() + "" : "";
            row++;
        }
        return datos;
    }
}
