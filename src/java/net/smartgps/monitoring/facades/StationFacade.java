/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.smartgps.monitoring.entitys.Station;

/**
 *
 * @author Panker
 */
@Stateless
public class StationFacade extends AbstractFacade<Station> {

    @PersistenceContext(unitName = "net.smartgps.monitoring_1.0.2PU")
    private EntityManager em;

    public StationFacade() {
        super(Station.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
