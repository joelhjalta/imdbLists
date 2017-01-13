package com.flimflam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.util.Pair;

public class List {
	public ObservableList<Pair<WebView, Object>> data = FXCollections.observableArrayList();
	public ArrayList<Item> arrList = new ArrayList<>();
	public HashSet<String> genres = new HashSet<String>();
	public SearchParameters sp;
	final String file = "WATCHLIST2.csv";
	private Table t = new Table(this);
	
	public HashMap<String, List> allLists = new HashMap<String, List>();
	
	
	List(){
		
	}
	
	List(boolean fullList){
		readCSV(file);
		sp = new SearchParameters();
	}
	
	public void add(Item item){
		Webs wv = new Webs(item.json);
        this.data.add( t.pair(wv.browser, new Image(item.json.get("Poster").toString())) );
		this.arrList.add(item);
	}
	
	public List search(){
//		String poster;
		List tmpList = new List();
		for(Item item : this.arrList) {
//			System.out.println(item.json.get("Title"));
			if(sp.validateItem(item, sp)){
//				poster = item.json.get("Poster").toString();
//				if(poster.equals("N/A")) 
//        			poster = "http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png";
				Webs wv = new Webs(item.json);
				tmpList.data.add(t.pair(wv.browser, new Image(item.json.get("Poster").toString())));
			}
		}
		
//		System.out.println("Sending " + tmpList.data.size() + " items.");
		return tmpList;
	}
	
	public void readCSV(String file){
	        String csvFile = "src/main/resources/com/flimflam/" + file;
	        String line = "";
	        String genresStr = "";
	        String[] genresArr;
	        
//	        File dir = new File(".");
//	    	for(String fileNames : dir.list()) System.out.println(fileNames);

	        //To show what files are in the directory
	        //File file = new File(".");
	        //for(String fileNames : file.list()) System.out.println(fileNames);
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] values = line.split(",");
	                FetchItem fi = new FetchItem(values[1].substring(2, values[1].length()-2));
	                Item it = new Item(fi.itemString);
	                genresStr = it.json.get("Genre").toString();
	                genresArr = genresStr.split(", ");
//	                System.out.println(it.json.get("Genre"));
	                checkPoster(it);
	                for(String s : genresArr) genres.add(s);

	                add(it);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        /*} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();*/
			}
	        
//	        System.out.println(genres.size()+"\n"+genres);

	}
	
	public void checkPoster(Item it){
		if(it.json.get("Poster").toString().equals("N/A")) 
        	it.json.put("Poster", "http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png");
	}
	
	public void printList(){
		for(Item item : this.arrList){
			item.printItem();
		}
	}
}
