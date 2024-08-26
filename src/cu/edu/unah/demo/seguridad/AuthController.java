/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.seguridad;

import cu.edu.unah.demo.serializadores.AutenticationRequestBody;
import java.io.Serializable;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AutenticationRequestBody autentication){//(@RequestParam String username, @RequestParam String password) {
        // Aquí deberías validar las credenciales del usuario (ej. consultando a la base de datos)
        String username=autentication.getUsername();
        
        if(username==null || !username.equals("admin")){
            System.out.println("username:"+username);
            System.out.println("bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("no fue bad");
        HashMap<String,String> reponse=new HashMap<>();
        reponse.put("token",jwtUtil.generarToken(autentication.getUsername()) );
        System.out.println("va a dar respuesta");
        // Si las credenciales son válidas
        return new ResponseEntity<>(reponse,HttpStatus.OK);
    }
}

