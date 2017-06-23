package com.citi.fx.compare.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.citi.fx.compare.file.FileCompare;
import com.citi.fx.compare.model.TreeNode;



class FileNameComparator implements Comparator<File>{

	@Override
	public int compare(File o1, File o2) {

		return o1.getName().compareTo(o2.getName());
	}

}
public class FolderAlgo {

	
	public static void folderCompare(TreeNode node1,TreeNode node2){
		node1.isDir=node2.isDir=true;
		readDir(node1,node2);
		System.out.println("\n");
		printTree(node1);
		System.out.println("\n");
		printTree(node2);
	}

	//prints the directory tree for one side
	private static void printTree(TreeNode node){
		if(node==null){
			return;
		}
		if(node.file.isFile()){
			System.out.print(node.name+"("+node.isDiff+")");
			return;
		}

		System.out.print(node.name+"("+node.isDiff+")"+"[ ");
		if(node.items==null){
			System.out.print("] ");
			return;
		}
		for(TreeNode node1:node.items){
			printTree(node1);
		}
		System.out.print("] ");
	}

	//attaches inner folders/files which were not compared to their parent nodes, one side of comparison has ended
	private static void attachNodes(TreeNode node){
		if(node==null || node.file.isFile()){
			node.isDiff=true;
			node.isDir=false;
			return;
		}
		File[] list=node.file.listFiles();
		node.isDiff=true;
		node.isDir=true;
		Arrays.sort(list,new FileNameComparator());
		List<TreeNode> items1=new ArrayList<>();
		for(File file:list){
			items1.add(new TreeNode(file));
		}
		node.items=items1;
		for(TreeNode item:items1){
			attachNodes(item);
		}

	}
	//called when one side of comparison has ended and you wanna traverse the other side's pending tree of untraversed folders
	private static void recursePendingNodes(TreeNode node1,TreeNode node2){
		if(node1==null && node2==null){
			return;
		}
		if(node1!=null && node2==null){
			if(node1.file.isFile()){
				node1.isDir=false;
				node1.isDiff=true;
				return;
			}else if(node1.file.isDirectory() && node1.file.list().length>0){
				attachNodes(node1);
			}
		}else if(node1==null && node2!=null){
			if(node2.file.isFile()){
				node2.isDir=false;
				node2.isDiff=true;
				return;
			}else if(node2.file.isDirectory() && node2.file.list().length>0){
				attachNodes(node2);
			}
		}
	}
	//actual recursive comparson algo for comparing folder structures
	public static void readDir(TreeNode node1,TreeNode node2){

		if(node1==null || node2==null){
			//no more comparisons
			recursePendingNodes(node1,node2);
			return;
		}
		//if both are files
		if(node1.file.isFile() &&  node2.file.isFile()){
			node1.isDir=false;
			node2.isDir=false;
			//compare the text contents only
			if(!FileCompare.areFilesEqual(node1.file,node2.file)){
				node1.isDiff=true;
				node2.isDiff=true;
			}else{
				node1.isDiff=false;
				node2.isDiff=false;
			}

			return;
		}
		//if both are directories
		else if(node1.file.isDirectory() && node2.file.isDirectory()){
			node1.isDir=node2.isDir=true;

			File[] files1= node1.file.listFiles();
			File[] files2= node2.file.listFiles();

			Arrays.sort(files1, new FileNameComparator());
			Arrays.sort(files2, new FileNameComparator());

			List<TreeNode> items1=new ArrayList<>();
			List<TreeNode> items2=new ArrayList<>();
			for(File file:files1){
				items1.add(new TreeNode(file));
			}
			for(File file:files2){
				items2.add(new TreeNode(file));
			}

			node1.items=items1;
			node2.items=items2;

			Iterator<TreeNode> fileIter1=items1.iterator();
			Iterator<TreeNode> fileIter2=items2.iterator();
			int move=0;//0[both],1[left],2[right]
					if(items1.size()==0 && items2.size()!=0){
						move=2;
					}
					if(items1.size()!=0 && items2.size()==0){
						move=1;
					}
					TreeNode treeObj1=null;
					TreeNode treeObj2=null;
					while( (fileIter1.hasNext() || fileIter2.hasNext()) || ( (!fileIter1.hasNext() && !fileIter2.hasNext()) && (move==2 || move==1)) ){

						if(move==0){
							treeObj1=fileIter1.hasNext()?fileIter1.next():null;
							treeObj2=fileIter2.hasNext()?fileIter2.next():null;
						}else if(move==1){
							treeObj1=fileIter1.hasNext()?fileIter1.next():null;
						}else if(move==2){
							treeObj2=fileIter2.hasNext()?fileIter2.next():null;
						}
						if(treeObj1!=null  && treeObj2!=null ){	

							if(treeObj1.file.getName().equalsIgnoreCase(treeObj2.file.getName())){

								readDir(treeObj1,treeObj2);
								move=0;


							}else if(treeObj1.file.getName().compareToIgnoreCase(treeObj2.file.getName())<0){
								treeObj1.isDiff=true;
								move=1; //move on the left tree

								recursePendingNodes(treeObj1,null);

							}else if(treeObj1.file.getName().compareToIgnoreCase(treeObj2.file.getName())>0){
								treeObj2.isDiff=true;
								move=2;//move on the right tree

								//attach files recursively
								recursePendingNodes(null,treeObj1);

							}

						}else if(treeObj1!=null  && treeObj2==null){
							treeObj1.isDiff=true;
							recursePendingNodes(treeObj1,null);
							while(fileIter1.hasNext()){
								treeObj1=fileIter1.next();
								treeObj1.isDiff=true;	
								recursePendingNodes(treeObj1,null);
							}
							move=-1; //stops the iterations happening on the left side folder structure

						}else if(treeObj2!=null  && treeObj1==null){
							treeObj2.isDiff=true;
							recursePendingNodes(null,treeObj2);	
							while(fileIter2.hasNext()){
								treeObj2=fileIter2.next();
								treeObj2.isDiff=true;	
								recursePendingNodes(null,treeObj2);		
							}
							move=-1;//stops the iterations happening on the right side folder structure

						}



					}
					//this happens for every rebound after recursion for each branch level
					//sets isDiff flags based on inner tree traversals
					setIsDiff(node1,node2,items1,items2);


		}

	}


	private static void setIsDiff(TreeNode node1,TreeNode node2,List<TreeNode> items1,List<TreeNode> items2){
		node1.isDiff=false;
		for(TreeNode item:items1){
			if(item.isDiff){
				node1.isDiff=true;
				break;
			}
		}
		node2.isDiff=false;
		for(TreeNode item:items2){
			if(item.isDiff){
				node2.isDiff=true;
				break;
			}
		}
	}

}
