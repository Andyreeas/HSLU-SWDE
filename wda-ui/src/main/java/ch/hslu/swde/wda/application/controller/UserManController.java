package ch.hslu.swde.wda.application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.slf4j.LoggerFactory;

public class UserManController implements Initializable{
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MainController.class);
    
    String url = "jdbc:postgresql://localhost:5432/weather_data_app";
    String userPG = "postgres";
    String passwordPG = "postgres";
    
    ArrayList<String> user_id = new ArrayList<String>();
    ArrayList<String> userNames = new ArrayList<String>();
    ArrayList<String> userPwd = new ArrayList<String>();
    ArrayList<String> userrole = new ArrayList<String>();
    
    // ChoiceBox Inputs----------------
    @FXML
    public ChoiceBox<String> choiceBoxUser;
    
    // --------------------------------
    // TextField-----------------------
    @FXML
    public TextField userField;
    @FXML
    public TextField pwdField;
    @FXML
    public TextField userId;
    @FXML
    public TextField roleField;
    
    // --------------------------------
    //Buttons--------------------------
    @FXML
    public Button bttdelete;
    @FXML
    public Button bttcreate;
    @FXML
    public Button bttsave;
    //---------------------------------
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        readUserData();
        ObservableList<String> userMan = FXCollections.observableArrayList(userNames);
        choiceBoxUser.setItems(userMan.sorted());
        choiceBoxUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> pushdata(newValue));
        userId.setText(getNextid());
        userField.setText("Neuer Username");
        pwdField.setText("Neues Passwort");
        roleField.setText("'admin' oder 'user'");
    }
    
    public void pushdata(String newValue){
        userField.setText(userNames.get(userNames.indexOf(newValue)));
        pwdField.setText(userPwd.get(userNames.indexOf(newValue)));
        userId.setText(user_id.get(userNames.indexOf(newValue)));
        roleField.setText(userrole.get(userNames.indexOf(newValue)));
    }
    
    public void refreshData(){
        user_id.clear();
        userNames.clear();
        userPwd.clear();
        userrole.clear();
        readUserData();
        ObservableList<String> userMan = FXCollections.observableArrayList(userNames);
        choiceBoxUser.setItems(userMan.sorted());
    }
    
    // Button fx:id bttsave
    public void save(){
        String usrInput = userField.getText();
        String pwdInput = pwdField.getText();
        String useridInput = userId.getText();
        String roleInput = roleField.getText();
        String query = "UPDATE logins SET user_id=" + useridInput + ",username='" + usrInput +"',password='" + pwdInput +"',role='" + roleInput + "' WHERE user_id='" + user_id.get(user_id.lastIndexOf(useridInput)) + "'";
        updatePostgres(query);
        refreshData();
        choiceBoxUser.getSelectionModel().select(usrInput);
    }
    
    //Button fx:id bttcreate
    public void create(){
        String usrInput = userField.getText();
        String pwdInput = pwdField.getText();
        String useridInput = userId.getText();
        String roleInput = roleField.getText();
        String query = "INSERT INTO logins (user_id, username, password, role) VALUES (" + useridInput +",'"+usrInput+"','"+pwdInput+"','"+roleInput+"')";
        if(usrInput != null && pwdInput != null && roleInput != null && useridInput != null){
            if(user_id.contains(useridInput)){
                informationAlert("Usermanagement", "User ID wird bereits verwendet!");
            } else {
                updatePostgres(query);
                refreshData();
                choiceBoxUser.getSelectionModel().select(usrInput);
                informationAlert("Usermanagement", "User '" + usrInput + "' wurde erstellt");
            }
        } else {
            informationAlert("Usermanagement", "Bitte alle Informationen ausfüllen");
        }
    }
    
    //Button fx:id bttdelete
    public void delete(){
        String usrInput = userField.getText();
        String pwdInput = pwdField.getText();
        String useridInput = userId.getText();
        String roleInput = roleField.getText();
        String query = "DELETE FROM logins WHERE user_id=" + useridInput;
        if(user_id.contains(useridInput)){
            updatePostgres(query);
            refreshData();
            choiceBoxUser.getSelectionModel().selectFirst();
            informationAlert("Usermanagement", "Benutzer mit der ID: " + useridInput + " wurde gelöscht");
        } else {
            informationAlert("Usermanagement", "Benutzer mit ID: " + useridInput + "existiert nicht");
        }
    }

    public void readUserData(){
        String query = "SELECT * FROM logins";
         try (Connection con = DriverManager.getConnection(url, userPG, passwordPG);
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet user = pst.executeQuery()){
            while(user.next()) {
                user_id.add(user.getArray(1).toString());
                userNames.add(user.getArray(2).toString());
                userPwd.add(user.getArray(3).toString());
                userrole.add(user.getArray(4).toString());
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(LoginController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            }
    }
    
    public void updatePostgres(String query){
        int affectedrows = 0;
        try (Connection con = DriverManager.getConnection(url, userPG, passwordPG);
                PreparedStatement pst = con.prepareStatement(query)){
            affectedrows = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(LoginController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            }
    }
    public void informationAlert(String title, String infotext){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(infotext);
        alert.showAndWait();
    }
    public String getNextid(){
        int a = 1;
        String newid = "" + a;
        for(String i = newid; user_id.contains(i); ){
            a++;
            i = "" + a;
            newid = i;
        }
        return newid;
    }
}