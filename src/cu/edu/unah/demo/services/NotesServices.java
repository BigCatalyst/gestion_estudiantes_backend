package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesServices {

    @Autowired
    private NotesRepository notesrepository;

    public List<Notes> findAll() {
        return notesrepository.findAll();
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
    public boolean existNote(Notes note){
            NotesPK notespk=note.getNotesPK();
            return notespk.getStudentCi()!=null  && findById(notespk.getStudentCi(),notespk.getSubjectId())!=null;
        }

    public Notes save(Notes note) {
        if (existNote(note)) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return notesrepository.save(note);
    }

    public Notes update(Notes note) {
        if (!existNote(note)) {
            throw new EntityExistsException("There is no entity with such ID in the database.");
        }
        return notesrepository.save(note);
    }

    public void delete(String studentCi, int subjectId) {
        Notes note=findById(studentCi, subjectId);
        if (note==null) {
            throw new EntityExistsException("There is no entity with such ID in the database.");
        }
        notesrepository.delete(note);
    }
}
