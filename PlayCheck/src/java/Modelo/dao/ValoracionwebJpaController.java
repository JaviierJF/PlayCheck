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
import Modelo.Entidades.Valoracionweb;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Javier
 */
public class ValoracionwebJpaController implements Serializable {

    public ValoracionwebJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valoracionweb valoracionweb) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = valoracionweb.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                valoracionweb.setUserId(userId);
            }
            em.persist(valoracionweb);
            if (userId != null) {
                userId.getValoracionwebCollection().add(valoracionweb);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Valoracionweb valoracionweb) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoracionweb persistentValoracionweb = em.find(Valoracionweb.class, valoracionweb.getValoracionWebid());
            Usuario userIdOld = persistentValoracionweb.getUserId();
            Usuario userIdNew = valoracionweb.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                valoracionweb.setUserId(userIdNew);
            }
            valoracionweb = em.merge(valoracionweb);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getValoracionwebCollection().remove(valoracionweb);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getValoracionwebCollection().add(valoracionweb);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valoracionweb.getValoracionWebid();
                if (findValoracionweb(id) == null) {
                    throw new NonexistentEntityException("The valoracionweb with id " + id + " no longer exists.");
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
            Valoracionweb valoracionweb;
            try {
                valoracionweb = em.getReference(Valoracionweb.class, id);
                valoracionweb.getValoracionWebid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valoracionweb with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = valoracionweb.getUserId();
            if (userId != null) {
                userId.getValoracionwebCollection().remove(valoracionweb);
                userId = em.merge(userId);
            }
            em.remove(valoracionweb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Valoracionweb> findValoracionwebEntities() {
        return findValoracionwebEntities(true, -1, -1);
    }

    public List<Valoracionweb> findValoracionwebEntities(int maxResults, int firstResult) {
        return findValoracionwebEntities(false, maxResults, firstResult);
    }

    private List<Valoracionweb> findValoracionwebEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Valoracionweb.class));
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

    public Valoracionweb findValoracionweb(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Valoracionweb.class, id);
        } finally {
            em.close();
        }
    }

    public int getValoracionwebCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Valoracionweb> rt = cq.from(Valoracionweb.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
