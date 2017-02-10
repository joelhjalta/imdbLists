package com.flimflam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.util.Pair;

public class List {
	public ObservableList<Pair<WebView, Object>> data = FXCollections.observableArrayList();
	public ArrayList<Item> arrList = new ArrayList<>();
	public HashSet<String> genres = new HashSet<String>();
	public TreeSet<String> actors = new TreeSet<String>();
	public TreeMap<Integer, ObservableList<Pair<WebView, Object>>> searchLists = new TreeMap<Integer, ObservableList<Pair<WebView, Object>>>(
			Collections.reverseOrder());
	public SearchParameters sp;

	List() {

	}

	List(File file) {
		readCSV(file);
		sp = new SearchParameters();
	}

	public void add(Item item) {
		this.data.add(item.pair);
		this.arrList.add(item);
	}

	public List search() {
		List tmpList = new List();
		if (this.sp.isEmpty()) {
			System.out.println("sp empty");
			return tmpList;
		}
		for (Item item : this.arrList) {
			int matches = sp.validateItem(item, sp);
			if (matches > -1) {
				Integer matchNum = new Integer(matches);
				if (tmpList.searchLists.containsKey(new Integer(matches)))
					tmpList.searchLists.get(matches).add(item.pair);
				else {
					ObservableList<Pair<WebView, Object>> newlist = FXCollections.observableArrayList();
					newlist.add(item.pair);
					tmpList.searchLists.put(matchNum, newlist);
				}
			}
		}
		return tmpList;
	}

	public void readCSV(File file) {
		// String csvFile = "src/main/resources/com/flimflam/" + file;
		String line = "";
		String genresStr = "";
		String actorsStr = "";
		String[] actorsArr;
		String[] genresArr;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			FileWriter fil = new FileWriter("file1.txt");
			JSONArray ja = new JSONArray();

			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if (values[0].toLowerCase().trim().equals("\"position\""))
					continue;
				FetchItem fi = new FetchItem(values[1].substring(1, values[1].length() - 1));
				Item item = new Item(fi.itemString);

				// add json objects to jsonarray
				ja.add(item.json);
				// System.out.println("\nJSON Object: " +
				// item.json.get("Title"));

				genresStr = item.json.get("Genre").toString();
				genresArr = genresStr.split(", ");
				for (String s : genresArr)
					this.genres.add(s);

				actorsStr = item.json.get("Actors").toString();
				actorsArr = actorsStr.split(", ");
				for (String s : actorsArr) {
					// System.out.println("-" + s+ "-");
					this.actors.add(s);
				}

				add(item);
			}

			File fill = new File("preMaster.txt");
			Writer output = new BufferedWriter(new FileWriter(fill));
			output.write(ja.toJSONString());
			output.close();

			// fil.write(ja.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readPreMaster(){
		JSONParser parser = new JSONParser();
		File file = new File("preMaster.txt");
		String genresStr = "";
		String actorsStr = "";
		String[] actorsArr;
		String[] genresArr;
		
		try {
//			System.out.println("Reading JSON file from Java program");
			FileReader fileReader = new FileReader(file);
			JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
			Iterator iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JSONObject json = (JSONObject)iterator.next();
				System.out.println("reading...");
				
				Item item = new Item(json);
				genresStr = item.json.get("Genre").toString();
				genresArr = genresStr.split(", ");
				for (String s : genresArr)
					this.genres.add(s);

				actorsStr = item.json.get("Actors").toString();
				actorsArr = actorsStr.split(", ");
				for (String s : actorsArr) {
					// System.out.println("-" + s+ "-");
					this.actors.add(s);
				}

				add(item);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void printList() {
		for (Item item : this.arrList) {
			item.printItem();
		}
	}
}
