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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "students")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s")
    , @NamedQuery(name = "Students.findByCi", query = "SELECT s FROM Students s WHERE s.ci = :ci")
    , @NamedQuery(name = "Students.findByAddress", query = "SELECT s FROM Students s WHERE s.address = :address")
    , @NamedQuery(name = "Students.findByGrade", query = "SELECT s FROM Students s WHERE s.grade = :grade")
    , @NamedQuery(name = "Students.findByLastName", query = "SELECT s FROM Students s WHERE s.lastName = :lastName")
    , @NamedQuery(name = "Students.findByName", query = "SELECT s FROM Students s WHERE s.name = :name")
    , @NamedQuery(name = "Students.findByRegNumber", query = "SELECT s FROM Students s WHERE s.regNumber = :regNumber")
    , @NamedQuery(name = "Students.findBySex", query = "SELECT s FROM Students s WHERE s.sex = :sex")})
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ci")
    private String ci;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "grade")
    private int grade;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "reg_number")
    private String regNumber;
    @Basic(optional = false)
    @Column(name = "sex")
    private String sex;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "students")
//    private Collection<Notes> notesCollection;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "students")
//    private AltasBajas altasBajas;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "students")
//    private Collection<StudentCareer> studentCareerCollection;

    public Students() {
    }

    public Students(String ci) {
        this.ci = ci;
    }

    public Students(String ci, String address, int grade, String lastName, String name, String regNumber, String sex) {
        this.ci = ci;
        this.address = address;
        this.grade = grade;
        this.lastName = lastName;
        this.name = name;
        this.regNumber = regNumber;
        this.sex = sex;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

//    @XmlTransient
//    public Collection<Notes> getNotesCollection() {
//        return notesCollection;
//    }
//
//    public void setNotesCollection(Collection<Notes> notesCollection) {
//        this.notesCollection = notesCollection;
//    }
//
//    public AltasBajas getAltasBajas() {
//        return altasBajas;
//    }
//
//    public void setAltasBajas(AltasBajas altasBajas) {
//        this.altasBajas = altasBajas;
//    }
//
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
        hash += (ci != null ? ci.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        if ((this.ci == null && other.ci != null) || (this.ci != null && !this.ci.equals(other.ci))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Students[ ci=" + ci + " ]";
    }
    
}
