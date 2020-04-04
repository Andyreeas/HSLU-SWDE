package ch.hslu.swde.wda.application.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hslu.swde.wda.application.MainWda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.prefs.Preferences;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
	// LOGGER
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    
    // -------------------------------------------------------------------------------------------------------------------
    // Observable Lists
    // -------------------------------------------------------------------------------------------------------------------
    
    // Observable Lists for City-Names
    ObservableList<String> listLeft = FXCollections.observableArrayList(MainWda.listOfCities);
    public static ObservableList<String> listRight = FXCollections.observableArrayList();
    
    // Observable List for data of last 24h
    ObservableList<String> listOfData24h = FXCollections.observableArrayList();
    // Observable List for WeatherData
    ObservableList<String> listOfWeatherdata = FXCollections.observableArrayList();
    // Observable List for AvgTempData for Timeperiod
    ObservableList<String> listOfAvgTempData = FXCollections.observableArrayList();
    // Observable List for MinMaxTempdata for Timeperiod
    ObservableList<String> listOfMinMaxTempData = FXCollections.observableArrayList();
    // Observable List for AvgPressure for Timeperiod
    ObservableList<String> listOfAvgPressure = FXCollections.observableArrayList();
    // Observable List for MinMaxPressure for Timeperiod
    ObservableList<String> listOfMinMaxPressure = FXCollections.observableArrayList();
    // Observable List for AvgHumidity for Timeperiod
    ObservableList<String> listOfAvgHumidity = FXCollections.observableArrayList();
    // Observable List for MinMaxHumidity for Timeperiod
    ObservableList<String> listOfMinMaxHumidity = FXCollections.observableArrayList();
    // Observable List for TempData History for Timeperiod
    ObservableList<String> listOfTempDataHistory = FXCollections.observableArrayList();
    // Observable List for Pressure History for Timeperiod
    ObservableList<String> listOfPressureHistory = FXCollections.observableArrayList();
    // Observable List for Humidity History for Timeperiod
    ObservableList<String> listOfHumidityHistory = FXCollections.observableArrayList();
    
    
    // -------------------------------------------------------------------------------------------------------------------
    // Initialize Method
    // -------------------------------------------------------------------------------------------------------------------
    
    public void initialize(URL url, ResourceBundle rb){
        listViewLeft.getItems().setAll(listLeft);
    }
    
    // -------------------------------------------------------------------------------------------------------------------
    // Define all Buttons and ListViews from FXML
    // -------------------------------------------------------------------------------------------------------------------
    
    // UserManagement ----------------
    @FXML
    public MenuItem editUser;
    // --------------------------------
    
    // ListView -----------------------
    @FXML
    public ListView listViewLeft;
    
    @FXML
    public Button bttMoveRightAll;
    
    @FXML
    public Button bttMoveRightOne;
    
    @FXML
    public Button bttMoveLeftOne;
    
    @FXML
    public Button bttMoveLeftAll;
      
    @FXML
    public ListView listViewRight;
    //---------------------------------
    
    // Buttons
    @FXML
    public Button last24h;
    
    @FXML
    public DatePicker dpDateFrom;
    public String dateFrom;
    
    @FXML
    public DatePicker dpDateTo;
    public String dateTo;
    
    @FXML
    public Button averageTemp;
    
    @FXML
    public Button minMaxTemp;
    
    @FXML
    public Button averagePressure;
    
    @FXML
    public Button averageHumidity;

    @FXML
    public Button minMaxHumidity;
    
    @FXML
    public Button tempCourse;
     
    @FXML
    public Button humidityCourse;
    
    @FXML
    public Button pressureCourse;
    
    @FXML
    public Button export;
    
    @FXML
    public TextArea textOutput;
    // ---------------------------------------
    
    // -------------------------------------------------------------------------------------------------------------------
    // UserManagement
    // -------------------------------------------------------------------------------------------------------------------

    /**
     * Reads all usernames from postgresql with role = admin
     * @return ArrayList with all usernames with role admin in postgresql
     */
    public ArrayList<String> getAdminusers(){       
        String url = "jdbc:postgresql://localhost:5432/weather_data_app";
        String userPG = "postgres";
        String passwordPG = "postgres";
        
        String query = "SELECT username FROM logins WHERE role='admin'";
        ArrayList<String> roleUsr = new ArrayList<String>();
        
         try (Connection con = DriverManager.getConnection(url, userPG, passwordPG);
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet user = pst.executeQuery()){

            while(user.next()) {
                roleUsr.add(user.getArray(1).toString());
            }

        } catch (SQLException ex) {
            java.util.logging.Logger lgr = java.util.logging.Logger.getLogger(LoginController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            }
        return roleUsr;
    }

    /**
     * Alertbox if not logged in as an admin for user managemnt
     */
    public void wrongUserAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setContentText("Not logged in as admin");
        alert.showAndWait();
    }
    
    // -------------------------------------------------------------------------------------------------------------------
    // Buttons UserManagement
    // -------------------------------------------------------------------------------------------------------------------
    
    //Button "Mitglieder bearbeiten" onAction="openEditUser"
    public void openEditUser(){
        Preferences userPreferences = Preferences.userRoot();
        String info = userPreferences.get("0", "");
        if (getAdminusers().contains(info)){
        try{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userMagGUI.fxml"));
            Stage userMagGUI = new Stage();
            userMagGUI.setTitle("Usermanagement");
            userMagGUI.setScene(new Scene(root, 601, 285));
            userMagGUI.show();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            }
        } else {
            wrongUserAlert();
        }
    }
    
    
    // -------------------------------------------------------------------------------------------------------------------
    // ListView Settings
    // -------------------------------------------------------------------------------------------------------------------
    
    //refreshing ListViewLeft & right
    public void refreshListView(){
        listViewLeft.getItems().setAll(listLeft.sorted());
        listViewRight.getItems().setAll(listRight.sorted());
    }
    
    //set on Button fx:id bttMoveRightAll
    public void sendAllRight(){
        listRight.addAll(listLeft);
        listLeft.clear();
        refreshListView();
    }
    
     //set on Button fx:id bttMoveRightOne
    public void sendOneRight(){
        try {
            String city = listViewLeft.getSelectionModel().getSelectedItem().toString();
            if (city != null) {
                listViewLeft.getSelectionModel().clearSelection();
                listLeft.remove(city);
                listRight.addAll(city);
                refreshListView();
            }
        } catch (RuntimeException e){
                log.info("Nothing selected");
                textAusgabe("Nothing selected");
            }
    }
    
    //set on Button fx:id bttMoveLeftOne
    public void sendOneLeft(){
        try {
            String city = listViewRight.getSelectionModel().getSelectedItem().toString();
            if (city != null) {
                listViewRight.getSelectionModel().clearSelection();
                listRight.remove(city);
                listLeft.addAll(city);
                refreshListView();
            }
        } catch (RuntimeException e) {
            log.info("Nothing selected");
            textAusgabe("Nothing selected");
        }
    }
    
    //set on Button fx:id bttMoveLeftAll
    public void sendAllLeft(){
        listLeft.addAll(listRight);
        listRight.clear();
        refreshListView();
    }
    
    
    // -------------------------------------------------------------------------------------------------------------------
    // Buttons Weatherdata
    // -------------------------------------------------------------------------------------------------------------------
    
    /**
     * set on Button fx:id last24h
     * Outputing content of ListViewRight
     */    
    public void bttLast24hClicked() {
    	String urlBackup = MainWda.JSON_URL_WeatherdataLast24h;
        
    	try {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_WeatherdataLast24h = MainWda.JSON_URL_WeatherdataLast24h + cityName + "/" + "last24hours";
	            
	        	MainWda.fetchWeatherdataOflast24h();
	        	
	    		listOfData24h = MainWda.listOf24h;
	    		
	    		MainWda.JSON_URL_WeatherdataLast24h = urlBackup;
	        	
	    		textAusgabe("Die Wetterdaten des Orts " + "'" + cityName + "'" + " der letzten 24h sind: " + "\n" + listOfData24h.toString());
	    		
	    		listOfData24h.clear();
	        }
        
    	} catch (NullPointerException npe) {	
        	log.error("Die URL kann nicht geöffnet werden. Überprüfen Sie die REST-API!");
    		textAusgabe("Die URL kann nicht geöffnet werden. Überprüfen Sie die REST-API!");
    	}
    	
        log.info("Ausgabe Wetterdaten der letzten 24h");
    }
    
    /**
     * set on Button fx:id dpDateFrom
     * Read FromDate
     */
    public void dpDateFromSelected() {
    	LocalDate getDateFrom = dpDateFrom.getValue();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    	dateFrom = getDateFrom.format(formatter);
    }
    
    /**
     * set on Button fx:id dpDateTo
     * Read ToDate
     */
    public void dpDateToSelected() {
    	LocalDate getDateTo = dpDateTo.getValue();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    	dateTo = getDateTo.format(formatter);
    }
    
    /**
     * set on Button fx:id showWeatherData
     * Outputing WeatherData for a City
     */    
    public void bttgetWeatherDataClicked() {
    	String urlBackup = MainWda.JSON_URL_Weatherdata;
    	
		if ((dpDateFrom.getValue() != null) && (dpDateTo.getValue() != null)) {
    		for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_Weatherdata = MainWda.JSON_URL_Weatherdata + cityName + "/during?fromDate=" + dateFrom + "&toDate=" + dateTo;
	            
	        	MainWda.fetchWeatherdata();
	        	
	        	listOfWeatherdata = MainWda.weatherDataList;
	    		
	    		MainWda.JSON_URL_Weatherdata = urlBackup;
	        	
	    		textAusgabe("Die Wetterdaten des Orts " + "'" + cityName + "' sind: " + "\n" + listOfWeatherdata.toString());
	    		
	    		listOfWeatherdata.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe Wetterdaten in bestimmten Zeitpunkt.");
    }
    
    /**
     * set on Button fx:id averageTemp
     * Display Average Temp for period of selected Cities
     */
    public void bttAverageTempClicked() {
    	String urlBackup = MainWda.JSON_URL_AvgTempDataTp;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_AvgTempDataTp = MainWda.JSON_URL_AvgTempDataTp + cityName + "/" + "temperature?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchAvgTempData();
	        	
	        	listOfAvgTempData = MainWda.listAvgTempDataOfTp;
	        	
	        	MainWda.JSON_URL_AvgTempDataTp = urlBackup;
	        	
	    		textAusgabe("Die Durchschnittstemperatur für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " ist: " + listOfAvgTempData.toString() + " Celsius");
	    		
	    		listOfAvgTempData.clear();
	        }
        
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
        log.info("Ausgabe durschn. TempDaten waehrend Zeitperiode");
        
    }
    
    /**
     * set on Button fx:id minMaxTemp
     * Show MinMaxTemp for period of selected Cities
     */
    public void bttMinMaxTempClicked() {
       String urlBackup = MainWda.JSON_URL_MinMaxTempDataTp;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_MinMaxTempDataTp = MainWda.JSON_URL_MinMaxTempDataTp + cityName + "/" + "minmaxtemp?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchMinMaxTempData();
	    		
	        	listOfMinMaxTempData = MainWda.listMinMaxTempDataOfTp;
	        	
	        	MainWda.JSON_URL_MinMaxTempDataTp = urlBackup;
	        	
	    		textAusgabe("Die minimale und maximale Temperatur für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " sind: " + listOfMinMaxTempData.toString() + " Celsius");
	    		
	    		listOfMinMaxTempData.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe MinMax TempDaten waehrend Zeitperiode");
    }
    
    /**
     * set on Button fx:id averagePressure
     * Show averagePressure for period of selected Cities
     */
    public void bttAveragePressureClicked() {
       String urlBackup = MainWda.JSON_URL_AvgPressureTp;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_AvgPressureTp = MainWda.JSON_URL_AvgPressureTp + cityName + "/" + "pressure?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchAvgPressure();
	        	
	        	listOfAvgPressure = MainWda.listAvgPressureOfTp;
	        	
	        	MainWda.JSON_URL_AvgPressureTp = urlBackup;
	        	
	    		textAusgabe("Der durchschnittliche Luftdruck für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " ist: " + listOfAvgPressure.toString() + " Pascal");
	    		
	    		listOfAvgPressure.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe durschn. LuftdruckDaten waehrend Zeitperiode");
    }
    
    /**
     * set on Button fx:id averagePressure
     * Show averagePressure for period of selected Cities
     */
    public void bttMinMaxPressureClicked() {
       String urlBackup = MainWda.JSON_URL_MinMaxPressureTp;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_MinMaxPressureTp = MainWda.JSON_URL_MinMaxPressureTp + cityName + "/" + "minmaxpressure?fromDate=" + dateFrom + "&toDate=" + dateTo;
	            
	        	MainWda.fetchMinMaxPressure();
	        	
	        	listOfMinMaxPressure = MainWda.listMinMaxPressureOfTp;
	        	
	        	MainWda.JSON_URL_MinMaxPressureTp = urlBackup;
	        	
	    		textAusgabe("Der minimale und maximale Luftdruck für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " sind: " + listOfMinMaxPressure.toString() + " Pascal");
	        
	    		listOfMinMaxPressure.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe MinMax Luftdruck waehrend Zeitperiode");
    }
    
    /**
     * set on Button fx:id averageHumidity
     * Show averageHumidity for period of selected Cities
     */
    public void bttAverageHumidityClicked() {
       String urlBackup = MainWda.JSON_URL_AvgHumidityTp;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_AvgHumidityTp = MainWda.JSON_URL_AvgHumidityTp + cityName + "/" + "humidity?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchAvgHumidity();
	        	
	        	listOfAvgHumidity = MainWda.listAvgHumidityOfTp;
	        	
	        	MainWda.JSON_URL_AvgHumidityTp = urlBackup;
	        	
	    		textAusgabe("Die durchschnittliche Luftfeuchtigkeit für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " ist: " + listOfAvgHumidity.toString() + " %");
	    		
	    		listOfAvgHumidity.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe durschn. LuftfeuchtigkeitsDaten waehrend Zeitperiode");
    }
    
    /**
     * set on Button fx:id minMaxHumidity
     * Show minMaxHumidity for period of selected Cities
     */
    public void bttMinMaxHumidity() {
       String urlBackup = MainWda.JSON_URL_MinMaxHumidityTp;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_MinMaxHumidityTp = MainWda.JSON_URL_MinMaxHumidityTp + cityName + "/" + "minmaxhumidity?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchMinMaxHumidity();
	        	
	        	listOfMinMaxHumidity = MainWda.listMinMaxHumidityOfTp;
	        	
	        	MainWda.JSON_URL_MinMaxHumidityTp = urlBackup;
	        	
	    		textAusgabe("Die minimale und maximale Luftfeuchtigkeit für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " sind: " + listOfMinMaxHumidity.toString() + " %");
	    		
	    		listOfMinMaxHumidity.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe MinMax LuftfeuchtigkeitsDaten waehrend Zeitperiode");
    }
    
    // -------------------------------------------------------------------------------------------------------------------
    // HistoryDaten
    // -------------------------------------------------------------------------------------------------------------------
    
    /**
     * set on Button fx:id tempCourse
     * Show tempCourse for period of selected Cities
     */
    public void bttTempCourseClicked() {
       String urlBackup = MainWda.JSON_URL_TempDataHistory;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_TempDataHistory = MainWda.JSON_URL_TempDataHistory + cityName + "/" + "temphistory?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchTempDataHistory();
	        	
	        	listOfTempDataHistory = MainWda.listTempDataHistory;
	        	
	        	MainWda.JSON_URL_TempDataHistory = urlBackup;
	        	
	    		textAusgabe("Der Temperaturverlauf für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " ist: " + "\n" + listOfTempDataHistory.toString() + " (in Celsius)");
	    		
	    		listOfTempDataHistory.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe TempDaten Verlauf waehrend Zeitperiode");
    }

    /**
     * set on Button fx:id pressureCourse
     * Show pressureCourse for period of selected Cities
     */
    public void bttPressureCourseClicked() {
       String urlBackup = MainWda.JSON_URL_PressureHistory;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_PressureHistory = MainWda.JSON_URL_PressureHistory + cityName + "/" + "pressurehistory?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchPressureHistory();
	        	
	        	listOfPressureHistory = MainWda.listPressureHistory;
	        	
	        	MainWda.JSON_URL_PressureHistory = urlBackup;
	        	
	    		textAusgabe("Der Luftdrucksverlauf für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " ist: " + "\n" + listOfPressureHistory.toString() + " (in Pascal)");
	    		
	    		listOfPressureHistory.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe Luftdruckverlauf waehrend Zeitperiode");
    }
    
    /**
     * set on Button fx:id humidityCourse
     * Show humidityCourse for period of selected Cities
     */
    public void bttHumidityCourseClicked() {
       String urlBackup = MainWda.JSON_URL_HumidityHistory;
        
		if ((dpDateFrom.getValue() != null) && (dpDateFrom.getValue() != null)) {
	        for (int i = 0; i < listRight.size(); i++) {
	        	String cityName = listRight.get(i).toString();
	        	MainWda.JSON_URL_HumidityHistory = MainWda.JSON_URL_HumidityHistory + cityName + "/" + "humidityhistory?fromDate=" + dateFrom + "&toDate=" + dateTo;
	        	
	        	MainWda.fetchHumidityHistory();
	        	
	        	listOfHumidityHistory = MainWda.listHumidityHistory;
	        	
	        	MainWda.JSON_URL_HumidityHistory = urlBackup;
	        	
	    		textAusgabe("Der Luftfeuchtigkeitsverlauf für den angegeben Zeitraum des Orts " + "'" + cityName + "'" + " ist: " + "\n" + listOfHumidityHistory.toString() + " (in %)");
	    		
	    		listOfHumidityHistory.clear();
	        }
		} else {
			log.error("Die Datumsfelder sind leer! Bitte ausfuellen! " + "\n");
			textAusgabe("Die Datumsfelder sind leer! Bitte ausfuellen!" + "\n");
		}
		
        log.info("Ausgabe Luftfeuchtigkeitsverlauf waehrend Zeitperiode");
    }
    
    // -------------------------------------------------------------------------------------------------------------------
    // Textausgabe, Export und Clear Output
    // -------------------------------------------------------------------------------------------------------------------
    
    /**
     * set on Button fx:id textAusgabe
     * Textausgabe
     */
    public void textAusgabe(String text) {
        textOutput.appendText(text + "\n");
    }
    
    /**
     * set on Button fx:id exportToTXT
     * exportToTXT
     */
    public void bttExportToCSVClicked() {
    	 ObservableList<CharSequence> paragraph = textOutput.getParagraphs();
    	    Iterator<CharSequence>  iter = paragraph.iterator();
    	    try
    	    {
    	    	
    	    	File file = new File("C:\\temp\\textArea.txt");
    		      
				if (file.createNewFile()){
					System.out.println("File is created!");
				} else {
					System.out.println("File already exists.");
				}
    	    	
    	    	
    	        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
    	        while(iter.hasNext())
    	        {
    	            CharSequence seq = iter.next();
    	            bf.append(seq);
    	            bf.newLine();
    	        }
    	        bf.flush();
    	        bf.close();
    	    }
    	    catch (IOException e)
    	    {
    	        e.printStackTrace();
    	    }
    }
    
    /**
     * set on Button fx:id clearText
     * Clearing text output area
     */
    public void clearText() {
        textOutput.clear();
    }
}
