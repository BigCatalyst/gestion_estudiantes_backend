package cu.edu.unah.demo.services;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cu.edu.unah.demo.model.Authorities;
import cu.edu.unah.demo.model.AuthoritiesPK;
import java.util.ArrayList;

@Service
public class AuthoritiesServices {

	@Autowired
	private AuthoritiesRepository authoritiesRepository;
	
	public List<Authorities> findAll() {
		return authoritiesRepository.findAll();
	}

	public Authorities findById(AuthoritiesPK id) {
            List<Authorities> la=authoritiesRepository.findAll();
            
            for (Authorities authorities : la) {
                if(authorities.getAuthoritiesPK().getUsername().equals(id.getUsername())
                        &&authorities.getAuthoritiesPK().getAuthority().equals(id.getAuthority())
                        ){
                    return authorities;
                }
            }
            return null;
		//return authoritiesRepository.findById(id).get();
	}
	public List<Authorities> findByUsername(String username){
            List<Authorities> la=authoritiesRepository.findAll();
            List<Authorities> r=new ArrayList<Authorities>();
            for (Authorities authorities : la) {
                if(authorities!=null&&authorities.getUsers()!=null
                        &&authorities.getUsers().getUsername()!=null
                        &&authorities.getUsers().getUsername().equals(username)){
                    r.add(authorities);
                }
            }
            return r;
		//return authoritiesRepository.findByUsername(username);
	}

	public Authorities save(Authorities authorities) {
		if (authorities.getAuthoritiesPK()!=null && authoritiesRepository.existsById(authorities.getAuthoritiesPK())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}

		return authoritiesRepository.save(authorities);
	}

	public Authorities update(Authorities authorities) {
		if (authorities.getAuthoritiesPK()!=null && !authoritiesRepository.existsById(authorities.getAuthoritiesPK())) {
			throw new EntityNotFoundException("There is no entity with such ID in the database.");
		}

		return authoritiesRepository.save(authorities);
	}

	public void delete(AuthoritiesPK id) {
		authoritiesRepository.deleteById(id);
	}
}
