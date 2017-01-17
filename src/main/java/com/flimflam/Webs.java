package com.flimflam;

import org.json.simple.JSONObject;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Webs  {
	
	public final WebView browser = new WebView();
    public final WebEngine webEngine = browser.getEngine();
	
    Webs(JSONObject json){
//    	System.out.println(json.get("Webs" + "Title"));
    	String year = "";
    	if(json.get("Year").toString().length()==4)
    		year = json.get("Year").toString();
    	else if(json.get("Year").toString().length()==7)
    		year = json.get("Year").toString().substring(0, 4) + "-present";
    	else if(json.get("Year").toString().length()==11)
    		year = json.get("Year").toString().substring(0, 3) + "-" + json.get("Year").toString().substring(7, 10);
    		
    	this.webEngine.loadContent("<h1>"+ json.get("Title").toString() +"</h1><br>"
    			+ "<h3>" + year + "</h3><br>"
    			+ "<h3>" + json.get("imdbRating").toString() + "</h3><br>"
    			+ json.get("Plot").toString() + "<br>");
        this.webEngine.setUserStyleSheetLocation(getClass().getResource("application.css").toString());
	}
    
    Webs(String picURL){
    	this.webEngine.loadContent("<img src=" + picURL + " alt="+"Poster"+ "style="+"width:304px;height:228px;>");
    	this.webEngine.setUserStyleSheetLocation(getClass().getResource("application.css").toString());
    }
}