package cu.edu.unah.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cu.edu.unah.demo.model.Authorities;
import cu.edu.unah.demo.model.AuthoritiesPK;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesPK>{

	public List<Authorities> findByUsername(String username);
}
