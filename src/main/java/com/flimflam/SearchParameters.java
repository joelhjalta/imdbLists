package com.flimflam;

import java.util.HashSet;

class SearchParameters {
	private double imdbRating = 0.0;
	private int year = 0;
	private boolean g;
	private boolean l;
	public HashSet<String> genres = new HashSet<String>();

	// SearchParameters(){
	//
	// }

	public boolean validateItem(Item item, SearchParameters sp) {
		if ((sp.imdbRating != 0.0) && !(validateRating(item, sp))) {
//			System.out.println("rating mismatch");
			return false;
		}

//		else if (!(validateYear(item, sp)))
//			return false;

		else if (!(sp.genres.isEmpty())&& !(validateGenre(item, sp))) {
//			System.out.println("genre mismatch " + sp.genres.toString());
			return false;
		}

		return true;
	}

	private boolean validateGenre(Item item, SearchParameters sp) {
		if (sp.genres.isEmpty())
			return true;

		String[] genresArr = item.json.get("Genre").toString().split(",");
		for (String g : genresArr)
			if (sp.genres.contains(g.trim()))
				return true;

		return false;
	}

	private boolean validateYear(Item item, SearchParameters sp) {
		if (sp.year == 0)
			return true;
		
		else if(Integer.parseInt(item.json.get("Year").toString()) == sp.year)
			return true;
		
		else if(sp.g && Integer.parseInt(item.json.get("Year").toString()) >= sp.year)
			return true;
			
		else if(sp.l && Integer.parseInt(item.json.get("Year").toString()) <= sp.year)
			return true;
		
		
		return false;
	}

	private boolean validateRating(Item item, SearchParameters sp) {
		if (item.json.get("imdbRating").equals("N/A")) {
			return true;
		} else if (Double.parseDouble(item.json.get("imdbRating").toString()) >= sp.imdbRating) {
//			System.out.println(Double.parseDouble(item.json.get("imdbRating").toString()) + " >= " + sp.imdbRating);
			return true;
		}

		return false;
	}
	
	public void setYear(String year, boolean g, boolean l){
		this.year = Integer.parseInt(year);
		this.g = g;
		this.l = l;
	}

	public void setRating(double rating) {
//		System.out.println("Setting rating to: " + rating);
		this.imdbRating = rating;
	}

	public void addGenre(String genre) {
		genres.add(genre);
	}

	public void removeGenre(String genre) {
		genres.remove(genre);
	}

	public double getIMDBRating() {
		return imdbRating;
	}

}