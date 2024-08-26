/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import java.io.Serializable;
import javax.persistence.Basic;
public class LogoutRequestBody implements Serializable{
        @Basic(optional = false)
        private String token;

    public LogoutRequestBody(String token) {
        this.token = token;
    }

    public LogoutRequestBody() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
        
        
        
}
