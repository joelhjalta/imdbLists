package com.flimflam;

import java.io.File;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public List masterList;
	public Table table = new Table();
	private MenuBtn menuBtn = new MenuBtn();
	private Button submitBtn = new Button("Submit");
	private TextField ratingInput = new TextField();
	private TextField yearInput = new TextField();
	private CheckBox g = null;
	private CheckBox l = null;
	private EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	if (ratingInput.getText().isEmpty())
				masterList.sp.setRating(0.0);
			else 
				masterList.sp.setRating(Double.parseDouble(ratingInput.getText()));
        	
        	if(!(yearInput.getText().isEmpty()))
        		masterList.sp.setYear(yearInput.getText(), g.isSelected(), l.isSelected());
        	
        	else{
        		masterList.sp.setYear("0", false, false);
        		g.setSelected(false);
        		l.setSelected(false);
        	}
        	
        	List search = masterList.search();
			table.table.getItems().clear();
			table.populateTable(search);
			
        }
    };

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {

//		getHostServices().showDocument("http://www.google.com");
		final FileChooser fileChooser = new FileChooser();
        final Button fileBtn = new Button("Select file");
        fileBtn.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
//                        System.out.println("absolute file path: " + file.getAbsolutePath());
                    	masterList = new List(file);
                    	menuBtn.populateGenresList(masterList);
                    	table.setMasterList(masterList);
                    	table.populateTable(masterList);
                    	
                    	menuBtn.menubutton.setDisable(false);
                    	submitBtn.setDisable(false);
                    	ratingInput.setDisable(false);
                    	yearInput.setDisable(false);
                    	g.setDisable(false);
                    	l.setDisable(false);
                    }
                }
            });
		
		submitBtn.setOnAction(eh);
		submitBtn.setStyle("-fx-Alignment: center;");
		submitBtn.setDisable(true);
		menuBtn.menubutton.setDisable(true);
		
		yearInput.setPromptText("Year");
		yearInput.setMinWidth(50);
		yearInput.setMaxWidth(50);
		yearInput.setDisable(true);
		final Tooltip gTip = new Tooltip("Greater than or equal to.");
		final Tooltip lTip = new Tooltip("Less than or equal to.");

		g = new CheckBox("<=");
		g.setTooltip(gTip);
		g.setDisable(true);
		l = new CheckBox(">=");
		l.setTooltip(lTip);
		l.setDisable(true);

		HBox yearBox = new HBox(yearInput, g, l);
		yearBox.setSpacing(10);
		
		ratingInput.setPromptText("Rating");
		ratingInput.setMinWidth(50);
		ratingInput.setMaxWidth(50);
		ratingInput.setDisable(true);


		VBox vb = new VBox(fileBtn, menuBtn.menubutton, ratingInput, yearBox, submitBtn);
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
//		stage.setMaximized(true);
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