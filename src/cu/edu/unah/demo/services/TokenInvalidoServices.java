/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.services;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenInvalidoServices {
    @Autowired
    TokenInvalidoRepository tokenInvalidoRepository;
    
    public List<Tokeninvalido> findAll() {
		return tokenInvalidoRepository.findAll();
	}
        public Tokeninvalido findByToken(String token) {
            for (Tokeninvalido tokenInvalido : findAll()) {
                if(tokenInvalido.getToken().equals(token)){
                    return tokenInvalido;
                }
            }
		return null;
	}
        public boolean existTokeninvalido(String token){
            return findByToken(token)!=null;
        }
	public Tokeninvalido findById(Integer id) {
		return tokenInvalidoRepository.findById(id).get();
	}
	public Tokeninvalido save(Tokeninvalido tokenInvalido) {
		if (tokenInvalido.getId()!=null&&tokenInvalidoRepository.existsById(tokenInvalido.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return tokenInvalidoRepository.save(tokenInvalido);
	}
        public void delete(Integer id) {
		tokenInvalidoRepository.deleteById(id);
	}
}
