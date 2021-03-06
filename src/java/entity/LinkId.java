package entity;
// Generated Jan 19, 2017 1:02:32 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * LinkId generated by hbm2java
 */
@Embeddable
public class LinkId  implements java.io.Serializable {


     private int idFestival;
     private String name;

    public LinkId() {
    }

    public LinkId(int idFestival, String name) {
       this.idFestival = idFestival;
       this.name = name;
    }
   


    @Column(name="IdFestival", nullable=false)
    public int getIdFestival() {
        return this.idFestival;
    }
    
    public void setIdFestival(int idFestival) {
        this.idFestival = idFestival;
    }


    @Column(name="Name", nullable=false, length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LinkId) ) return false;
		 LinkId castOther = ( LinkId ) other; 
         
		 return (this.getIdFestival()==castOther.getIdFestival())
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdFestival();
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         return result;
   }   


}


