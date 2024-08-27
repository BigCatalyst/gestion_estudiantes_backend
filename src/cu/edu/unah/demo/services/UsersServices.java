package cu.edu.unah.demo.services;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cu.edu.unah.demo.model.Users;

@Service
public class UsersServices {

	@Autowired
	private UsersRepository usersRepository;
	
	public List<Users> findAll() {
		return usersRepository.findAll();
	}

	public Users findById(String id) {if(id!=null){
                List<Users> l=findAll();
                for (Users c : l) {
                    if(c!=null&&c.getUsername()!=null&&
                            c.getUsername().equals(id)
                            ){
                        return c;
                    }
                }
            }
            
            return null;
		//return usersRepository.findById(id).get();
	}

	public Users save(Users users) {
		if (users.getUsername()!=null && usersRepository.existsById(users.getUsername())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}

		return usersRepository.save(users);
	}

	public Users update(Users users) {
		if (users.getUsername()!=null && !usersRepository.existsById(users.getUsername())) {
			throw new EntityNotFoundException("There is no entity with such ID in the database.");
		}

		return usersRepository.save(users);
	}

	public void delete(String id) {
		usersRepository.deleteById(id);
	}
}
