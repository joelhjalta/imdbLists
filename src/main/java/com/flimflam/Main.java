package com.flimflam;

import java.io.File;
import java.io.FileWriter;
import java.util.TreeSet;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public List masterList;
	public Table table = new Table(masterList);
	public Controls controls = new Controls(masterList, table);
	public Button fileBtn = new Button("Select file");
	private final FileChooser fileChooser = new FileChooser();
	private HBox typesBox = new HBox();
	private HBox yearBox = new HBox();
	private HBox submitResetBox = new HBox();
	private VBox vb = new VBox();
	private HBox hb = new HBox();

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {

		File titlesFile = new File("titles.txt");
		if (titlesFile.exists() && !titlesFile.isDirectory()) {
			masterList = new List(true);
			table.setMaster(masterList);
			controls.setMaster(masterList, table);
//			controls.populateActorsList();
//			controls.populateGenresList();
			table.loadMasterList();
		}
		
		else
			controls.toggleControls(false);
		
//		else
//			controls.toggleControls(false);
		// getHostServices().showDocument("http://www.google.com");

		fileBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					// System.out.println("absolute file path: " +
					// file.getAbsolutePath());
					masterList = new List(file);
					table.setMasterList(masterList);
					table.addDataToTable(masterList);
					controls.setMaster(masterList, table);
//					controls.populateGenresList();
//					controls.actorsList.setAll(masterList.actors);
//					controls.actorsMenu.setItems(controls.actorsList);
					fileBtn.setDisable(true);
				}

				// for(String s: masterList.actors.keySet())
				// System.out.println(s);
			}
		});

		typesBox = new HBox(controls.tv, controls.mov);
		yearBox = new HBox(controls.yearInput, controls.g, controls.l);
		submitResetBox = new HBox(controls.submitBtn, controls.resetBtn);
		vb = new VBox(fileBtn, typesBox, controls.genresMenu, controls.ratingInput, yearBox, controls.actorsMenu,
				submitResetBox);
		typesBox.setSpacing(10);
		yearBox.setSpacing(10);
		submitResetBox.setSpacing(10);

		vb.setSpacing(10);
		vb.setPadding(new Insets(10, 50, 50, 10));
		vb.setMaxWidth(300);
		vb.setMinWidth(300);

		vb.setMaxHeight(900);
		vb.setMinHeight(900);
		hb.getChildren().add(table.table);
		hb.getChildren().add(vb);

		final StackPane layout = new StackPane();
		layout.getChildren().setAll(hb);

		Scene scene = new Scene(layout);
		scene.getStylesheets().add("com/flimflam/application.css");
		stage.setScene(scene);
		// stage.setMaximized(true);
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