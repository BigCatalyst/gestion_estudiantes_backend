
package cu.edu.unah.demo.services;
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
	public Subjects findById(Integer id) {
		return subjectsrepository.findById(id).get();
	}
	public Subjects save(Subjects subjects) {
		if (subjects.getId()!=null && subjectsrepository.existsById(subjects.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return subjectsrepository.save(subjects);
	}
	public Subjects update(Subjects subjects) {
		if (subjects.getId()!=null && !subjectsrepository.existsById(subjects.getId())) {
			throw new EntityExistsException("There is no entity with such ID in the database.");
		}
		return subjectsrepository.save(subjects);
	}
	public void delete(Integer id) {
		subjectsrepository.deleteById(id);
	}
}
