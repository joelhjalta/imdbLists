package com.flimflam;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import org.controlsfx.control.textfield.AutoCompletionBinding;

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
	    public void start(Stage primaryStage) {
	        
	        TreeSet<String> ts = new TreeSet<String>();
	    	ts.add("Jim Carrey");
	    	ts.add("Jennifer Lawerence");
	    	ts.add("Kate Beckinsale");
	    	ts.add("Brooklyn Decker");
	    	ts.add("Jennifer Aniston");
	    	ts.add("Jessica Alba");
	    	ts.add("Beyonce");
	    	ts.add("Shakira");
	    	ts.add("Katy Perry");
	    	ts.add("Banks");
	    	ts.add("Nicole Scherzinger");
	        
	        ObservableList<String> list = FXCollections.observableArrayList();
	        list.setAll(ts);
	        
	        combo.setItems(list);
	        new AutoCompleteComboBoxListener(combo);
	        
	        Button submit = new Button("Submit");
	        submit.setOnAction(eh);
	        
	        HBox hb = new HBox(combo, submit);

	        StackPane root = new StackPane();
	        root.getChildren().add(hb);

	        Scene scene = new Scene(root, 300, 250);

	        primaryStage.setTitle("Hello World!");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    private EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(combo.getEditor().getText());
			}
		};
	}



//public void start(final Stage stage) throws Exception {
//	TreeMap<String, String> ts = new TreeMap<String, String>();
//	ts.put("Jim Carrey", "The Mask");
//	ts.put("Jennifer Lawerence", "Liar Liar");
//	ts.put("Kate Beckinsale", "Underworld");
//	ts.put("Brooklyn Decker", "The Mask");
//	ts.put("Jennifer Aniston", "Liar Liar");
//	ts.put("Jessica Alba", "Underworld");
//	ts.put("Beyonce", "The Mask");
//	ts.put("Shakira", "Liar Liar");
//	ts.put("Katy Perry", "Underworld");
//	ts.put("Banks", "The Mask");
//	ts.put("Nicole Scherzinger", "Liar Liar");
//	input.setPromptText("Actor name");
//	
//	
//	new AutoCompletionTextFieldBinding(input, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
//		public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
//			return ts.keySet();
//		}
//	});
//	
//	input.setOnAction(eh);