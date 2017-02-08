/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Festival;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nikola
 */
public class Admin_dao {

    public List<Festival> findMostVisited() {
        
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select f.name, f.place, f.startDate, f.endDate "
                    + "from Festival f "
                    + "order by f.viewCount desc "
                    + "limit 5");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    
    }
    
    public List<Festival> findMostPurchased() {
        
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select f.name, f.place, f.startDate, f.endDate "
                    + "from Festival f "
                    + "order by f.ticketsSold desc "
                    + "limit 5");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    
    }

    public List<Festival> findRequests() {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select u.username, u.email, u.name, u.surname "
                    + "from User u "
                    + "where u.approved = 0");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public void accept(String username){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createQuery("update User set approved = 1 where username = :username");
            query.setParameter("username", username);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
    
    public void reject(String username){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createQuery("delete User where username = :username" );
            query.setParameter("username", username);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }

    public List<User> findLastVisited() {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<User> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select username, email, name, surname, phone " +
                                        "from User " +
                                        "order by lastLogin desc " +
                                        "limit 10");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public List<Object[]> toCancel() {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createQuery("select f.name, f.place, f.startDate, f.endDate, f.idFestival "
                    + "from Festival f "
                    + "where f.endDate > CURDATE()");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public void deleteFestival(Integer idFestival) {
        
        informUsers(idFestival);
        
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        Query q = s.createQuery("delete Festival where idFestival = :idFestival ");
        q.setParameter("idFestival", idFestival);
        int result = q.executeUpdate();
        s.getTransaction().commit();
    }
    
    public void informUsers(Integer idFestival) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Integer> lst = new ArrayList<>();
        
        s.beginTransaction();
        Query q = s.createSQLQuery("select idUser from Reservation where idFestival = :idFestival and expired = 0");
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
            q.setParameter("message", "One of the booked Festivals was canceled");
            int result = q.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
    
}
