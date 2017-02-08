/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSON.Fest;
import JSON.PerformersList;
import JSON.SocialNetwork;
import com.google.gson.Gson;
import model.Util;
import static model.Util.*;
import entity.Attendencie;
import entity.Festival;
import entity.Link;
import entity.Reservation;
import entity.User;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Festival_dao;
import model.NewHibernateUtil;
import org.hibernate.Session;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Nikola
 */
@SessionScoped
@ManagedBean(name = "festCtrl")
public class FestivalController implements Serializable{
    private List<Object[]> lst = new ArrayList<>();
    private List<Object[]> selectedLst = new ArrayList<>();
    private List<Attendencie> lstPerformers = new ArrayList<>();
    private List<Object[]> lstTopRated = new ArrayList<>();
    private String keyWord;
    private Date keyWordDate;
    private Festival selectedFestival;
    private Double selectedFestivalRating;
    private Integer selectedFestivalId;
    private List<Object[]> selectedFestivalReviews = new ArrayList<>();
    private Integer dayNumber;
    private Integer ticketCount;
    private Integer ticketsSold;
    private Integer ticketsPerReservation;
    private Integer ticketsPerDay;
    private Integer diffDays;
    private List<byte[]> byteImages;
    private List<StreamedContent> images;
    private StreamedContent image;
    
    
    public FestivalController() {
        Festival_dao dao = new Festival_dao();
        lstTopRated = dao.topRated();
    } 
    
    public void renderImages(Integer id){
        images = new ArrayList<>();
        Festival_dao dao = new Festival_dao();
        byteImages = dao.getImages(id);
        Long i = new Long(0);
        for (byte[] b:byteImages){
            StreamedContent img = new DefaultStreamedContent(new ByteArrayInputStream(b));
            images.add(img);
            image = img;
        }
    }    
    
    public String search(){
        Festival_dao dao = new Festival_dao();
        if (getSession().getAttribute("username") == null) {
            this.lst = dao.search(keyWord);
        } else {
            this.lst = dao.search1(keyWord);
        }
        return "search";
    }
    
    public String searchDate(){
        Festival_dao dao = new Festival_dao();
        this.lst = dao.searchDate(keyWordDate);
        return "search.xhtml?faces-redirect=true";
    }   
    
    public void onRowSelect(SelectEvent event) {
        //      TESTIRANJA RADI!!!
        if(!userLogged()) return; 
        Object[] o = (Object[]) event.getObject();
        selectedFestivalId = (Integer)o[0];
        Festival_dao dao = new Festival_dao();
        this.selectedLst = dao.findFestivalById(selectedFestivalId);
        this.lstPerformers = dao.findPerformersByFestivalId(selectedFestivalId);
       
        Date startDate = (Date)selectedLst.get(0)[2];
        Date endDate = (Date)selectedLst.get(0)[3];
        ticketsPerReservation = (Integer) selectedLst.get(0)[8];
        ticketsPerDay = (Integer) selectedLst.get(0)[9];
        ticketsSold = (Integer) selectedLst.get(0)[7];
        long diffMilliseconds = endDate.getTime() -  startDate.getTime();
        
        diffDays = (int) (long) TimeUnit.DAYS.convert(diffMilliseconds, TimeUnit.MILLISECONDS)+1;
        
        
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        String hqlUpdate = "update Festival f set f.viewCount = f.viewCount + 1 where f.idFestival = :selectedFestivalId";
        session.createQuery( hqlUpdate ).setLong( "selectedFestivalId", selectedFestivalId ).executeUpdate();
        session.getTransaction().commit();
        
        String selectedFestivalName = (String) o[1];
        selectedFestivalRating = dao.getRating(selectedFestivalName);
        selectedFestivalReviews = dao.getReviews(selectedFestivalName);
        
        //renderImages(selectedFestivalId);
        
        Util.redirect("festival");
    }
    
    private boolean validate(Integer dayNumber, Integer ticketCount){
        Festival_dao dao = new Festival_dao();
        Integer dbIdUser = Util.getIdUser();
        if (!dao.validateExpired(selectedFestivalId, dbIdUser)){
            return false;
        }
        if (dayNumber == 0) {
            for (Integer i = 1; i <= diffDays; i++) {
                if(!dao.validateByDay(i, selectedFestivalId, ticketCount, ticketsPerDay)) {
                    return false;
                }                
            }
        } else {
            if(!dao.validateByDay(dayNumber, selectedFestivalId, ticketCount, ticketsPerDay)) {
                return false;
            } 
        }
        
        return true;
    }
    
    public String submitReservation(){
        if (validate(dayNumber, ticketCount)) {
            Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Reservation r = new Reservation();
            r.setFestival((Festival)session.get(Festival.class, selectedFestivalId));
            r.setUser((User)session.get(User.class, Util.getIdUser()));
            if (dayNumber == 0){
                r.setTicketCount(ticketCount*diffDays);
            } else {
                r.setTicketCount(ticketCount);
            }
            r.setType(dayNumber);
            r.setReservationTime(new Date());
            r.setBought(0);
            r.setExpired(0);
            r.setReviewed(0);
            session.persist(r);
            session.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successful Reservation"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Not Able to Make Reservation"));
        }
        return "festival";
    }
    
    

    public List<byte[]> getByteImages() {
        return byteImages;
    }
    
    public StreamedContent getImage() {
        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }
    
    public List<StreamedContent> getImages() {
        return images;
    }

    public void setImages(List<StreamedContent> images) {
        this.images = images;
    }

    public List<Object[]> getLstTopRated() {
        return lstTopRated;
    }

    public void setLstTopRated(List<Object[]> lstTopRated) {
        this.lstTopRated = lstTopRated;
    }
    
    public List<Object[]> getSelectedLst() {
        return selectedLst;
    }

    public void setSelectedLst(List<Object[]> selectedLst) {
        this.selectedLst = selectedLst;
    }
    
    public List<Object[]> getLst() {
        return lst;
    }

    public void setLst(List<Object[]> lst) {
        this.lst = lst;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Date getKeyWordDate() {
        return keyWordDate;
    }

    public void setKeyWordDate(Date keyWordDate) {
        this.keyWordDate = keyWordDate;
    }

    public Festival getSelectedFestival() {
        return selectedFestival;
    }

    public void setSelectedFestival(Festival selectedFestival) {
        this.selectedFestival = selectedFestival;
    }

    public Integer getSelectedFestivalId() {
        return selectedFestivalId;
    }

    public void setSelectedFestivalId(Integer selectedFestivalId) {
        this.selectedFestivalId = selectedFestivalId;
    }

    public List<Attendencie> getLstPerformers() {
        return lstPerformers;
    }

    public void setLstPerformers(List<Attendencie> lstPerformers) {
        this.lstPerformers = lstPerformers;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Integer getDiffDays() {
        return diffDays;
    }

    public Integer getTicketsPerReservation() {
        return ticketsPerReservation;
    }

    public Integer getTicketsPerDay() {
        return ticketsPerDay;
    }

    public Integer getTicketsSold() {
        return ticketsSold;
    }

    public Double getSelectedFestivalRating() {
        return selectedFestivalRating;
    }    

    public List<Object[]> getSelectedFestivalReviews() {
        return selectedFestivalReviews;
    }
}
