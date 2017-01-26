package com.flimflam;

import java.util.Iterator;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tester extends Application {
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {
		TreeMap<String, String> ts = new TreeMap<String, String>();
		ts.put("Jim Carrey", "The Mask");
		ts.put("Jim Carrey", "Liar Liar");
		ts.put("Kate Beckinsale", "Underworld");
		Iterator iterator = ts.entrySet().iterator();
		
		
        TextField input = new TextField();
        input.setPromptText("Actor name");
//        setGraphic(imageView);
        Scene scene = new Scene(input);
		scene.getStylesheets().add("com/flimflam/application.css");
		stage.setScene(scene);
//		stage.setMaximized(true);
		stage.show();
	}
}