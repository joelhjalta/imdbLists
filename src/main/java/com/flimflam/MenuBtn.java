package com.flimflam;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;

public class MenuBtn {
	public MenuButton menubutton = new MenuButton ("Genre") ;
	private ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();
	private List master;
	private EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	if (event.getSource() instanceof CheckBox) {
                CheckBox chbx = (CheckBox) event.getSource();
//                System.out.println(chbx.getText() + " - " + chbx.isSelected());
                
                if(chbx.isSelected())
                	master.sp.addGenre(chbx.getText());
                else if(!(chbx.isSelected()))
                	master.sp.removeGenre(chbx.getText());
                
//                List search = Main.masterList.search();
//                System.out.println("Sending list with " + search.arrList.size() + " elements.");
//                Main.table.table.getItems().clear();
//                Main.table.populateTable(search);
            }
        }
    };
	
	MenuBtn(){
		
	}
	
	public void resetBoxes(){
		for (CheckBox cb : this.boxes) {
			cb.setSelected(false);
		}
	}
	
	public void populateGenresList(List list){
		master = list;
		ArrayList<CustomMenuItem> menuItems = new ArrayList<CustomMenuItem>();
		for(String s: list.genres){
	    	CheckBox checkbox = new CheckBox(s);
	    	checkbox.setOnAction(eh);
	    	this.boxes.add(checkbox);
	    	CustomMenuItem cmi = new CustomMenuItem ( checkbox ) ;
	    	cmi.setHideOnClick(false);
	    	menuItems.add(cmi);
	    }
		for(CustomMenuItem cmi: menuItems){
			menubutton.getItems().add(cmi);
		}
	}
}
