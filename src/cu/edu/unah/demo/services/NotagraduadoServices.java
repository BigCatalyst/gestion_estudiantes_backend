
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NotagraduadoServices {
	@Autowired
	private NotagraduadoRepository notagraduadorepository;
	public List<Notagraduado> findAll() {
		return notagraduadorepository.findAll();
	}
	public Notagraduado findById(Integer id) {
		return notagraduadorepository.findById(id).get();
	}
	public Notagraduado save(Notagraduado notagraduado) {
		if (notagraduado.getId()!=null && notagraduadorepository.existsById(notagraduado.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return notagraduadorepository.save(notagraduado);
	}
	public Notagraduado update(Notagraduado notagraduado) {
		if (notagraduado.getId()!=null && !notagraduadorepository.existsById(notagraduado.getId())) {
			throw new EntityNotFoundException("There is no entity with such ID in the database.");
		}
		return notagraduadorepository.save(notagraduado);
	}
	public void delete(Integer id) {
		notagraduadorepository.deleteById(id);
	}
}
