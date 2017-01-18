package com.flimflam;

import java.io.File;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
//testing
public class Main extends Application {

	// private static final Callback<ResizeFeatures, Boolean>
	// CONSTRAINED_RESIZE_POLICY = null;
	// private CSVReader cr = new CSVReader("WATCHLIST2.csv");
	// private static final Callback<ResizeFeatures, Boolean>
	// CONSTRAINED_RESIZE_POLICY = null;
	// private CSVReader cr = new CSVReader("WATCHLIST2.csv");
	public List masterList = new List(true);
	public Table table = new Table(masterList);
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

		table.populateTable(masterList);

		Button submitBtn = new Button("Submit");
		submitBtn.setOnAction(eh);
		submitBtn.setStyle("-fx-Alignment: center;");
		
		yearInput.setPromptText("Year");
		yearInput.setMinWidth(50);
		yearInput.setMaxWidth(50);
		final Tooltip gTip = new Tooltip("Greater than or equal to.");
		final Tooltip lTip = new Tooltip("Less than or equal to.");

		g = new CheckBox("<=");
		g.setTooltip(gTip);
		l = new CheckBox(">=");
		l.setTooltip(lTip);

		HBox yearBox = new HBox(yearInput, g, l);
		yearBox.setSpacing(10);
		
		ratingInput.setPromptText("Rating");
		ratingInput.setMinWidth(50);
		ratingInput.setMaxWidth(50);

		MenuBtn mb = new MenuBtn(masterList);

		VBox vb = new VBox(mb.menubutton, ratingInput, yearBox, submitBtn);
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