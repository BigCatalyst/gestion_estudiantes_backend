/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import java.io.Serializable;
import javax.persistence.Basic;

public class SubirDeGradoRequestBody implements Serializable {
    @Basic(optional = false)
    private String ci;
    @Basic(optional = false)
    private String carrera;
    @Basic(optional = false)
    private String Nodematricula;
    
    private Double notaescalafon;
    
    private Integer noescalafon;

    public SubirDeGradoRequestBody(String ci, String carrera, String Nodematricula, Double notaescalafon, Integer noescalafon) {
        this.ci = ci;
        this.carrera = carrera;
        this.Nodematricula = Nodematricula;
        this.notaescalafon = notaescalafon;
        this.noescalafon = noescalafon;
    }

    

    public SubirDeGradoRequestBody() {
    }

    public Double getNotaescalafon() {
        return notaescalafon;
    }

    public void setNotaescalafon(Double notaescalafon) {
        this.notaescalafon = notaescalafon;
    }

    public Integer getNoescalafon() {
        return noescalafon;
    }

    public void setNoescalafon(Integer noescalafon) {
        this.noescalafon = noescalafon;
    }
    
    
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getNodematricula() {
        return Nodematricula;
    }

    public void setNodematricula(String Nodematricula) {
        this.Nodematricula = Nodematricula;
    }
    
}
