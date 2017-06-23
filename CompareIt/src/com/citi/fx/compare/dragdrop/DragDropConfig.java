package com.citi.fx.compare.dragdrop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.web.HTMLEditor;
import javafx.scene.control.Label;

public class DragDropConfig {

	
	public static void setDragTarget(Node dragTarget,Node dropTarget,Label label){
		
		 dragTarget.setOnDragOver(new EventHandler<DragEvent>() {

	            @Override
	            public void handle(DragEvent event) {
	                if (event.getGestureSource() != dragTarget
	                        && event.getDragboard().hasFiles()) {
	                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	                }
	                
	                event.consume();
	            }
	        });

	        dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {

	            @Override
	            public void handle(DragEvent event) {
	                Dragboard db = event.getDragboard();
	                boolean success = false;
	               /* if (db.hasFiles()) {
	                	try {
	                		//label.setText(db.getFiles().get(0).getAbsolutePath());
	                		
							writeToTextArea(db.getFiles().get(0),(HTMLEditor)dropTarget);
						} catch (IOException e) {
							e.printStackTrace();
						}
	                    success = true;
	                }	     */        
	                event.setDropCompleted(success);

	                event.consume();
	            }
	        });
	        
	}
	
	 private static void writeToTextArea(File file , HTMLEditor area) throws IOException{
	    	try(BufferedReader br=new BufferedReader(new FileReader(file))) {

			    StringBuilder temp=new StringBuilder();
			    String tmp=null;
				while((tmp=br.readLine())!=null){
				
					temp.append(tmp+"\n");	
				}
				
				area.setHtmlText(temp.toString());

			} 
	    }
	 
}
