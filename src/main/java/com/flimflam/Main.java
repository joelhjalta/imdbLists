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

	public List masterList;
	public Table table = new Table();
	private MenuBtn menuBtn = new MenuBtn();
	private Button submitBtn = new Button("Submit");
	private Button resetBtn = new Button("Reset");
	private TextField ratingInput = new TextField();
	private TextField yearInput = new TextField();
	private ComboBox<String> combo = new ComboBox<>();
	private ObservableList<String> actorsList = FXCollections.observableArrayList();
//	private TreeSet<String> actors = new TreeSet<String>();
	private CheckBox g = null;
	private CheckBox l = null;
	private CheckBox tv = null;
	private CheckBox mov = null;
	private EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (ratingInput.getText().isEmpty())
				masterList.sp.setRating(0.0);
			else
				masterList.sp.setRating(Double.parseDouble(ratingInput.getText()));

			if (!(yearInput.getText().isEmpty()))
				masterList.sp.setYear(yearInput.getText(), g.isSelected(), l.isSelected());

			else {
				masterList.sp.setYear("0", false, false);
				g.setSelected(false);
				l.setSelected(false);
			}
			
			List search = masterList.search();
			table.table.getItems().clear();
			table.addDataToTable(search);
			
			System.out.println("combo: " + FxUtilTest.getComboBoxValue(combo));
//			System.out.println(combo.getEditor().getText());
		}
	};
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {

		// getHostServices().showDocument("http://www.google.com");
		final FileChooser fileChooser = new FileChooser();
		final Button fileBtn = new Button("Select file");
		fileBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					// System.out.println("absolute file path: " +
					// file.getAbsolutePath());
					masterList = new List(file);
					menuBtn.populateGenresList(masterList);
					table.setMasterList(masterList);
					table.addDataToTable(masterList);

					menuBtn.menubutton.setDisable(false);
					submitBtn.setDisable(false);
					resetBtn.setDisable(false);
					ratingInput.setDisable(false);
					yearInput.setDisable(false);
					g.setDisable(false);
					l.setDisable(false);
					tv.setDisable(false);
					mov.setDisable(false);
					combo.setDisable(false);
				}
				
				actorsList.setAll(masterList.actors);
				combo.setItems(actorsList);
				fileBtn.setDisable(true);
//				for(String s: masterList.actors.keySet())
//					System.out.println(s);
			}
		});
		
		
		
        combo.setDisable(true);
        
//        final EventHandler<KeyEvent> keyEventHandler =
//                new EventHandler<KeyEvent>() {
//                    public void handle(final KeyEvent keyEvent) {
//                        if (keyEvent.getCode() == KeyCode.ENTER) {
////                            setPressed(keyEvent.getEventType()
////                                == KeyEvent.KEY_PRESSED);
//                        	System.out.println("combo: " + FxUtilTest.getComboBoxValue(combo));
//                            keyEvent.consume();
//                        }
//                    }
//                };
//         
//            combo.setOnKeyPressed(keyEventHandler);
//            combo.setOnKeyReleased(keyEventHandler);
//        FxUtilTest.autoCompleteComboBoxPlus(combo, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()) 
//        		|| itemToCompare.getAge().toString().equals(typedText));

//        new AutoCompleteComboBoxListener(combo);
		
		tv = new CheckBox("TV");
		tv.setDisable(true);
		mov = new CheckBox("Movies");
		mov.setDisable(true);
		
		tv.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(newValue==true)
		    	mov.setSelected(oldValue);
		    	masterList.sp.setMov(oldValue);
		    }
		});
		
		mov.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(newValue==true)
		    	tv.setSelected(oldValue);
		    	masterList.sp.setTV(oldValue);
		    }
		});
		
		HBox typesBox = new HBox(tv, mov);
		typesBox.setSpacing(10);

		submitBtn.setOnAction(eh);
		submitBtn.setStyle("-fx-Alignment: center;");
		submitBtn.setDisable(true);
		menuBtn.menubutton.setDisable(true);

		resetBtn.setDisable(true);
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent e) {
				menuBtn.resetBoxes();
				yearInput.clear();
				ratingInput.clear();
				g.setSelected(false);
				l.setSelected(false);
				tv.setSelected(false);
				mov.setSelected(false);
				masterList.sp.resetParameters();
				table.table.getItems().clear();
				table.addDataToTable(masterList);
			}
		});

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
		
		g.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(newValue==true)
		    	l.setSelected(oldValue);
		    }
		});
		
		l.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if(newValue==true)
		    	g.setSelected(oldValue);
		    }
		});

		HBox yearBox = new HBox(yearInput, g, l);
		yearBox.setSpacing(10);

		ratingInput.setPromptText("Rating");
		ratingInput.setMinWidth(50);
		ratingInput.setMaxWidth(50);
		ratingInput.setDisable(true);

		HBox submitResetBox = new HBox(submitBtn, resetBtn);
		submitResetBox.setSpacing(10);

		VBox vb = new VBox(fileBtn, typesBox, menuBtn.menubutton, ratingInput, yearBox, combo, submitResetBox);
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
}