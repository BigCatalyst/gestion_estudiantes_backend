/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

/**
 *
 * @author Rene
 */
public class EleccionBoleta {
    private String carrera;
    private String ci;
    private int lugar;

    public EleccionBoleta() {
    }

    public EleccionBoleta(String carrera, String ci, int lugar) {
        this.carrera = carrera;
        this.ci = ci;
        this.lugar = lugar;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }
    
}
