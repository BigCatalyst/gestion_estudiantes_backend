
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AltasBajasServices {
	@Autowired
	private AltasBajasRepository altasbajasrepository;
        @Autowired
	private StudentsRepository studentsRepository;
	public List<AltasBajas> findAll() {
		return altasbajasrepository.findAll();
	}
	public AltasBajas findById(String id) {
		return altasbajasrepository.findById(id).get();
	}
	public AltasBajas save(AltasBajas altasbajas) {
		if (altasbajas.getCi()!=null && altasbajasrepository.existsById(altasbajas.getCi())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
//                System.out.println("paso");
//                altasbajas.setStudents(studentsRepository.findById(altasbajas.getCi()).get());
                return altasbajasrepository.save(altasbajas);
	}
	public AltasBajas update(AltasBajas altasbajas) {
		if (altasbajas.getCi()!=null && !altasbajasrepository.existsById(altasbajas.getCi())) {
			throw new EntityNotFoundException("There is no entity with such ID in the database.");
		}
		return altasbajasrepository.save(altasbajas);
	}
	public void delete(String id) {
            if(!altasbajasrepository.existsById(id)){
                throw new EntityNotFoundException("There is no entity with such ID in the database.");
            }
		altasbajasrepository.deleteById(id);
	}
}
