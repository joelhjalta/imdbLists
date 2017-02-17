package com.flimflam;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;

public class Controls {

	public List masterList;
	public Table table;
//	public MenuBtn menuBtn;
	public Button submitBtn = new Button("Submit");
	public Button resetBtn = new Button("Reset");
	public TextField ratingInput = new TextField();
	public TextField yearInput = new TextField();
	public ComboBox<String> combo = new ComboBox<>();
	public ObservableList<String> actorsList = FXCollections.observableArrayList();
	public CheckBox g = null;
	public CheckBox l = null;
	public CheckBox tv = null;
	public CheckBox mov = null;
	public MenuButton genresMenu = new MenuButton("Genre");
	public ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();

	Controls(List master, Table table) {
		this.masterList = master;
		this.table = table;

		setupSubmit();
		setupReset();
		setupRating();
		setupType();
		setupYear();
		populateGenresList();
	}
	
	private void setupSubmit(){
		submitBtn.setOnAction(submitEH);
		submitBtn.setStyle("-fx-Alignment: center;");
	}
	
	private void setupReset(){
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent e) {
				resetGUI();
			}
		});
		
	}
	
	private void setupRating(){
		ratingInput.setPromptText("Rating");
		ratingInput.setMinWidth(50);
		ratingInput.setMaxWidth(50);
		
	}
	
	private void setupType(){
		tv = new CheckBox("TV");
		mov = new CheckBox("Movies");
		
		tv.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					mov.setSelected(oldValue);
				masterList.sp.setMov(oldValue);
			}
		});

		mov.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					tv.setSelected(oldValue);
				masterList.sp.setTV(oldValue);
			}
		});
	}
	
	private void setupYear(){
		yearInput.setPromptText("Year");
		yearInput.setMinWidth(50);
		yearInput.setMaxWidth(50);
		
		final Tooltip gTip = new Tooltip("Greater than or equal to.");
		final Tooltip lTip = new Tooltip("Less than or equal to.");
		g = new CheckBox("<=");
		g.setTooltip(gTip);
		l = new CheckBox(">=");
		l.setTooltip(lTip);
		g.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					l.setSelected(oldValue);
			}
		});

		l.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					g.setSelected(oldValue);
			}
		});
	}
	
	public void resetGUI() {
		resetGenreBoxes();
		yearInput.clear();
		ratingInput.clear();
		g.setSelected(false);
		l.setSelected(false);
		tv.setSelected(false);
		mov.setSelected(false);
		combo.getSelectionModel().clearSelection();
		masterList.sp.resetParameters();
		table.table.getItems().clear();
		table.loadMasterList();
	}
	

	public void populateGenresList() {
		ArrayList<CustomMenuItem> menuItems = new ArrayList<CustomMenuItem>();
		for (String s : masterList.genres) {
			CheckBox checkbox = new CheckBox(s);
			checkbox.setOnAction(genresMenuEH);
			this.boxes.add(checkbox);
			CustomMenuItem cmi = new CustomMenuItem(checkbox);
			cmi.setHideOnClick(false);
			menuItems.add(cmi);
		}
		for (CustomMenuItem cmi : menuItems) {
			genresMenu.getItems().add(cmi);
		}
	}

	private void resetGenreBoxes() {
		for (CheckBox cb : this.boxes) {
			cb.setSelected(false);
		}
	}

	private EventHandler<ActionEvent> submitEH = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
//			System.out.println("submit button");
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
			if (FxUtilTest.getComboBoxValue(combo) != null)
				masterList.sp.setActor(FxUtilTest.getComboBoxValue(combo));

			List search = masterList.search();
			table.table.getItems().clear();
			table.addDataToTable(search);

		}
	};

	

	private EventHandler<ActionEvent> genresMenuEH = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() instanceof CheckBox) {
//				System.out.println("genre menu");
				CheckBox chbx = (CheckBox) event.getSource();
				if (chbx.isSelected())
					masterList.sp.addGenre(chbx.getText());
				else if (!(chbx.isSelected()))
					masterList.sp.removeGenre(chbx.getText());
			}
		}
	};
}
