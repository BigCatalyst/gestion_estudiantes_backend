/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.exceptions;

import javax.persistence.PersistenceException;


public class BadRequestException extends PersistenceException{

    public BadRequestException(String message) {
        super(message);
    }
    
}
