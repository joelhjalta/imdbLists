package com.flimflam;

import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Pair;

public class Table {

	public static final String INFO_COLUMN_NAME = "Info";
	public static final String POSTER_COLUMN_NAME = "Poster";
	public final TableView<Pair<WebView, Object>> table = new TableView<>();
	private List master;

	Table() {
		TableColumn<Pair<WebView, Object>, WebView> infoColumn = new TableColumn<>(INFO_COLUMN_NAME);
		infoColumn.setPrefWidth(600);
		infoColumn.setEditable(false);
		TableColumn<Pair<WebView, Object>, Object> posterColumn = new TableColumn<>(POSTER_COLUMN_NAME);
		posterColumn.setSortable(false);
		posterColumn.setPrefWidth(600);
		posterColumn.setEditable(false);
		posterColumn.getStyleClass().add("posterColumn");

		infoColumn.setCellValueFactory(new PairKeyFactory());
		posterColumn.setCellValueFactory(new PairValueFactory());

		table.getColumns().setAll(infoColumn, posterColumn);
		posterColumn.setCellFactory(
				new Callback<TableColumn<Pair<WebView, Object>, Object>, TableCell<Pair<WebView, Object>, Object>>() {
					@Override
					public TableCell<Pair<WebView, Object>, Object> call(
							TableColumn<Pair<WebView, Object>, Object> column) {
						return new PairValueCell();
					}
				});
		table.setFixedCellSize(600.0);
		table.setMaxWidth(1215);
		table.setMinWidth(1215);
		posterColumn.setStyle("-fx-Alignment: center;");
	}

	public void addDataToTable(List search) {
		if (search.searchLists.isEmpty()) {
			System.out.println("No searchLists, loading master list.");
//			search = master;
//			System.out.println("Loading " + search.data.size() + " elements to table.");
//			table.getItems().addAll(search.data);
			this.loadMasterList();
		}

		else
			for (Map.Entry<Integer, ObservableList<Pair<WebView, Object>>> entry : search.searchLists.entrySet()) {
				table.getItems().addAll(entry.getValue());
				System.out.println("Loading " + entry.getValue().size() + " elements to table.");
			}

		// table.setPrefHeight(275);
		// table.getItems().setAll(list.data);
	}

	public void loadMasterList() {
		System.out.println("Loading " + master.data.size() + " elements to table.");
		table.getItems().addAll(master.data);
	}

	public void setMasterList(List list) {
		this.master = list;
	}
}
