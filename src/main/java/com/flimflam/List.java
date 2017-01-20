package com.flimflam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
//	final String file = "WATCHLIST.csv";
//	private Table t = new Table();
	
	public HashMap<String, ObservableList<Pair<WebView, Object>>> searchLists = new HashMap<String, ObservableList<Pair<WebView, Object>>>();
	
	
	List(){
		
	}
	
	List(File file){
		readCSV(file);
		sp = new SearchParameters();
	}
	
	public void add(Item item){
        this.data.add(item.pair);
		this.arrList.add(item);
//		System.out.println(item.json.get("Year").toString().length());
	}
	
	public List search(){
		List tmpList = new List();
		if((sp.genres.isEmpty()) && (sp.getIMDBRating()==0.0) && (sp.getYear() == 0)){
//			System.out.println("sp empty");
			return tmpList;
		}
		for(Item item : this.arrList) {
//			System.out.println(item.json.get("Title"));
			if(sp.validateItem(item, sp)){
//				System.out.println("item match");
				tmpList.data.add(item.pair);
			}
		}
		return tmpList;
	}
	
	public void readCSV(File file){
	        String csvFile = "src/main/resources/com/flimflam/" + file;
	        String line = "";
	        String genresStr = "";
	        String[] genresArr;
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(",");
	                if(values[0].toLowerCase().trim().equals("\"position\""))
	                	continue;
//	                for(String s : values)
//	                	System.out.print(s);
//	                System.out.println("\n" + values[1].substring(1, values[1].length()-1));
	                FetchItem fi = new FetchItem(values[1].substring(1, values[1].length()-1));
	                Item item = new Item(fi.itemString);
//	                System.out.println("\nfi.itemString: " + fi.itemString);
	                genresStr = item.json.get("Genre").toString();
	                genresArr = genresStr.split(", ");
	                for(String s : genresArr) this.genres.add(s);

	                add(item);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	}
	
	public void printList(){
		for(Item item : this.arrList){
			item.printItem();
		}
	}
}
