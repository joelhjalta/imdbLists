package com.flimflam;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.util.Pair;

class Item {
	public JSONObject json;
	public Pair pair;

	Item(String itemStr) {

//		System.out.println("Item:26 - itemStr: " + itemStr);
		try {
			this.json = (JSONObject) new JSONParser().parse(itemStr);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JSON parse exception.");
		}
		checkPoster();
		this.pair = pair(new Webs(this.json), new Image(this.json.get("Poster").toString()));
	}
	
	Item(JSONObject jsonObj){
		this.json = jsonObj;
		checkPoster();
		this.pair = pair(new Webs(this.json), new Image(this.json.get("Poster").toString()));
	}

	public void printItem() {
		Iterator it = this.json.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	private void checkPoster() {
		try{
		if (this.json.get("Poster").toString().equals("N/A"))
			this.json.put("Poster",
					"http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png");
	
		}catch (NullPointerException npe) {
			// TODO: handle exception
			System.out.println("Checkposter() error.");
		}
	}

	public Pair<WebView, Object> pair(Webs web, Object value) {
		return new Pair<>(web.browser, value);
	}
}
