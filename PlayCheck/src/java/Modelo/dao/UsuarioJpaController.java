/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Entidades.Resena;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.Entidades.Amigos;
import Modelo.Entidades.Valoracion;
import Modelo.Entidades.Valoracionweb;
import Modelo.Entidades.Comentarios;
import Modelo.Entidades.ListaJuegos;
import Modelo.Entidades.Usuario;
import Modelo.dao.exceptions.IllegalOrphanException;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Javier
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getResenaCollection() == null) {
            usuario.setResenaCollection(new ArrayList<Resena>());
        }
        if (usuario.getAmigosCollection() == null) {
            usuario.setAmigosCollection(new ArrayList<Amigos>());
        }
        if (usuario.getAmigosCollection1() == null) {
            usuario.setAmigosCollection1(new ArrayList<Amigos>());
        }
        if (usuario.getValoracionCollection() == null) {
            usuario.setValoracionCollection(new ArrayList<Valoracion>());
        }
        if (usuario.getValoracionwebCollection() == null) {
            usuario.setValoracionwebCollection(new ArrayList<Valoracionweb>());
        }
        if (usuario.getComentariosCollection() == null) {
            usuario.setComentariosCollection(new ArrayList<Comentarios>());
        }
        if (usuario.getListaJuegosCollection() == null) {
            usuario.setListaJuegosCollection(new ArrayList<ListaJuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Resena> attachedResenaCollection = new ArrayList<Resena>();
            for (Resena resenaCollectionResenaToAttach : usuario.getResenaCollection()) {
                resenaCollectionResenaToAttach = em.getReference(resenaCollectionResenaToAttach.getClass(), resenaCollectionResenaToAttach.getResenaId());
                attachedResenaCollection.add(resenaCollectionResenaToAttach);
            }
            usuario.setResenaCollection(attachedResenaCollection);
            Collection<Amigos> attachedAmigosCollection = new ArrayList<Amigos>();
            for (Amigos amigosCollectionAmigosToAttach : usuario.getAmigosCollection()) {
                amigosCollectionAmigosToAttach = em.getReference(amigosCollectionAmigosToAttach.getClass(), amigosCollectionAmigosToAttach.getAmigoId());
                attachedAmigosCollection.add(amigosCollectionAmigosToAttach);
            }
            usuario.setAmigosCollection(attachedAmigosCollection);
            Collection<Amigos> attachedAmigosCollection1 = new ArrayList<Amigos>();
            for (Amigos amigosCollection1AmigosToAttach : usuario.getAmigosCollection1()) {
                amigosCollection1AmigosToAttach = em.getReference(amigosCollection1AmigosToAttach.getClass(), amigosCollection1AmigosToAttach.getAmigoId());
                attachedAmigosCollection1.add(amigosCollection1AmigosToAttach);
            }
            usuario.setAmigosCollection1(attachedAmigosCollection1);
            Collection<Valoracion> attachedValoracionCollection = new ArrayList<Valoracion>();
            for (Valoracion valoracionCollectionValoracionToAttach : usuario.getValoracionCollection()) {
                valoracionCollectionValoracionToAttach = em.getReference(valoracionCollectionValoracionToAttach.getClass(), valoracionCollectionValoracionToAttach.getValoracionId());
                attachedValoracionCollection.add(valoracionCollectionValoracionToAttach);
            }
            usuario.setValoracionCollection(attachedValoracionCollection);
            Collection<Valoracionweb> attachedValoracionwebCollection = new ArrayList<Valoracionweb>();
            for (Valoracionweb valoracionwebCollectionValoracionwebToAttach : usuario.getValoracionwebCollection()) {
                valoracionwebCollectionValoracionwebToAttach = em.getReference(valoracionwebCollectionValoracionwebToAttach.getClass(), valoracionwebCollectionValoracionwebToAttach.getValoracionWebid());
                attachedValoracionwebCollection.add(valoracionwebCollectionValoracionwebToAttach);
            }
            usuario.setValoracionwebCollection(attachedValoracionwebCollection);
            Collection<Comentarios> attachedComentariosCollection = new ArrayList<Comentarios>();
            for (Comentarios comentariosCollectionComentariosToAttach : usuario.getComentariosCollection()) {
                comentariosCollectionComentariosToAttach = em.getReference(comentariosCollectionComentariosToAttach.getClass(), comentariosCollectionComentariosToAttach.getComentarioId());
                attachedComentariosCollection.add(comentariosCollectionComentariosToAttach);
            }
            usuario.setComentariosCollection(attachedComentariosCollection);
            Collection<ListaJuegos> attachedListaJuegosCollection = new ArrayList<ListaJuegos>();
            for (ListaJuegos listaJuegosCollectionListaJuegosToAttach : usuario.getListaJuegosCollection()) {
                listaJuegosCollectionListaJuegosToAttach = em.getReference(listaJuegosCollectionListaJuegosToAttach.getClass(), listaJuegosCollectionListaJuegosToAttach.getListaId());
                attachedListaJuegosCollection.add(listaJuegosCollectionListaJuegosToAttach);
            }
            usuario.setListaJuegosCollection(attachedListaJuegosCollection);
            em.persist(usuario);
            for (Resena resenaCollectionResena : usuario.getResenaCollection()) {
                Usuario oldUserIdOfResenaCollectionResena = resenaCollectionResena.getUserId();
                resenaCollectionResena.setUserId(usuario);
                resenaCollectionResena = em.merge(resenaCollectionResena);
                if (oldUserIdOfResenaCollectionResena != null) {
                    oldUserIdOfResenaCollectionResena.getResenaCollection().remove(resenaCollectionResena);
                    oldUserIdOfResenaCollectionResena = em.merge(oldUserIdOfResenaCollectionResena);
                }
            }
            for (Amigos amigosCollectionAmigos : usuario.getAmigosCollection()) {
                Usuario oldUser1IdOfAmigosCollectionAmigos = amigosCollectionAmigos.getUser1Id();
                amigosCollectionAmigos.setUser1Id(usuario);
                amigosCollectionAmigos = em.merge(amigosCollectionAmigos);
                if (oldUser1IdOfAmigosCollectionAmigos != null) {
                    oldUser1IdOfAmigosCollectionAmigos.getAmigosCollection().remove(amigosCollectionAmigos);
                    oldUser1IdOfAmigosCollectionAmigos = em.merge(oldUser1IdOfAmigosCollectionAmigos);
                }
            }
            for (Amigos amigosCollection1Amigos : usuario.getAmigosCollection1()) {
                Usuario oldUser2IdOfAmigosCollection1Amigos = amigosCollection1Amigos.getUser2Id();
                amigosCollection1Amigos.setUser2Id(usuario);
                amigosCollection1Amigos = em.merge(amigosCollection1Amigos);
                if (oldUser2IdOfAmigosCollection1Amigos != null) {
                    oldUser2IdOfAmigosCollection1Amigos.getAmigosCollection1().remove(amigosCollection1Amigos);
                    oldUser2IdOfAmigosCollection1Amigos = em.merge(oldUser2IdOfAmigosCollection1Amigos);
                }
            }
            for (Valoracion valoracionCollectionValoracion : usuario.getValoracionCollection()) {
                Usuario oldUserIdOfValoracionCollectionValoracion = valoracionCollectionValoracion.getUserId();
                valoracionCollectionValoracion.setUserId(usuario);
                valoracionCollectionValoracion = em.merge(valoracionCollectionValoracion);
                if (oldUserIdOfValoracionCollectionValoracion != null) {
                    oldUserIdOfValoracionCollectionValoracion.getValoracionCollection().remove(valoracionCollectionValoracion);
                    oldUserIdOfValoracionCollectionValoracion = em.merge(oldUserIdOfValoracionCollectionValoracion);
                }
            }
            for (Valoracionweb valoracionwebCollectionValoracionweb : usuario.getValoracionwebCollection()) {
                Usuario oldUserIdOfValoracionwebCollectionValoracionweb = valoracionwebCollectionValoracionweb.getUserId();
                valoracionwebCollectionValoracionweb.setUserId(usuario);
                valoracionwebCollectionValoracionweb = em.merge(valoracionwebCollectionValoracionweb);
                if (oldUserIdOfValoracionwebCollectionValoracionweb != null) {
                    oldUserIdOfValoracionwebCollectionValoracionweb.getValoracionwebCollection().remove(valoracionwebCollectionValoracionweb);
                    oldUserIdOfValoracionwebCollectionValoracionweb = em.merge(oldUserIdOfValoracionwebCollectionValoracionweb);
                }
            }
            for (Comentarios comentariosCollectionComentarios : usuario.getComentariosCollection()) {
                Usuario oldUserIdOfComentariosCollectionComentarios = comentariosCollectionComentarios.getUserId();
                comentariosCollectionComentarios.setUserId(usuario);
                comentariosCollectionComentarios = em.merge(comentariosCollectionComentarios);
                if (oldUserIdOfComentariosCollectionComentarios != null) {
                    oldUserIdOfComentariosCollectionComentarios.getComentariosCollection().remove(comentariosCollectionComentarios);
                    oldUserIdOfComentariosCollectionComentarios = em.merge(oldUserIdOfComentariosCollectionComentarios);
                }
            }
            for (ListaJuegos listaJuegosCollectionListaJuegos : usuario.getListaJuegosCollection()) {
                Usuario oldUserIdOfListaJuegosCollectionListaJuegos = listaJuegosCollectionListaJuegos.getUserId();
                listaJuegosCollectionListaJuegos.setUserId(usuario);
                listaJuegosCollectionListaJuegos = em.merge(listaJuegosCollectionListaJuegos);
                if (oldUserIdOfListaJuegosCollectionListaJuegos != null) {
                    oldUserIdOfListaJuegosCollectionListaJuegos.getListaJuegosCollection().remove(listaJuegosCollectionListaJuegos);
                    oldUserIdOfListaJuegosCollectionListaJuegos = em.merge(oldUserIdOfListaJuegosCollectionListaJuegos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUserId());
            Collection<Resena> resenaCollectionOld = persistentUsuario.getResenaCollection();
            Collection<Resena> resenaCollectionNew = usuario.getResenaCollection();
            Collection<Amigos> amigosCollectionOld = persistentUsuario.getAmigosCollection();
            Collection<Amigos> amigosCollectionNew = usuario.getAmigosCollection();
            Collection<Amigos> amigosCollection1Old = persistentUsuario.getAmigosCollection1();
            Collection<Amigos> amigosCollection1New = usuario.getAmigosCollection1();
            Collection<Valoracion> valoracionCollectionOld = persistentUsuario.getValoracionCollection();
            Collection<Valoracion> valoracionCollectionNew = usuario.getValoracionCollection();
            Collection<Valoracionweb> valoracionwebCollectionOld = persistentUsuario.getValoracionwebCollection();
            Collection<Valoracionweb> valoracionwebCollectionNew = usuario.getValoracionwebCollection();
            Collection<Comentarios> comentariosCollectionOld = persistentUsuario.getComentariosCollection();
            Collection<Comentarios> comentariosCollectionNew = usuario.getComentariosCollection();
            Collection<ListaJuegos> listaJuegosCollectionOld = persistentUsuario.getListaJuegosCollection();
            Collection<ListaJuegos> listaJuegosCollectionNew = usuario.getListaJuegosCollection();
            List<String> illegalOrphanMessages = null;
            for (Amigos amigosCollectionOldAmigos : amigosCollectionOld) {
                if (!amigosCollectionNew.contains(amigosCollectionOldAmigos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Amigos " + amigosCollectionOldAmigos + " since its user1Id field is not nullable.");
                }
            }
            for (Amigos amigosCollection1OldAmigos : amigosCollection1Old) {
                if (!amigosCollection1New.contains(amigosCollection1OldAmigos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Amigos " + amigosCollection1OldAmigos + " since its user2Id field is not nullable.");
                }
            }
            for (Valoracionweb valoracionwebCollectionOldValoracionweb : valoracionwebCollectionOld) {
                if (!valoracionwebCollectionNew.contains(valoracionwebCollectionOldValoracionweb)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Valoracionweb " + valoracionwebCollectionOldValoracionweb + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Resena> attachedResenaCollectionNew = new ArrayList<Resena>();
            for (Resena resenaCollectionNewResenaToAttach : resenaCollectionNew) {
                resenaCollectionNewResenaToAttach = em.getReference(resenaCollectionNewResenaToAttach.getClass(), resenaCollectionNewResenaToAttach.getResenaId());
                attachedResenaCollectionNew.add(resenaCollectionNewResenaToAttach);
            }
            resenaCollectionNew = attachedResenaCollectionNew;
            usuario.setResenaCollection(resenaCollectionNew);
            Collection<Amigos> attachedAmigosCollectionNew = new ArrayList<Amigos>();
            for (Amigos amigosCollectionNewAmigosToAttach : amigosCollectionNew) {
                amigosCollectionNewAmigosToAttach = em.getReference(amigosCollectionNewAmigosToAttach.getClass(), amigosCollectionNewAmigosToAttach.getAmigoId());
                attachedAmigosCollectionNew.add(amigosCollectionNewAmigosToAttach);
            }
            amigosCollectionNew = attachedAmigosCollectionNew;
            usuario.setAmigosCollection(amigosCollectionNew);
            Collection<Amigos> attachedAmigosCollection1New = new ArrayList<Amigos>();
            for (Amigos amigosCollection1NewAmigosToAttach : amigosCollection1New) {
                amigosCollection1NewAmigosToAttach = em.getReference(amigosCollection1NewAmigosToAttach.getClass(), amigosCollection1NewAmigosToAttach.getAmigoId());
                attachedAmigosCollection1New.add(amigosCollection1NewAmigosToAttach);
            }
            amigosCollection1New = attachedAmigosCollection1New;
            usuario.setAmigosCollection1(amigosCollection1New);
            Collection<Valoracion> attachedValoracionCollectionNew = new ArrayList<Valoracion>();
            for (Valoracion valoracionCollectionNewValoracionToAttach : valoracionCollectionNew) {
                valoracionCollectionNewValoracionToAttach = em.getReference(valoracionCollectionNewValoracionToAttach.getClass(), valoracionCollectionNewValoracionToAttach.getValoracionId());
                attachedValoracionCollectionNew.add(valoracionCollectionNewValoracionToAttach);
            }
            valoracionCollectionNew = attachedValoracionCollectionNew;
            usuario.setValoracionCollection(valoracionCollectionNew);
            Collection<Valoracionweb> attachedValoracionwebCollectionNew = new ArrayList<Valoracionweb>();
            for (Valoracionweb valoracionwebCollectionNewValoracionwebToAttach : valoracionwebCollectionNew) {
                valoracionwebCollectionNewValoracionwebToAttach = em.getReference(valoracionwebCollectionNewValoracionwebToAttach.getClass(), valoracionwebCollectionNewValoracionwebToAttach.getValoracionWebid());
                attachedValoracionwebCollectionNew.add(valoracionwebCollectionNewValoracionwebToAttach);
            }
            valoracionwebCollectionNew = attachedValoracionwebCollectionNew;
            usuario.setValoracionwebCollection(valoracionwebCollectionNew);
            Collection<Comentarios> attachedComentariosCollectionNew = new ArrayList<Comentarios>();
            for (Comentarios comentariosCollectionNewComentariosToAttach : comentariosCollectionNew) {
                comentariosCollectionNewComentariosToAttach = em.getReference(comentariosCollectionNewComentariosToAttach.getClass(), comentariosCollectionNewComentariosToAttach.getComentarioId());
                attachedComentariosCollectionNew.add(comentariosCollectionNewComentariosToAttach);
            }
            comentariosCollectionNew = attachedComentariosCollectionNew;
            usuario.setComentariosCollection(comentariosCollectionNew);
            Collection<ListaJuegos> attachedListaJuegosCollectionNew = new ArrayList<ListaJuegos>();
            for (ListaJuegos listaJuegosCollectionNewListaJuegosToAttach : listaJuegosCollectionNew) {
                listaJuegosCollectionNewListaJuegosToAttach = em.getReference(listaJuegosCollectionNewListaJuegosToAttach.getClass(), listaJuegosCollectionNewListaJuegosToAttach.getListaId());
                attachedListaJuegosCollectionNew.add(listaJuegosCollectionNewListaJuegosToAttach);
            }
            listaJuegosCollectionNew = attachedListaJuegosCollectionNew;
            usuario.setListaJuegosCollection(listaJuegosCollectionNew);
            usuario = em.merge(usuario);
            for (Resena resenaCollectionOldResena : resenaCollectionOld) {
                if (!resenaCollectionNew.contains(resenaCollectionOldResena)) {
                    resenaCollectionOldResena.setUserId(null);
                    resenaCollectionOldResena = em.merge(resenaCollectionOldResena);
                }
            }
            for (Resena resenaCollectionNewResena : resenaCollectionNew) {
                if (!resenaCollectionOld.contains(resenaCollectionNewResena)) {
                    Usuario oldUserIdOfResenaCollectionNewResena = resenaCollectionNewResena.getUserId();
                    resenaCollectionNewResena.setUserId(usuario);
                    resenaCollectionNewResena = em.merge(resenaCollectionNewResena);
                    if (oldUserIdOfResenaCollectionNewResena != null && !oldUserIdOfResenaCollectionNewResena.equals(usuario)) {
                        oldUserIdOfResenaCollectionNewResena.getResenaCollection().remove(resenaCollectionNewResena);
                        oldUserIdOfResenaCollectionNewResena = em.merge(oldUserIdOfResenaCollectionNewResena);
                    }
                }
            }
            for (Amigos amigosCollectionNewAmigos : amigosCollectionNew) {
                if (!amigosCollectionOld.contains(amigosCollectionNewAmigos)) {
                    Usuario oldUser1IdOfAmigosCollectionNewAmigos = amigosCollectionNewAmigos.getUser1Id();
                    amigosCollectionNewAmigos.setUser1Id(usuario);
                    amigosCollectionNewAmigos = em.merge(amigosCollectionNewAmigos);
                    if (oldUser1IdOfAmigosCollectionNewAmigos != null && !oldUser1IdOfAmigosCollectionNewAmigos.equals(usuario)) {
                        oldUser1IdOfAmigosCollectionNewAmigos.getAmigosCollection().remove(amigosCollectionNewAmigos);
                        oldUser1IdOfAmigosCollectionNewAmigos = em.merge(oldUser1IdOfAmigosCollectionNewAmigos);
                    }
                }
            }
            for (Amigos amigosCollection1NewAmigos : amigosCollection1New) {
                if (!amigosCollection1Old.contains(amigosCollection1NewAmigos)) {
                    Usuario oldUser2IdOfAmigosCollection1NewAmigos = amigosCollection1NewAmigos.getUser2Id();
                    amigosCollection1NewAmigos.setUser2Id(usuario);
                    amigosCollection1NewAmigos = em.merge(amigosCollection1NewAmigos);
                    if (oldUser2IdOfAmigosCollection1NewAmigos != null && !oldUser2IdOfAmigosCollection1NewAmigos.equals(usuario)) {
                        oldUser2IdOfAmigosCollection1NewAmigos.getAmigosCollection1().remove(amigosCollection1NewAmigos);
                        oldUser2IdOfAmigosCollection1NewAmigos = em.merge(oldUser2IdOfAmigosCollection1NewAmigos);
                    }
                }
            }
            for (Valoracion valoracionCollectionOldValoracion : valoracionCollectionOld) {
                if (!valoracionCollectionNew.contains(valoracionCollectionOldValoracion)) {
                    valoracionCollectionOldValoracion.setUserId(null);
                    valoracionCollectionOldValoracion = em.merge(valoracionCollectionOldValoracion);
                }
            }
            for (Valoracion valoracionCollectionNewValoracion : valoracionCollectionNew) {
                if (!valoracionCollectionOld.contains(valoracionCollectionNewValoracion)) {
                    Usuario oldUserIdOfValoracionCollectionNewValoracion = valoracionCollectionNewValoracion.getUserId();
                    valoracionCollectionNewValoracion.setUserId(usuario);
                    valoracionCollectionNewValoracion = em.merge(valoracionCollectionNewValoracion);
                    if (oldUserIdOfValoracionCollectionNewValoracion != null && !oldUserIdOfValoracionCollectionNewValoracion.equals(usuario)) {
                        oldUserIdOfValoracionCollectionNewValoracion.getValoracionCollection().remove(valoracionCollectionNewValoracion);
                        oldUserIdOfValoracionCollectionNewValoracion = em.merge(oldUserIdOfValoracionCollectionNewValoracion);
                    }
                }
            }
            for (Valoracionweb valoracionwebCollectionNewValoracionweb : valoracionwebCollectionNew) {
                if (!valoracionwebCollectionOld.contains(valoracionwebCollectionNewValoracionweb)) {
                    Usuario oldUserIdOfValoracionwebCollectionNewValoracionweb = valoracionwebCollectionNewValoracionweb.getUserId();
                    valoracionwebCollectionNewValoracionweb.setUserId(usuario);
                    valoracionwebCollectionNewValoracionweb = em.merge(valoracionwebCollectionNewValoracionweb);
                    if (oldUserIdOfValoracionwebCollectionNewValoracionweb != null && !oldUserIdOfValoracionwebCollectionNewValoracionweb.equals(usuario)) {
                        oldUserIdOfValoracionwebCollectionNewValoracionweb.getValoracionwebCollection().remove(valoracionwebCollectionNewValoracionweb);
                        oldUserIdOfValoracionwebCollectionNewValoracionweb = em.merge(oldUserIdOfValoracionwebCollectionNewValoracionweb);
                    }
                }
            }
            for (Comentarios comentariosCollectionOldComentarios : comentariosCollectionOld) {
                if (!comentariosCollectionNew.contains(comentariosCollectionOldComentarios)) {
                    comentariosCollectionOldComentarios.setUserId(null);
                    comentariosCollectionOldComentarios = em.merge(comentariosCollectionOldComentarios);
                }
            }
            for (Comentarios comentariosCollectionNewComentarios : comentariosCollectionNew) {
                if (!comentariosCollectionOld.contains(comentariosCollectionNewComentarios)) {
                    Usuario oldUserIdOfComentariosCollectionNewComentarios = comentariosCollectionNewComentarios.getUserId();
                    comentariosCollectionNewComentarios.setUserId(usuario);
                    comentariosCollectionNewComentarios = em.merge(comentariosCollectionNewComentarios);
                    if (oldUserIdOfComentariosCollectionNewComentarios != null && !oldUserIdOfComentariosCollectionNewComentarios.equals(usuario)) {
                        oldUserIdOfComentariosCollectionNewComentarios.getComentariosCollection().remove(comentariosCollectionNewComentarios);
                        oldUserIdOfComentariosCollectionNewComentarios = em.merge(oldUserIdOfComentariosCollectionNewComentarios);
                    }
                }
            }
            for (ListaJuegos listaJuegosCollectionOldListaJuegos : listaJuegosCollectionOld) {
                if (!listaJuegosCollectionNew.contains(listaJuegosCollectionOldListaJuegos)) {
                    listaJuegosCollectionOldListaJuegos.setUserId(null);
                    listaJuegosCollectionOldListaJuegos = em.merge(listaJuegosCollectionOldListaJuegos);
                }
            }
            for (ListaJuegos listaJuegosCollectionNewListaJuegos : listaJuegosCollectionNew) {
                if (!listaJuegosCollectionOld.contains(listaJuegosCollectionNewListaJuegos)) {
                    Usuario oldUserIdOfListaJuegosCollectionNewListaJuegos = listaJuegosCollectionNewListaJuegos.getUserId();
                    listaJuegosCollectionNewListaJuegos.setUserId(usuario);
                    listaJuegosCollectionNewListaJuegos = em.merge(listaJuegosCollectionNewListaJuegos);
                    if (oldUserIdOfListaJuegosCollectionNewListaJuegos != null && !oldUserIdOfListaJuegosCollectionNewListaJuegos.equals(usuario)) {
                        oldUserIdOfListaJuegosCollectionNewListaJuegos.getListaJuegosCollection().remove(listaJuegosCollectionNewListaJuegos);
                        oldUserIdOfListaJuegosCollectionNewListaJuegos = em.merge(oldUserIdOfListaJuegosCollectionNewListaJuegos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUserId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Amigos> amigosCollectionOrphanCheck = usuario.getAmigosCollection();
            for (Amigos amigosCollectionOrphanCheckAmigos : amigosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Amigos " + amigosCollectionOrphanCheckAmigos + " in its amigosCollection field has a non-nullable user1Id field.");
            }
            Collection<Amigos> amigosCollection1OrphanCheck = usuario.getAmigosCollection1();
            for (Amigos amigosCollection1OrphanCheckAmigos : amigosCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Amigos " + amigosCollection1OrphanCheckAmigos + " in its amigosCollection1 field has a non-nullable user2Id field.");
            }
            Collection<Valoracionweb> valoracionwebCollectionOrphanCheck = usuario.getValoracionwebCollection();
            for (Valoracionweb valoracionwebCollectionOrphanCheckValoracionweb : valoracionwebCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Valoracionweb " + valoracionwebCollectionOrphanCheckValoracionweb + " in its valoracionwebCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Resena> resenaCollection = usuario.getResenaCollection();
            for (Resena resenaCollectionResena : resenaCollection) {
                resenaCollectionResena.setUserId(null);
                resenaCollectionResena = em.merge(resenaCollectionResena);
            }
            Collection<Valoracion> valoracionCollection = usuario.getValoracionCollection();
            for (Valoracion valoracionCollectionValoracion : valoracionCollection) {
                valoracionCollectionValoracion.setUserId(null);
                valoracionCollectionValoracion = em.merge(valoracionCollectionValoracion);
            }
            Collection<Comentarios> comentariosCollection = usuario.getComentariosCollection();
            for (Comentarios comentariosCollectionComentarios : comentariosCollection) {
                comentariosCollectionComentarios.setUserId(null);
                comentariosCollectionComentarios = em.merge(comentariosCollectionComentarios);
            }
            Collection<ListaJuegos> listaJuegosCollection = usuario.getListaJuegosCollection();
            for (ListaJuegos listaJuegosCollectionListaJuegos : listaJuegosCollection) {
                listaJuegosCollectionListaJuegos.setUserId(null);
                listaJuegosCollectionListaJuegos = em.merge(listaJuegosCollectionListaJuegos);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
