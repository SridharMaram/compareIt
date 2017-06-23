package com.citi.fx.compare.components;

import java.io.File;
import java.util.List;

import com.citi.fx.compare.file.FolderAlgo;
import com.citi.fx.compare.model.TreeNode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class DirCompareComp {

	private File dir1;
	private File dir2;
	
	public DirCompareComp(){
		dir1=null;
		dir2=null;
	}
	
	
	private void buildTree(TreeNode node, TreeItem<String> rootItem){
		List<TreeNode> items=node.items;
		
		
		for(TreeNode node1:items){
			TreeItem<String> tmp = null;
			if(node1.isDir){
				if(node1.isDiff){
					tmp = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/folder-diff.png"))));
				}else{
				tmp = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/folder.png"))));
				}
				tmp.setExpanded(true);
				buildTree(node1,tmp);
			}else {
				if(node1.isDiff){
					tmp = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/diff-file.png"))));	
				}else{
				tmp = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/file.png"))));	
				}
			}
			rootItem.getChildren().add(tmp);
		}
		
	
	}
    public VBox createUI(Scene scene) {
 
    	HBox hboxTop=new HBox(10);
    	Button compDirButton=new Button("Compare It");
    	
    	Button dirDrowseButton=new Button("Select Source Dir");
    	Button dirDrowseButton2=new Button("Select Destination Dir");
    	
    	hboxTop.getChildren().addAll(dirDrowseButton,dirDrowseButton2,compDirButton);
    	hboxTop.setPadding(new Insets(10,20,0,35));
    	dirDrowseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	  DirectoryChooser chooser = new DirectoryChooser();
        		  chooser.setInitialDirectory(new File("C:\\"));
        		  File file = chooser.showDialog(scene.getWindow());
        		  if(file!=null){
        			  dir1=file;
        			 
        		  }
        		 
            }
        });
    	dirDrowseButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	  DirectoryChooser chooser = new DirectoryChooser();
        		  chooser.setInitialDirectory(new File("C:\\"));
        		  File file = chooser.showDialog(scene.getWindow());
        		  if(file!=null){
        			  dir2=file;
        			
        		  }
        		 
            }
        });
       
    		 
        HBox hbox = new HBox();     
		
       VBox vbox1=new VBox();
      
      
        VBox vbox2=new VBox();
  
     hbox.setCenterShape(true);
     hbox.setSpacing(60);
  
        hbox.getChildren().addAll(vbox1,vbox2);
        hbox.setPadding(new Insets(45));
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.getChildren().addAll(hboxTop,hbox);
 
        compDirButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	addTwoTrees(vbox1,vbox2);
            }
        });
        
        return root;
    }
    public void addTwoTrees(VBox vbox1,VBox vbox2){
    	
    	if(vbox1.getChildren().size()>0){
    		vbox1.getChildren().clear();
    	}
    	if(vbox2.getChildren().size()>0){
    		vbox2.getChildren().clear();
    	}
    	TreeNode node1=new TreeNode(dir1);
		TreeNode node2=new TreeNode(dir2);
		
    	 FolderAlgo.folderCompare(node1,node2);
         TreeItem<String> rootItem1=null;
         if(node1.isDiff){
         	rootItem1 = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/folder-diff.png"))));
         }else{
         	rootItem1 = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/folder.png"))));
         }
    	 TreeView<String> tree1 = new TreeView<String>(rootItem1);
         tree1.setMinWidth(500);
         tree1.setMinHeight(500);
         rootItem1.setExpanded(true);
         vbox1.getChildren().add(tree1);
         
         buildTree(node1,rootItem1);
         
         
         
         TreeItem<String> rootItem2=null;
         if(node2.isDiff){
         	rootItem2 = new TreeItem<String>(node2.name,new ImageView(new Image(getClass().getResourceAsStream("/folder-diff.png"))));
         }else{
         	rootItem2 = new TreeItem<String>(node2.name,new ImageView(new Image(getClass().getResourceAsStream("/folder.png"))));
         }
         TreeView<String> tree2 = new TreeView<String>(rootItem2);
         tree2.setMinWidth(500);
         tree2.setMinHeight(500);
         rootItem2.setExpanded(true);
         vbox2.getChildren().add(tree2);
         buildTree(node2,rootItem2);
       
         
    	
    }
}
