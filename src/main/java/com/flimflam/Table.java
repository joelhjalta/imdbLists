package com.flimflam;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Pair;

public class Table {

	public static final String INFO_COLUMN_NAME  = "Info";
    public static final String POSTER_COLUMN_NAME = "Poster";
    public final TableView<Pair<WebView, Object>> table = new TableView<>();
//    private ObservableList<Pair<WebView, Object>> data = FXCollections.observableArrayList();
    private List master;
	
	Table(){
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
		posterColumn.setCellFactory(new Callback<TableColumn<Pair<WebView, Object>, Object>, TableCell<Pair<WebView, Object>, Object>>() {
		    @Override
		    public TableCell<Pair<WebView, Object>, Object> call(TableColumn<Pair<WebView, Object>, Object> column) {
		        return new PairValueCell();
		    }
		});
		table.setFixedCellSize(600.0);
		table.setMaxWidth(1215);
		table.setMinWidth(1215);
		posterColumn.setStyle("-fx-Alignment: center;");
	}
	
	public void populateTable(List list){
//		data = FXCollections.observableArrayList();
//		String poster;
//	    Webs wv;
	    
	    if(list.data.isEmpty()){
	    	System.out.println("List was empty, loading master list.");
	    	list = master;
	    }
	    
	    System.out.println("Populating table with " + list.data.size() + " elements.");
//		for(Item item : list.arrList){
//    		
//    		poster = item.json.get("Poster").toString();
//    		wv = new Webs(item.json);
//    		
//    		if(poster.equals("N/A")) {
////        		System.out.println(item.json.get("Title").toString() + " " + poster);
////        		continue;
//    			poster = "http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png";
//    		}
//    		
//    		data.add( pair(wv.browser, new Image(poster)) );
//    		System.out.println("add");
//    	}
		
    	table.getItems().setAll(list.data);
    	table.setPrefHeight(275);
    }
	
	public void setMasterList(List list){
		this.master = list;
	}
}
