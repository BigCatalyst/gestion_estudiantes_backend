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
@Table(name = "subjects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subjects.findAll", query = "SELECT s FROM Subjects s")
    , @NamedQuery(name = "Subjects.findById", query = "SELECT s FROM Subjects s WHERE s.id = :id")
    , @NamedQuery(name = "Subjects.findByGrade", query = "SELECT s FROM Subjects s WHERE s.grade = :grade")
    , @NamedQuery(name = "Subjects.findByName", query = "SELECT s FROM Subjects s WHERE s.name = :name")
    , @NamedQuery(name = "Subjects.findByTcp2", query = "SELECT s FROM Subjects s WHERE s.tcp2 = :tcp2")})
public class Subjects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "grade")
    private int grade;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "tcp2")
    private boolean tcp2;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjects")
//    private Collection<Notes> notesCollection;

    public Subjects() {
    }

    public Subjects(Integer id) {
        this.id = id;
    }

    public Subjects(Integer id, int grade, String name, boolean tcp2) {
        this.id = id;
        this.grade = grade;
        this.name = name;
        this.tcp2 = tcp2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getTcp2() {
        return tcp2;
    }

    public void setTcp2(boolean tcp2) {
        this.tcp2 = tcp2;
    }

//    @XmlTransient
//    public Collection<Notes> getNotesCollection() {
//        return notesCollection;
//    }
//
//    public void setNotesCollection(Collection<Notes> notesCollection) {
//        this.notesCollection = notesCollection;
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
        if (!(object instanceof Subjects)) {
            return false;
        }
        Subjects other = (Subjects) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Subjects[ id=" + id + " ]";
    }
    
}
