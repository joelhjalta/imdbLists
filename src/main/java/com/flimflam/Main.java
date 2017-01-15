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
          	
//              table.table.getItems().clear();
//              masterList.sp.addGenre("Adventure");
//              List search = masterList.search();
//              table.populateTable(search);
          }
      });
      submitBtn.setStyle("-fx-Alignment: center;");
      
      MenuBtn mb = new MenuBtn(masterList);
      
      VBox vb = new VBox(mb.menubutton, submitBtn);
//      Webs web = new Webs("http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png");
      HBox hb = new HBox(table.table, vb);
//      hb.setHgrow(table, Priority.ALWAYS);
      
      // layout the scene.
  	final StackPane layout = new StackPane();
      layout.getChildren().setAll(hb);
      
      Scene scene = new Scene(layout, 1200, 900);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.show();
		 

//		powerSet();
	}

	@Test
	public void powerSet() {
		HashSet<String> strs = new HashSet<String>();
		strs.add("beta");
		strs.add("alpha");
		strs.add("delta");
		strs.add("niner");
		Set<Set<String>> result = Sets.powerSet(strs);
		String tmpstr;
		String[] tmparr;
		Iterator iter = result.iterator();

		while (iter.hasNext()) {
			tmpstr = iter.next().toString();
			// System.out.println(tmpstr);
			tmparr = tmpstr.substring(1, tmpstr.length() - 1).split(",");

			// printArray(tmparr);
			for (int i = 0; i < tmparr.length; i++) {
				// System.out.print("before: -"+s+"-");
				tmparr[i] = tmparr[i].trim();
				// System.out.print("after: -"+s+"-\n");
			}

			System.out.println("before:");
			printArray(tmparr);
			Arrays.sort(tmparr);
			System.out.println("after:");
			printArray(tmparr);
			System.out.println();

			// if (tmparr.length > 1) {
			//// printArray(tmparr);
			// System.out.println(tmparr.hashCode());
			// List<String> list = Arrays.asList(tmparr);
			//
			// //next, reverse the list using Collections.reverse method
			// Collections.reverse(list);
			//
			// //next, convert the list back to String array
			// tmparr = (String[]) list.toArray();
			// printArray(tmparr);
			// System.out.println(tmparr.hashCode() + "\n");
			// }

		}

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