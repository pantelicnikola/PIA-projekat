/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSON.Fest;
import JSON.MainFest;
import JSON.PerformersList;
import JSON.SocialNetwork;
import com.google.gson.Gson;
import entity.Attendencie;
import entity.Festival;
import entity.Link;
import entity.Media;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.AddFestival_dao;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import javax.faces.application.FacesMessage;



/**
 *
 * @author Nikola
 */

@ManagedBean(name = "wizard")
@ViewScoped
public class AddFestivalController implements Serializable {
    private Festival festival = new Festival();
    private Attendencie performer = new Attendencie();
    
    private List<Attendencie> performers = new ArrayList<>();
    private List<Media> files = new ArrayList<>();
    private Set<Link> links = new HashSet<>();

    private String facebookLink;
    private String twitterLink;
    private String instagramLink;
    private String youtubeLink;
    
    private MainFest result;
    
    
    public void save(){
        AddFestival_dao dao = new AddFestival_dao();
        Set<Link> set = summonLinks();
        dao.addFestival(festival, set, performers, files);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Festival added successfully"));
    }
     
    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
    
    public void addPerformer(){
        if (performer.getId().getPerformer().equals("") || performer.getStartTime() == null || performer.getEndTime() == null ){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "All fields must be filled"));
            return;
        }
        if(performer.getStartTime().before(performer.getEndTime())){
            performers.add(performer); 
            performer = new Attendencie();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Performer added successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", " Start time and End time ERROR"));
        }   
        
    }
    
    
    public Set<Link> summonLinks(){
        Set<Link> set = new HashSet<>();
        Link facebookLinks = new Link();
        if(!facebookLink.equals("")){
            facebookLinks.setLink(facebookLink);
            facebookLinks.getId().setName("facebook");
            set.add(facebookLinks);
        }
        Link twitterLinks = new Link();
        if(!twitterLink.equals("")){
            twitterLinks.setLink(twitterLink);
            twitterLinks.getId().setName("twitter");
            set.add(twitterLinks);
        }
        Link instagramLinks = new Link();
        if(!instagramLink.equals("")){
            instagramLinks.setLink(instagramLink);
            instagramLinks.getId().setName("instagram");
            set.add(instagramLinks);
        }
        Link youtubeLinks = new Link();
        if(!youtubeLink.equals("")){
            youtubeLinks.setLink(youtubeLink);
            youtubeLinks.getId().setName("youtube");
            set.add(youtubeLinks);
        }
        return set;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        Media media = new Media();
        media.setFile(event.getFile().getContents());
        files.add(media);
    }
    
    public void parseJSON() {
        files = new ArrayList<>();
        try {
            Gson gson = new Gson();
            BufferedReader br = null;
            br = new BufferedReader(new FileReader("C:\\Users\\Nikola\\Documents\\NetBeansProjects\\PIA\\src\\java\\controller\\data.json"));
            result = gson.fromJson(br, MainFest.class);
            if (result == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "File 'data.json' could not be parsed"));
            } else {
                
                festival = new Festival();
                links = new HashSet<>();
                performers = new ArrayList<>();
                files = new ArrayList<>();
                Fest fest = result.getFestival();
                try {
                    festival = new Festival();
                    festival.setName(fest.getName());
                    festival.setPlace(fest.getPlace());
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    festival.setStartDate(df.parse(fest.getStartDate()));
                    festival.setEndDate(df.parse(fest.getEndDate()));
                    festival.setDescription("Bla bla");
                    festival.setSingleDay(Integer.parseInt(fest.getTickets().get(0)));
                    festival.setAllDays(Integer.parseInt(fest.getTickets().get(1)));
                    festival.setNumRatings(0);
                    festival.setRating(0.0);
                    festival.setViewCount(0);
                    festival.setTicketsSold(0);
                    festival.setTicketsPerDay(10);
                    festival.setTicketsPerReservation(10);
                    

                    for (PerformersList performer: fest.getPerformersList()) {
                        Attendencie a = new Attendencie();
                        a.setFestival(festival);
                        a.getId().setPerformer(performer.getPerformer());
                        df = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss a");
                        Date date = df.parse(performer.getStartDate() + performer.getStartTime());
                        a.setStartTime(date);
                        date = df.parse(performer.getEndDate() + performer.getEndTime());
                        a.setEndTime(date);
                        performers.add(a);
                    }
                    for (SocialNetwork social: fest.getSocialNetworks()) {
                        Link l = new Link();
                        l.getId().setName(social.getName());
                        l.setFestival(festival);
                        l.setLink(social.getLink());
                        links.add(l);
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "File 'data.json' successfully parsed"));
                } catch (ParseException ex) {
                    Logger.getLogger(AddFestivalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddFestivalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseCSV() {
        BufferedReader br = null;
        String line;
        String[] fields;
        festival = new Festival();
        links = new HashSet<>();
        performers = new ArrayList<>();
        files = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader("C:\\Users\\Nikola\\Documents\\NetBeansProjects\\PIA\\src\\java\\controller\\data.csv"));
            
            br.readLine();
            line = br.readLine();
            fields = line.split("\"");
            festival.setName(fields[1]);
            festival.setPlace(fields[3]);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            festival.setStartDate(df.parse(fields[5]));
            festival.setEndDate(df.parse(fields[7]));
            festival.setNumRatings(0);
            festival.setRating(0.0);
            festival.setViewCount(0);
            festival.setTicketsSold(0);
            festival.setTicketsPerDay(10);
            festival.setTicketsPerReservation(10);
            festival.setDescription("Bla bla");
            
            
            br.readLine();
            line = br.readLine();
            fields = line.split("\"");
            festival.setSingleDay(Integer.parseInt(fields[3]));
            
            line = br.readLine();
            fields = line.split("\"");
            festival.setAllDays(Integer.parseInt(fields[3]));
            
            br.readLine();
            line = br.readLine();
            fields = line.split("\"");
            while (!fields[1].equals("Social Network")) {
                Attendencie performer = new Attendencie();
                performer.getId().setPerformer(fields[1]);
                df = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss a");
                Date date = df.parse(fields[3] + fields[7]);
                performer.setStartTime(date);
                date = df.parse(fields[5] + fields[9]);
                performer.setEndTime(date);
                performer.setFestival(festival);
                performers.add(performer);
                line = br.readLine();
                fields = line.split("\"");
            }
            
            while ((line = br.readLine()) != null) {
                fields = line.split("\"");
                Link link = new Link();
                link.getId().setName(fields[1]);
                link.setLink(fields[3]);
                link.setFestival(festival);
                links.add(link);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "File 'data.csv' successfully parsed"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddFestivalController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Failed to parse 'data.csv'"));
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AddFestivalController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Failed to parse 'data.csv'"));
        }
    }
    
    public void insertData() {
        
        AddFestival_dao dao = new AddFestival_dao();
        dao.addFestival(festival, links, performers, files);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Festival successfully added"));
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public Attendencie getPerformer() {
        return performer;
    }

    public void setPerformer(Attendencie performer) {
        this.performer = performer;
    }

    public List<Attendencie> getPerformers() {
        return performers;
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void setPerformers(List<Attendencie> performers) {
        this.performers = performers;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }
    
}
