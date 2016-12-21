/**
 *
 */
/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.Details;

import java.util.List;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordRunTimeException;

/**
 * Purpose to provide a Tree Details that does nothing. It will be used
 * instead of Null to ensure there is always a TreeDetails when the user
 * does AbstractLine.getTreeDetails
 *
 * @author Bruce Martin
 *
 */
public class NullTreeDtls<FieldDtls extends FieldDetail,
				RecordDtls extends AbstractRecordDetail,
				Layout extends AbstractLayoutDetails,
				ChildDtls extends AbstractChildDetails<RecordDtls>,
				LineType extends AbstractLine>
implements AbstractTreeDetails<FieldDtls, RecordDtls, Layout, LineType> {

//	public static final NullTreeDtls<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, AbstractLine<LayoutDetail>, Object>
//				STANDARD_NULL_DETAILS = new NullTreeDtls<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, AbstractLine<LayoutDetail>, Object>();
//

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#addChild(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void addChild(LineType newLine, int location) {
		updateTried(newLine);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getChildDefinitionInParent()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ChildDtls getChildDefinitionInParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getChildDetails(int)
	 */
	@Override
	public ChildDtls getChildDetails(int idx) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getChildName(int)
	 */
	@Override
	public String getChildName(int idx) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getLines(int)
	 */
	@Override
	public List<LineType> getLines(int idx) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getLines(java.lang.String)
	 */
	@Override
	public List<LineType> getLines(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getParentLine()
	 */
	@Override
	public LineType getParentLine() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#getRootLine()
	 */
	@Override
	public LineType getRootLine() {
		return null;
	}

//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Common.AbstractChildLines#getTreeNode()
//	 */
//	@Override
//	public TreeNode getTreeNode() {
//		return null;
//	}
//
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Common.AbstractTreeDetails#setTreeNode(java.lang.Object)
//	 */
//	@Override
//	public  void setTreeNode(TreeNode node) {
//	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#hasLines(int)
	 */
	@Override
	public boolean hasLines(int idx) {

		return false;
	}

//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Common.AbstractChildLines#isRebuildTreeRequired()
//	 */
//	@Override
//	public boolean isError() {
//
//		return false;
//	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#removeChild(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void removeChild(AbstractLine child) {

	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractTreeDetails#setParentLine(net.sf.JRecord.Details.AbstractLine, int)
	 */
	@Override
	public void setParentLine(LineType line, int index) {
		updateTried(line);
	}

	@Override
	public final int getParentIndex() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractTreeDetails#setParentIndex(int)
	 */
	@Override
	public void setParentIndex(int index) {
	}

	public List<ChildDtls> getInsertRecordOptions() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractTreeDetails#addChild(net.sf.JRecord.Common.AbstractChildDetails, int)
	 */
	@Override
	public <childDtls extends AbstractChildDetails<RecordDtls>> LineType addChild(
			childDtls childDef, int location) {
		updateTried(childDef);
		return null;
	}

	public boolean removeChildren(AbstractChildDetails childDef) {
		return false;
	}


	private void updateTried(Object o) {
		if (o != null) {
			throw new RecordRunTimeException("Action not supported by NullTree");
		}
	}

}
