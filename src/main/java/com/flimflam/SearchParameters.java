package com.flimflam;

import java.util.HashSet;

class SearchParameters {
	private double imdbRating = 0.0;
	private int year = 0;
	private boolean g;
	private boolean l;
	public HashSet<String> genres = new HashSet<String>();
	private boolean tv = true;
	private boolean mov = true;
	private String actor = "";

	public int validateItem(Item item, SearchParameters sp) {
		int ret = 0;
//		System.out.println("sp.actor isEmpty:" + sp.actor.isEmpty());
		if (!(sp.tv && sp.mov) && !(validateType(item, sp))) {
			// System.out.println("type mismatch");
			return -1;
		}

		else if ((sp.imdbRating != 0.0) && !(validateRating(item, sp)))
			return -1;

		else if ((sp.year != 0) && !(validateYear(item, sp)))
			return -1;
		
		else if (!(sp.actor.isEmpty()) && !validateActor(item, sp))
			return -1;

		else if (!(sp.genres.isEmpty())) {
			// System.out.println("genres empty");
			ret = (validateGenre(item, sp));
		}

		return ret;
	}

	private boolean validateType(Item item, SearchParameters sp) {
		// System.out.println(item.json.get("Type").toString() + " - sp.tv: " +
		// sp.tv + " sp.mov: " + sp.mov);
		if ((sp.tv) && !(sp.mov) && (item.json.get("Type").toString().equals("series")))
			return true;

		else if ((sp.mov) && !(sp.tv) && (item.json.get("Type").toString().equals("movie")))
			return true;

		return false;
	}

	private int validateGenre(Item item, SearchParameters sp) {
		int matches = -1;

		String[] genresArr = item.json.get("Genre").toString().split(",");
		for (String g : genresArr)
			if (sp.genres.contains(g.trim()))
				matches++;

		return matches;
	}

	private boolean validateActor(Item item, SearchParameters sp) {
//		System.out.println("Validating actor");
		if (sp.actor.isEmpty()){
//			System.out.println("sp.actor isEmpty");
			return true;
		}

		 else if(item.json.get("Actors").toString().contains(sp.actor)){
//			 System.out.println("Actor match - sp.actor:" + sp.actor + " item actor:" + item.json.get("Actors").toString());
			 return true;
		 }

//		System.out.println("Actor mismatch, returning false");
		return false;
	}

	private boolean validateYear(Item item, SearchParameters sp) {
		if (sp.year == 0)
			return true;

		// System.out.println("sp.year: " + sp.year);
		int y = 0;
		int varLength = item.json.get("Year").toString().length();
		if (varLength == 4)
			y = Integer.parseInt(item.json.get("Year").toString());

		else if (varLength == 7)
			y = Integer.parseInt(item.json.get("Year").toString().substring(0, 4));

		else if (varLength == 11)
			return validatePeriod(item, sp);

		// System.out.println("y: " + y);
		if (y == sp.year) {
			// System.out.println("y: " + y + " sp.year: " + sp.year);
			return true;
		}

		else if (sp.g && y >= sp.year)
			return true;

		else if (sp.l && y <= sp.year)
			return true;

		return false;
	}

	private boolean validatePeriod(Item item, SearchParameters sp) {
		int from = Integer.parseInt(item.json.get("Year").toString().substring(0, 4));
		int to = Integer.parseInt(item.json.get("Year").toString().substring(7, 11));

		if ((sp.year >= from) && (sp.year <= to))
			return true;

		return false;
	}

	private boolean validateRating(Item item, SearchParameters sp) {
		if (item.json.get("imdbRating").equals("N/A")) {
			return true;
		} else if (Double.parseDouble(item.json.get("imdbRating").toString()) >= sp.imdbRating) {
			return true;
		}

		// System.out.println("Rating mismatch");
		return false;
	}

	public void resetParameters() {
		this.imdbRating = 0.0;
		this.year = 0;
		this.g = false;
		this.l = false;
		this.tv = true;
		this.mov = true;
		this.genres.clear();
		this.actor = "";
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

	public int getYear() {
		return this.year;
	}

	public void setG(boolean setting) {
		this.g = setting;
	}

	public void setL(boolean setting) {
		this.l = setting;
	}

	public void setTV(boolean setting) {
		this.tv = setting;
	}

	public void setMov(boolean setting) {
		this.mov = setting;
	}

	public boolean getTV() {
		return this.tv;
	}

	public boolean getMov() {
		return this.mov;
	}

	public String getActor() {
		return this.actor;
	}

	public void setActor(String name) {
		this.actor = name;
	}

	public boolean isEmpty() {
		if ((this.genres.isEmpty()) && (this.getIMDBRating() == 0.0) && (this.getYear() == 0) && (this.tv && this.mov) && (this.actor.isEmpty()))
			return true;
		else
			return false;
	}

}