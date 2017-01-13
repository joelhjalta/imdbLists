package com.flimflam;
	
import java.io.File;
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
    
//	private static final Callback<ResizeFeatures, Boolean> CONSTRAINED_RESIZE_POLICY = null;
//    private CSVReader cr = new CSVReader("WATCHLIST2.csv");
	public  List masterList = new List(true);
	public  Table table = new Table(masterList);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(final Stage stage) throws Exception {
        
        table.populateTable(masterList);
        
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	List search = masterList.search();
                table.table.getItems().clear();
                table.populateTable(search);
            	
//                table.table.getItems().clear();
//                masterList.sp.addGenre("Adventure");
//                List search = masterList.search();
//                table.populateTable(search);
            }
        });
        submitBtn.setStyle("-fx-Alignment: center;");
        
        MenuBtn mb = new MenuBtn(masterList);
        
        VBox vb = new VBox(mb.menubutton, submitBtn);
//        Webs web = new Webs("http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png");
        HBox hb = new HBox(table.table, vb);
//        hb.setHgrow(table, Priority.ALWAYS);
        
        // layout the scene.
    	final StackPane layout = new StackPane();
        layout.getChildren().setAll(hb);
        
        Scene scene = new Scene(layout, 1200, 900);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
//        powerSet();
    }
    
    @Test
    public void powerSet() {
    	for(String s : masterList.genres)
    		System.out.println(s);
        Set<Set<String>> result = Sets.powerSet(masterList.genres);
     
        Iterator iter = result.iterator();
        while(iter.hasNext()){
        	System.out.println(iter.next());
        }
     
    }
    
    public void printFileInDir(String directory){
    	File file = new File(directory);
    	for(String fileNames : file.list()) System.out.println(fileNames);
    }
}