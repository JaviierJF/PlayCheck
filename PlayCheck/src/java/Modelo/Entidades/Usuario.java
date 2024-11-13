/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUserId", query = "SELECT u FROM Usuario u WHERE u.userId = :userId"),
    @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByFechaNacimiento", query = "SELECT u FROM Usuario u WHERE u.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Usuario.findByFechaRegistro", query = "SELECT u FROM Usuario u WHERE u.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Usuario.findByAvatarUrl", query = "SELECT u FROM Usuario u WHERE u.avatarUrl = :avatarUrl"),
    @NamedQuery(name = "Usuario.findByRol", query = "SELECT u FROM Usuario u WHERE u.rol = :rol")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "rol")
    private String rol;
    @OneToMany(mappedBy = "userId")
    private Collection<Resena> resenaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user1Id")
    private Collection<Amigos> amigosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user2Id")
    private Collection<Amigos> amigosCollection1;
    @OneToMany(mappedBy = "userId")
    private Collection<Valoracion> valoracionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Valoracionweb> valoracionwebCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Comentarios> comentariosCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<ListaJuegos> listaJuegosCollection;

    public Usuario() {
    }

    public Usuario(Integer userId) {
        this.userId = userId;
    }

    public Usuario(Integer userId, String username, String email, String password, Date fechaNacimiento, Date fechaRegistro) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @XmlTransient
    public Collection<Resena> getResenaCollection() {
        return resenaCollection;
    }

    public void setResenaCollection(Collection<Resena> resenaCollection) {
        this.resenaCollection = resenaCollection;
    }

    @XmlTransient
    public Collection<Amigos> getAmigosCollection() {
        return amigosCollection;
    }

    public void setAmigosCollection(Collection<Amigos> amigosCollection) {
        this.amigosCollection = amigosCollection;
    }

    @XmlTransient
    public Collection<Amigos> getAmigosCollection1() {
        return amigosCollection1;
    }

    public void setAmigosCollection1(Collection<Amigos> amigosCollection1) {
        this.amigosCollection1 = amigosCollection1;
    }

    @XmlTransient
    public Collection<Valoracion> getValoracionCollection() {
        return valoracionCollection;
    }

    public void setValoracionCollection(Collection<Valoracion> valoracionCollection) {
        this.valoracionCollection = valoracionCollection;
    }

    @XmlTransient
    public Collection<Valoracionweb> getValoracionwebCollection() {
        return valoracionwebCollection;
    }

    public void setValoracionwebCollection(Collection<Valoracionweb> valoracionwebCollection) {
        this.valoracionwebCollection = valoracionwebCollection;
    }

    @XmlTransient
    public Collection<Comentarios> getComentariosCollection() {
        return comentariosCollection;
    }

    public void setComentariosCollection(Collection<Comentarios> comentariosCollection) {
        this.comentariosCollection = comentariosCollection;
    }

    @XmlTransient
    public Collection<ListaJuegos> getListaJuegosCollection() {
        return listaJuegosCollection;
    }

    public void setListaJuegosCollection(Collection<ListaJuegos> listaJuegosCollection) {
        this.listaJuegosCollection = listaJuegosCollection;
    }
    
    // Función de hash MD5
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Entidades.Usuario[ userId=" + userId + " ]";
    }
    
}
