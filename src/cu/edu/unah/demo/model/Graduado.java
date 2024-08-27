/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "graduado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Graduado.findAll", query = "SELECT g FROM Graduado g")
    , @NamedQuery(name = "Graduado.findById", query = "SELECT g FROM Graduado g WHERE g.id = :id")
    , @NamedQuery(name = "Graduado.findByCi", query = "SELECT g FROM Graduado g WHERE g.ci = :ci")
    , @NamedQuery(name = "Graduado.findByNombre", query = "SELECT g FROM Graduado g WHERE g.nombre = :nombre")
    , @NamedQuery(name = "Graduado.findByApellidos", query = "SELECT g FROM Graduado g WHERE g.apellidos = :apellidos")
    , @NamedQuery(name = "Graduado.findBySexo", query = "SELECT g FROM Graduado g WHERE g.sexo = :sexo")
    , @NamedQuery(name = "Graduado.findByDireccion", query = "SELECT g FROM Graduado g WHERE g.direccion = :direccion")
    , @NamedQuery(name = "Graduado.findByNodematricula", query = "SELECT g FROM Graduado g WHERE g.nodematricula = :nodematricula")
    , @NamedQuery(name = "Graduado.findByFechagraduacion", query = "SELECT g FROM Graduado g WHERE g.fechagraduacion = :fechagraduacion")
    , @NamedQuery(name = "Graduado.findByCarrera", query = "SELECT g FROM Graduado g WHERE g.carrera = :carrera")
    , @NamedQuery(name = "Graduado.findByNotaescalafon", query = "SELECT g FROM Graduado g WHERE g.notaescalafon = :notaescalafon")
    , @NamedQuery(name = "Graduado.findByNoescalafon", query = "SELECT g FROM Graduado g WHERE g.noescalafon = :noescalafon")})
public class Graduado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ci")
    private String ci;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "nodematricula")
    private String nodematricula;
    @Column(name = "fechagraduacion")
    @Temporal(TemporalType.DATE)
    private Date fechagraduacion;
    @Column(name = "carrera")
    private String carrera;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "notaescalafon")
    private Double notaescalafon;
    @Column(name = "noescalafon")
    private Integer noescalafon;
//    @OneToMany(mappedBy = "idgraduado")
//    private Collection<Notagraduado> notagraduadoCollection;

    public Graduado() {
    }

    public Graduado(Integer id) {
        this.id = id;
    }

    public Graduado(Integer id, String ci, String nombre, String apellidos) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellidos = apellidos;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNodematricula() {
        return nodematricula;
    }

    public void setNodematricula(String nodematricula) {
        this.nodematricula = nodematricula;
    }

    public Date getFechagraduacion() {
        return fechagraduacion;
    }

    public void setFechagraduacion(Date fechagraduacion) {
        this.fechagraduacion = fechagraduacion;
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

//    @XmlTransient
//    public Collection<Notagraduado> getNotagraduadoCollection() {
//        return notagraduadoCollection;
//    }
//
//    public void setNotagraduadoCollection(Collection<Notagraduado> notagraduadoCollection) {
//        this.notagraduadoCollection = notagraduadoCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Graduado)) {
            return false;
        }
        Graduado other = (Graduado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Graduado[ id=" + id + " ]";
    }
    
}
