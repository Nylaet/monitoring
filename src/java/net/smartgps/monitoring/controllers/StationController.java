/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.controllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import net.smartgps.monitoring.entitys.Station;

/**
 * 
 * @author Panker
 */
@ManagedBean(name="station")
@SessionScoped
public class StationController implements Serializable{
    private Station selected;
    @EJB
    private net.smartgps.monitoring.facades.StationFacade stationFacade;
    private List<Station> stations;
    private Station newStation;
    
    @PostConstruct
    public void init(){
        stations=stationFacade.findAll();
    }

    public Station getSelected() {
        return selected;
    }

    public void setSelected(Station selected) {
        this.selected = selected;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Station getNewStation() {
        return newStation;
    }

    public void create(){
        stationFacade.create(newStation);
        addMessage("Новая станция успешно добавлена");
    }
    
    public void edit(){
        stationFacade.edit(newStation);
        addMessage("Данные станции успешно обновлены");
    }
    
    public void delete(){
        stationFacade.remove(selected);
        addMessage("Данные о станции удалены");
    }
    
    private void addMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

}
