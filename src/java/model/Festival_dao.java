/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Attendencie;
import entity.Festival;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Session;
import java.util.ArrayList;
import org.hibernate.Query;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Nikola
 */
@SessionScoped
public class Festival_dao {
    public List<Object[]> search(String keyWord){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createQuery("select f.idFestival, f.name, f.place, f.startDate, f.endDate "
                    + "from Festival f, Attendencie a "
                    + "where (f.name like :keyWord) or (f.place like :keyWord) or (f.idFestival = a.id.idFestival and a.id.performer like :keyWord) "
                    + "group by f.idFestival");
            q.setParameter("keyWord", "%" + keyWord + "%");
            
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
        
    }

    public List<Object[]> searchDate(java.util.Date keyWord) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        Timestamp sqlDate = new Timestamp(keyWord.getTime());
        try{
            s.beginTransaction();
            Query q = s.createQuery("select f.idFestival, f.name, f.place, f.startDate, f.endDate " +
                                    "from Festival f " +
                                    "where '" + sqlDate + "' between f.startDate and f.endDate ");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    // LOGGED IN USERS

    public List<Object[]> search1(String keyWord) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createQuery("select f.idFestival, f.name, f.place, f.startDate, f.endDate "
                    + "from Festival f, Attendencie a "
                    + "where ((f.name like :keyWord) or (f.place like :keyWord) or "
                    + "(f.idFestival = a.id.idFestival and a.id.performer like :keyWord)) and "
                    + "(f.endDate > CURDATE()) "
                    + "group by f.name");
            q.setParameter("keyWord", "%" + keyWord + "%");
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
            
            Query q = s.createQuery("select f.name, f.place, f.startDate, f.endDate, " +
                                   "f.rating, f.numRatings, f.description, f.ticketsSold, f.ticketsPerReservation, f.ticketsPerDay " +
                                    "from Festival f " +
                                    "where '" + id + "' = f.idFestival ");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public List<Attendencie> findPerformersByFestivalId(Integer id){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Attendencie> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createQuery("select a.id.performer, a.startTime, a.endTime " +
                                    "from Attendencie a " +
                                    "where '" + id + "' = a.id.idFestival ");   
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public String getSocialLink(Integer selectedFestivalId, String network){
        try{
            Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
     
            s.beginTransaction();

            Query q = s.createQuery("select m.link "
                    + "from Link m "
                    + "where m.id.idFestival = '" + selectedFestivalId + "' and m.id.name = '" + network + "'");

            List lst = q.list();
            s.getTransaction().commit();
            return (String)lst.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        
    }

    public List<Object[]> topRated() {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select f.name, f.place, f.startDate, f.endDate "
                    + "from Festival f "
                    + "order by f.rating limit 5");            
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
        return lst;
    }

    public List<byte[]> getImages(Integer idFestival) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<byte[]> lst = new ArrayList<>();
        
        s.beginTransaction();
        Query q = s.createQuery("select file from Media where idFestival = :idFestival");
        q.setParameter("idFestival", idFestival);
        lst = q.list();
        s.getTransaction().commit();
        return lst;
    }

    public boolean validateByDay(Integer dayNumber, Integer selectedFestivalId, Integer ticketCount, Integer ticketsPerDay) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Long> lst = new ArrayList<>();
        s.beginTransaction();
        Query q = s.createQuery("select sum(ticketCount) " +
                                "from Reservation " +
                                "where expired = 0 " +
                                "and idFestival = :idFestival " +
                                "and (type = :dayNumber or type = 0)");
        q.setParameter("idFestival", selectedFestivalId);
        q.setParameter("dayNumber", dayNumber);
        lst = q.list();
        s.getTransaction().commit();
        Long i;
        
        if (lst.get(0) == null) {
            i = (long) 0;
        } else {
            i = lst.get(0);
        }
        
        if ((i + ticketCount) > ticketsPerDay) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean validateExpired(Integer selectedFestivalId, Integer idUser){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Long> lst = new ArrayList<>();
        s.beginTransaction();
        Query q = s.createQuery("select sum(expired) " +
                                "from Reservation " +
                                "where idUser = :idUser " +
                                "and idFestival = :idFestival ");
        q.setParameter("idFestival", selectedFestivalId);
        q.setParameter("idUser", idUser);
        lst = q.list();
        s.getTransaction().commit();
        if (lst.get(0) == null) {
            return true;
        }
        if (lst.get(0) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public Double getRating(String selectedFestivalName) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Double> lst = new ArrayList<>();
        s.beginTransaction();
        Query q = s.createQuery("select sum(rating*numRatings)/sum(numRatings)\n" +
                                "from Festival\n" +
                                "where name = :festivalName");
        q.setParameter("festivalName", selectedFestivalName);
        lst = q.list();
        
        s.getTransaction().commit();
        return lst.get(0);
    }

    public List<Object[]> getReviews(String selectedFestivalName) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> lst = new ArrayList<>();
        s.beginTransaction();
        Query q = s.createQuery("select comment, date, username\n" +
                                "from Review\n" +
                                "where festivalName = :festivalName");
        q.setParameter("festivalName", selectedFestivalName);
        lst = q.list();
        s.getTransaction().commit();
        return lst;
    }
}
 

