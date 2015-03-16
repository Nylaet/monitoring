/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smartgps.monitoring.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Panker
 */
@Entity
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String personalAccount;
    private int accountMoney;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date switchingOn;
    
    @ElementCollection
    @CollectionTable(name = "DocumentScans",
            joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "_DokumentScans")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private List<byte[]> scans;
    
    @OneToOne
    @JoinColumn(name = "StationId", nullable = false)
    private Station station;
    
    @OneToOne
    @JoinColumn(name="Tarif",nullable = false)
    private Tarif tarif;
    
    @OneToOne
    @JoinColumn(name="PaymentHistory")
    private PaymentHistory paymentHistory;
   
    @OneToOne
    private User whoAdded;
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    public Date getSwitchingOn() {
        return switchingOn;
    }

    public void setSwitchingOn(Date switchingOn) {
        this.switchingOn = switchingOn;
    }

    public List<byte[]> getScans() {
        return scans;
    }

    public void setScans(byte[] scan) {

        this.scans.add(scan);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public int getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(int accountMoney) {
        this.accountMoney = accountMoney;
    }

    public PaymentHistory getPaymentHistory() {
        return paymentHistory;
    }

    public User getWhoAdded() {
        return whoAdded;
    }

    public void setWhoAdded(User whoAdded) {
        this.whoAdded = whoAdded;
    }

    
    public void setPaymentHistory(PaymentHistory paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.smartgps.monitoring.entitys.Client[ id=" + id + " ]";
    }

}
