package com.flimflam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Tester extends Application {
	private ComboBox<String> combo = new ComboBox<>();

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, IOException, ParseException {

		JSONParser parser = new JSONParser();
		File file = new File("preMaster.txt");
		try {
			System.out.println("Reading JSON file from Java program");
			FileReader fileReader = new FileReader(file);
			JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
			Iterator iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JSONObject json = (JSONObject)iterator.next();
				System.out.println(json.toJSONString());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		StackPane root = new StackPane();
		// root.getChildren().add(hb);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		// primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}