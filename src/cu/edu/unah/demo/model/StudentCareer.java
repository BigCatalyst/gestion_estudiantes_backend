/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "student_career")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentCareer.findAll", query = "SELECT s FROM StudentCareer s")
    , @NamedQuery(name = "StudentCareer.findByCareerId", query = "SELECT s FROM StudentCareer s WHERE s.studentCareerPK.careerId = :careerId")
    , @NamedQuery(name = "StudentCareer.findByStudentCi", query = "SELECT s FROM StudentCareer s WHERE s.studentCareerPK.studentCi = :studentCi")
    , @NamedQuery(name = "StudentCareer.findByIndex", query = "SELECT s FROM StudentCareer s WHERE s.index = :index")})
public class StudentCareer implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentCareerPK studentCareerPK;
    @Basic(optional = false)
    @Column(name = "index")
    private int index;
//    @JoinColumn(name = "career_id", referencedColumnName = "id", insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private Careers careers;
//    @JoinColumn(name = "student_ci", referencedColumnName = "ci", insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private Students students;

    public StudentCareer() {
    }

    public StudentCareer(StudentCareerPK studentCareerPK) {
        this.studentCareerPK = studentCareerPK;
    }

    public StudentCareer(StudentCareerPK studentCareerPK, int index) {
        this.studentCareerPK = studentCareerPK;
        this.index = index;
    }

    public StudentCareer(int careerId, String studentCi) {
        this.studentCareerPK = new StudentCareerPK(careerId, studentCi);
    }

    public StudentCareerPK getStudentCareerPK() {
        return studentCareerPK;
    }

    public void setStudentCareerPK(StudentCareerPK studentCareerPK) {
        this.studentCareerPK = studentCareerPK;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

//    public Careers getCareers() {
//        return careers;
//    }
//
//    public void setCareers(Careers careers) {
//        this.careers = careers;
//    }
//
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
        hash += (studentCareerPK != null ? studentCareerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentCareer)) {
            return false;
        }
        StudentCareer other = (StudentCareer) object;
        if ((this.studentCareerPK == null && other.studentCareerPK != null) || (this.studentCareerPK != null && !this.studentCareerPK.equals(other.studentCareerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.StudentCareer[ studentCareerPK=" + studentCareerPK + " ]";
    }
    
}
