package com.flimflam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchItem {
	public String itemString;
	
	FetchItem(String ID) {
//		System.out.println(ID.substring(1, ID.length()-1));
		URL omdbapi;
		try {
			omdbapi = new URL(URLer(ID));
		BufferedReader in = new BufferedReader(new InputStreamReader(omdbapi.openStream()));
		itemString = in.readLine();
//		System.out.println("C:FetchItem - itemString:"+itemString);
		in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Fetching error.");
		}
	}
	
	private String URLer(String ID){
//		System.out.println("http://www.omdbapi.com/?i=" + ID.substring(1, ID.length()-1) + "&plot=full&r=json&apikey=2819c61f");
		return "http://www.omdbapi.com/?i=" + ID.substring(1, ID.length()-1) + "&plot=full&r=json&apikey=2819c61f";
	}
}
