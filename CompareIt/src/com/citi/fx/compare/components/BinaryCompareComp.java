package com.citi.fx.compare.components;

import java.io.File;

import com.citi.fx.compare.file.FileCompare;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BinaryCompareComp {

	private File file1;
	private File file2;
	
	public BinaryCompareComp(){
		file1=file2=null;
	}
	
	

    public VBox createUI(Stage stage) {
 
    	HBox hboxTop=new HBox(10);
    	Button compDirButton=new Button("Compare It");
    	
    	Button dirDrowseButton=new Button("Select Source File");
    	Button dirDrowseButton2=new Button("Select Destination File");
    	
    	hboxTop.getChildren().addAll(dirDrowseButton,dirDrowseButton2,compDirButton);
    	hboxTop.setPadding(new Insets(10,20,0,35));
    	dirDrowseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	 final FileChooser fileChooser = new FileChooser();
            	File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                   file1=file;
                }
        		 
            }
        });
    	dirDrowseButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	 final FileChooser fileChooser = new FileChooser();
             	File file = fileChooser.showOpenDialog(stage);
                 if (file != null) {
                    file2=file;
                 }
        		 
        		 
            }
        });
       
    		 
        HBox hbox = new HBox();     
	
  
     hbox.setCenterShape(true);
     hbox.setSpacing(60);
     
        hbox.setPadding(new Insets(45));
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.getChildren().addAll(hboxTop,hbox);
 
        compDirButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	hbox.getChildren().clear();
            	boolean equal=FileCompare.binaryFileCompare(file1, file2);
            	if(equal){
            		hbox.getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/equal.png"))));
            	}else{
            		hbox.getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/unequal.png"))));
            	}
            	

            }
        });
        
        return root;
    }
   
}
