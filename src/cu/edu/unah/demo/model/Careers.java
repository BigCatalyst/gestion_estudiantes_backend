/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "careers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Careers.findAll", query = "SELECT c FROM Careers c")
    , @NamedQuery(name = "Careers.findById", query = "SELECT c FROM Careers c WHERE c.id = :id")
    , @NamedQuery(name = "Careers.findByAmount", query = "SELECT c FROM Careers c WHERE c.amount = :amount")
    , @NamedQuery(name = "Careers.findByName", query = "SELECT c FROM Careers c WHERE c.name = :name")})
public class Careers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount")
    private int amount;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "careers")
//    private Collection<StudentCareer> studentCareerCollection;

    public Careers() {
    }

    public Careers(Integer id) {
        this.id = id;
    }

    public Careers(Integer id, int amount, String name) {
        this.id = id;
        this.amount = amount;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @XmlTransient
//    public Collection<StudentCareer> getStudentCareerCollection() {
//        return studentCareerCollection;
//    }
//
//    public void setStudentCareerCollection(Collection<StudentCareer> studentCareerCollection) {
//        this.studentCareerCollection = studentCareerCollection;
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
        if (!(object instanceof Careers)) {
            return false;
        }
        Careers other = (Careers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Careers[ id=" + id + " ]";
    }
    
}
