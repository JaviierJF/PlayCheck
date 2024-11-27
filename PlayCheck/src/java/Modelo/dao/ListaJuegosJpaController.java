/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import Modelo.Entidades.ListaJuegos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Entidades.Usuario;
import Modelo.Entidades.Videojuego;
import Modelo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Javier
 */
public class ListaJuegosJpaController implements Serializable {

    public ListaJuegosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaJuegos listaJuegos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario userId = listaJuegos.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                listaJuegos.setUserId(userId);
            }
            Videojuego juegoId = listaJuegos.getJuegoId();
            if (juegoId != null) {
                juegoId = em.getReference(juegoId.getClass(), juegoId.getJuegoId());
                listaJuegos.setJuegoId(juegoId);
            }
            em.persist(listaJuegos);
            if (userId != null) {
                userId.getListaJuegosCollection().add(listaJuegos);
                userId = em.merge(userId);
            }
            if (juegoId != null) {
                juegoId.getListaJuegosCollection().add(listaJuegos);
                juegoId = em.merge(juegoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaJuegos listaJuegos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaJuegos persistentListaJuegos = em.find(ListaJuegos.class, listaJuegos.getListaId());
            Usuario userIdOld = persistentListaJuegos.getUserId();
            Usuario userIdNew = listaJuegos.getUserId();
            Videojuego juegoIdOld = persistentListaJuegos.getJuegoId();
            Videojuego juegoIdNew = listaJuegos.getJuegoId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                listaJuegos.setUserId(userIdNew);
            }
            if (juegoIdNew != null) {
                juegoIdNew = em.getReference(juegoIdNew.getClass(), juegoIdNew.getJuegoId());
                listaJuegos.setJuegoId(juegoIdNew);
            }
            listaJuegos = em.merge(listaJuegos);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getListaJuegosCollection().remove(listaJuegos);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getListaJuegosCollection().add(listaJuegos);
                userIdNew = em.merge(userIdNew);
            }
            if (juegoIdOld != null && !juegoIdOld.equals(juegoIdNew)) {
                juegoIdOld.getListaJuegosCollection().remove(listaJuegos);
                juegoIdOld = em.merge(juegoIdOld);
            }
            if (juegoIdNew != null && !juegoIdNew.equals(juegoIdOld)) {
                juegoIdNew.getListaJuegosCollection().add(listaJuegos);
                juegoIdNew = em.merge(juegoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listaJuegos.getListaId();
                if (findListaJuegos(id) == null) {
                    throw new NonexistentEntityException("The listaJuegos with id " + id + " no longer exists.");
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
            ListaJuegos listaJuegos;
            try {
                listaJuegos = em.getReference(ListaJuegos.class, id);
                listaJuegos.getListaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaJuegos with id " + id + " no longer exists.", enfe);
            }
            Usuario userId = listaJuegos.getUserId();
            if (userId != null) {
                userId.getListaJuegosCollection().remove(listaJuegos);
                userId = em.merge(userId);
            }
            Videojuego juegoId = listaJuegos.getJuegoId();
            if (juegoId != null) {
                juegoId.getListaJuegosCollection().remove(listaJuegos);
                juegoId = em.merge(juegoId);
            }
            em.remove(listaJuegos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaJuegos> findListaJuegosEntities() {
        return findListaJuegosEntities(true, -1, -1);
    }

    public List<ListaJuegos> findListaJuegosEntities(int maxResults, int firstResult) {
        return findListaJuegosEntities(false, maxResults, firstResult);
    }

    private List<ListaJuegos> findListaJuegosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaJuegos.class));
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

    public ListaJuegos findListaJuegos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaJuegos.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaJuegosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaJuegos> rt = cq.from(ListaJuegos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public ListaJuegos findListaJuegosByVideojuegoId(Videojuego juegoId) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery<ListaJuegos> query = em.createQuery("SELECT l FROM ListaJuegos l where l.juegoId = :juegoId", ListaJuegos.class);
            query.setParameter("juegoId", juegoId);
            // Ejecutar la consulta y devolver los resultados
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}
