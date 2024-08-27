/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import cu.edu.unah.demo.services.TokenInvalidoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenInvalidoServices tokenInvalidoServices;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setTokenInvalidoServices(tokenInvalidoServices);
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Permitir acceso a la ruta de autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra ruta
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Usar estado sin sesión
    }
}
