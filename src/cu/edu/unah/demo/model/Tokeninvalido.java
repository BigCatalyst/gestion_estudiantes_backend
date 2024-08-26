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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rene
 */
@Entity
@Table(name = "tokeninvalido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tokeninvalido.findAll", query = "SELECT t FROM Tokeninvalido t")
    , @NamedQuery(name = "Tokeninvalido.findById", query = "SELECT t FROM Tokeninvalido t WHERE t.id = :id")
    , @NamedQuery(name = "Tokeninvalido.findByToken", query = "SELECT t FROM Tokeninvalido t WHERE t.token = :token")})
public class Tokeninvalido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;

    public Tokeninvalido() {
    }

    public Tokeninvalido(Integer id) {
        this.id = id;
    }

    public Tokeninvalido(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        if (!(object instanceof Tokeninvalido)) {
            return false;
        }
        Tokeninvalido other = (Tokeninvalido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cu.edu.unah.demo.model.Tokeninvalido[ id=" + id + " ]";
    }
    
}
