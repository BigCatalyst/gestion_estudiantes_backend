/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Rene
 */
@Embeddable
public class StudentCareerPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "career_id")
    private int careerId;
    @Basic(optional = false)
    @Column(name = "student_ci")
    private String studentCi;

    public StudentCareerPK() {
    }

    public StudentCareerPK(int careerId, String studentCi) {
        this.careerId = careerId;
        this.studentCi = studentCi;
    }

    public int getCareerId() {
        return careerId;
    }

    public void setCareerId(int careerId) {
        this.careerId = careerId;
    }

    public String getStudentCi() {
        return studentCi;
    }

    public void setStudentCi(String studentCi) {
        this.studentCi = studentCi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) careerId;
        hash += (studentCi != null ? studentCi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentCareerPK)) {
            return false;
        }
        StudentCareerPK other = (StudentCareerPK) object;
        if (this.careerId != other.careerId) {
            return false;
        }
        if ((this.studentCi == null && other.studentCi != null) || (this.studentCi != null && !this.studentCi.equals(other.studentCi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.StudentCareerPK[ careerId=" + careerId + ", studentCi=" + studentCi + " ]";
    }
    
}
