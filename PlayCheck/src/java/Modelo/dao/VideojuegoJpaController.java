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
import Modelo.Entidades.Genero;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.Entidades.Resena;
import Modelo.Entidades.Valoracion;
import Modelo.Entidades.ListaJuegos;
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
public class VideojuegoJpaController implements Serializable {

    public VideojuegoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Videojuego videojuego) {
        if (videojuego.getGeneroCollection() == null) {
            videojuego.setGeneroCollection(new ArrayList<Genero>());
        }
        if (videojuego.getResenaCollection() == null) {
            videojuego.setResenaCollection(new ArrayList<Resena>());
        }
        if (videojuego.getValoracionCollection() == null) {
            videojuego.setValoracionCollection(new ArrayList<Valoracion>());
        }
        if (videojuego.getListaJuegosCollection() == null) {
            videojuego.setListaJuegosCollection(new ArrayList<ListaJuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Genero> attachedGeneroCollection = new ArrayList<Genero>();
            for (Genero generoCollectionGeneroToAttach : videojuego.getGeneroCollection()) {
                generoCollectionGeneroToAttach = em.getReference(generoCollectionGeneroToAttach.getClass(), generoCollectionGeneroToAttach.getGeneroId());
                attachedGeneroCollection.add(generoCollectionGeneroToAttach);
            }
            videojuego.setGeneroCollection(attachedGeneroCollection);
            Collection<Resena> attachedResenaCollection = new ArrayList<Resena>();
            for (Resena resenaCollectionResenaToAttach : videojuego.getResenaCollection()) {
                resenaCollectionResenaToAttach = em.getReference(resenaCollectionResenaToAttach.getClass(), resenaCollectionResenaToAttach.getResenaId());
                attachedResenaCollection.add(resenaCollectionResenaToAttach);
            }
            videojuego.setResenaCollection(attachedResenaCollection);
            Collection<Valoracion> attachedValoracionCollection = new ArrayList<Valoracion>();
            for (Valoracion valoracionCollectionValoracionToAttach : videojuego.getValoracionCollection()) {
                valoracionCollectionValoracionToAttach = em.getReference(valoracionCollectionValoracionToAttach.getClass(), valoracionCollectionValoracionToAttach.getValoracionId());
                attachedValoracionCollection.add(valoracionCollectionValoracionToAttach);
            }
            videojuego.setValoracionCollection(attachedValoracionCollection);
            Collection<ListaJuegos> attachedListaJuegosCollection = new ArrayList<ListaJuegos>();
            for (ListaJuegos listaJuegosCollectionListaJuegosToAttach : videojuego.getListaJuegosCollection()) {
                listaJuegosCollectionListaJuegosToAttach = em.getReference(listaJuegosCollectionListaJuegosToAttach.getClass(), listaJuegosCollectionListaJuegosToAttach.getListaId());
                attachedListaJuegosCollection.add(listaJuegosCollectionListaJuegosToAttach);
            }
            videojuego.setListaJuegosCollection(attachedListaJuegosCollection);
            em.persist(videojuego);
            for (Genero generoCollectionGenero : videojuego.getGeneroCollection()) {
                generoCollectionGenero.getVideojuegoCollection().add(videojuego);
                generoCollectionGenero = em.merge(generoCollectionGenero);
            }
            for (Resena resenaCollectionResena : videojuego.getResenaCollection()) {
                Videojuego oldJuegoIdOfResenaCollectionResena = resenaCollectionResena.getJuegoId();
                resenaCollectionResena.setJuegoId(videojuego);
                resenaCollectionResena = em.merge(resenaCollectionResena);
                if (oldJuegoIdOfResenaCollectionResena != null) {
                    oldJuegoIdOfResenaCollectionResena.getResenaCollection().remove(resenaCollectionResena);
                    oldJuegoIdOfResenaCollectionResena = em.merge(oldJuegoIdOfResenaCollectionResena);
                }
            }
            for (Valoracion valoracionCollectionValoracion : videojuego.getValoracionCollection()) {
                Videojuego oldJuegoIdOfValoracionCollectionValoracion = valoracionCollectionValoracion.getJuegoId();
                valoracionCollectionValoracion.setJuegoId(videojuego);
                valoracionCollectionValoracion = em.merge(valoracionCollectionValoracion);
                if (oldJuegoIdOfValoracionCollectionValoracion != null) {
                    oldJuegoIdOfValoracionCollectionValoracion.getValoracionCollection().remove(valoracionCollectionValoracion);
                    oldJuegoIdOfValoracionCollectionValoracion = em.merge(oldJuegoIdOfValoracionCollectionValoracion);
                }
            }
            for (ListaJuegos listaJuegosCollectionListaJuegos : videojuego.getListaJuegosCollection()) {
                Videojuego oldJuegoIdOfListaJuegosCollectionListaJuegos = listaJuegosCollectionListaJuegos.getJuegoId();
                listaJuegosCollectionListaJuegos.setJuegoId(videojuego);
                listaJuegosCollectionListaJuegos = em.merge(listaJuegosCollectionListaJuegos);
                if (oldJuegoIdOfListaJuegosCollectionListaJuegos != null) {
                    oldJuegoIdOfListaJuegosCollectionListaJuegos.getListaJuegosCollection().remove(listaJuegosCollectionListaJuegos);
                    oldJuegoIdOfListaJuegosCollectionListaJuegos = em.merge(oldJuegoIdOfListaJuegosCollectionListaJuegos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Videojuego videojuego) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Videojuego persistentVideojuego = em.find(Videojuego.class, videojuego.getJuegoId());
            Collection<Genero> generoCollectionOld = persistentVideojuego.getGeneroCollection();
            Collection<Genero> generoCollectionNew = videojuego.getGeneroCollection();
            Collection<Resena> resenaCollectionOld = persistentVideojuego.getResenaCollection();
            Collection<Resena> resenaCollectionNew = videojuego.getResenaCollection();
            Collection<Valoracion> valoracionCollectionOld = persistentVideojuego.getValoracionCollection();
            Collection<Valoracion> valoracionCollectionNew = videojuego.getValoracionCollection();
            Collection<ListaJuegos> listaJuegosCollectionOld = persistentVideojuego.getListaJuegosCollection();
            Collection<ListaJuegos> listaJuegosCollectionNew = videojuego.getListaJuegosCollection();
            Collection<Genero> attachedGeneroCollectionNew = new ArrayList<Genero>();
            for (Genero generoCollectionNewGeneroToAttach : generoCollectionNew) {
                generoCollectionNewGeneroToAttach = em.getReference(generoCollectionNewGeneroToAttach.getClass(), generoCollectionNewGeneroToAttach.getGeneroId());
                attachedGeneroCollectionNew.add(generoCollectionNewGeneroToAttach);
            }
            generoCollectionNew = attachedGeneroCollectionNew;
            videojuego.setGeneroCollection(generoCollectionNew);
            Collection<Resena> attachedResenaCollectionNew = new ArrayList<Resena>();
            for (Resena resenaCollectionNewResenaToAttach : resenaCollectionNew) {
                resenaCollectionNewResenaToAttach = em.getReference(resenaCollectionNewResenaToAttach.getClass(), resenaCollectionNewResenaToAttach.getResenaId());
                attachedResenaCollectionNew.add(resenaCollectionNewResenaToAttach);
            }
            resenaCollectionNew = attachedResenaCollectionNew;
            videojuego.setResenaCollection(resenaCollectionNew);
            Collection<Valoracion> attachedValoracionCollectionNew = new ArrayList<Valoracion>();
            for (Valoracion valoracionCollectionNewValoracionToAttach : valoracionCollectionNew) {
                valoracionCollectionNewValoracionToAttach = em.getReference(valoracionCollectionNewValoracionToAttach.getClass(), valoracionCollectionNewValoracionToAttach.getValoracionId());
                attachedValoracionCollectionNew.add(valoracionCollectionNewValoracionToAttach);
            }
            valoracionCollectionNew = attachedValoracionCollectionNew;
            videojuego.setValoracionCollection(valoracionCollectionNew);
            Collection<ListaJuegos> attachedListaJuegosCollectionNew = new ArrayList<ListaJuegos>();
            for (ListaJuegos listaJuegosCollectionNewListaJuegosToAttach : listaJuegosCollectionNew) {
                listaJuegosCollectionNewListaJuegosToAttach = em.getReference(listaJuegosCollectionNewListaJuegosToAttach.getClass(), listaJuegosCollectionNewListaJuegosToAttach.getListaId());
                attachedListaJuegosCollectionNew.add(listaJuegosCollectionNewListaJuegosToAttach);
            }
            listaJuegosCollectionNew = attachedListaJuegosCollectionNew;
            videojuego.setListaJuegosCollection(listaJuegosCollectionNew);
            videojuego = em.merge(videojuego);
            for (Genero generoCollectionOldGenero : generoCollectionOld) {
                if (!generoCollectionNew.contains(generoCollectionOldGenero)) {
                    generoCollectionOldGenero.getVideojuegoCollection().remove(videojuego);
                    generoCollectionOldGenero = em.merge(generoCollectionOldGenero);
                }
            }
            for (Genero generoCollectionNewGenero : generoCollectionNew) {
                if (!generoCollectionOld.contains(generoCollectionNewGenero)) {
                    generoCollectionNewGenero.getVideojuegoCollection().add(videojuego);
                    generoCollectionNewGenero = em.merge(generoCollectionNewGenero);
                }
            }
            for (Resena resenaCollectionOldResena : resenaCollectionOld) {
                if (!resenaCollectionNew.contains(resenaCollectionOldResena)) {
                    resenaCollectionOldResena.setJuegoId(null);
                    resenaCollectionOldResena = em.merge(resenaCollectionOldResena);
                }
            }
            for (Resena resenaCollectionNewResena : resenaCollectionNew) {
                if (!resenaCollectionOld.contains(resenaCollectionNewResena)) {
                    Videojuego oldJuegoIdOfResenaCollectionNewResena = resenaCollectionNewResena.getJuegoId();
                    resenaCollectionNewResena.setJuegoId(videojuego);
                    resenaCollectionNewResena = em.merge(resenaCollectionNewResena);
                    if (oldJuegoIdOfResenaCollectionNewResena != null && !oldJuegoIdOfResenaCollectionNewResena.equals(videojuego)) {
                        oldJuegoIdOfResenaCollectionNewResena.getResenaCollection().remove(resenaCollectionNewResena);
                        oldJuegoIdOfResenaCollectionNewResena = em.merge(oldJuegoIdOfResenaCollectionNewResena);
                    }
                }
            }
            for (Valoracion valoracionCollectionOldValoracion : valoracionCollectionOld) {
                if (!valoracionCollectionNew.contains(valoracionCollectionOldValoracion)) {
                    valoracionCollectionOldValoracion.setJuegoId(null);
                    valoracionCollectionOldValoracion = em.merge(valoracionCollectionOldValoracion);
                }
            }
            for (Valoracion valoracionCollectionNewValoracion : valoracionCollectionNew) {
                if (!valoracionCollectionOld.contains(valoracionCollectionNewValoracion)) {
                    Videojuego oldJuegoIdOfValoracionCollectionNewValoracion = valoracionCollectionNewValoracion.getJuegoId();
                    valoracionCollectionNewValoracion.setJuegoId(videojuego);
                    valoracionCollectionNewValoracion = em.merge(valoracionCollectionNewValoracion);
                    if (oldJuegoIdOfValoracionCollectionNewValoracion != null && !oldJuegoIdOfValoracionCollectionNewValoracion.equals(videojuego)) {
                        oldJuegoIdOfValoracionCollectionNewValoracion.getValoracionCollection().remove(valoracionCollectionNewValoracion);
                        oldJuegoIdOfValoracionCollectionNewValoracion = em.merge(oldJuegoIdOfValoracionCollectionNewValoracion);
                    }
                }
            }
            for (ListaJuegos listaJuegosCollectionOldListaJuegos : listaJuegosCollectionOld) {
                if (!listaJuegosCollectionNew.contains(listaJuegosCollectionOldListaJuegos)) {
                    listaJuegosCollectionOldListaJuegos.setJuegoId(null);
                    listaJuegosCollectionOldListaJuegos = em.merge(listaJuegosCollectionOldListaJuegos);
                }
            }
            for (ListaJuegos listaJuegosCollectionNewListaJuegos : listaJuegosCollectionNew) {
                if (!listaJuegosCollectionOld.contains(listaJuegosCollectionNewListaJuegos)) {
                    Videojuego oldJuegoIdOfListaJuegosCollectionNewListaJuegos = listaJuegosCollectionNewListaJuegos.getJuegoId();
                    listaJuegosCollectionNewListaJuegos.setJuegoId(videojuego);
                    listaJuegosCollectionNewListaJuegos = em.merge(listaJuegosCollectionNewListaJuegos);
                    if (oldJuegoIdOfListaJuegosCollectionNewListaJuegos != null && !oldJuegoIdOfListaJuegosCollectionNewListaJuegos.equals(videojuego)) {
                        oldJuegoIdOfListaJuegosCollectionNewListaJuegos.getListaJuegosCollection().remove(listaJuegosCollectionNewListaJuegos);
                        oldJuegoIdOfListaJuegosCollectionNewListaJuegos = em.merge(oldJuegoIdOfListaJuegosCollectionNewListaJuegos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = videojuego.getJuegoId();
                if (findVideojuego(id) == null) {
                    throw new NonexistentEntityException("The videojuego with id " + id + " no longer exists.");
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
            Videojuego videojuego;
            try {
                videojuego = em.getReference(Videojuego.class, id);
                videojuego.getJuegoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The videojuego with id " + id + " no longer exists.", enfe);
            }
            Collection<Genero> generoCollection = videojuego.getGeneroCollection();
            for (Genero generoCollectionGenero : generoCollection) {
                generoCollectionGenero.getVideojuegoCollection().remove(videojuego);
                generoCollectionGenero = em.merge(generoCollectionGenero);
            }
            Collection<Resena> resenaCollection = videojuego.getResenaCollection();
            for (Resena resenaCollectionResena : resenaCollection) {
                resenaCollectionResena.setJuegoId(null);
                resenaCollectionResena = em.merge(resenaCollectionResena);
            }
            Collection<Valoracion> valoracionCollection = videojuego.getValoracionCollection();
            for (Valoracion valoracionCollectionValoracion : valoracionCollection) {
                valoracionCollectionValoracion.setJuegoId(null);
                valoracionCollectionValoracion = em.merge(valoracionCollectionValoracion);
            }
            Collection<ListaJuegos> listaJuegosCollection = videojuego.getListaJuegosCollection();
            for (ListaJuegos listaJuegosCollectionListaJuegos : listaJuegosCollection) {
                listaJuegosCollectionListaJuegos.setJuegoId(null);
                listaJuegosCollectionListaJuegos = em.merge(listaJuegosCollectionListaJuegos);
            }
            em.remove(videojuego);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Videojuego> findVideojuegoEntities() {
        return findVideojuegoEntities(true, -1, -1);
    }

    public List<Videojuego> findVideojuegoEntities(int maxResults, int firstResult) {
        return findVideojuegoEntities(false, maxResults, firstResult);
    }

    private List<Videojuego> findVideojuegoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Videojuego.class));
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

    public Videojuego findVideojuego(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Videojuego.class, id);
        } finally {
            em.close();
        }
    }
    
    public Videojuego findVideojuegoByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery<Videojuego> query = em.createQuery("SELECT v FROM Videojuego v where v.nombre = :nombre", Videojuego.class);
            query.setParameter("nombre", nombre);
            // Ejecutar la consulta y devolver los resultados
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }



    public int getVideojuegoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Videojuego> rt = cq.from(Videojuego.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
