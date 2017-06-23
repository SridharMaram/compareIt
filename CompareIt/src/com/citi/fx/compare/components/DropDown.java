package com.citi.fx.compare.components;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class DropDown {

	
	public static ChoiceBox<String> getChoiceBox(){
	
		 ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList("File Compare", "Directory Compare", "Binary File Compare"));
		 
		 return cb;
		 
	}
	
}
