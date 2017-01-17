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
	// private static final Callback<ResizeFeatures, Boolean>
	// CONSTRAINED_RESIZE_POLICY = null;
	// private CSVReader cr = new CSVReader("WATCHLIST2.csv");
	public List masterList = new List(true);
	public Table table = new Table(masterList);
	private TextField input = new TextField();
	private CheckBox g = null;
	private CheckBox l = null;
	private EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	if (input.getText().isEmpty())
				masterList.sp.setRating(0.0);
			else {
				// System.out.println(input.getText());
				masterList.sp.setRating(Double.parseDouble(input.getText()));
			}
			table.table.getItems().clear();
			List search = masterList.search();
			table.populateTable(search);
			
			System.out.println(g.getText() + ": " + g.isSelected());
        }
    };

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {

		table.populateTable(masterList);

		Button submitBtn = new Button("Submit");
		/*submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// System.out.println("input: " + input.getText());

				if (input.getText().isEmpty())
					masterList.sp.setRating(0.0);
				else {
					// System.out.println(input.getText());
					masterList.sp.setRating(Double.parseDouble(input.getText()));
				}
				table.table.getItems().clear();
				List search = masterList.search();
				table.populateTable(search);
			}

			
		});*/
		submitBtn.setOnAction(eh);
		submitBtn.setStyle("-fx-Alignment: center;");
		
		TextField input = new TextField();
		input.setPromptText("Year");
		final Tooltip gTip = new Tooltip("Greater than or equal to. (checking both means =)");
		final Tooltip lTip = new Tooltip("Less than or equal to. (checking both means =)");

		g = new CheckBox("<=");
		g.setTooltip(gTip);
		l = new CheckBox(">=");
		l.setTooltip(lTip);

		HBox yearBox = new HBox(input, g, l);
		yearBox.setSpacing(10);
		
		input.setPromptText("Rating");

		MenuBtn mb = new MenuBtn(masterList);

		VBox vb = new VBox(mb.menubutton, input, yearBox, submitBtn);
		vb.setSpacing(10);
		vb.setPadding(new Insets(10, 50, 50, 10));
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