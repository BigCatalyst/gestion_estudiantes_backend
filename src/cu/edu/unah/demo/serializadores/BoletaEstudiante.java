/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import java.util.List;

/**
 *
 * @author Rene
 */
public class BoletaEstudiante {
    String ci;
    public List<String> nombre_carreras;

    public BoletaEstudiante(String ci, List<String> nombre_carreras) {
        this.ci = ci;
        this.nombre_carreras = nombre_carreras;
    }

    public BoletaEstudiante() {
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public List<String> getNombre_carreras() {
        return nombre_carreras;
    }

    public void setNombre_carreras(List<String> nombre_carreras) {
        this.nombre_carreras = nombre_carreras;
    }
    
}
