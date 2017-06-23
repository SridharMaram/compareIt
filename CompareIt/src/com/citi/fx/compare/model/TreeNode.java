package com.citi.fx.compare.model;

import java.io.File;
import java.util.List;

public class TreeNode{

	public boolean isDir;
	public boolean isDiff;
	public String name;
	public File file;

	public List<TreeNode> items;

	public TreeNode(File file) {
		super();
		this.name = file.getName();
		this.file=file;
	}

}