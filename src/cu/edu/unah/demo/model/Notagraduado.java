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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "notagraduado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notagraduado.findAll", query = "SELECT n FROM Notagraduado n")
    , @NamedQuery(name = "Notagraduado.findById", query = "SELECT n FROM Notagraduado n WHERE n.id = :id")
    , @NamedQuery(name = "Notagraduado.findByNombreasignatura", query = "SELECT n FROM Notagraduado n WHERE n.nombreasignatura = :nombreasignatura")
    , @NamedQuery(name = "Notagraduado.findByAsnota", query = "SELECT n FROM Notagraduado n WHERE n.asnota = :asnota")
    , @NamedQuery(name = "Notagraduado.findByTcp1", query = "SELECT n FROM Notagraduado n WHERE n.tcp1 = :tcp1")
    , @NamedQuery(name = "Notagraduado.findByTcp2", query = "SELECT n FROM Notagraduado n WHERE n.tcp2 = :tcp2")
    , @NamedQuery(name = "Notagraduado.findByPruebafinal", query = "SELECT n FROM Notagraduado n WHERE n.pruebafinal = :pruebafinal")
    , @NamedQuery(name = "Notagraduado.findByNotafinal", query = "SELECT n FROM Notagraduado n WHERE n.notafinal = :notafinal")})
public class Notagraduado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombreasignatura")
    private String nombreasignatura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "asnota")
    private Double asnota;
    @Column(name = "tcp1")
    private Double tcp1;
    @Column(name = "tcp2")
    private Double tcp2;
    @Column(name = "pruebafinal")
    private Double pruebafinal;
    @Column(name = "notafinal")
    private Double notafinal;
    @JoinColumn(name = "idgraduado", referencedColumnName = "id")
    @ManyToOne
    private Graduado idgraduado;

    public Notagraduado() {
    }

    public Notagraduado(Integer id) {
        this.id = id;
    }

    public Notagraduado(Integer id, String nombreasignatura) {
        this.id = id;
        this.nombreasignatura = nombreasignatura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreasignatura() {
        return nombreasignatura;
    }

    public void setNombreasignatura(String nombreasignatura) {
        this.nombreasignatura = nombreasignatura;
    }

    public Double getAsnota() {
        return asnota;
    }

    public void setAsnota(Double asnota) {
        this.asnota = asnota;
    }

    public Double getTcp1() {
        return tcp1;
    }

    public void setTcp1(Double tcp1) {
        this.tcp1 = tcp1;
    }

    public Double getTcp2() {
        return tcp2;
    }

    public void setTcp2(Double tcp2) {
        this.tcp2 = tcp2;
    }

    public Double getPruebafinal() {
        return pruebafinal;
    }

    public void setPruebafinal(Double pruebafinal) {
        this.pruebafinal = pruebafinal;
    }

    public Double getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(Double notafinal) {
        this.notafinal = notafinal;
    }

    public Graduado getIdgraduado() {
        return idgraduado;
    }

    public void setIdgraduado(Graduado idgraduado) {
        this.idgraduado = idgraduado;
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
        if (!(object instanceof Notagraduado)) {
            return false;
        }
        Notagraduado other = (Notagraduado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Notagraduado[ id=" + id + " ]";
    }
    
}
