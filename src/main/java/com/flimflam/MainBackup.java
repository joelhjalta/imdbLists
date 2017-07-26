package com.flimflam;

import java.io.File;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainBackup extends Application {

	public List masterList = new List();
	public Table table = new Table(masterList);
	public Controls controls = new Controls(masterList, table);
	public Button fileBtn = new Button("Select file");
	private final FileChooser fileChooser = new FileChooser();
	private HBox typesBox = new HBox();
	private HBox yearBox = new HBox();
	private HBox submitResetBox = new HBox();
	private VBox vb = new VBox();

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {

		File pmFile = new File("preMaster.txt");
		if (pmFile.exists() && !pmFile.isDirectory()) {
			masterList.readPreMaster();
			controls.populateActorsList();
			controls.populateGenresList();
			table.loadMasterList();
		}
		
		else
			controls.disableControls(true);
		

		fileBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					masterList.readCSV(file);
					controls.populateActorsList();
					controls.populateGenresList();
					table.addDataToTable(masterList);
				}

				controls.disableControls(false);
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

		
        GridPane root = new GridPane();
        ColumnConstraints tableColumn = new ColumnConstraints();
        tableColumn.setPercentWidth(20);
        tableColumn.setHalignment(HPos.CENTER);
        root.getColumnConstraints().add(tableColumn);
        root.add(table.table, 0, 0);
        ColumnConstraints controlsColumn = new ColumnConstraints();
        tableColumn.setPercentWidth(80);
        root.getColumnConstraints().add(controlsColumn);
        
                
        RowConstraints rowCon = new RowConstraints();
        rowCon.setPercentHeight(100.0);
//        rowCon.setValignment(VPos.BOTTOM);
        root.getRowConstraints().add(rowCon);
//        GridPane.setFillWidth(table.table, true);

        root.setStyle("-fx-background-image: url('http://texturelib.com/Textures/wood/planks%20new/wood_planks_new_0004_04_preview.jpg')");
        root.add(vb, 1, 0);
        
        Scene scene = new Scene(root, Color.RED);
        scene.getStylesheets().add("com/flimflam/application.css");
        stage.setMaximized(true);
        stage.setScene(scene);
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