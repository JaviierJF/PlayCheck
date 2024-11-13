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
import Modelo.Entidades.Valoracion;
import Modelo.Entidades.Videojuego;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Javier
 */
public class ValoracionJpaController implements Serializable {

    public ValoracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valoracion valoracion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = valoracion.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                valoracion.setUserId(userId);
            }
            Videojuego juegoId = valoracion.getJuegoId();
            if (juegoId != null) {
                juegoId = em.getReference(juegoId.getClass(), juegoId.getJuegoId());
                valoracion.setJuegoId(juegoId);
            }
            em.persist(valoracion);
            if (userId != null) {
                userId.getValoracionCollection().add(valoracion);
                userId = em.merge(userId);
            }
            if (juegoId != null) {
                juegoId.getValoracionCollection().add(valoracion);
                juegoId = em.merge(juegoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Valoracion valoracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoracion persistentValoracion = em.find(Valoracion.class, valoracion.getValoracionId());
            Usuario userIdOld = persistentValoracion.getUserId();
            Usuario userIdNew = valoracion.getUserId();
            Videojuego juegoIdOld = persistentValoracion.getJuegoId();
            Videojuego juegoIdNew = valoracion.getJuegoId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                valoracion.setUserId(userIdNew);
            }
            if (juegoIdNew != null) {
                juegoIdNew = em.getReference(juegoIdNew.getClass(), juegoIdNew.getJuegoId());
                valoracion.setJuegoId(juegoIdNew);
            }
            valoracion = em.merge(valoracion);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getValoracionCollection().remove(valoracion);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getValoracionCollection().add(valoracion);
                userIdNew = em.merge(userIdNew);
            }
            if (juegoIdOld != null && !juegoIdOld.equals(juegoIdNew)) {
                juegoIdOld.getValoracionCollection().remove(valoracion);
                juegoIdOld = em.merge(juegoIdOld);
            }
            if (juegoIdNew != null && !juegoIdNew.equals(juegoIdOld)) {
                juegoIdNew.getValoracionCollection().add(valoracion);
                juegoIdNew = em.merge(juegoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valoracion.getValoracionId();
                if (findValoracion(id) == null) {
                    throw new NonexistentEntityException("The valoracion with id " + id + " no longer exists.");
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
            Valoracion valoracion;
            try {
                valoracion = em.getReference(Valoracion.class, id);
                valoracion.getValoracionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valoracion with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = valoracion.getUserId();
            if (userId != null) {
                userId.getValoracionCollection().remove(valoracion);
                userId = em.merge(userId);
            }
            Videojuego juegoId = valoracion.getJuegoId();
            if (juegoId != null) {
                juegoId.getValoracionCollection().remove(valoracion);
                juegoId = em.merge(juegoId);
            }
            em.remove(valoracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Valoracion> findValoracionEntities() {
        return findValoracionEntities(true, -1, -1);
    }

    public List<Valoracion> findValoracionEntities(int maxResults, int firstResult) {
        return findValoracionEntities(false, maxResults, firstResult);
    }

    private List<Valoracion> findValoracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Valoracion.class));
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

    public Valoracion findValoracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Valoracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getValoracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Valoracion> rt = cq.from(Valoracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
