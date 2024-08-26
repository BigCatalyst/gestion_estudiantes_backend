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
public class NotesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "student_ci")
    private String studentCi;
    @Basic(optional = false)
    @Column(name = "subject_id")
    private int subjectId;

    public NotesPK() {
    }

    public NotesPK(String studentCi, int subjectId) {
        this.studentCi = studentCi;
        this.subjectId = subjectId;
    }

    public String getStudentCi() {
        return studentCi;
    }

    public void setStudentCi(String studentCi) {
        this.studentCi = studentCi;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentCi != null ? studentCi.hashCode() : 0);
        hash += (int) subjectId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotesPK)) {
            return false;
        }
        NotesPK other = (NotesPK) object;
        if ((this.studentCi == null && other.studentCi != null) || (this.studentCi != null && !this.studentCi.equals(other.studentCi))) {
            return false;
        }
        if (this.subjectId != other.subjectId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.NotesPK[ studentCi=" + studentCi + ", subjectId=" + subjectId + " ]";
    }
    
}
