package net.sf.RecordEditor.edit.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.display.util.CreateRecordTreePnl;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.TreeParserRecord;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


@SuppressWarnings("serial")
public class CreateRecordTree extends ReFrame implements ActionListener  {

	public final CreateRecordTreePnl treeDisplay;
	
	private AbstractLayoutDetails<?, ?> layout;
	
	private AbstractFileDisplay source;

	@SuppressWarnings("rawtypes")
	private FileView view;


	public CreateRecordTree(AbstractFileDisplay src, @SuppressWarnings("rawtypes") FileView fileView) {
		super(fileView.getFileNameNoDirectory(), "Create Record Tree",
				fileView.getBaseFile());
		
		view   = fileView;
		source = src;
		layout = view.getLayout();
		
		treeDisplay = new CreateRecordTreePnl(layout, true);
		this.addMainComponent(treeDisplay.getPanel());

		treeDisplay.execute.addActionListener(this);
		
		super.addMainComponent(treeDisplay.getPanel());		
		this.setVisible(true);
		this.setToMaximum(false);
	}

	
	
//	/**
//	 * @see net.sf.RecordEditor.utils.filter.AbstractSaveDetails#getSaveDetails()
//	 */
//	@Override
//	public final EditorTask getSaveDetails() {
//		RecordTree tree = new RecordTree();
//		int i, j, size;
//		Integer[] parent = pnl.getParent();
//		
//		Common.stopCellEditing(pnl.recordTbl);
//		size = 0;
//		for (i = 0; i < parent.length; i++) {
//			if (parent[i] != CreateRecordTreePnl.BLANK_PARENT) {
//				size += 1;
//			}
//		}
//		
//		j = 0;
//		tree.parentRelationship = new RecordParent[size];
//		for (i = 0; i < parent.length; i++) {
//			if (parent[i] != CreateRecordTreePnl.BLANK_PARENT) {
//				tree.parentRelationship[j] = new RecordParent();
//				tree.parentRelationship[j].recordName   = layout.getRecord(i).getRecordName();
//				
//				tree.parentRelationship[j++].parentName 
//					= layout.getRecord(parent[i].intValue()).getRecordName();
//			}
//		}
//		return (new EditorTask()).setRecordTree(layout.getLayoutName(), tree);
//	}
//	
//	/**
//	 * update tree definition from save details
//	 * @param saveDetails details that have been saved
//	 */
//	public final void setFromSavedDetails(EditorTask saveDetails) {
//		int i, idx;
//		RecordParent[] parentRel = saveDetails.recordTree.parentRelationship;
//		Integer[] parent = pnl.getParent();
//
//		
//		for (i = 0; i < parentRel.length; i++) {
//			idx = layout.getRecordIndex(parentRel[i].recordName);
//			if (idx >= 0) {
//				parent[idx] = Integer.valueOf(layout.getRecordIndex(parentRel[i].parentName));
//			}
//		}
//		pnl.setParent(parent);
//	}
	

	
	/**
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(ActionEvent event) {

	     Common.stopCellEditing(treeDisplay.recordTbl);
	     
	     doAction();
	}
	
	/**
	 * execute action
	 */
	public final void doAction() {
	     treeDisplay.messageFld.setText("");
	     
	     try {
	     	@SuppressWarnings("rawtypes")
			FileView newView = getNewView();
	        
	        if (newView == null) {
	        	treeDisplay.messageFld.setText("No Records Selected");
	        } else {
	        	Integer[] parent = treeDisplay.getParent();
	        	int[] parentIdxs = new int[parent.length];
	        	
	        	//System.out.print("Parents --> ");
	        	for (int i = 0; i < parent.length; i++) {
	        		parentIdxs[i] = parent[i].intValue();
	        		//System.out.print(" > " + i + " " + parentIdxs[i]);
	        	}
	        	//System.out.println(" <--");
	        	TreeParserRecord parser = new TreeParserRecord(parentIdxs);
	          
	            new LineTree(newView, parser, false, 0);
	        }

	        this.setClosed(true);
	     } catch (Exception e) {
	    	 treeDisplay.messageFld.setText(e.getMessage());
	    	 e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected final FileView getNewView() {  	

        return source.getFileView().getView();
	}

    
	/**
	 *  Execute standard RecordEditor actions
	 *
	 * @param action action to perform
	 */
	public void executeAction(int action) {
		if (action == ReActionHandler.HELP) {
		    treeDisplay.getPanel().showHelp();
		} else {
			super.executeAction(action);
		}
	}

	/**
	 * Check if action is available
	 *
	 * @param action action to be checked
	 *
	 * @return wether action is available
	 */
	public boolean isActionAvailable(final int action) {
		return  (action == ReActionHandler.HELP) 
		    ||  super.isActionAvailable(action);
	}



}
