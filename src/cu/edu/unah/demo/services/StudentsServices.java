
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StudentsServices {
	@Autowired
	private StudentsRepository studentsrepository;
	public List<Students> findAll() {
		return studentsrepository.findAll();
	}
	public Students findById(String id) {
		return studentsrepository.findById(id).get();
	}
	public Students save(Students students) {
		if (students.getCi()!=null && studentsrepository.existsById(students.getCi())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return studentsrepository.save(students);
	}
	public Students update(Students students) {
		if (students.getCi()!=null && !studentsrepository.existsById(students.getCi())) {
			throw new EntityExistsException("There is no entity with such ID in the database.");
		}
		return studentsrepository.save(students);
	}
	public void delete(String id) {
		studentsrepository.deleteById(id);
	}
}
