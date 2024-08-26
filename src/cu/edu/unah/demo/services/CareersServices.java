
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CareersServices {
	@Autowired
	private CareersRepository careersrepository;
	public List<Careers> findAll() {
		return careersrepository.findAll();
	}
	public Careers findById(Integer id) {
		return careersrepository.findById(id).get();
	}
	public Careers save(Careers careers) {
		if (careers.getId()!=null && careersrepository.existsById(careers.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return careersrepository.save(careers);
	}
	public Careers update(Careers careers) {
		if (careers.getId()!=null && !careersrepository.existsById(careers.getId())) {
			throw new EntityExistsException("There is no entity with such ID in the database.");
		}
		return careersrepository.save(careers);
	}
	public void delete(Integer id) {
		careersrepository.deleteById(id);
	}
}
