/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.views;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker
 */
@ManagedBean(name="mess")
@RequestScoped
public class GrowlMessages {
    private String message;
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
     
    public void wellcomeMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Добро пожаловать") );
    }
    public void logout(){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("До свидания!"));
    }
}
