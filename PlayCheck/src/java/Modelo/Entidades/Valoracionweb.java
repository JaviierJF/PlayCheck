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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier
 */
@Entity
@Table(name = "valoracionweb")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracionweb.findAll", query = "SELECT v FROM Valoracionweb v"),
    @NamedQuery(name = "Valoracionweb.findByValoracionWebid", query = "SELECT v FROM Valoracionweb v WHERE v.valoracionWebid = :valoracionWebid"),
    @NamedQuery(name = "Valoracionweb.findByPuntuacion", query = "SELECT v FROM Valoracionweb v WHERE v.puntuacion = :puntuacion"),
    @NamedQuery(name = "Valoracionweb.findByFechaValoracion", query = "SELECT v FROM Valoracionweb v WHERE v.fechaValoracion = :fechaValoracion")})
public class Valoracionweb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "valoracionWeb_id")
    private Integer valoracionWebid;
    @Column(name = "puntuacion")
    private Integer puntuacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_valoracion")
    @Temporal(TemporalType.DATE)
    private Date fechaValoracion;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Usuario userId;

    public Valoracionweb() {
    }

    public Valoracionweb(Integer valoracionWebid) {
        this.valoracionWebid = valoracionWebid;
    }

    public Valoracionweb(Integer valoracionWebid, Date fechaValoracion) {
        this.valoracionWebid = valoracionWebid;
        this.fechaValoracion = fechaValoracion;
    }

    public Integer getValoracionWebid() {
        return valoracionWebid;
    }

    public void setValoracionWebid(Integer valoracionWebid) {
        this.valoracionWebid = valoracionWebid;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaValoracion() {
        return fechaValoracion;
    }

    public void setFechaValoracion(Date fechaValoracion) {
        this.fechaValoracion = fechaValoracion;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valoracionWebid != null ? valoracionWebid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracionweb)) {
            return false;
        }
        Valoracionweb other = (Valoracionweb) object;
        if ((this.valoracionWebid == null && other.valoracionWebid != null) || (this.valoracionWebid != null && !this.valoracionWebid.equals(other.valoracionWebid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.Valoracionweb[ valoracionWebid=" + valoracionWebid + " ]";
    }
    
}
