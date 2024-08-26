/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "altas_bajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AltasBajas.findAll", query = "SELECT a FROM AltasBajas a")
    , @NamedQuery(name = "AltasBajas.findByBaja", query = "SELECT a FROM AltasBajas a WHERE a.baja = :baja")
    , @NamedQuery(name = "AltasBajas.findByDate", query = "SELECT a FROM AltasBajas a WHERE a.date = :date")
    , @NamedQuery(name = "AltasBajas.findByMunicipality", query = "SELECT a FROM AltasBajas a WHERE a.municipality = :municipality")
    , @NamedQuery(name = "AltasBajas.findByProvince", query = "SELECT a FROM AltasBajas a WHERE a.province = :province")
    , @NamedQuery(name = "AltasBajas.findBySchool", query = "SELECT a FROM AltasBajas a WHERE a.school = :school")
    , @NamedQuery(name = "AltasBajas.findByCi", query = "SELECT a FROM AltasBajas a WHERE a.ci = :ci")})
public class AltasBajas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "baja")
    private Boolean baja;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "municipality")
    private String municipality;
    @Basic(optional = false)
    @Column(name = "province")
    private String province;
    @Basic(optional = false)
    @Column(name = "school")
    private String school;
    @Id
    @Basic(optional = false)
    @Column(name = "ci")
    private String ci;
//    @JoinColumn(name = "ci", referencedColumnName = "ci", insertable = false, updatable = false)
//    @OneToOne(optional = false)
//    private Students students;

    public AltasBajas() {
    }

    public AltasBajas(String ci) {
        this.ci = ci;
    }

    public AltasBajas(String ci, Date date, String municipality, String province, String school) {
        this.ci = ci;
        this.date = date;
        this.municipality = municipality;
        this.province = province;
        this.school = school;
    }

    public Boolean getBaja() {
        return baja;
    }

    public void setBaja(Boolean baja) {
        this.baja = baja;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

//    public Students getStudents() {
//        return students;
//    }
//
//    public void setStudents(Students students) {
//        this.students = students;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ci != null ? ci.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AltasBajas)) {
            return false;
        }
        AltasBajas other = (AltasBajas) object;
        if ((this.ci == null && other.ci != null) || (this.ci != null && !this.ci.equals(other.ci))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.AltasBajas[ ci=" + ci + " ]";
    }
    
}
