/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Festival;
import entity.Media;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nikola
 */
public class User_dao {
    public List<Festival> fiveSoonest() {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            //Query q = s.createQuery("select f.name, f.place, f.startDate, f.endDate from Festival f");
            Query q = s.createSQLQuery("select f.name, f.place, f.startDate, f.endDate\n" +
                                    "from Festival f\n" +
                                    "where (CURDATE() between f.startDate and f.endDate) or (f.startDate >= CURDATE())\n" +
                                    "order by f.startDate\n" +
                                    "limit 5");
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return lst;
    }
    
    public void updateLastLogin(Integer id){
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        String hqlUpdate = "update User u " +
                         "set u.lastLogin = NOW() " +
                         "where u.idUser= :id";
        session.createQuery( hqlUpdate ).setLong("id", id).executeUpdate();
        session.getTransaction().commit();
    }

    public void setNewPassword(String dbUsername, String newPassword) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createQuery("update User set password = :password where username = :username");
            query.setParameter("username", dbUsername);
            query.setParameter("password", newPassword);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }

    public void checkReservations() {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createSQLQuery("update Reservation r, User u set r.expired = 1, u.lives = u.lives - 1 " +
                                        "where r.bought = 0 " +
                                        "and r.expired = 0 " +
                                        "and NOW() > DATE_ADD(r.reservationTime,INTERVAL 2 DAY) " +
                                        "and r.idUser = u.idUser ");
            
            int result = query.executeUpdate();
            s.getTransaction().commit();
            blockUsers();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
    
    public void blockUsers(){
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createSQLQuery("update User u set u.blocked = 1 " +
                                        "where u.lives = 0 ");
            
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }

    public List<Object> getPendingReservations(Integer id) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select f.name, r.type, r.ticketCount, r.idReservation, r.idFestival " +
                                        "from Festival f, Reservation r " +
                                        "where f.idFestival = r.idFestival " +
                                        "and r.idUser = :id " +
                                        "and r.expired = 0 " +
                                        "and r.bought = 0");
            q.setParameter("id", id);
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return lst;
    }

    public List<Object> getBoughtReservations(Integer id) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select f.name, r.type, r.ticketCount, r.idReservation, r.idFestival " +
                                        "from Festival f, Reservation r " +
                                        "where f.idFestival = r.idFestival " +
                                        "and r.idUser = :id " +
                                        "and r.expired = 0 " +
                                        "and r.bought = 1");
            q.setParameter("id", id);
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return lst;
    }

    public List<Object> getExpiredReservations(Integer id) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object> lst = new ArrayList<>();
        try{
            s.beginTransaction();
            Query q = s.createSQLQuery("select f.name, r.type, r.ticketCount, r.idReservation, r.idFestival " +
                                        "from Festival f, Reservation r " +
                                        "where f.idFestival = r.idFestival " +
                                        "and r.idUser = :id " +
                                        "and r.expired = 1 " +
                                        "and r.bought = 0");
            q.setParameter("id", id);
            lst = q.list();
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return lst;
    }

    public void buy(Object selectedResevation) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Integer idReservation = (Integer) ((Object[])selectedResevation)[3];
            Integer idFestival = (Integer) ((Object[])selectedResevation)[4];
            Query query = s.createSQLQuery("update Reservation r, Festival f " +
                                        "set r.bought = 1, f.ticketsSold = f.ticketsSold + r.ticketCount " +
                                        "where r.idReservation = :idReservation and f.idFestival = :idFestival ");
            
            query.setParameter("idReservation", idReservation);
            query.setParameter("idFestival", idFestival );
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }

    public void cancel(Object selectedResevation) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Integer idReservation = (Integer) ((Object[])selectedResevation)[3];
            Query query = s.createQuery("delete Reservation where idReservation = :idReservation");
            query.setParameter("idReservation", idReservation);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
    
    public List<Object> toReview(Integer idUser) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<Object> lst = new ArrayList<>();
        
        s.beginTransaction();
        Query q = s.createSQLQuery("select f.name, f.startDate, f.endDate, r.idReservation, f.idFestival\n" +
                                "from Reservation r, Festival f\n" +
                                "where f.endDate < CURDATE()\n" +
                                "and r.idFestival = f.idFestival\n" +
                                "and r.idUser = :idUser\n" +
                                "and r.reviewed = 0\n" +
                                "and r.bought = 1");
        q.setParameter("idUser", idUser);
        lst = q.list();
        s.getTransaction().commit();
        return lst;
    }

    public void updateReservation(Integer idReservation) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createSQLQuery("update Reservation set reviewed = 1 where idReservation = :idReservation");            
            query.setParameter("idReservation", idReservation);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }

    public void updateFestival(Integer idFestival, Integer rating) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createSQLQuery("update Festival set rating = (numRatings*rating + :rating) / (numRatings + 1), \n" +
                                            "numRatings = numRatings + 1 \n" +
                                            "where idFestival = :idFestival");            
            query.setParameter("idFestival", idFestival);
            query.setParameter("rating", rating);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
    
    public void addMedia(Media m, Integer idFestival) {
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Media media = new Media();
        media.setFestival(m.getFestival());
        media.setFile(m.getFile());
        session.persist(media);
        session.getTransaction().commit();
    }

    public String getAdminMessage(String dbIdUser) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        List<String> lst = new ArrayList<>();
        
        s.beginTransaction();
        Query q = s.createSQLQuery("select adminMessage from User where idUser = :idUser");
        q.setParameter("idUser", dbIdUser);
        String ret = (String) q.list().get(0);
        s.getTransaction().commit();
        return ret;
    }

    public void deleteMessage(Integer idUser) {
        Session s = NewHibernateUtil.getSessionFactory().getCurrentSession();
        try{
            s.beginTransaction();
            Query query = s.createSQLQuery("update User set adminMessage = NULL where idUser = :idUser");            
            query.setParameter("idUser", idUser);
            int result = query.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        }
    }
}
