package com.flimflam;

import java.util.HashSet;

class SearchParameters {
	private double imdbRating = 0.0;
	private int year = 0;
	private boolean g;
	private boolean l;
	private String type = "";
	public HashSet<String> genres = new HashSet<String>();


	public int validateItem(Item item, SearchParameters sp) {
		int ret = -1;
//		if ((sp.type.length()>0) && !(validateType(item, sp)))
//			ret = -1;
		
		if ((sp.imdbRating != 0.0) && !(validateRating(item, sp)))
			ret = -1;

		else if ((sp.year != 0) && !(validateYear(item, sp)))
			ret = -1;

		else if (!(sp.genres.isEmpty())){ 
			ret = (validateGenre(item, sp));
		}

		return ret;
	}

	private boolean validateType(Item item, SearchParameters sp){
	
		if(sp.type.equals(item.json.get("Type")))
			return true;
		return false;
	}
	
	private int validateGenre(Item item, SearchParameters sp) {
		int matches=-1;

		String[] genresArr = item.json.get("Genre").toString().split(",");
		for (String g : genresArr)
			if (sp.genres.contains(g.trim()))
				matches++;

		return matches;
	}

	private boolean validateYear(Item item, SearchParameters sp) {
		if (sp.year == 0)
			return true;
		
		int y = 0;
		int varLength = item.json.get("Year").toString().length();
		if(varLength == 4 )
			y = Integer.parseInt(item.json.get("Year").toString());
		
		else if(varLength == 7)
			y = Integer.parseInt(item.json.get("Year").toString().substring(0, 4));
		
		else if(varLength == 11)
			return validatePeriod(item, sp);
			
		if(y == sp.year)
			return true;
		
		else if(sp.g && y >= sp.year)
			return true;
			
		else if(sp.l && y <= sp.year)
			return true;
		
		
		return false;
	}
	
	private boolean validatePeriod(Item item, SearchParameters sp){
		int from = Integer.parseInt(item.json.get("Year").toString().substring(0, 4));
		int to = Integer.parseInt(item.json.get("Year").toString().substring(7, 11));
		
		if((sp.year >= from) && (sp.year <= to))
			return true;
		
		return false;
	}

	private boolean validateRating(Item item, SearchParameters sp) {
		if (item.json.get("imdbRating").equals("N/A")) {
			return true;
		} else if (Double.parseDouble(item.json.get("imdbRating").toString()) >= sp.imdbRating) {
			return true;
		}

		return false;
	}

	public void resetParameters(){
		 this.imdbRating = 0.0;
		 this.year = 0;
		 this.g = false;
		 this.l = false;
		 genres.clear();
	}

	public void setYear(String yearInput, boolean gInput, boolean lInput) {
		this.year = Integer.parseInt(yearInput);
		this.g = gInput;
		this.l = lInput;
	}

	public void setRating(double rating) {
		this.imdbRating = rating;
	}

	public void addGenre(String genre) {
		genres.add(genre);
	}

	public void removeGenre(String genre) {
		genres.remove(genre);
	}

	public double getIMDBRating() {
		return this.imdbRating;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public void setG(boolean setting){
		this.g = setting;
	}
	
	public void setL(boolean setting){
		this.l = setting;
	}

}