/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Festival;
import entity.User;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Admin_dao;

/**
 *
 * @author Nikola
 */
@SessionScoped
@ManagedBean(name = "adminCtrl")
public class AdminController {
    private List<Festival> lstMostVisited;
    private List<Festival> lstMostPurchased;
    private List<Festival> lstRequests;
    private List<User> lstLastVisited;
    private List<Object[]> lstToCancel;
    private Object selectedUser;
    private Object cancelSelection;
    
    public AdminController() {
        Admin_dao dao = new Admin_dao();
        lstMostVisited = dao.findMostVisited();
        lstMostPurchased = dao.findMostPurchased();
        lstRequests = dao.findRequests();
        lstLastVisited = dao.findLastVisited();
        lstToCancel = dao.toCancel();
    }
    
    public String accept(){
        Admin_dao dao = new Admin_dao();
        String username = (String) ((Object[])selectedUser)[0];
        dao.accept(username);
        lstRequests = dao.findRequests();
        return "userRequest?facesRedirect=true";
    }
    
    public String reject(){
        Admin_dao dao = new Admin_dao();
        String username = (String) ((Object[])selectedUser)[0];
        dao.reject(username);
        lstRequests = dao.findRequests();
        return "userRequest?facesRedirect=true";
    }
    
    public String cancel(){
        Admin_dao dao = new Admin_dao();
        Integer idFestival = (Integer) ((Object[])cancelSelection)[4];
        dao.deleteFestival(idFestival);
        lstToCancel = dao.toCancel();
        return "cancelFestival?facesRedirect=true";
    }

    public Object getCancelSelection() {
        return cancelSelection;
    }

    public void setCancelSelection(Object cancelSelection) {
        this.cancelSelection = cancelSelection;
    }
    
    public List<Object[]> getLstToCancel() {
        return lstToCancel;
    }

    public void setLstToCancel(List<Object[]> lstToCancel) {
        this.lstToCancel = lstToCancel;
    }

    public List<User> getLstLastVisited() {
        return lstLastVisited;
    }

    public void setLstLastVisited(List<User> lstLastVisited) {
        this.lstLastVisited = lstLastVisited;
    }
    
    public List<Festival> getLstRequests() {
        return lstRequests;
    }

    public void setLstRequests(List<Festival> lstRequests) {
        this.lstRequests = lstRequests;
    }
    
    public List<Festival> getLstMostVisited() {
        return lstMostVisited;
    }

    public List<Festival> getLstMostPurchased() {
        return lstMostPurchased;
    }

    public void setLstMostVisited(List<Festival> lstMostVisited) {
        this.lstMostVisited = lstMostVisited;
    }

    public void setLstMostPurchased(List<Festival> lstMostPurchased) {
        this.lstMostPurchased = lstMostPurchased;
    }

    public Object getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Object selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    
    
    
    
}
