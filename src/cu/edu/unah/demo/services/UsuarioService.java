/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.seguridad.*;
@Service
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository; // Supón que ya tienes este repositorio
    
    public Usuario findByUsername(String username){
        for(Usuario usuario: usuarioRepository.findAll()){
            if(usuario.getUsername().equals(username)){
                return usuario;
            }
        }
        return null;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = findByUsername(username);
        if(usuario==null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
              
        return new UsuarioDetails(usuario);
    }
}