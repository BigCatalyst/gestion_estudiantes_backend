package cu.edu.unah.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cu.edu.unah.demo.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>{

}
