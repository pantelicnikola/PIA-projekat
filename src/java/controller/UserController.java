package controller;



import model.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import entity.Festival;
import entity.Media;
import entity.Reservation;
import entity.Review;
import entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.NewHibernateUtil;
import model.User_dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nikola
 */

@ManagedBean(name="userCtrl")
@SessionScoped
public class UserController {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String ppassword;
    private String phone;
    private String dbPassword;
    private String dbUsername;
    private String dbIdUser;
    private int dbApproved;
    private int dbBlocked;
    private DataSource ds;
    private String loginMessage;
    private String registerMessage;
    private String changePasswordMessage;
    private List<Festival> lst;
    private List<Object> lstPendingReservations;
    private List<Object> lstExpiredReservations;
    private List<Object> lstBoughtReservations;
    private List<Object> lstToReview;
    private Object selectedResevation;
    private String newPassword;
    private String newPpassword;
    private Integer rating;
    private String comment;
    private Object reviewSelection;
    private List<Media> files;
    

    public UserController() {
        try{
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/festivali");
            if(Util.userLogged()){
                User_dao dao = new User_dao();
                Integer id = Util.getIdUser();

                lstPendingReservations = dao.getPendingReservations(id);
                lstBoughtReservations = dao.getBoughtReservations(id);
                lstExpiredReservations = dao.getExpiredReservations(id);
                
                files = new ArrayList<>();
            }
        } catch (NamingException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void refresh(){
        if(Util.userLogged()){
            User_dao dao = new User_dao();
            Integer id = Util.getIdUser();

            lstPendingReservations = dao.getPendingReservations(id);
            lstBoughtReservations = dao.getBoughtReservations(id);
            lstExpiredReservations = dao.getExpiredReservations(id);
        }
    }
    
    public String register(){
        
        try{
            if (!password.equals(ppassword)){
                registerMessage = "Passwords do not match";
                return "register";
            }
            registerMessage = "";
            Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setPhone(phone);
            u.setEmail(email);
            u.setName(username);
            u.setSurname(lastName);
            u.setApproved(0);
            u.setBlocked(0);
            u.setLives(3);
            session.persist(u);
            session.getTransaction().commit();
            return "login";
        } catch (ConstraintViolationException e) {
            registerMessage = "Username must be unique";
            return "register";

        }
    }
    
    
    
    public void dbData(String uName){
        if(uName != null){
            List<Object[]> lst = new ArrayList<>();
            Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Query query = session.createQuery("select username,password,idUser,approved,blocked from User where username = :username");
            query.setParameter("username", uName);
            lst = query.list();
            session.getTransaction().commit();
            
            dbUsername = (String) ((Object[])lst.get(0))[0];
            dbPassword = (String) ((Object[])lst.get(0))[1];
            dbIdUser = Integer.toString((Integer)((Object[])lst.get(0))[2]);
            dbApproved = (Integer) ((Object[])lst.get(0))[3];
            dbBlocked = (Integer) ((Object[])lst.get(0))[4];
        }
    }
    
    public String login(){
        if (username.equals("admin") && password.equals("admin")) {
            return "admin";
        }
        dbData(username);
        User_dao dao = new User_dao();
        if(dbBlocked == 0) {
            dao.checkReservations();
            dbData(username);
        }     
        if (username.equals(dbUsername) && password.equals(dbPassword) && dbApproved == 1 && dbBlocked == 0){
           
            HttpSession hs = Util.getSession();
            hs.setAttribute("username", username);
            hs.setAttribute("idUser", dbIdUser);
            username = "";
            password = "";
            loginMessage = "";
           
            this.setLst(dao.fiveSoonest());
            Integer id = Integer.parseInt(dbIdUser);
            dao.updateLastLogin(id);
            lstPendingReservations = dao.getPendingReservations(id);
            lstBoughtReservations = dao.getBoughtReservations(id);
            lstExpiredReservations = dao.getExpiredReservations(id);
            lstToReview = dao.toReview(Integer.parseInt(dbIdUser));
            String message = dao.getAdminMessage(dbIdUser);
            if (message != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
            }
            return "home";
        } else {
            if (!username.equals(dbUsername)){
                loginMessage = "Username doesnt exist";
            }  else if (!password.equals(dbPassword)){
                loginMessage = "Wrong password";
            } else if (dbApproved == 0) {
                loginMessage = "Account not activated yet";
            } else {
                loginMessage = "Account is blocked";
            }
            return "login";
        }
    }
    
    public String logout(){
        User_dao dao = new User_dao();
        dao.deleteMessage(Util.getIdUser());
        HttpSession hs = Util.getSession();
        hs.invalidate();
        
        return "login";
    }
    
    public String logoutAdmin() {
        HttpSession hs = Util.getSession();
        hs.invalidate();
        
        return "login";
    }
    
    public String changePassword(){
        if(password.equals(dbPassword) && username.equals(dbUsername)){
            if (newPassword.equals(newPpassword)){
                User_dao dao = new User_dao();
                dao.setNewPassword(dbUsername, newPassword);
                logout();
                return "login";
            } else {
                changePasswordMessage = "New Passwords Do Not Match";
            }
        } else {
            changePasswordMessage = "Wrong Username or Password";
        }
        return "changePassword";
    }
    
    public String buy(){
        User_dao dao = new User_dao();
        dao.buy(selectedResevation);
        return "activity?facesRedirect=true";
    }
    
    public String cancel(){
        User_dao dao = new User_dao();
        dao.cancel(selectedResevation);
        return "activity?facesRedirect=true";
    }
    
    public String submitReview(){
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Integer idReservation = (Integer) ((Object[])reviewSelection)[3];
            Integer idFestival = (Integer) ((Object[])reviewSelection)[4];
            String festivalName = (String) ((Object[])reviewSelection)[0];
            Review r = new Review();
            r.getId().setIdUser(Integer.parseInt(dbIdUser));
            r.getId().setIdReservation(idReservation);
            r.setComment(comment);
            r.setRating(rating);
            r.setFestivalName(festivalName);
            r.setDate(new Date());
            r.setUsername(dbUsername);
            
            session.persist(r);
            session.getTransaction().commit();
            User_dao dao = new User_dao();
            dao.updateReservation(idReservation);
            dao.updateFestival(idFestival, rating);
            for (Media m : files){
                dao.addMedia(m, idFestival);
            }
            lstToReview = dao.toReview(Integer.parseInt(dbIdUser));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Review Added"));
        return "review?facesRedirect=true";
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        Media media = new Media();
        media.setFile(event.getFile().getContents());
        files.add(media);
    }
    

    public Object getSelectedResevation() {
        return selectedResevation;
    }

    public void setSelectedResevation(Object selectedResevation) {
        this.selectedResevation = selectedResevation;
    }

    public List<Object> getLstPendingReservations() {
        User_dao dao = new User_dao();
        Integer id = Util.getIdUser();
        lstPendingReservations = dao.getPendingReservations(id);
        return lstPendingReservations;
    }

    public List<Object> getLstExpiredReservations() {
        User_dao dao = new User_dao();
        Integer id = Util.getIdUser();
        lstExpiredReservations = dao.getExpiredReservations(id);
        return lstExpiredReservations;
    }

    public List<Object> getLstBoughtReservations() {
        User_dao dao = new User_dao();
        Integer id = Util.getIdUser();
        lstBoughtReservations = dao.getBoughtReservations(id);
        return lstBoughtReservations;
    }

    public void setLstPendingReservations(List<Object> lstPendingReservations) {
        this.lstPendingReservations = lstPendingReservations;
    }

    public void setLstExpiredReservations(List<Object> lstExpiredReservations) {
        this.lstExpiredReservations = lstExpiredReservations;
    }

    public void setLstBoughtReservations(List<Object> lstBoughtReservations) {
        this.lstBoughtReservations = lstBoughtReservations;
    }

    public String getChangePasswordMessage() {
        return changePasswordMessage;
    }

    public void setChangePasswordMessage(String changePasswordMessage) {
        this.changePasswordMessage = changePasswordMessage;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPpassword(String ppassword) {
        this.ppassword = ppassword;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public void setDbName(String dbName) {
        this.dbUsername = dbName;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPpassword() {
        return newPpassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPpassword(String newPpassword) {
        this.newPpassword = newPpassword;
    }
    
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPpassword() {
        return ppassword;
    }

    public String getPhone() {
        return phone;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbName() {
        return dbUsername;
    }

    public DataSource getDs() {
        return ds;
    }

    public String getLoginMessage() {
        return loginMessage;
    }
    
    public String getRegisterMessage() {
        return registerMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }
    
    public void setRegisterMessage(String registerMessage) {
        this.registerMessage = registerMessage;
    }

    public List<Festival> getLst() {
        return lst;
    }

    public void setLst(List<Festival> lst) {
        this.lst = lst;
    }
    
    public List<Object> getLstToReview() {
        return lstToReview;
    }

    public void setLstToReview(List<Object> lstToReview) {
        this.lstToReview = lstToReview;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Object getReviewSelection() {
        return reviewSelection;
    }

    public void setReviewSelection(Object reviewSelection) {
        this.reviewSelection = reviewSelection;
    }
    
    
}
