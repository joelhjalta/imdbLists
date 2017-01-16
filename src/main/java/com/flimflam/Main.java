package com.flimflam;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.GroupLayout.Alignment;

import org.junit.Test;

import javafx.application.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


public class Main extends Application {

	// private static final Callback<ResizeFeatures, Boolean>
	// CONSTRAINED_RESIZE_POLICY = null;
	// private CSVReader cr = new CSVReader("WATCHLIST2.csv");
//	private static final Callback<ResizeFeatures, Boolean> CONSTRAINED_RESIZE_POLICY = null;
//  private CSVReader cr = new CSVReader("WATCHLIST2.csv");
	public  List masterList = new List(true);
	public  Table table = new Table(masterList);
	private TextField input = new TextField ();

  public static void main(String[] args) throws Exception {
      launch(args);
  }

  public void start(final Stage stage) throws Exception {
      
      table.populateTable(masterList);
      
      Button submitBtn = new Button("Submit");
      submitBtn.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent e) {
        	  List search = masterList.search();
//	          System.out.println("input: " + input.getText());
	          Double rating=null;
	          
	          if(input.getText().isEmpty())
	        	  masterList.sp.setRating(0.0);
	          else{ 
//	        	  System.out.println(input.getText());
	        	  masterList.sp.setRating(Double.parseDouble(input.getText()));
	          }
	          table.table.getItems().clear();
	          table.populateTable(search);
          }
      });
      submitBtn.setStyle("-fx-Alignment: center;");
      
      input.setPromptText("Rating");
      
      MenuBtn mb = new MenuBtn(masterList);
      
      VBox vb = new VBox(mb.menubutton, input , submitBtn);
      HBox hb = new HBox(table.table, vb);
      
      final StackPane layout = new StackPane();
      layout.getChildren().setAll(hb);
      
      Scene scene = new Scene(layout, 1200, 900);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.show();
		 
	}

	public void printArray(String[] arr) {
		for (String s : arr)
			System.out.print(s);
		System.out.println();
	}

	public void printFileInDir(String directory) {
		File file = new File(directory);
		for (String fileNames : file.list())
			System.out.println(fileNames);
	}
}