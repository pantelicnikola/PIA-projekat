package entity;
// Generated Jan 19, 2017 1:02:32 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Review generated by hbm2java
 */
@Entity
@Table(name="review"
    ,catalog="festivali"
)
public class Review  implements java.io.Serializable {


     private ReviewId id;
     private String comment;
     private Integer rating;
     private String festivalName;
     private Date date;
     private String username;

    public Review() {
        id = new ReviewId();
    }

	
    public Review(ReviewId id) {
        this.id = id;
    }
    public Review(ReviewId id, String comment, Integer rating, String festivalName, Date date, String username) {
       this.id = id;
       this.comment = comment;
       this.rating = rating;
       this.festivalName = festivalName;
       this.date = date;
       this.username = username;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idUser", column=@Column(name="IdUser", nullable=false) ), 
        @AttributeOverride(name="idReservation", column=@Column(name="IdReservation", nullable=false) ) } )
    public ReviewId getId() {
        return this.id;
    }
    
    public void setId(ReviewId id) {
        this.id = id;
    }

    
    @Column(name="Comment", length=160)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    @Column(name="Rating")
    public Integer getRating() {
        return this.rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    
    @Column(name="FestivalName", length=45)
    public String getFestivalName() {
        return this.festivalName;
    }
    
    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Date", length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="Username", length=45)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    


}


