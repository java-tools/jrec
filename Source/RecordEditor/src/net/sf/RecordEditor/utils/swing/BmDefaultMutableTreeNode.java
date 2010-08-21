/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.RecordEditor.utils.swing;


import java.util.Enumeration;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;


/**
 * Default implementation of {@link MutableTreeNode}.
 * 
 * @author <a HREF="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision: 1.5 $ $Date: 2005/02/24 01:46:20 $
 * 
 *          $Log: DefaultMutableTreeNode.java,v $ Revision 1.5 2005/02/24
 *          01:46:20 oros fixed constructor to use List instead of ArrayList
 * 
 *          Revision 1.4 2004/11/26 12:14:10 oros MYFACES-8: applied tree table
 *          patch by David Le Strat
 * 
 *          revised Bruce Martin (for my needs) 1/11/2009 
 */
public class BmDefaultMutableTreeNode implements MutableTreeNode {

	private Vector<MutableTreeNode> children = new Vector<MutableTreeNode>();

	private Object userObject;

	MutableTreeNode parent;

	private boolean allowsChildren = true;

	/**
	 * @param userObject
	 *            The userObject.
	 */
	public BmDefaultMutableTreeNode(Object userObject) {
		this.userObject = userObject;
	}

	/**
	 * @param children
	 *            The children.
	 * @param allowsChildren
	 *            The allowsChildren.
	 */
	public BmDefaultMutableTreeNode(List<MutableTreeNode> children, boolean allowsChildren) {

		this.children = new Vector<MutableTreeNode>(children);
		this.allowsChildren = allowsChildren;
	}

	/**
	 * @param userObject
	 *            The userobject.
	 * @param parent
	 *            The parent.
	 * @param allowsChildren
	 *            The allowsChildren.
	 */
	public BmDefaultMutableTreeNode(Object userObject, MutableTreeNode parent,
			boolean allowsChildren) {
		this.userObject = userObject;
		this.parent = parent;
		this.allowsChildren = allowsChildren;
	}


	public void add(MutableTreeNode child) {
		getChildren().add(child);
		child.setParent(this);
	}

	public void insert(MutableTreeNode child, int index) {
		if (index < 0 || index >= getChildren().size()) {
			getChildren().add(child);
		} else {
			getChildren().add(index, child);
		}
		child.setParent(this);
	}
	public void remove(int index) {
		MutableTreeNode child = getChildren().remove(index);
		child.setParent(null);
	}

	public void remove(MutableTreeNode node) {
		if (getChildren().remove(node)) {
			node.setParent(null);
		}
	}

	public void setUserObject(Object object) {
		this.userObject = object;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void removeFromParent() {
		if (parent == null) {
			return;
		}
		parent.remove(this);
	}

	public void setParent(MutableTreeNode parent) {
		this.parent = parent;
	}

	public TreeNode getChildAt(int index) {
		return getChildren().get(index);
	}

	public int getChildCount() {
		return getChildren().size();
	}

	public TreeNode getParent() {
		return parent;
	}

	public int getIndex(TreeNode node) {
		return getChildren().indexOf(node);
	}

	public boolean getAllowsChildren() {
		return allowsChildren;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public Enumeration<MutableTreeNode> children() {
		return getChildren().elements();
	}

	public String toString() {
		if (userObject != null) {
			return userObject.toString();
		}
		return super.toString();
	}

	protected Vector<MutableTreeNode> getChildren() {
		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getPath()
	 */
	public TreeNode[] getPath() {
		return getPathToRoot(this, 0);
	}

	public void removeAllChildren() {
		children.removeAllElements();
	}
	
	public boolean isRoot() {
		return getParent() == null;
	}
	
	protected TreeNode[] getPathToRoot(TreeNode node, int depth) {
		if (node == null) {
			if (depth == 0)
				return null;

			return new TreeNode[depth];
		}

		TreeNode[] path = getPathToRoot(node.getParent(), depth + 1);
		path[path.length - depth - 1] = node;
		return path;
	}

}
