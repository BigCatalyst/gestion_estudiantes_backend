/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import java.io.Serializable;
import javax.persistence.Basic;

public class AutenticationRequestBody implements Serializable{
        @Basic(optional = false)
        private String username;
        @Basic(optional = false)
        private String password;
        public AutenticationRequestBody(){
        }
        
        
        public AutenticationRequestBody(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
        
    }

