package com.flimflam;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.security.sasl.SaslException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.util.Pair;

public class List implements Serializable {
	public ObservableList<Pair<WebView, Object>> data = FXCollections.observableArrayList();
	public ArrayList<Item> arrList = new ArrayList<>();
	public TreeSet<String> genres = new TreeSet<String>();
	public TreeSet<String> actors = new TreeSet<String>();
	public TreeSet<String> IDset = new TreeSet<String>();
	public TreeMap<Integer, ObservableList<Pair<WebView, Object>>> searchLists = new TreeMap<Integer, ObservableList<Pair<WebView, Object>>>(
			Collections.reverseOrder());
	public SearchParameters sp = new SearchParameters();

	List() {

	}

//	List(boolean master) throws SAXException {
//		// sp = new SearchParameters();
//		readPreMaster();
//		System.out.println("PreMaster read.");
//	}

//	List(File file) {
//		readCSV(file);
//		// sp = new SearchParameters();
//	}

	public void add(Item item) {
		this.data.add(item.pair);
		this.arrList.add(item);
		this.IDset.add(item.json.get("imdbID").toString());
//		System.out.println("-" + item.json.get("imdbID").toString() + "-");
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
		String line = "";
		String genresStr = "";
		String actorsStr = "";
		String[] actorsArr;
		String[] genresArr;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
			//skip the first line
			br.readLine();
			
			JSONArray ja = new JSONArray();

			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				
//				System.out.println("checking for:-" + values[1].substring(2, values[1].length() - 2) + "-");
				if(IDset.contains(values[1].substring(2, values[1].length() - 2))){
					System.out.println("Already there.");
					continue;
				}
				System.out.println("List:116 - " + values[1].substring(1, values[1].length() - 1));
				FetchItem fi = new FetchItem(values[1].substring(1, values[1].length() - 1));
//				System.out.println("List:118 - " + fi.itemString);
				Item item = new Item(fi.itemString);

				// add json objects to jsonarray
				ja.add(item.json);

				genresStr = item.json.get("Genre").toString();
				genresArr = genresStr.split(", ");
				for (String s : genresArr)
					this.genres.add(s);

				actorsStr = item.json.get("Actors").toString();
				actorsArr = actorsStr.split(", ");
				for (String s : actorsArr) {
//					System.out.println("-" + s+ "-");
					this.actors.add(s);
				}

				add(item);
			}

			File fill = new File("preMaster.txt");
			Writer output = new BufferedWriter(new FileWriter(fill));
			output.write(ja.toJSONString());
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("IDset:" + IDset.size());
	}
	
	

	public void readPreMaster() {
		JSONParser parser = new JSONParser();
		File file = new File("preMaster.txt");
		String genresStr = "";
		String actorsStr = "";
		String[] actorsArr;
		String[] genresArr;

		try {
			// System.out.println("Reading JSON file from Java program");
			FileReader fileReader = new FileReader(file);
			JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
			Iterator iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject json = (JSONObject) iterator.next();
//				System.out.println("adding: " + json.get("Title"));

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
			System.out.println("IDset:" + IDset.size());

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
