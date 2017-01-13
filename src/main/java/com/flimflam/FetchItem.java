package com.flimflam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FetchItem {
	public String itemString;
	
	FetchItem(String ID) throws IOException{
		System.out.println(".");
		URL omdbapi = new URL(URLer(ID));
		BufferedReader in = new BufferedReader(new InputStreamReader(omdbapi.openStream()));
		itemString = in.readLine();
		in.close();
	}
	
	private String URLer(String ID){
		return "http://www.omdbapi.com/?i=" + ID + "&plot=full&r=json";
	}
}
