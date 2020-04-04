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
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

public class LoginController implements Initializable {
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MainController.class);
    public String loggedinUser;
    
    // TextField Inputs----------------
    @FXML
    public TextField usernameInput;
    @FXML
    public PasswordField passwordInput;
    // --------------------------------
    
    // Buttons ------------------------
    @FXML
    public Button loignbttn;
    // --------------------------------
    
    public void initialize(URL url, ResourceBundle rb){
        usernameInput.setPromptText("Enter Username here");
        passwordInput.setPromptText("Enter Password here");
    }
    
    public void readLogins(){
        
        String url = "jdbc:postgresql://localhost:5432/weather_data_app";
        String userPG = "postgres";
        String passwordPG = "postgres";
        
        String query = "SELECT username, password FROM logins";
        ArrayList<String> userNames = new ArrayList<String>();
        ArrayList<String> userPwd = new ArrayList<String>();
        
        
         try (Connection con = DriverManager.getConnection(url, userPG, passwordPG);
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet user = pst.executeQuery()){

            while(user.next()) {

                userNames.add(user.getArray(1).toString());
                userPwd.add(user.getArray(2).toString());

            }
        String usrInput = usernameInput.getText();
        String pwdInput = passwordInput.getText();
            
        if (userNames.contains(usrInput)){
                if (pwdInput.equals(userPwd.get(userNames.indexOf(usrInput)))){
                log.info("Logging Successful");
                // Saving username login
                Preferences userPreferences = Preferences.userRoot();
                userPreferences.put("0", usrInput);
                //Opens wdGui.fxml if Logging Successful
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("wdGui.fxml"));
                Stage wdGui = new Stage();
                wdGui.setTitle("Wetterdaten App");
                wdGui.setScene(new Scene(root, 939, 673));
                wdGui.show();
                //Close LoginGui.fxml
                Stage stage = (Stage) usernameInput.getScene().getWindow();
                stage.close();

                } else {
                    log.info("Logging failed");
                    loginfailedalert();
                }
            } else {
                log.info("Logging failed");
                loginfailedalert();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(LoginController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            }
    }
    public void loginfailedalert(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Login");
        alert.setContentText("Failed");
        alert.showAndWait();
    }
}