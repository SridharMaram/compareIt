package com.citi.fx.compare.components;

import java.io.File;

import com.citi.fx.compare.file.FileCompare;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class FileCompareComp {

	public FileCompareComp(){
		
	}
	public VBox createUI(){
		HBox topHbox=new HBox();
		
		Button compareBut = new Button("Compare It");
		topHbox.getChildren().addAll(compareBut);
		topHbox.setPadding(new Insets(10,0,0,30));
		VBox root = new VBox();
    	root.setAlignment(Pos.TOP_LEFT);
    	root.setPadding(new Insets(10));
        HBox rootActual = new HBox();
        // left section
        VBox left = new VBox();
        Label labelLeft=new Label("Source File");
        HBox innerHbox = new HBox();
        Label textAreaLeft = new Label();
        textAreaLeft.setPadding(new Insets(4));
        textAreaLeft.setMaxHeight(8);         
        innerHbox.getChildren().addAll(textAreaLeft);
        HTMLEditor textAreaLeftDown = new HTMLEditor();
        textAreaLeftDown.setMinHeight(600); 
        left.setPadding(new Insets(30));
        left.getChildren().addAll(labelLeft,innerHbox,textAreaLeftDown);
 
      //  DragDropConfig.setDragTarget(textAreaLeftDown, textAreaLeftDown,textAreaLeft);
       
        
        // right section
       
        VBox right = new VBox();
        HBox innerHboxRight = new HBox();
        Label labelRight=new Label("Destination File");
        Label textAreaRight = new Label();
        textAreaRight.setMaxHeight(5);      
        innerHboxRight.getChildren().addAll(textAreaRight);
        HTMLEditor textAreaRightDown = new HTMLEditor();
        textAreaRightDown.setMinHeight(600); 
        right.setPadding(new Insets(30));
        right.getChildren().addAll(labelRight,innerHboxRight,textAreaRightDown);
        //DragDropConfig.setDragTarget(textAreaRightDown, textAreaRightDown,textAreaRight);
        
              
        hideHTMLEditorToolbars(textAreaRightDown);
        hideHTMLEditorToolbars(textAreaLeftDown);
         
        compareBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	String inp1=textAreaLeftDown.getHtmlText();
            	String inp2=textAreaRightDown.getHtmlText();

            	if(inp1==null || inp2==null || inp1.isEmpty() || inp2.isEmpty()){
            		return;
            	}
            	inp1=inp1.substring(75);
            	inp2=inp2.substring(75);
            	
            	int idx1=inp1.indexOf('>');
            	inp1=inp1.substring(0,idx1-1);
            	int idx2=inp2.indexOf('>');
            	inp2=inp2.substring(0,idx2-1);
            	String results[]=FileCompare.fileCompare(new File(inp1),new File(inp2));
            	textAreaLeftDown.setHtmlText("");
            	textAreaLeftDown.setHtmlText(results[0]);
            	
            	textAreaRightDown.setHtmlText("");
            	textAreaRightDown.setHtmlText(results[1]);
            }
        });
       
        
        rootActual.getChildren().addAll(left,right); 
        root.getChildren().addAll(topHbox,rootActual);
    
        return root;
	}
	 public static void hideHTMLEditorToolbars(final HTMLEditor editor)
	    {
	        editor.setVisible(false);
	        Platform.runLater(new Runnable()
	        {
	            @Override
	            public void run()
	            {
	                Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
	                for(Node node : nodes)
	                {
	                    node.setVisible(false);
	                    node.setManaged(false);
	                }
	                editor.setVisible(true);
	            }
	        });
	    }  
}
