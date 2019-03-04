package com.allmsi.sys.model;

import java.io.Serializable;

public class TreeDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 801260193466619267L;
	private Object tree;

	public TreeDataBean() {

	}

	public TreeDataBean(Object tree) {
		super();
		this.tree = tree;
	}
	
	public Object getTree() {
		return tree;
	}

	public void setTree(Object tree) {
		this.tree = tree;
	}
}
