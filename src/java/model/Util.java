/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.FestivalController;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nikola
 */
@ManagedBean(name="util")
@ApplicationScoped
public class Util {
    //private static String loggedUsername = "";
    
    public Util(){
    }

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    public String setNavbar(){
        if(userLogged()){
            return "includes/navbarUser.xhtml";
        } else {
            return "includes/navbar.xhtml";
        }
    }
    
    public String getUsername(){
        return getSession().getAttribute("username").toString();
    }
    
    public static Integer getIdUser(){
        return Integer.parseInt(getSession().getAttribute("idUser").toString());
    }
    
    public static boolean userLogged(){
        if (getSession()==null || (getSession().getAttribute("username") == null)) {
            return false;
        } else {
            return true;
        }
    }
    
    public static void redirect(String url){
        ConfigurableNavigationHandler configurableNavigationHandler =
             (ConfigurableNavigationHandler)FacesContext.
               getCurrentInstance().getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url+"?faces-redirect=true");
    }
    
    
}
