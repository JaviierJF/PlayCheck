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
@Table(name = "valoracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracion.findAll", query = "SELECT v FROM Valoracion v"),
    @NamedQuery(name = "Valoracion.findByValoracionId", query = "SELECT v FROM Valoracion v WHERE v.valoracionId = :valoracionId"),
    @NamedQuery(name = "Valoracion.findByPuntuacion", query = "SELECT v FROM Valoracion v WHERE v.puntuacion = :puntuacion"),
    @NamedQuery(name = "Valoracion.findByFecha", query = "SELECT v FROM Valoracion v WHERE v.fecha = :fecha")})
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "valoracion_id")
    private Integer valoracionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntuacion")
    private short puntuacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "juego_id", referencedColumnName = "juego_id")
    @ManyToOne
    private Videojuego juegoId;

    public Valoracion() {
    }

    public Valoracion(Integer valoracionId) {
        this.valoracionId = valoracionId;
    }

    public Valoracion(Integer valoracionId, short puntuacion, Date fecha) {
        this.valoracionId = valoracionId;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
    }

    public Integer getValoracionId() {
        return valoracionId;
    }

    public void setValoracionId(Integer valoracionId) {
        this.valoracionId = valoracionId;
    }

    public short getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(short puntuacion) {
        this.puntuacion = puntuacion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valoracionId != null ? valoracionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracion)) {
            return false;
        }
        Valoracion other = (Valoracion) object;
        if ((this.valoracionId == null && other.valoracionId != null) || (this.valoracionId != null && !this.valoracionId.equals(other.valoracionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.Valoracion[ valoracionId=" + valoracionId + " ]";
    }
    
}
