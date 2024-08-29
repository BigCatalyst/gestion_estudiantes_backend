/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import cu.edu.unah.demo.model.Students;
import java.io.Serializable;

/**
 *
 * @author Rene
 */
public class UbicacionEscalafonResponse implements Serializable {
    private int lugar;
    private double promedio;
    private Students estudiante;

    public UbicacionEscalafonResponse() {
    }

    public UbicacionEscalafonResponse(int lugar, double promedio, Students estudiante) {
        this.lugar = lugar;
        this.promedio = promedio;
        this.estudiante = estudiante;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public Students getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Students estudiante) {
        this.estudiante = estudiante;
    }
    
}
