/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartsys.monitoring.views;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Panker
 */
@ManagedBean
public class ContentView {
    
    private final String monitoring="monitoring.xhtml";
    private final String clientsDB="clientsdb.xhtml";
    private final String orders="orders.xhtml";
    private String nowSelected;
    
    public String getNowSelected() {
        if(nowSelected==null)nowSelected=monitoring;
        return nowSelected;
    }

    public void setNowSelected(String nowSelected) {
        this.nowSelected = nowSelected;
    }
    
    
}
