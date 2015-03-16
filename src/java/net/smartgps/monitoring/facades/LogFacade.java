/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.smartgps.monitoring.entitys.Log;

/**
 *
 * @author Panker
 */
@Stateless
public class LogFacade extends AbstractFacade<Log> {
    @PersistenceContext(unitName = "net.smartgps.monitoring_1.0.2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogFacade() {
        super(Log.class);
    }
    
}
