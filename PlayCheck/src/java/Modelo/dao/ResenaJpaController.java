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
import Modelo.Entidades.Usuario;
import Modelo.Entidades.Videojuego;
import Modelo.Entidades.Comentarios;
import Modelo.Entidades.Resena;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Javier
 */
public class ResenaJpaController implements Serializable {

    public ResenaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resena resena) {
        if (resena.getComentariosCollection() == null) {
            resena.setComentariosCollection(new ArrayList<Comentarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = resena.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                resena.setUserId(userId);
            }
            Videojuego juegoId = resena.getJuegoId();
            if (juegoId != null) {
                juegoId = em.getReference(juegoId.getClass(), juegoId.getJuegoId());
                resena.setJuegoId(juegoId);
            }
            Collection<Comentarios> attachedComentariosCollection = new ArrayList<Comentarios>();
            for (Comentarios comentariosCollectionComentariosToAttach : resena.getComentariosCollection()) {
                comentariosCollectionComentariosToAttach = em.getReference(comentariosCollectionComentariosToAttach.getClass(), comentariosCollectionComentariosToAttach.getComentarioId());
                attachedComentariosCollection.add(comentariosCollectionComentariosToAttach);
            }
            resena.setComentariosCollection(attachedComentariosCollection);
            em.persist(resena);
            if (userId != null) {
                userId.getResenaCollection().add(resena);
                userId = em.merge(userId);
            }
            if (juegoId != null) {
                juegoId.getResenaCollection().add(resena);
                juegoId = em.merge(juegoId);
            }
            for (Comentarios comentariosCollectionComentarios : resena.getComentariosCollection()) {
                Resena oldResenaIdOfComentariosCollectionComentarios = comentariosCollectionComentarios.getResenaId();
                comentariosCollectionComentarios.setResenaId(resena);
                comentariosCollectionComentarios = em.merge(comentariosCollectionComentarios);
                if (oldResenaIdOfComentariosCollectionComentarios != null) {
                    oldResenaIdOfComentariosCollectionComentarios.getComentariosCollection().remove(comentariosCollectionComentarios);
                    oldResenaIdOfComentariosCollectionComentarios = em.merge(oldResenaIdOfComentariosCollectionComentarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resena resena) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resena persistentResena = em.find(Resena.class, resena.getResenaId());
            Usuario userIdOld = persistentResena.getUserId();
            Usuario userIdNew = resena.getUserId();
            Videojuego juegoIdOld = persistentResena.getJuegoId();
            Videojuego juegoIdNew = resena.getJuegoId();
            Collection<Comentarios> comentariosCollectionOld = persistentResena.getComentariosCollection();
            Collection<Comentarios> comentariosCollectionNew = resena.getComentariosCollection();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                resena.setUserId(userIdNew);
            }
            if (juegoIdNew != null) {
                juegoIdNew = em.getReference(juegoIdNew.getClass(), juegoIdNew.getJuegoId());
                resena.setJuegoId(juegoIdNew);
            }
            Collection<Comentarios> attachedComentariosCollectionNew = new ArrayList<Comentarios>();
            for (Comentarios comentariosCollectionNewComentariosToAttach : comentariosCollectionNew) {
                comentariosCollectionNewComentariosToAttach = em.getReference(comentariosCollectionNewComentariosToAttach.getClass(), comentariosCollectionNewComentariosToAttach.getComentarioId());
                attachedComentariosCollectionNew.add(comentariosCollectionNewComentariosToAttach);
            }
            comentariosCollectionNew = attachedComentariosCollectionNew;
            resena.setComentariosCollection(comentariosCollectionNew);
            resena = em.merge(resena);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getResenaCollection().remove(resena);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getResenaCollection().add(resena);
                userIdNew = em.merge(userIdNew);
            }
            if (juegoIdOld != null && !juegoIdOld.equals(juegoIdNew)) {
                juegoIdOld.getResenaCollection().remove(resena);
                juegoIdOld = em.merge(juegoIdOld);
            }
            if (juegoIdNew != null && !juegoIdNew.equals(juegoIdOld)) {
                juegoIdNew.getResenaCollection().add(resena);
                juegoIdNew = em.merge(juegoIdNew);
            }
            for (Comentarios comentariosCollectionOldComentarios : comentariosCollectionOld) {
                if (!comentariosCollectionNew.contains(comentariosCollectionOldComentarios)) {
                    comentariosCollectionOldComentarios.setResenaId(null);
                    comentariosCollectionOldComentarios = em.merge(comentariosCollectionOldComentarios);
                }
            }
            for (Comentarios comentariosCollectionNewComentarios : comentariosCollectionNew) {
                if (!comentariosCollectionOld.contains(comentariosCollectionNewComentarios)) {
                    Resena oldResenaIdOfComentariosCollectionNewComentarios = comentariosCollectionNewComentarios.getResenaId();
                    comentariosCollectionNewComentarios.setResenaId(resena);
                    comentariosCollectionNewComentarios = em.merge(comentariosCollectionNewComentarios);
                    if (oldResenaIdOfComentariosCollectionNewComentarios != null && !oldResenaIdOfComentariosCollectionNewComentarios.equals(resena)) {
                        oldResenaIdOfComentariosCollectionNewComentarios.getComentariosCollection().remove(comentariosCollectionNewComentarios);
                        oldResenaIdOfComentariosCollectionNewComentarios = em.merge(oldResenaIdOfComentariosCollectionNewComentarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resena.getResenaId();
                if (findResena(id) == null) {
                    throw new NonexistentEntityException("The resena with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resena resena;
            try {
                resena = em.getReference(Resena.class, id);
                resena.getResenaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resena with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = resena.getUserId();
            if (userId != null) {
                userId.getResenaCollection().remove(resena);
                userId = em.merge(userId);
            }
            Videojuego juegoId = resena.getJuegoId();
            if (juegoId != null) {
                juegoId.getResenaCollection().remove(resena);
                juegoId = em.merge(juegoId);
            }
            Collection<Comentarios> comentariosCollection = resena.getComentariosCollection();
            for (Comentarios comentariosCollectionComentarios : comentariosCollection) {
                comentariosCollectionComentarios.setResenaId(null);
                comentariosCollectionComentarios = em.merge(comentariosCollectionComentarios);
            }
            em.remove(resena);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resena> findResenaEntities() {
        return findResenaEntities(true, -1, -1);
    }

    public List<Resena> findResenaEntities(int maxResults, int firstResult) {
        return findResenaEntities(false, maxResults, firstResult);
    }

    private List<Resena> findResenaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resena.class));
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

    public Resena findResena(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resena.class, id);
        } finally {
            em.close();
        }
    }

    public int getResenaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resena> rt = cq.from(Resena.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
