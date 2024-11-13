/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import Modelo.Entidades.Genero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Entidades.Videojuego;
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
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) {
        if (genero.getVideojuegoCollection() == null) {
            genero.setVideojuegoCollection(new ArrayList<Videojuego>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Videojuego> attachedVideojuegoCollection = new ArrayList<Videojuego>();
            for (Videojuego videojuegoCollectionVideojuegoToAttach : genero.getVideojuegoCollection()) {
                videojuegoCollectionVideojuegoToAttach = em.getReference(videojuegoCollectionVideojuegoToAttach.getClass(), videojuegoCollectionVideojuegoToAttach.getJuegoId());
                attachedVideojuegoCollection.add(videojuegoCollectionVideojuegoToAttach);
            }
            genero.setVideojuegoCollection(attachedVideojuegoCollection);
            em.persist(genero);
            for (Videojuego videojuegoCollectionVideojuego : genero.getVideojuegoCollection()) {
                videojuegoCollectionVideojuego.getGeneroCollection().add(genero);
                videojuegoCollectionVideojuego = em.merge(videojuegoCollectionVideojuego);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getGeneroId());
            Collection<Videojuego> videojuegoCollectionOld = persistentGenero.getVideojuegoCollection();
            Collection<Videojuego> videojuegoCollectionNew = genero.getVideojuegoCollection();
            Collection<Videojuego> attachedVideojuegoCollectionNew = new ArrayList<Videojuego>();
            for (Videojuego videojuegoCollectionNewVideojuegoToAttach : videojuegoCollectionNew) {
                videojuegoCollectionNewVideojuegoToAttach = em.getReference(videojuegoCollectionNewVideojuegoToAttach.getClass(), videojuegoCollectionNewVideojuegoToAttach.getJuegoId());
                attachedVideojuegoCollectionNew.add(videojuegoCollectionNewVideojuegoToAttach);
            }
            videojuegoCollectionNew = attachedVideojuegoCollectionNew;
            genero.setVideojuegoCollection(videojuegoCollectionNew);
            genero = em.merge(genero);
            for (Videojuego videojuegoCollectionOldVideojuego : videojuegoCollectionOld) {
                if (!videojuegoCollectionNew.contains(videojuegoCollectionOldVideojuego)) {
                    videojuegoCollectionOldVideojuego.getGeneroCollection().remove(genero);
                    videojuegoCollectionOldVideojuego = em.merge(videojuegoCollectionOldVideojuego);
                }
            }
            for (Videojuego videojuegoCollectionNewVideojuego : videojuegoCollectionNew) {
                if (!videojuegoCollectionOld.contains(videojuegoCollectionNewVideojuego)) {
                    videojuegoCollectionNewVideojuego.getGeneroCollection().add(genero);
                    videojuegoCollectionNewVideojuego = em.merge(videojuegoCollectionNewVideojuego);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genero.getGeneroId();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getGeneroId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            Collection<Videojuego> videojuegoCollection = genero.getVideojuegoCollection();
            for (Videojuego videojuegoCollectionVideojuego : videojuegoCollection) {
                videojuegoCollectionVideojuego.getGeneroCollection().remove(genero);
                videojuegoCollectionVideojuego = em.merge(videojuegoCollectionVideojuego);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
