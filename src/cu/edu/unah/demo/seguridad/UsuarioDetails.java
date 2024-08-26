/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import cu.edu.unah.demo.model.*;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
public class UsuarioDetails implements UserDetails {

    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities;

    public UsuarioDetails(Usuario usuario) {
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.authorities = usuario.getRoles().stream()
                                   .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                                   .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementar lógica según tu caso
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementar lógica según tu caso
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementar lógica según tu caso
    }

    @Override
    public boolean isEnabled() {
        return true; // Implementar lógica según tu caso
    }
}
