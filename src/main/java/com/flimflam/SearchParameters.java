package com.flimflam;

import java.util.HashSet;

class SearchParameters {
	private double imdbRating = 0.0;
	private String ratingEquality = "NA";
	private int year=0, fromYear=0, toYear=0;
	public HashSet<String> genres = new HashSet<String>();
	
//	SearchParameters(){
//		
//	}
	
	public boolean validateItem(Item item, SearchParameters sp){
		if( !(validateRating(item, sp)))
			return false;
			
		else if( !(validateYear(item, sp)))
			return false;
		
		else if( !(validateGenre(item, sp)))
			return false;
		
		return true;
	}
	
	private boolean validateGenre(Item item, SearchParameters sp){
		if( !(sp.genres.isEmpty())){
			String[] genresArr = item.json.get("Genre").toString().split(",");
			for(String g : genresArr)
				if(sp.genres.contains(g.trim()))
					return true;
		}
		
		return false;
	}
	
	private boolean validateYear(Item item, SearchParameters sp){
		if ( (sp.year != 0) && (Integer.parseInt(item.json.get("Year").toString()) != sp.year) )
			return false;
		
		else if(sp.fromYear!=0)
			if(Integer.parseInt(item.json.get("Year").toString()) < sp.fromYear)
				return false;
			else if(Integer.parseInt(item.json.get("Year").toString()) > sp.toYear)
				return false;
		return true;
	}
	
	private boolean validateRating(Item item, SearchParameters sp){
		if(sp.imdbRating!=0.0){
			if(sp.ratingEquality.equals("g")){
				if(Double.parseDouble(item.json.get("imdbRating").toString()) < sp.imdbRating )
					return false;
			}
			else if(sp.ratingEquality.equals("l"))
				if(Double.parseDouble(item.json.get("imdbRating").toString()) >= sp.imdbRating )
					return false;
			else if((sp.ratingEquality.equals("NA")) || (sp.ratingEquality.equals("e")))
				if(Double.parseDouble(item.json.get("imdbRating").toString()) != sp.imdbRating )
					return false;
		}
		
		return true;
	}
	
	public void setRating(double rating, String equality){
		this.imdbRating = rating;
		this.ratingEquality = equality;
	}
	
	public void addGenre(String genre){
		genres.add(genre);
	}
	
	public void removeGenre(String genre){
		genres.remove(genre);
	}
	
	public double getIMDBRating(){
		return imdbRating;
	}
	
	public String getRatingEquality(){
		return ratingEquality;
	}
}