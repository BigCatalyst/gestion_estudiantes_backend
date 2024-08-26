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
    
    public List<TokenInvalido> findAll() {
		return tokenInvalidoRepository.findAll();
	}
        public TokenInvalido findByToken(String token) {
            for (TokenInvalido tokenInvalido : findAll()) {
                if(tokenInvalido.getToken().equals(findAll())){
                    return tokenInvalido;
                }
            }
		return null;
	}
        public boolean existTokenInvalido(String token){
            return findByToken(token)!=null;
        }
	public TokenInvalido findById(Long id) {
		return tokenInvalidoRepository.findById(id).get();
	}
	public TokenInvalido save(TokenInvalido tokenInvalido) {
		if (tokenInvalido.getId()!=null&&tokenInvalidoRepository.existsById(tokenInvalido.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return tokenInvalidoRepository.save(tokenInvalido);
	}
        public void delete(Long id) {
		tokenInvalidoRepository.deleteById(id);
	}
}
