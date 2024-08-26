
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StudentCareerServices {
	@Autowired
	private StudentCareerRepository studentcareerrepository;
	public List<StudentCareer> findAll() {
		return studentcareerrepository.findAll();
	}
	public StudentCareer findById(String studentCi,int careerId) {
            for (StudentCareer entidadPadre : findAll()) {
                StudentCareerPK entidad =entidadPadre.getStudentCareerPK();
                if(entidad.getCareerId()==careerId&&entidad.getStudentCi().endsWith(studentCi)){
                    return entidadPadre;
                }
            }
            return null;
		//return studentcareerrepository.findById(id).get();
	}
        public boolean existStudentCareer(StudentCareer studentCareer){
            if (studentCareer.getStudentCareerPK()==null){
                return false;
            }
            StudentCareerPK studentcareerpk=studentCareer.getStudentCareerPK();
            return studentcareerpk.getStudentCi()!=null  && findById(studentcareerpk.getStudentCi(),studentcareerpk.getCareerId())!=null;
        }
	public StudentCareer save(StudentCareer studentcareer) {
		if (existStudentCareer(studentcareer)) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
                //StudentCareerPK pk=new StudentCareerPK();
                //pk.setCareerId(studentcareer.getCareers().getId());
                
                //studentcareer.setStudentCareerPK();
		return studentcareerrepository.save(studentcareer);
	}
	public StudentCareer update(StudentCareer studentcareer) {
		if (!existStudentCareer(studentcareer)) {
			throw new EntityExistsException("There is no entity with such ID in the database.");
		}
		return studentcareerrepository.save(studentcareer);
	}
	public void delete(String studentCi, int careerId) {
        StudentCareer studentCareer = findById(studentCi, careerId);
        if (studentCareer == null) {
            throw new EntityExistsException("There is no entity with such ID in the database.");
        }
		studentcareerrepository.delete(studentCareer);
	}
}
