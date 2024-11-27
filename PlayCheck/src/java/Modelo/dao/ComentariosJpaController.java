/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import Modelo.Entidades.Comentarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Entidades.Resena;
import Modelo.Entidades.Usuario;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Javier
 */
public class ComentariosJpaController implements Serializable {

    public ComentariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentarios comentarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resena resenaId = comentarios.getResenaId();
            if (resenaId != null) {
                resenaId = em.getReference(resenaId.getClass(), resenaId.getResenaId());
                comentarios.setResenaId(resenaId);
            }
            Usuario userId = comentarios.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                comentarios.setUserId(userId);
            }
            em.persist(comentarios);
            if (resenaId != null) {
                resenaId.getComentariosCollection().add(comentarios);
                resenaId = em.merge(resenaId);
            }
            if (userId != null) {
                userId.getComentariosCollection().add(comentarios);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentarios comentarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentarios persistentComentarios = em.find(Comentarios.class, comentarios.getComentarioId());
            Resena resenaIdOld = persistentComentarios.getResenaId();
            Resena resenaIdNew = comentarios.getResenaId();
            Usuario userIdOld = persistentComentarios.getUserId();
            Usuario userIdNew = comentarios.getUserId();
            if (resenaIdNew != null) {
                resenaIdNew = em.getReference(resenaIdNew.getClass(), resenaIdNew.getResenaId());
                comentarios.setResenaId(resenaIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                comentarios.setUserId(userIdNew);
            }
            comentarios = em.merge(comentarios);
            if (resenaIdOld != null && !resenaIdOld.equals(resenaIdNew)) {
                resenaIdOld.getComentariosCollection().remove(comentarios);
                resenaIdOld = em.merge(resenaIdOld);
            }
            if (resenaIdNew != null && !resenaIdNew.equals(resenaIdOld)) {
                resenaIdNew.getComentariosCollection().add(comentarios);
                resenaIdNew = em.merge(resenaIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getComentariosCollection().remove(comentarios);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getComentariosCollection().add(comentarios);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentarios.getComentarioId();
                if (findComentarios(id) == null) {
                    throw new NonexistentEntityException("The comentarios with id " + id + " no longer exists.");
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
            Comentarios comentarios;
            try {
                comentarios = em.getReference(Comentarios.class, id);
                comentarios.getComentarioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentarios with id " + id + " no longer exists.", enfe);
            }
            Resena resenaId = comentarios.getResenaId();
            if (resenaId != null) {
                resenaId.getComentariosCollection().remove(comentarios);
                resenaId = em.merge(resenaId);
            }
            Usuario userId = comentarios.getUserId();
            if (userId != null) {
                userId.getComentariosCollection().remove(comentarios);
                userId = em.merge(userId);
            }
            em.remove(comentarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentarios> findComentariosEntities() {
        return findComentariosEntities(true, -1, -1);
    }

    public List<Comentarios> findComentariosEntities(int maxResults, int firstResult) {
        return findComentariosEntities(false, maxResults, firstResult);
    }

    private List<Comentarios> findComentariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentarios.class));
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

    public Comentarios findComentarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentarios> rt = cq.from(Comentarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
