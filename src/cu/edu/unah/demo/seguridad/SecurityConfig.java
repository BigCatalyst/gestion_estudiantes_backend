/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import cu.edu.unah.demo.services.TokenInvalidoServices;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        
        
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Permitir acceso a la ruta de autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra ruta
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Usar estado sin sesión
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Especifica los orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Todos los encabezados permitidos
        configuration.setAllowCredentials(true); // Si necesitas enviar cookies o autenticación

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración a todas las rutas
        return source;
    }
    
}
