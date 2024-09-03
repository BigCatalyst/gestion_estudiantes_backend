
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
        public boolean exist(Integer id) {
            return graduadorepository.existsById(id);
        }
        public Graduado findByCi(String ci) {
            for (Graduado graduado : findAll()) {
                if(graduado.getCi().equals(ci)){
                    return graduado;
                }
            }
            return null;
        }
	public Graduado save(Graduado graduado) {
		if (graduado.getId()!=null && graduadorepository.existsById(graduado.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return graduadorepository.save(graduado);
	}
	public Graduado update(Graduado graduado) {
		if (graduado.getId()!=null && !graduadorepository.existsById(graduado.getId())) {
			throw new EntityNotFoundException("There is no entity with such ID in the database.");
		}
		return graduadorepository.save(graduado);
	}
	public void delete(Integer id) {
		graduadorepository.deleteById(id);
	}
        public String[][] getDatosGraduado() {
            return getDatosGraduado(findAll());
        }
        private String[][] getDatosGraduado(List<Graduado> graduados) {
        String[] titulos = new String[]{"Ci", "Nombre", "Apellido", "Carrera", "No.Es","Genero","Nota"};
        String[][] datos = new String[graduados.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "2", "2", "2", "1","1","1"};
        int row = 2;
        for (Graduado estudiante : graduados) {
            datos[row][0] = estudiante.getCi();
            datos[row][1] = estudiante.getNombre();
            datos[row][2] = estudiante.getApellidos();
            datos[row][3] = estudiante.getCarrera()+"";
            datos[row][4] = estudiante.getNoescalafon()+"";
            datos[row][5] = estudiante.getSexo()+"";
            datos[row][6] = estudiante.getNotaescalafon()+"";
            
            row++;
        }
        return datos;
    }
}
