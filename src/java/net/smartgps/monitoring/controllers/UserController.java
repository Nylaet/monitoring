/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import net.smartgps.monitoring.entitys.User;

/**
 * Управляемый бин для работы с User.Entity
 *
 * @author Panker
 *
 */
@ManagedBean(name = "user")
@SessionScoped
public class UserController implements Serializable{

    private User current;
    private boolean entryUser = false;
    @EJB
    private net.smartgps.monitoring.facades.UserFacade ejbFacades;
    private List<User> users;
    private User newUser;
    FacesContext fc = FacesContext.getCurrentInstance();
    private int failCount = 0;
    
    @PostConstruct
    public void init() {
        users = ejbFacades.findAll();
        if (users.isEmpty()) {
            User admin = new User();
            admin.setAdmin(true);
            admin.setEmail("panker@mksat.net");
            admin.setLastVisit(new Date());
            admin.setPassword("156456851");
            admin.setUntc(false);
            admin.setUsername("panker");
            ejbFacades.create(admin);
        }
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            entryUser = false;
        }
        return current;
    }

    public boolean isEntryUser() {
        return entryUser;
    }

    public void setEntryUser(boolean entryUser) {
        this.entryUser = entryUser;
    }

    public void setSelected(User current) {
        this.current = current;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public void create() {
        ejbFacades.create(newUser);
        addMessage(newUser.getUsername() + " успешно добавлен");
    }

    public void edit() {
        ejbFacades.edit(newUser);
        addMessage(newUser.getUsername() + " успешно изменен");
    }

    public void destroy() {
        ejbFacades.remove(newUser);
        addMessage(newUser.getUsername() + " успешно удален");
    }

    public String checkUser() {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(current.getUsername())&&failCount<3) {
                if (user.getPassword().equals(current.getPassword())) {
                    current = user;
                    entryUser = true;
                    current.setLastVisit(new Date());
                    ejbFacades.edit(current);
                    addMessage("С возвращением, " + current.getUsername());
                    return ("index.xhtml");
                }
            }
        }
        failCount++;
        if (failCount < 3) {
            addMessage("Не верные логин или пароль");
            return "login.xhtml";
        } else {
            addMessage("Свяжитесь с администратором узла. Ваша сессия заблокирована!");
            return "login.xhtml";
        }
    }

    public String logout() {
        fc.getExternalContext().invalidateSession();
        addMessage("До свидания!");
        entryUser = false;
        current = null;
        return "login.xhml";
    }

    public List<User> getUsers() {
        return users;
    }

    private void addMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
}
