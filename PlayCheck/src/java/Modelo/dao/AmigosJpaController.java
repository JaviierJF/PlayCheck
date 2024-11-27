/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import Modelo.Entidades.Amigos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Entidades.Usuario;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Javier
 */
public class AmigosJpaController implements Serializable {

    public AmigosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Amigos amigos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario user1Id = amigos.getUser1Id();
            if (user1Id != null) {
                user1Id = em.getReference(user1Id.getClass(), user1Id.getUserId());
                amigos.setUser1Id(user1Id);
            }
            Usuario user2Id = amigos.getUser2Id();
            if (user2Id != null) {
                user2Id = em.getReference(user2Id.getClass(), user2Id.getUserId());
                amigos.setUser2Id(user2Id);
            }
            em.persist(amigos);
            if (user1Id != null) {
                user1Id.getAmigosCollection().add(amigos);
                user1Id = em.merge(user1Id);
            }
            if (user2Id != null) {
                user2Id.getAmigosCollection().add(amigos);
                user2Id = em.merge(user2Id);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Amigos amigos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amigos persistentAmigos = em.find(Amigos.class, amigos.getAmigoId());
            Usuario user1IdOld = persistentAmigos.getUser1Id();
            Usuario user1IdNew = amigos.getUser1Id();
            Usuario user2IdOld = persistentAmigos.getUser2Id();
            Usuario user2IdNew = amigos.getUser2Id();
            if (user1IdNew != null) {
                user1IdNew = em.getReference(user1IdNew.getClass(), user1IdNew.getUserId());
                amigos.setUser1Id(user1IdNew);
            }
            if (user2IdNew != null) {
                user2IdNew = em.getReference(user2IdNew.getClass(), user2IdNew.getUserId());
                amigos.setUser2Id(user2IdNew);
            }
            amigos = em.merge(amigos);
            if (user1IdOld != null && !user1IdOld.equals(user1IdNew)) {
                user1IdOld.getAmigosCollection().remove(amigos);
                user1IdOld = em.merge(user1IdOld);
            }
            if (user1IdNew != null && !user1IdNew.equals(user1IdOld)) {
                user1IdNew.getAmigosCollection().add(amigos);
                user1IdNew = em.merge(user1IdNew);
            }
            if (user2IdOld != null && !user2IdOld.equals(user2IdNew)) {
                user2IdOld.getAmigosCollection().remove(amigos);
                user2IdOld = em.merge(user2IdOld);
            }
            if (user2IdNew != null && !user2IdNew.equals(user2IdOld)) {
                user2IdNew.getAmigosCollection().add(amigos);
                user2IdNew = em.merge(user2IdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = amigos.getAmigoId();
                if (findAmigos(id) == null) {
                    throw new NonexistentEntityException("The amigos with id " + id + " no longer exists.");
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
            Amigos amigos;
            try {
                amigos = em.getReference(Amigos.class, id);
                amigos.getAmigoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The amigos with id " + id + " no longer exists.", enfe);
            }
            Usuario user1Id = amigos.getUser1Id();
            if (user1Id != null) {
                user1Id.getAmigosCollection().remove(amigos);
                user1Id = em.merge(user1Id);
            }
            Usuario user2Id = amigos.getUser2Id();
            if (user2Id != null) {
                user2Id.getAmigosCollection().remove(amigos);
                user2Id = em.merge(user2Id);
            }
            em.remove(amigos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amigos> findAmigosEntities() {
        return findAmigosEntities(true, -1, -1);
    }

    public List<Amigos> findAmigosEntities(int maxResults, int firstResult) {
        return findAmigosEntities(false, maxResults, firstResult);
    }

    private List<Amigos> findAmigosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Amigos.class));
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

    public Amigos findAmigos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Amigos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAmigosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Amigos> rt = cq.from(Amigos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
