/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSON;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Nikola
 */
public class Fest {
    
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Place")
    @Expose
    private String place;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("Tickets")
    @Expose
    private List<String> tickets = null;
    @SerializedName("PerformersList")
    @Expose
    private List<PerformersList> performersList = null;
    @SerializedName("SocialNetworks")
    @Expose
    private List<SocialNetwork> socialNetworks = null;

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getPlace() {
    return place;
    }

    public void setPlace(String place) {
    this.place = place;
    }

    public String getStartDate() {
    return startDate;
    }

    public void setStartDate(String startDate) {
    this.startDate = startDate;
    }

    public String getEndDate() {
    return endDate;
    }

    public void setEndDate(String endDate) {
    this.endDate = endDate;
    }

    public List<String> getTickets() {
    return tickets;
    }

    public void setTickets(List<String> tickets) {
    this.tickets = tickets;
    }

    public List<PerformersList> getPerformersList() {
    return performersList;
    }

    public void setPerformersList(List<PerformersList> performersList) {
    this.performersList = performersList;
    }

    public List<SocialNetwork> getSocialNetworks() {
    return socialNetworks;
    }

    public void setSocialNetworks(List<SocialNetwork> socialNetworks) {
    this.socialNetworks = socialNetworks;
    }
}
