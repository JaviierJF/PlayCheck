/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier
 */
@Entity
@Table(name = "amigos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amigos.findAll", query = "SELECT a FROM Amigos a"),
    @NamedQuery(name = "Amigos.findByAmigoId", query = "SELECT a FROM Amigos a WHERE a.amigoId = :amigoId"),
    @NamedQuery(name = "Amigos.findByFechaAmistad", query = "SELECT a FROM Amigos a WHERE a.fechaAmistad = :fechaAmistad")})
public class Amigos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "amigo_id")
    private Integer amigoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_amistad")
    @Temporal(TemporalType.DATE)
    private Date fechaAmistad;
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Usuario user1Id;
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Usuario user2Id;

    public Amigos() {
    }

    public Amigos(Integer amigoId) {
        this.amigoId = amigoId;
    }

    public Amigos(Integer amigoId, Date fechaAmistad) {
        this.amigoId = amigoId;
        this.fechaAmistad = fechaAmistad;
    }

    public Integer getAmigoId() {
        return amigoId;
    }

    public void setAmigoId(Integer amigoId) {
        this.amigoId = amigoId;
    }

    public Date getFechaAmistad() {
        return fechaAmistad;
    }

    public void setFechaAmistad(Date fechaAmistad) {
        this.fechaAmistad = fechaAmistad;
    }

    public Usuario getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Usuario user1Id) {
        this.user1Id = user1Id;
    }

    public Usuario getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Usuario user2Id) {
        this.user2Id = user2Id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amigoId != null ? amigoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amigos)) {
            return false;
        }
        Amigos other = (Amigos) object;
        if ((this.amigoId == null && other.amigoId != null) || (this.amigoId != null && !this.amigoId.equals(other.amigoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.Amigos[ amigoId=" + amigoId + " ]";
    }
    
}
