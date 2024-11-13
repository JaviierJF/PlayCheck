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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "videojuego")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videojuego.findAll", query = "SELECT v FROM Videojuego v"),
    @NamedQuery(name = "Videojuego.findByJuegoId", query = "SELECT v FROM Videojuego v WHERE v.juegoId = :juegoId"),
    @NamedQuery(name = "Videojuego.findByNombre", query = "SELECT v FROM Videojuego v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "Videojuego.findByDesarrollador", query = "SELECT v FROM Videojuego v WHERE v.desarrollador = :desarrollador"),
    @NamedQuery(name = "Videojuego.findByGenero", query = "SELECT v FROM Videojuego v WHERE v.genero = :genero"),
    @NamedQuery(name = "Videojuego.findByImagenJuego", query = "SELECT v FROM Videojuego v WHERE v.imagenJuego = :imagenJuego"),
    @NamedQuery(name = "Videojuego.findByFechaLanzamiento", query = "SELECT v FROM Videojuego v WHERE v.fechaLanzamiento = :fechaLanzamiento"),
    @NamedQuery(name = "Videojuego.findByImagenUrl", query = "SELECT v FROM Videojuego v WHERE v.imagenUrl = :imagenUrl")})
public class Videojuego implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "juego_id")
    private Integer juegoId;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "desarrollador")
    private String desarrollador;
    @Column(name = "genero")
    private String genero;
    @Column(name = "imagen_juego")
    private String imagenJuego;
    @Column(name = "fecha_lanzamiento")
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen_url")
    private String imagenUrl;
    @JoinTable(name = "videojuego_genero", joinColumns = {
        @JoinColumn(name = "juego_id", referencedColumnName = "juego_id")}, inverseJoinColumns = {
        @JoinColumn(name = "genero_id", referencedColumnName = "genero_id")})
    @ManyToMany
    private Collection<Genero> generoCollection;
    @OneToMany(mappedBy = "juegoId")
    private Collection<Resena> resenaCollection;
    @OneToMany(mappedBy = "juegoId")
    private Collection<Valoracion> valoracionCollection;
    @OneToMany(mappedBy = "juegoId")
    private Collection<ListaJuegos> listaJuegosCollection;

    public Videojuego() {
    }

    public Videojuego(Integer juegoId) {
        this.juegoId = juegoId;
    }

    public Videojuego(Integer juegoId, String nombre) {
        this.juegoId = juegoId;
        this.nombre = nombre;
    }

    public Integer getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(Integer juegoId) {
        this.juegoId = juegoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getImagenJuego() {
        return imagenJuego;
    }

    public void setImagenJuego(String imagenJuego) {
        this.imagenJuego = imagenJuego;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @XmlTransient
    public Collection<Genero> getGeneroCollection() {
        return generoCollection;
    }

    public void setGeneroCollection(Collection<Genero> generoCollection) {
        this.generoCollection = generoCollection;
    }

    @XmlTransient
    public Collection<Resena> getResenaCollection() {
        return resenaCollection;
    }

    public void setResenaCollection(Collection<Resena> resenaCollection) {
        this.resenaCollection = resenaCollection;
    }

    @XmlTransient
    public Collection<Valoracion> getValoracionCollection() {
        return valoracionCollection;
    }

    public void setValoracionCollection(Collection<Valoracion> valoracionCollection) {
        this.valoracionCollection = valoracionCollection;
    }

    @XmlTransient
    public Collection<ListaJuegos> getListaJuegosCollection() {
        return listaJuegosCollection;
    }

    public void setListaJuegosCollection(Collection<ListaJuegos> listaJuegosCollection) {
        this.listaJuegosCollection = listaJuegosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (juegoId != null ? juegoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videojuego)) {
            return false;
        }
        Videojuego other = (Videojuego) object;
        if ((this.juegoId == null && other.juegoId != null) || (this.juegoId != null && !this.juegoId.equals(other.juegoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.Videojuego[ juegoId=" + juegoId + " ]";
    }
    
}
