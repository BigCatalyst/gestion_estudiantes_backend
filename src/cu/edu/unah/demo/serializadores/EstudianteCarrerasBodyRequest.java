/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;


import java.io.Serializable;
import java.util.List;

public class EstudianteCarrerasBodyRequest implements Serializable{
    
    private String ci;

    private List<Integer> carreras;

    public EstudianteCarrerasBodyRequest(String ci, List<Integer> carreras) {
        this.ci = ci;
        this.carreras = carreras;
    }

    public EstudianteCarrerasBodyRequest() {
    }
    
    // Getters y Setters

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public List<Integer> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Integer> carreras) {
        this.carreras = carreras;
    }
}