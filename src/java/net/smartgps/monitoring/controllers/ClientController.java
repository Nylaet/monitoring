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
import javax.faces.context.FacesContext;
import net.smartgps.monitoring.entitys.Client;

/**
 *
 * @author Panker
 */
@ManagedBean(name="client")
public class ClientController implements Serializable{
    private Client selected;
    @EJB
    private net.smartgps.monitoring.facades.ClientFacade clientFacade;
    private List<Client> clients;
    private Client newClient;
    
    @PostConstruct
    public void init(){
        clients=clientFacade.findAll();
    }
    
    public void create(){
        clientFacade.create(newClient);
        addMessage("Клиент успешно добавлен");
    }

    public void edit(){
        clientFacade.edit(selected);
        addMessage("Клиент успешно изменен");
    }
    
    public void delete(){
        clientFacade.remove(selected);
        addMessage("Клиент удален");
    }
    public Client getSelected() {
        if(selected==null)selected=new Client();
        return selected;
    }

    public void setSelected(Client selected) {
        this.selected = selected;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Client getNewClient() {
        newClient=new Client();
        return newClient;
    }
    
    private void addMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
            
            
}
