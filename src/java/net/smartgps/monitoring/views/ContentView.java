/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.views;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Panker
 */
@ManagedBean
public class ContentView {
    
    private final String monitoring="/resources/templates/monitoring.xhtml";
    private final String clientsDB="/resources/templates/clientsDB.xhtml";
    private final String orders="/resources/templates/orders.xhtml";
    private String nowSelected;
    
    public String getNowSelected() {
        if(nowSelected==null)nowSelected=monitoring;
        return nowSelected;
    }

    public void setNowSelected(String nowSelected) {
        this.nowSelected = nowSelected;
    }
    
    public void setContentAsMonitoring(){
        nowSelected=monitoring;
    }
    public void setContentAsClientDB(){
        nowSelected=clientsDB;
    }
    
    public void setContentAsOrders(){
        nowSelected=orders;
    }
    
    
}