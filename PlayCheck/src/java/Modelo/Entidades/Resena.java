/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Javier
 */
@Entity
@Table(name = "resena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resena.findAll", query = "SELECT r FROM Resena r"),
    @NamedQuery(name = "Resena.findByResenaId", query = "SELECT r FROM Resena r WHERE r.resenaId = :resenaId"),
    @NamedQuery(name = "Resena.findByFecha", query = "SELECT r FROM Resena r WHERE r.fecha = :fecha")})
public class Resena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "resena_id")
    private Integer resenaId;
    @Basic(optional = false)
    @Lob
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "juego_id", referencedColumnName = "juego_id")
    @ManyToOne
    private Videojuego juegoId;
    @OneToMany(mappedBy = "resenaId")
    private Collection<Comentarios> comentariosCollection;

    public Resena() {
    }

    public Resena(Integer resenaId) {
        this.resenaId = resenaId;
    }

    public Resena(Integer resenaId, String contenido, Date fecha) {
        this.resenaId = resenaId;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public Integer getResenaId() {
        return resenaId;
    }

    public void setResenaId(Integer resenaId) {
        this.resenaId = resenaId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    public Videojuego getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(Videojuego juegoId) {
        this.juegoId = juegoId;
    }

    @XmlTransient
    public Collection<Comentarios> getComentariosCollection() {
        return comentariosCollection;
    }

    public void setComentariosCollection(Collection<Comentarios> comentariosCollection) {
        this.comentariosCollection = comentariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resenaId != null ? resenaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resena)) {
            return false;
        }
        Resena other = (Resena) object;
        if ((this.resenaId == null && other.resenaId != null) || (this.resenaId != null && !this.resenaId.equals(other.resenaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.Resena[ resenaId=" + resenaId + " ]";
    }
    
}
