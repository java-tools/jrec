package net.sf.RecordEditor.re.script;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.CreateRecordTreePnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

public class CreateRecordTree  implements ActionListener  {

	public final CreateRecordTreePnl treeDisplay;
	

	private AbstractLayoutDetails layout;

//	private AbstractFileDisplay source;
	private JDialog dialog;

	private FileView view;
	private boolean ok = false;


	public CreateRecordTree(FileView fileView) {
		super();

		view   = fileView;
//		source = src;
		layout = view.getLayout();
		
		treeDisplay = new CreateRecordTreePnl(layout, true);
		dialog = new JDialog(ReMainFrame.getMasterFrame(), "Get Record Hierachy");
		dialog.getContentPane().add(treeDisplay.panel);

		treeDisplay.execute.addActionListener(this);

		dialog.setVisible(true);
//		dialog.setToMaximum(false);
	}




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
	public final AbstractFileDisplay doAction() {
		AbstractFileDisplay ret = null;
	    treeDisplay.panel.setMessageRawTxtRE("");

	    try {
//	        	Integer[] parent = treeDisplay.getParent();
//	        	int[] parentIdxs = new int[parent.length];
//
//	        	//System.out.print("Parents --> ");
//	        	for (int i = 0; i < parent.length; i++) {
//	        		parentIdxs[i] = parent[i].intValue();
//	        		//System.out.print(" > " + i + " " + parentIdxs[i]);
//	        	}
//	        	//System.out.println(" <--");
	        	ok = true;
		        dialog.setVisible(false);
	        
	     } catch (Exception e) {
	    	 treeDisplay.panel.setMessageRawTxtRE(e.getMessage());
	    	 e.printStackTrace();
		 }

	     return ret;
	}



	public final boolean isOk() {
		return ok;
	}
}
