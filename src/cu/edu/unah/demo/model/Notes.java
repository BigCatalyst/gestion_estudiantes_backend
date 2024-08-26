/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cu.edu.unah.demo.serializadores.NotesSerializer;
/**
 *
 * @author Rene
 */
@Entity
@Table(name = "notes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notes.findAll", query = "SELECT n FROM Notes n")
    , @NamedQuery(name = "Notes.findByStudentCi", query = "SELECT n FROM Notes n WHERE n.notesPK.studentCi = :studentCi")
    , @NamedQuery(name = "Notes.findBySubjectId", query = "SELECT n FROM Notes n WHERE n.notesPK.subjectId = :subjectId")
    , @NamedQuery(name = "Notes.findByAcs", query = "SELECT n FROM Notes n WHERE n.acs = :acs")
    , @NamedQuery(name = "Notes.findByFinalNote", query = "SELECT n FROM Notes n WHERE n.finalNote = :finalNote")
    , @NamedQuery(name = "Notes.findByFinalExam", query = "SELECT n FROM Notes n WHERE n.finalExam = :finalExam")
    , @NamedQuery(name = "Notes.findByTcp1", query = "SELECT n FROM Notes n WHERE n.tcp1 = :tcp1")
    , @NamedQuery(name = "Notes.findByTcp2", query = "SELECT n FROM Notes n WHERE n.tcp2 = :tcp2")})
@JsonSerialize(using = NotesSerializer.class)
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotesPK notesPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "acs")
    private Float acs;
    @Column(name = "final_note")
    private Float finalNote;
    @Column(name = "final_exam")
    private Float finalExam;
    @Column(name = "tcp1")
    private Float tcp1;
    @Column(name = "tcp2")
    private Float tcp2;
    @JoinColumn(name = "student_ci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Students students;
    @JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Subjects subjects;

    public Notes() {
    }

    public Notes(NotesPK notesPK) {
        this.notesPK = notesPK;
    }

    public Notes(String studentCi, int subjectId) {
        this.notesPK = new NotesPK(studentCi, subjectId);
    }

    public NotesPK getNotesPK() {
        return notesPK;
    }

    public void setNotesPK(NotesPK notesPK) {
        this.notesPK = notesPK;
    }

    public Float getAcs() {
        return acs;
    }

    public void setAcs(Float acs) {
        this.acs = acs;
    }

    public Float getFinalNote() {
        return finalNote;
    }

    public void setFinalNote(Float finalNote) {
        this.finalNote = finalNote;
    }

    public Float getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(Float finalExam) {
        this.finalExam = finalExam;
    }

    public Float getTcp1() {
        return tcp1;
    }

    public void setTcp1(Float tcp1) {
        this.tcp1 = tcp1;
    }

    public Float getTcp2() {
        return tcp2;
    }

    public void setTcp2(Float tcp2) {
        this.tcp2 = tcp2;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notesPK != null ? notesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        if ((this.notesPK == null && other.notesPK != null) || (this.notesPK != null && !this.notesPK.equals(other.notesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Notes[ notesPK=" + notesPK + " ]";
    }
    
}
