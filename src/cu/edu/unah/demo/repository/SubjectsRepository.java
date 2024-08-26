
package cu.edu.unah.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cu.edu.unah.demo.model.*;
@Repository
public interface SubjectsRepository extends JpaRepository<Subjects,Integer>{
}
