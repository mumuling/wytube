package com.cqxb.yecall.bean;

import java.util.ArrayList;

public class TreeElementBean {
	private String id;
	private String outlineTitle;
	private boolean mhasParent;
	private boolean mhasChild;
	private int level;
	private boolean expanded;
	private TreeElementBean parent;
	private ArrayList<TreeElementBean> childList = new ArrayList<TreeElementBean>();
	
	
	
	
	public TreeElementBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setChildList(ArrayList<TreeElementBean> childList) {
		this.childList = childList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutlineTitle() {
		return outlineTitle;
	}

	public void setOutlineTitle(String outlineTitle) {
		this.outlineTitle = outlineTitle;
	}

	public boolean isMhasParent() {
		return mhasParent;
	}

	public void setMhasParent(boolean mhasParent) {
		this.mhasParent = mhasParent;
	}

	public boolean isMhasChild() {
		return mhasChild;
	}

	public void setMhasChild(boolean mhasChild) {
		this.mhasChild = mhasChild;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public ArrayList<TreeElementBean> getChildList() {
		return childList;
	}

	public TreeElementBean getParent() {
		return parent;
	}

	public void setParent(TreeElementBean parent) {
		this.parent = parent;
	}


	public void addChild(TreeElementBean c) {
		this.childList.add(c);
		this.mhasParent = false;
		this.mhasChild = true;
		c.parent = this;
		c.level = this.level + 1;

	}

	public TreeElementBean(String id, String title) {
		super();
		this.id = id;
		this.outlineTitle = title;
		this.level = 0;
		this.mhasParent = true;
		this.mhasChild = false;
		this.parent = null;
	}

	public TreeElementBean(String id, String outlineTitle, boolean mhasParent,
			boolean mhasChild, TreeElementBean parent, int level, boolean expanded) {
		super();
		this.id = id;
		this.outlineTitle = outlineTitle;
		this.mhasParent = mhasParent;
		this.mhasChild = mhasChild;
		this.parent = parent;
		if (parent != null) {
			this.parent.getChildList().add(this);
		}
		this.level = level;
		this.expanded = expanded;
	}

}
