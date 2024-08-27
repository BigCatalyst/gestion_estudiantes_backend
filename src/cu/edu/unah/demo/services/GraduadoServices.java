
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GraduadoServices {
	@Autowired
	private GraduadoRepository graduadorepository;
	public List<Graduado> findAll() {
		return graduadorepository.findAll();
	}
	public Graduado findById(Integer id) {
		return graduadorepository.findById(id).get();
	}
	public Graduado save(Graduado graduado) {
		if (graduado.getId()!=null && graduadorepository.existsById(graduado.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return graduadorepository.save(graduado);
	}
	public Graduado update(Graduado graduado) {
		if (graduado.getId()!=null && !graduadorepository.existsById(graduado.getId())) {
			throw new EntityExistsException("There is no entity with such ID in the database.");
		}
		return graduadorepository.save(graduado);
	}
	public void delete(Integer id) {
		graduadorepository.deleteById(id);
	}
}
