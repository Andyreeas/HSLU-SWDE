package ch.hslu.swde.wda.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ch.hslu.swde.wda.domain.City;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWda extends Application{
	
	private static final Logger log = LoggerFactory.getLogger(MainWda.class);

	 // ----------------------------------------------------------------------------------------------------------------------
	// Define JSON URLs
	// ----------------------------------------------------------------------------------------------------------------------
	
	// Define URL for Weatherdata
	public static String urlweatherdata = "http://localhost:8080/weatherdata/";
	
	// Define JSON URL for City Names
	final String JSON_URL_CITIES = "http://localhost:8080/cities";
	// Define URL for Weatherdata
	public static String JSON_URL_WeatherdataLast24h = urlweatherdata;
	// Define URL for AvgTempData of Timeperiod
	public static String JSON_URL_AvgTempDataTp = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_MinMaxTempDataTp = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_AvgPressureTp = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_MinMaxPressureTp = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_AvgHumidityTp = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_MinMaxHumidityTp = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_TempDataHistory = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_PressureHistory = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_HumidityHistory = urlweatherdata;
	// Define URL for MinMaxTempData of Timeperiod
	public static String JSON_URL_Weatherdata = urlweatherdata;
	
	// ----------------------------------------------------------------------------------------------------------------------
	// Define Observable Lists
	// ----------------------------------------------------------------------------------------------------------------------
	
	// List for City-Names
	public static ObservableList<String> listOfCities = FXCollections.observableArrayList();
	// List for City-Names and data of last 24h
	public static ObservableList<String> weatherDataList = FXCollections.observableArrayList();
	// List for City-Names and data of last 24h
	public static ObservableList<String> listOf24h = FXCollections.observableArrayList();
	// List for Weatherdata during a specific timeperiod
	public static ObservableList<String> listAvgTempDataOfTp = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listMinMaxTempDataOfTp = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listAvgPressureOfTp = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listMinMaxPressureOfTp = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listAvgHumidityOfTp = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listMinMaxHumidityOfTp = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listTempDataHistory = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listPressureHistory = FXCollections.observableArrayList();
	// List for TempData during a specific timeperiod
	public static ObservableList<String> listHumidityHistory = FXCollections.observableArrayList();
		
	// ----------------------------------------------------------------------------------------------------------------------
	// Read JSON and return for using it later in Fetchlists
	// ----------------------------------------------------------------------------------------------------------------------
	
	public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;        
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[9999];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
        	
            return buffer.toString();
            

        } finally {
        	if (reader != null)
        		reader.close();
        }
        
	}
	
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for City-Names
	// ----------------------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	private Task<List<City>> fetchListCities = new Task() {
        @Override
        public List<City> call() throws Exception {
            List<City> list = null;
            try {
                Gson gson = new Gson();
                list = gson.fromJson(readUrl(JSON_URL_CITIES), new TypeToken<List<City>>() {}.getType());
                
                // Add Names of JSON to observable list
                for (int i = 0; i < list.size(); i++) {
                	String str = list.get(i).getName();
                	listOfCities.add(i, str);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return list;
         }
	 };
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetch WeatherDataLast24hours and WeatherDataOverTimePeriod from REST and save it nicely formatted into a list
	// ----------------------------------------------------------------------------------------------------------------------
	
	 private static String readAll(Reader rd) throws IOException {
		 
		 StringBuilder sb = new StringBuilder();
		 int cp;
		  
		 while ((cp = rd.read()) != -1) {
			 sb.append((char) cp);
		 }
		  
		 return sb.toString();
		  
	 }

	 public static List<String> readJsonFromUrl(String url) throws IOException {
		 InputStream is = new URL(url).openStream();
    	 List<String> list = new ArrayList<String>();
	     try {  	 
	    	 BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	 String jsonText = readAll(rd);
	      
	    	 String finalValue = null;

	    	 JSONArray jsonArray = new JSONArray(jsonText);
	      		for(int i =0; i< jsonArray.length(); i++) {
	      			JSONObject jsnObj = (JSONObject)jsonArray.get(i);
	      			finalValue = jsnObj.toString(4);
	      			list.add(finalValue);
	      		}
	      		
	      	return list;
	      	
	     } finally {
	    	 is.close();
	     }
	    
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for weatherdata of last 24h
	// ----------------------------------------------------------------------------------------------------------------------
	  
	 public static void fetchWeatherdataOflast24h() {
		 
		 try {
			 listOf24h.addAll(readJsonFromUrl(JSON_URL_WeatherdataLast24h));
		 } catch (Exception e) {
			 e.printStackTrace();
	     }
	 }
	 
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for weatherdata of last 24h
	// ----------------------------------------------------------------------------------------------------------------------
	
	 public static void fetchWeatherdata() {
		 try {
			 weatherDataList.addAll(readJsonFromUrl(JSON_URL_Weatherdata));
         } catch (Exception e) {
             e.printStackTrace();
         }
	 }
 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for Avg. TempData during a specific timeperiod
	// ----------------------------------------------------------------------------------------------------------------------
	
	 public static void fetchAvgTempData() {
		 try {
			 listAvgTempDataOfTp.add(readUrl(JSON_URL_AvgTempDataTp));
		 } catch (Exception e) {
             e.printStackTrace();
         }
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for MinMax TempData during a specific timeperiod
	// ----------------------------------------------------------------------------------------------------------------------
	
	 public static void fetchMinMaxTempData() {
		 try {
			 listMinMaxTempDataOfTp.add(readUrl(JSON_URL_MinMaxTempDataTp));
		 } catch (Exception e) {
             e.printStackTrace();
         }
	 }
	 
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for AvgPressure during a specific timeperiod
	// ----------------------------------------------------------------------------------------------------------------------
	 
	 public static void fetchAvgPressure() {
		 try {
			 listAvgPressureOfTp.add(readUrl(JSON_URL_AvgPressureTp));
		 } catch (Exception e) {
             e.printStackTrace();
         }
	 }
 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for MinMaxPressure during a specific timeperiod
	// ----------------------------------------------------------------------------------------------------------------------
	
	 public static void fetchMinMaxPressure() {
		 try {
             listMinMaxPressureOfTp.add(readUrl(JSON_URL_MinMaxPressureTp));
         } catch (Exception e) {
             e.printStackTrace();
         }
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for AvgHumidity during a specific timeperiod
	// ----------------------------------------------------------------------------------------------------------------------
	
	 public static void fetchAvgHumidity() {
         try {
             listAvgHumidityOfTp.add(readUrl(JSON_URL_AvgHumidityTp));
         } catch (Exception e) {
             e.printStackTrace();
         }
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for MinMaxHumidity during a specific timeperiod
	// ----------------------------------------------------------------------------------------------------------------------
	 
	 public static void fetchMinMaxHumidity() {
         try {
             listMinMaxHumidityOfTp.add(readUrl(JSON_URL_MinMaxHumidityTp));
         } catch (Exception e) {
             e.printStackTrace();
         }
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for TempData History
	// ----------------------------------------------------------------------------------------------------------------------
	 
	 public static void fetchTempDataHistory() {
         try {
           listTempDataHistory.add(readUrl(JSON_URL_TempDataHistory));
       } catch (Exception e) {
           e.printStackTrace();
       }
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for Pressure History
	// ----------------------------------------------------------------------------------------------------------------------
	 
	 public static void fetchPressureHistory() {
         try {
         	listPressureHistory.add(readUrl(JSON_URL_PressureHistory));
         } catch (Exception e) {
             e.printStackTrace();
         }
	 }
		 
	// ----------------------------------------------------------------------------------------------------------------------
	// Fetchlist for Humidity History
	// ----------------------------------------------------------------------------------------------------------------------
	
	 public static void fetchHumidityHistory() {
         try {
         	listHumidityHistory.add(readUrl(JSON_URL_HumidityHistory));
         } catch (Exception e) {
             e.printStackTrace();
         }
	 }
	 
	// ----------------------------------------------------------------------------------------------------------------------
	// End of Fetchlists start of start()
	// ----------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
            // Get City-Names from JSON
            fetchListCities.run();
		
            // ----- Run LoginGUIFXML ----------------------------------------------------------------------------------
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginGUI.fxml"));
            primaryStage.setTitle("Wetterdaten App");
            primaryStage.setScene(new Scene(root, 593, 355));
            primaryStage.show();        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}