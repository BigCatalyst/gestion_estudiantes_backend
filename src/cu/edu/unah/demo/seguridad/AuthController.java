/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import cu.edu.unah.demo.exceptions.BadRequestException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.reportes.ReportesUtiles;
import cu.edu.unah.demo.serializadores.*;
import cu.edu.unah.demo.services.TokenInvalidoServices;
import io.jsonwebtoken.SignatureException;
import java.io.Serializable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.digest.DigestUtils;
import cu.edu.unah.demo.seguridad.CustomUserDetailService;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.MediaType;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final JwtUtil jwtUtil;
    
    @Autowired
    private TokenInvalidoServices tokenInvalidoServices;
    
    @Autowired
    private  CustomUserDetailService userDetailsService;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AutenticationRequestBody autentication){//(@RequestParam String username, @RequestParam String password) {
        // Aquí deberías validar las credenciales del usuario (ej. consultando a la base de datos)
        String username=autentication.getUsername();
        String password=autentication.getPassword();
        if(username==null || password==null){
            System.out.println("username:"+username);
            System.out.println("bad request: username null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Users usuario=userDetailsService.findByUsername(username);
        System.out.println(usuario);
        if(usuario==null){
            System.out.println("username:"+username);
            System.out.println("bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!usuario.getPassword().equals(DigestUtils.shaHex(password))){
            System.out.println("username:"+username);
            System.out.println("contraseña incorrecta");
            System.out.println(usuario.getPassword());
            System.out.println(password);
            System.out.println(DigestUtils.shaHex(password));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("no fue bad");
        HashMap<String,String> reponse=new HashMap<>();
        reponse.put("token",jwtUtil.generarToken(autentication.getUsername()) );
        System.out.println("va a dar respuesta");
        // Si las credenciales son válidas
        return new ResponseEntity<>(reponse,HttpStatus.OK);
    }
    
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody LogoutRequestBody logoutRequestBody){//(@RequestParam String username, @RequestParam String password) {
        // Aquí deberías validar las credenciales del usuario (ej. consultando a la base de datos)
        String token=logoutRequestBody.getToken();
        String username = null;
        
        JwtUtil jwtUtil=new JwtUtil();
        jwtUtil.setTokenInvalidoServices(tokenInvalidoServices);
        HashMap<String,String> reponse=new HashMap<>();
        
        try{
            username = jwtUtil.extraerUsername(token);
        }catch(SignatureException ex){
            System.out.println("token incorrecto");
            reponse.put("error", "token incorrecto");
            return new ResponseEntity<>(reponse,HttpStatus.BAD_REQUEST);
        }
        
        if(tokenInvalidoServices.existTokeninvalido(token)){
            System.out.println("bad request Token invalido existe");
            reponse.put("error", "Token invalidado");
            return new ResponseEntity<>(reponse,HttpStatus.BAD_REQUEST);
        }
        
        if(!jwtUtil.validarToken(token, username)){
            System.out.println("bad request Token invalido ");
            reponse.put("error", "Token invalido ");
            return new ResponseEntity<>(reponse,HttpStatus.BAD_REQUEST);
        }
        Tokeninvalido tokenInvalido=new Tokeninvalido();
        tokenInvalido.setToken(token);
        tokenInvalidoServices.save(tokenInvalido);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
//    @GetMapping(path = {"/reporte"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity reporte() {
//        try {
//        return ReportesUtiles.generarReporte(null);
//        }catch(Exception ex){
//        return new ResponseEntity( HttpStatus.BAD_REQUEST);
//        }
//    }
}

