/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.seguridad.*;
import cu.edu.unah.demo.services.AuthoritiesServices;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    
    @Autowired
    private UsersRepository usuarioRepository; 
    
    @Autowired
    private AuthoritiesServices authoritiesServices;
    
    public Users findByUsername(String username){
        for(Users usuario: usuarioRepository.findAll()){
            if(usuario.getUsername().equals(username)){
                return usuario;
            }
        }
        return null;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users usuario = findByUsername(username);
        if(usuario==null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        List<Authorities> authoritieses=authoritiesServices.findByUsername(username);
        
        ArrayList<String> roles=new ArrayList<String>();
        for (Authorities authoritiese : authoritieses) {
            roles.add(authoritiese.getAuthoritiesPK().getAuthority());
        }
        return new UsuarioDetails(usuario,roles);
    }
}