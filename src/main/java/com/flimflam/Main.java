package com.flimflam;

import java.io.File;
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

	public List masterList = new List(true);
	public Table table = new Table();
	public Controls controls;
//	private MenuBtn menuBtn = new MenuBtn();
//	private Button submitBtn = new Button("Submit");
//	private Button resetBtn = new Button("Reset");
//	private TextField ratingInput = new TextField();
//	private TextField yearInput = new TextField();
//	private ComboBox<String> combo = new ComboBox<>();
//	private ObservableList<String> actorsList = FXCollections.observableArrayList();
//	private CheckBox g = null;
//	private CheckBox l = null;
//	private CheckBox tv = null;
//	private CheckBox mov = null;

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {

		masterList.readPreMaster();
		controls = new Controls(masterList, table);
//		controls.populateGenresList();
		table.setMasterList(masterList);
		table.loadMasterList();
		controls.actorsList.setAll(masterList.actors);
		controls.combo.setItems(controls.actorsList);

		// getHostServices().showDocument("http://www.google.com");
//		final FileChooser fileChooser = new FileChooser();
//		final Button fileBtn = new Button("Select file");
//		fileBtn.setOnAction(fileOpenEH);

//		controls.tv = new CheckBox("TV");
//		controls.mov = new CheckBox("Movies");


//		controls.tv.selectedProperty().addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (newValue == true)
//					controls.mov.setSelected(oldValue);
//				masterList.sp.setMov(oldValue);
//			}
//		});
//
//		controls.mov.selectedProperty().addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (newValue == true)
//					controls.tv.setSelected(oldValue);
//				masterList.sp.setTV(oldValue);
//			}
//		});

		HBox typesBox = new HBox(controls.tv, controls.mov);
		typesBox.setSpacing(10);

//		controls.submitBtn.setOnAction(submitEH);
//		controls.submitBtn.setStyle("-fx-Alignment: center;");
//
//		controls.resetBtn.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(final ActionEvent e) {
//				resetGUI();
//			}
//		});

		controls.yearInput.setPromptText("Year");
		controls.yearInput.setMinWidth(50);
		controls.yearInput.setMaxWidth(50);
		// yearInput.setDisable(true);
		final Tooltip gTip = new Tooltip("Greater than or equal to.");
		final Tooltip lTip = new Tooltip("Less than or equal to.");

		controls.g = new CheckBox("<=");
		controls.g.setTooltip(gTip);
		// g.setDisable(true);
		controls.l = new CheckBox(">=");
		controls.l.setTooltip(lTip);
		// l.setDisable(true);

		controls.g.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					controls.l.setSelected(oldValue);
			}
		});

		controls.l.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					controls.g.setSelected(oldValue);
			}
		});

		HBox yearBox = new HBox(controls.yearInput, controls.g, controls.l);
		yearBox.setSpacing(10);

		controls.ratingInput.setPromptText("Rating");
		controls.ratingInput.setMinWidth(50);
		controls.ratingInput.setMaxWidth(50);
		// ratingInput.setDisable(true);

		HBox submitResetBox = new HBox(controls.submitBtn, controls.resetBtn);
		submitResetBox.setSpacing(10);

		VBox vb = new VBox(controls.fileBtn, typesBox, controls.menuBtn.menubutton, controls.ratingInput, yearBox, controls.combo, submitResetBox);
		vb.setSpacing(10);
		vb.setPadding(new Insets(10, 50, 50, 10));
		vb.setMaxWidth(300);
		vb.setMinWidth(300);

		table.table.setMaxHeight(900);
		table.table.setMinHeight(900);
		vb.setMaxHeight(900);
		vb.setMinHeight(900);
		HBox hb = new HBox(table.table, vb);

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

//	public void resetGUI() {
//		controls.menuBtn.resetBoxes();
//		controls.yearInput.clear();
//		controls.ratingInput.clear();
//		// g.setSelected(false);
//		// l.setSelected(false);
//		// tv.setSelected(false);
//		// mov.setSelected(false);
//		controls.combo.getSelectionModel().clearSelection();
//		masterList.sp.resetParameters();
//		table.table.getItems().clear();
//		table.addDataToTable(masterList);
//	}

	
}