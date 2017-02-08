/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Festival;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nikola
 */
public class ChangeFestival_dao {
    public List<Object[]> findAll(){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createQuery("select f.idFestival, f.name, f.place, f.startDate, f.endDate "
                    + "from Festival f");            
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
        
    }
    
    public List<Object[]> findFestivalById(Integer id){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            
            Query q = s.createQuery("select f.name, f.place, f.startDate, f.endDate, f.description, f.singleDay, f.allDays " +
                                    "from Festival f " +
                                    "where '" + id + "' = f.idFestival ");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public void informUsers(Integer idFestival) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Integer> lst = new ArrayList<>();
        
        s.beginTransaction();
        Query q = s.createSQLQuery("select idUser from Reservation where idFestival = :idFestival");
        q.setParameter("idFestival", idFestival);
        lst = q.list();
        s.getTransaction().commit();
        
        for (Integer u: lst){
            setAdminMessage(u);
        }
        
    }

    private void setAdminMessage(Integer idUser) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query q = s.createQuery("update User set adminMessage = :message where idUser = :idUser ");
            q.setParameter("idUser", idUser);
            q.setParameter("message", "One of the booked Festivals was changed");
            int result = q.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
    
    
}
