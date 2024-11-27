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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Videojuego.findByFechaLanzamiento", query = "SELECT v FROM Videojuego v WHERE v.fechaLanzamiento = :fechaLanzamiento"),
    @NamedQuery(name = "Videojuego.findByImagenUrl", query = "SELECT v FROM Videojuego v WHERE v.imagenUrl = :imagenUrl"),
    @NamedQuery(name = "Videojuego.findByMetacritic", query = "SELECT v FROM Videojuego v WHERE v.metacritic = :metacritic"),
    @NamedQuery(name = "Videojuego.findByEnlaceCompra", query = "SELECT v FROM Videojuego v WHERE v.enlaceCompra = :enlaceCompra"),
    @NamedQuery(name = "Videojuego.findByEtiquetas", query = "SELECT v FROM Videojuego v WHERE v.etiquetas = :etiquetas")})
public class Videojuego implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "juego_id")
    private Integer juegoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "desarrollador")
    private String desarrollador;
    @Size(max = 50)
    @Column(name = "genero")
    private String genero;
    @Column(name = "fecha_lanzamiento")
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "imagenUrl")
    private String imagenUrl;
    @Size(max = 255)
    @Column(name = "metacritic")
    private String metacritic;
    @Lob
    @Size(max = 65535)
    @Column(name = "enlaceCompra")
    private String enlaceCompra;
    @Lob
    @Size(max = 65535)
    @Column(name = "etiquetas")
    private String etiquetas;
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

    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic) {
        this.metacritic = metacritic;
    }

    public String getEnlaceCompra() {
        return enlaceCompra;
    }

    public void setEnlaceCompra(String enlaceCompra) {
        this.enlaceCompra = enlaceCompra;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
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
