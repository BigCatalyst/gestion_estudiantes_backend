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
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
public class UsuarioDetails implements UserDetails {

    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities;
    private boolean enabled;

    public UsuarioDetails(Users usuario,List<String> roles) {
        this.enabled=usuario.getEnabled();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.authorities = roles.stream()
                                   .map(rol -> new SimpleGrantedAuthority(rol))
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
        return this.enabled; // Implementar lógica según tu caso
    }
}
