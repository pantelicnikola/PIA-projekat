package entity;
// Generated Jan 19, 2017 1:02:32 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Reservation generated by hbm2java
 */
@Entity
@Table(name="reservation"
    ,catalog="festivali"
)
public class Reservation  implements java.io.Serializable {


     private Integer idReservation;
     private Festival festival;
     private User user;
     private Integer type;
     private Date reservationTime;
     private Integer bought;
     private Integer expired;
     private Integer ticketCount;
    private Integer reviewed;

    public Reservation() {
    }

    public Reservation(Festival festival, User user, Integer type, Date reservationTime, Integer bought, Integer expired, Integer ticketCount, Integer reviewed) {
       this.festival = festival;
       this.user = user;
       this.type = type;
       this.reservationTime = reservationTime;
       this.bought = bought;
       this.expired = expired;
       this.ticketCount = ticketCount;
       this.reviewed = reviewed;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="IdReservation", unique=true, nullable=false)
    public Integer getIdReservation() {
        return this.idReservation;
    }
    
    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdFestival")
    public Festival getFestival() {
        return this.festival;
    }
    
    public void setFestival(Festival festival) {
        this.festival = festival;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdUser")
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    
    @Column(name="Type")
    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ReservationTime", length=19)
    public Date getReservationTime() {
        return this.reservationTime;
    }
    
    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    
    @Column(name="Bought")
    public Integer getBought() {
        return this.bought;
    }
    
    public void setBought(Integer bought) {
        this.bought = bought;
    }

    
    @Column(name="Expired")
    public Integer getExpired() {
        return this.expired;
    }
    
    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    
    @Column(name="TicketCount")
    public Integer getTicketCount() {
        return this.ticketCount;
    }
    
    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    @Column(name="Reviewed")
    public Integer getReviewed() {
        return this.reviewed;
    }
    
    public void setReviewed(Integer reviewed) {
        this.reviewed = reviewed;
    }
    

}


