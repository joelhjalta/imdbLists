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
import javafx.stage.FileChooser;

public class Controls {

	public List masterList;
	public Table table;
	public MenuBtn menuBtn;
	public Button submitBtn;
	public Button resetBtn;
	public TextField ratingInput;
	public TextField yearInput;
	public ComboBox<String> combo;
	public ObservableList<String> actorsList;
	public CheckBox g;
	public CheckBox l;
	public CheckBox tv;
	public CheckBox mov;
	public MenuButton menubutton = new MenuButton("Genre");
	public ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();
	public Button fileBtn = new Button("Select file");

	Controls(List master, Table table) {
		this.masterList = master;
		this.table = table;
		this.menuBtn = new MenuBtn();
		this.submitBtn = new Button("Submit");
		this.resetBtn = new Button("Reset");
		this.ratingInput = new TextField();
		this.yearInput = new TextField();
		this.combo = new ComboBox<>();
		this.actorsList = FXCollections.observableArrayList();
		this.g = null;
		this.l = null;
		this.tv = null;
		this.mov = null;

		submitBtn.setOnAction(submitEH);
		submitBtn.setStyle("-fx-Alignment: center;");

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent e) {
				resetGUI();
			}
		});
		
		final FileChooser fileChooser = new FileChooser();
		
		fileBtn.setOnAction(fileOpenEH);
		
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
		
		populateGenresList();
	}

	public void resetGUI() {
		menuBtn.resetBoxes();
		yearInput.clear();
		ratingInput.clear();
		// g.setSelected(false);
		// l.setSelected(false);
		// tv.setSelected(false);
		// mov.setSelected(false);
		combo.getSelectionModel().clearSelection();
		masterList.sp.resetParameters();
		table.table.getItems().clear();
		table.addDataToTable(masterList);
	}

	public void populateGenresList() {
		ArrayList<CustomMenuItem> menuItems = new ArrayList<CustomMenuItem>();
		for (String s : masterList.genres) {
			CheckBox checkbox = new CheckBox(s);
			checkbox.setOnAction(genresBtnEH);
			this.boxes.add(checkbox);
			CustomMenuItem cmi = new CustomMenuItem(checkbox);
			cmi.setHideOnClick(false);
			menuItems.add(cmi);
		}
		for (CustomMenuItem cmi : menuItems) {
			menubutton.getItems().add(cmi);
		}
	}

	public void resetGenreBoxes() {
		for (CheckBox cb : this.boxes) {
			cb.setSelected(false);
		}
	}

	private EventHandler<ActionEvent> submitEH = new EventHandler<ActionEvent>() {
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
			if (FxUtilTest.getComboBoxValue(combo) != null)
				masterList.sp.setActor(FxUtilTest.getComboBoxValue(combo));

			List search = masterList.search();
			table.table.getItems().clear();
			table.addDataToTable(search);

		}
	};

	private EventHandler<ActionEvent> fileOpenEH = new EventHandler<ActionEvent>() {
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
			if (FxUtilTest.getComboBoxValue(combo) != null)
				masterList.sp.setActor(FxUtilTest.getComboBoxValue(combo));

			List search = masterList.search();
			table.table.getItems().clear();
			table.addDataToTable(search);

		}
	};

	private EventHandler<ActionEvent> genresBtnEH = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() instanceof CheckBox) {
				CheckBox chbx = (CheckBox) event.getSource();
				if (chbx.isSelected())
					masterList.sp.addGenre(chbx.getText());
				else if (!(chbx.isSelected()))
					masterList.sp.removeGenre(chbx.getText());
			}
		}
	};
}
