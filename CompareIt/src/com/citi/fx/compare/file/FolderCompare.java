package com.citi.fx.compare.file;


import java.io.File;
import java.util.List;

import com.citi.fx.compare.model.TreeNode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FolderCompare extends Application {
 
	//building the tree on one side with one tree root
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
    @Override
    public void start(Stage primaryStage) {
 
        HBox hbox = new HBox();
        
    	TreeNode node1=new TreeNode(new File("C:\\Users\\BC06490\\Desktop\\A"));
		TreeNode node2=new TreeNode(new File("C:\\Users\\BC06490\\Desktop\\Z"));
		
         FolderAlgo.folderCompare(node1,node2);
        TreeItem<String> rootItem1=null;
        if(node1.isDiff){
        	rootItem1 = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/folder-diff.png"))));
        }else{
        	rootItem1 = new TreeItem<String>(node1.name,new ImageView(new Image(getClass().getResourceAsStream("/folder.png"))));
        }
        TreeView<String> tree1 = new TreeView<String>(rootItem1);
        tree1.setMinWidth(300);
        rootItem1.setExpanded(true);
        
        buildTree(node1,rootItem1);
        TreeItem<String> rootItem2=null;
        if(node2.isDiff){
        	rootItem2 = new TreeItem<String>(node2.name,new ImageView(new Image(getClass().getResourceAsStream("/folder-diff.png"))));
        }else{
        	rootItem2 = new TreeItem<String>(node2.name,new ImageView(new Image(getClass().getResourceAsStream("/folder.png"))));
        }
        TreeView<String> tree2 = new TreeView<String>(rootItem2);
        tree2.setMinWidth(300);
        rootItem2.setExpanded(true);
        
        buildTree(node2,rootItem2);
      
      
  
     hbox.setCenterShape(true);
     hbox.setSpacing(60);
  
        hbox.getChildren().addAll(tree1,tree2);
        hbox.setPadding(new Insets(45));
        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(hbox);
 
        primaryStage.setTitle("Compare It - folder compare");
        Scene scene=new Scene(root, 900, 850);
      
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
     
}