package com.flimflam;

import org.json.simple.JSONObject;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
    		year = json.get("Year").toString().substring(0, 4) + "-" + json.get("Year").toString().substring(7, 11);
    		
//    	System.out.println("<h1><a href=\"" + URLer(json.get("imdbID").toString()) + "\"" + json.get("Title").toString() +"</a></h1><br>");
    	this.webEngine.loadContent("<h1><a href=\"" + URLer(json.get("imdbID").toString()) + "\">" + json.get("Title").toString() +"</a></h1><br>"
    			+ "<h3>" + year + "</h3><br>"
    			+ "<h3>" + json.get("imdbRating").toString() + "</h3><br><p>"
    			+ json.get("Plot").toString() + "</p>");
        this.webEngine.setUserStyleSheetLocation(getClass().getResource("application.css").toString());
	}
    
    Webs(String picURL){
    	this.webEngine.loadContent("<img src=" + picURL + " alt="+"Poster"+ "style="+"width:304px;height:228px;>");
    	this.webEngine.setUserStyleSheetLocation(getClass().getResource("application.css").toString());
    }
    
    private String URLer(String ID){
    	return "http://www.imdb.com/title/" + ID + "/?ref_=nv_sr_1";
    }
}

// http://www.imdb.com/title/tt2543164/?ref_=nv_sr_1