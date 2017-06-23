package com.citi.fx.compare;


import com.citi.fx.compare.components.BinaryCompareComp;
import com.citi.fx.compare.components.DropDown;
import com.citi.fx.compare.components.FileCompareComp;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.citi.fx.compare.components.DirCompareComp;

public class Launcher extends Application {
	
	public static final HBox rootActual = new HBox();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
        HBox topHbox=new HBox();
		ChoiceBox<String> menu = DropDown.getChoiceBox();
		menu.setMinWidth(200);
		menu.getSelectionModel().selectFirst();
		
		topHbox.setPadding(new Insets(10,0,0,40));
		topHbox.setCenterShape(true);
		topHbox.getChildren().addAll(menu);
		
		VBox root = new VBox();
		root.setAlignment(Pos.TOP_LEFT);
		root.setPadding(new Insets(10));
		
	    root.getChildren().addAll(topHbox,rootActual);
        VBox node=new FileCompareComp().createUI();
		rootActual.getChildren().add(node);		

		Scene scene = new Scene(root, 1200, 900);
	
		menu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String type = menu.getItems().get(newValue.intValue());
				
				rootActual.getChildren().clear();
				
				switch(type){
				case "File Compare":					
						VBox node=new FileCompareComp().createUI();
					    rootActual.getChildren().add(node);
						break;
				case "Directory Compare":
						VBox pane=new DirCompareComp().createUI(scene);
					     rootActual.getChildren().add(pane);
						break;
				case "Binary File Compare":
					  VBox pane2=new BinaryCompareComp().createUI(primaryStage);
				      rootActual.getChildren().add(pane2);
						break;
				}
				
			}
		});;
   
		
		primaryStage.centerOnScreen();
		primaryStage.setTitle("CompareIt - Compare Anything");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	

}
