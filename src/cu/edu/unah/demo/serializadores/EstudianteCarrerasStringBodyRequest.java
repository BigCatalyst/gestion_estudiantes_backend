/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Rene
 */
public class EstudianteCarrerasStringBodyRequest implements Serializable{
    
    private String ci;

    private List<String> carreras;

    public EstudianteCarrerasStringBodyRequest(String ci, List<String> carreras) {
        this.ci = ci;
        this.carreras = carreras;
    }

    public EstudianteCarrerasStringBodyRequest() {
    }

    
    
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public List<String> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<String> carreras) {
        this.carreras = carreras;
    }
    
}
