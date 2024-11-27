/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier
 */
@Entity
@Table(name = "lista_juegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaJuegos.findAll", query = "SELECT l FROM ListaJuegos l"),
    @NamedQuery(name = "ListaJuegos.findByListaId", query = "SELECT l FROM ListaJuegos l WHERE l.listaId = :listaId"),
    @NamedQuery(name = "ListaJuegos.findByEstado", query = "SELECT l FROM ListaJuegos l WHERE l.estado = :estado")})
public class ListaJuegos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lista_id")
    private Integer listaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Usuario userId;
    @JoinColumn(name = "juego_id", referencedColumnName = "juego_id")
    @ManyToOne
    private Videojuego juegoId;

    public ListaJuegos() {
    }

    public ListaJuegos(Integer listaId) {
        this.listaId = listaId;
    }

    public ListaJuegos(Integer listaId, String estado) {
        this.listaId = listaId;
        this.estado = estado;
    }

    public Integer getListaId() {
        return listaId;
    }

    public void setListaId(Integer listaId) {
        this.listaId = listaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (listaId != null ? listaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaJuegos)) {
            return false;
        }
        ListaJuegos other = (ListaJuegos) object;
        if ((this.listaId == null && other.listaId != null) || (this.listaId != null && !this.listaId.equals(other.listaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.ListaJuegos[ listaId=" + listaId + " ]";
    }
    
}
