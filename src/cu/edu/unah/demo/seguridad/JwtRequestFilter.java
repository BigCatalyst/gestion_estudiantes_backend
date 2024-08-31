/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

//    @Autowired
    private final UserDetailsService userDetailsService;

//    public JwtRequestFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extraerUsername(jwt);
            } catch (SignatureException ex) {
                System.out.println("token incorrecto");
            } catch (MalformedJwtException ex) {
                System.out.println("token incorrecto");
            } catch (ExpiredJwtException ex) {
                System.out.println("token expirado");
            } 

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Aquí debes cargar el usuario desde la base de datos y establecer la autenticación
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Si el token es válido
                if (userDetails.isEnabled() && jwtUtil.validarToken(jwt, username)) {
                    // Configura la autenticación aquí
                    System.out.println("paso por login");
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Establecer la autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("termino de autenticar");
                }
            } catch (UsernameNotFoundException ex) {
                System.out.println("usuario no encontrado");
            }

        }
        chain.doFilter(request, response);
    }
}
