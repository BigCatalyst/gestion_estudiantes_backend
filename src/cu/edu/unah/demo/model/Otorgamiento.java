/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "otorgamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Otorgamiento.findAll", query = "SELECT o FROM Otorgamiento o")
    , @NamedQuery(name = "Otorgamiento.findById", query = "SELECT o FROM Otorgamiento o WHERE o.id = :id")
    , @NamedQuery(name = "Otorgamiento.findByCi", query = "SELECT o FROM Otorgamiento o WHERE o.ci = :ci")
    , @NamedQuery(name = "Otorgamiento.findByYeargraduacion", query = "SELECT o FROM Otorgamiento o WHERE o.yeargraduacion = :yeargraduacion")
    , @NamedQuery(name = "Otorgamiento.findByCarrera", query = "SELECT o FROM Otorgamiento o WHERE o.carrera = :carrera")
    , @NamedQuery(name = "Otorgamiento.findByNotaescalafon", query = "SELECT o FROM Otorgamiento o WHERE o.notaescalafon = :notaescalafon")
    , @NamedQuery(name = "Otorgamiento.findByNoescalafon", query = "SELECT o FROM Otorgamiento o WHERE o.noescalafon = :noescalafon")})
public class Otorgamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ci")
    private String ci;
    @Column(name = "yeargraduacion")
    private Integer yeargraduacion;
    @Column(name = "carrera")
    private String carrera;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "notaescalafon")
    private Double notaescalafon;
    @Column(name = "noescalafon")
    private Integer noescalafon;

    public Otorgamiento() {
    }

    public Otorgamiento(Integer id) {
        this.id = id;
    }

    public Otorgamiento(Integer id, String ci) {
        this.id = id;
        this.ci = ci;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Integer getYeargraduacion() {
        return yeargraduacion;
    }

    public void setYeargraduacion(Integer yeargraduacion) {
        this.yeargraduacion = yeargraduacion;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otorgamiento)) {
            return false;
        }
        Otorgamiento other = (Otorgamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Otorgamiento[ id=" + id + " ]";
    }
    
}
