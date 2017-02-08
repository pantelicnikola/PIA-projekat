/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Attendencie;
import entity.Festival;
import entity.Link;
import entity.Media;
import entity.User;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Nikola
 */
public class AddFestival_dao {
    
    

    public void addFestival(Festival festival, Set<Link> set, List<Attendencie> per, List<Media> media) {
            Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Festival f = new Festival();
            f.setName(festival.getName());
            f.setPlace(festival.getPlace());
            f.setStartDate(festival.getStartDate());
            f.setEndDate(festival.getEndDate());
            f.setRating(0.0);
            f.setNumRatings(0);
            f.setViewCount(0);
            f.setTicketsSold(0);
            f.setAllDays(festival.getAllDays());
            f.setSingleDay(festival.getSingleDay());
            f.setDescription(festival.getDescription());
            f.setTicketsPerDay(festival.getTicketsPerDay());
            f.setTicketsPerReservation(festival.getTicketsPerReservation());
            
            session.persist(f);
            session.getTransaction().commit();
            
            for (Link l : set){
                if (!l.getLink().equals("")){
                    l.setFestival(f);
                    l.getId().setIdFestival(f.getIdFestival());
                    addLink(l);                
                }
                
            }
            
            for (Attendencie a : per){
                a.setFestival(f);
                a.getId().setIdFestival(f.getIdFestival());
                addPerformer(a);
            }
            
            for (Media m: media){
                m.setFestival(f);
                addMedia(m);
            }
    }

    private void addLink(Link l) {
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Link link = new Link();
        link.setFestival(l.getFestival());
        link.setLink(l.getLink());
        link.setId(l.getId());
        session.persist(link);
        session.getTransaction().commit();
    }

    private void addPerformer(Attendencie a) {
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Attendencie attendencie = new Attendencie();
        attendencie.setFestival(a.getFestival());
        attendencie.setId(a.getId());
        attendencie.setStartTime(a.getStartTime());
        attendencie.setEndTime(a.getEndTime());
        session.persist(attendencie);
        session.getTransaction().commit();
    }
    
    private void addMedia(Media m) {
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Media media = new Media();
        media.setFestival(m.getFestival());
        media.setFile(m.getFile());
        session.persist(media);
        session.getTransaction().commit();
    }

}
