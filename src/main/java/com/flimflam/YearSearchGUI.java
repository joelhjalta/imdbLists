package com.flimflam;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class YearSearchGUI {

	public HBox yearBox = null;
	
	YearSearchGUI(){
		TextField input = new TextField ();
		input.setPromptText("Year");
		final Tooltip gTip = new Tooltip("Greater than or equal to. (checking both means =)");
		final Tooltip lTip = new Tooltip("Less than or equal to. (checking both means =)");		
		
		CheckBox g = new CheckBox("<=");
		g.setTooltip(gTip);
		CheckBox l = new CheckBox(">=");
		l.setTooltip(lTip);
		
		this.yearBox = new HBox(input, g, l);
		this.yearBox.setSpacing(10);
	}
}
