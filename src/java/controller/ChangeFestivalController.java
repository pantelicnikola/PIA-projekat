/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Festival;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.ChangeFestival_dao;
import model.NewHibernateUtil;
import model.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nikola
 */
@ManagedBean (name="upFest")
@SessionScoped
public class ChangeFestivalController {
    private String name;
    private String place;
    private Date startDate;
    private Date endDate;
    private String description;
    private Integer singleDay;
    private Integer allDays;
    private List<Object[]> lst = new ArrayList<>();
    private List<Object[]> selectedLst = new ArrayList<>();
    private Object selectedFestival1;
    private Festival selectedFestival;
    private Integer selectedFestivalId;

    public ChangeFestivalController() {
        ChangeFestival_dao dao = new ChangeFestival_dao();
        lst = dao.findAll();
    }
    
    
    public void onRowSelect(SelectEvent event) {
        Object[] o = (Object[]) event.getObject();
        selectedFestivalId = (Integer)o[0];
        selectedFestival = new Festival();
        ChangeFestival_dao dao = new ChangeFestival_dao();
        selectedLst = dao.findFestivalById(selectedFestivalId);
        
        selectedFestival.setIdFestival(selectedFestivalId);
        selectedFestival.setName((String) selectedLst.get(0)[0]);
        selectedFestival.setPlace((String) selectedLst.get(0)[1]);
        selectedFestival.setStartDate((Date) selectedLst.get(0)[2]);
        selectedFestival.setEndDate((Date) selectedLst.get(0)[3]);
        selectedFestival.setDescription((String) selectedLst.get(0)[4]);
        selectedFestival.setSingleDay((Integer) selectedLst.get(0)[5]);
        selectedFestival.setAllDays((Integer) selectedLst.get(0)[6]);
        Util.redirect("changeFestival");
        
        
    }
    
    public void submitChanges(){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            
            Query query = s.createQuery("update Festival set name = :name" +
                                        ", place = :place" +
                                        ", startDate = :startDate" +
                                        ", endDate = :endDate" +
                                        ", description = :description" +
                                        ", singleDay = :singleDay" +
                                        ", allDays = :allDays" +
                                        " where idFestival = :id");
            query.setParameter("id", selectedFestival.getIdFestival());
            query.setParameter("name", selectedFestival.getName());
            query.setParameter("place", selectedFestival.getPlace());
            query.setParameter("startDate", selectedFestival.getStartDate());
            query.setParameter("endDate", selectedFestival.getEndDate());
            query.setParameter("description", selectedFestival.getDescription());
            query.setParameter("singleDay", selectedFestival.getSingleDay());
            query.setParameter("allDays", selectedFestival.getAllDays());
            
            int result = query.executeUpdate();
            s.getTransaction().commit();
            
            ChangeFestival_dao dao = new ChangeFestival_dao();
            dao.informUsers(selectedFestival.getIdFestival());
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }

    public List<Object[]> getSelectedLst() {
        return selectedLst;
    }

    public void setSelectedLst(List<Object[]> selecedLst) {
        this.selectedLst = selecedLst;
    }
    
    public Festival getSelectedFestival() {
        return selectedFestival;
    }
    
    public void setSelectedFestival(Festival selectedFestival) {
        this.selectedFestival = selectedFestival;
    }

    public void setSelectedFestival1(Object selectedFestival1) {
        this.selectedFestival1 = selectedFestival1;
    }
    
    public Object getSelectedFestival1() {
        return selectedFestival1;
    }
    
    public List<Object[]> getLst() {
        return lst;
    }

    public void setLst(List<Object[]> lst) {
        this.lst = lst;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSingleDay() {
        return singleDay;
    }

    public Integer getAllDays() {
        return allDays;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSingleDay(Integer singleDay) {
        this.singleDay = singleDay;
    }

    public void setAllDays(Integer allDays) {
        this.allDays = allDays;
    }
    
    
}
