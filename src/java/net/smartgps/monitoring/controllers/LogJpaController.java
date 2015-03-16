/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.controllers;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import net.smartgps.monitoring.controllers.exceptions.NonexistentEntityException;
import net.smartgps.monitoring.controllers.exceptions.RollbackFailureException;
import net.smartgps.monitoring.entitys.Log;
import net.smartgps.monitoring.entitys.User;

/**
 *
 * @author Panker
 */
@ManagedBean(name="logController")
@SessionScoped
public class LogJpaController implements Serializable {

    public LogJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Log log) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User user = log.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                log.setUser(user);
            }
            em.persist(log);
            if (user != null) {
                Log oldLogOfUser = user.getLog();
                if (oldLogOfUser != null) {
                    oldLogOfUser.setUser(null);
                    oldLogOfUser = em.merge(oldLogOfUser);
                }
                user.setLog(log);
                user = em.merge(user);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Log log) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Log persistentLog = em.find(Log.class, log.getId());
            User userOld = persistentLog.getUser();
            User userNew = log.getUser();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                log.setUser(userNew);
            }
            log = em.merge(log);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.setLog(null);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                Log oldLogOfUser = userNew.getLog();
                if (oldLogOfUser != null) {
                    oldLogOfUser.setUser(null);
                    oldLogOfUser = em.merge(oldLogOfUser);
                }
                userNew.setLog(log);
                userNew = em.merge(userNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = log.getId();
                if (findLog(id) == null) {
                    throw new NonexistentEntityException("The log with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Log log;
            try {
                log = em.getReference(Log.class, id);
                log.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The log with id " + id + " no longer exists.", enfe);
            }
            User user = log.getUser();
            if (user != null) {
                user.setLog(null);
                user = em.merge(user);
            }
            em.remove(log);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Log> findLogEntities() {
        return findLogEntities(true, -1, -1);
    }

    public List<Log> findLogEntities(int maxResults, int firstResult) {
        return findLogEntities(false, maxResults, firstResult);
    }

    private List<Log> findLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Log.class));
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

    public Log findLog(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Log.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Log> rt = cq.from(Log.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
