/*
 * Created on 14/05/2004
 *
 *  This class displays one line (or Record) on the screen using the supplied record
 * layout. It displays each field of the record one field at a time going down
 * the screen. It displays each field in both a Formated view and a straight Text
 * field. For binary files, Hex format is also displayed
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Added right mouse button click popup menu on file list table
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Support for sorting (will find the new record position after
 *     the file has been sorted
 *
 * # Version 0.61 Bruce Martin 2007/04/05
 *   - Added three line hex display option
 *   - Added full line display at the bottom of the screen
 *   - code ReOrg
 */
package net.sf.RecordEditor.edit.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.file.FilePosition;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.ExpandLineTree;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;




/**
 *  This class displays one line (or Record) on the screen using the supplied record
 * layout. It displays each field of the record one field at a time going down
 * the screen. It displays each field in both a Formated view and a straight Text
 * field. For binary files, Hex format is also displayed
 *
 * @author Bruce Martin
 * @version 0.70
 */
public class LineFrameTree extends  BaseLineFrame {

	private static final int IDX_START = 0;
	private static final int IDX_PREV = 1;
	private static final int IDX_PARENT= 2;
	private static final int IDX_CHILD = 3;
	private static final int IDX_NEXT = 4;
	private static final int IDX_LAST = 5;
	
    //static final int NUM_MOVEMENT_ICONS = 4;
   // private int currRow;
    private ImageIcon[] icons = Common.getArrowTreeIcons();

	private ActionListener listner = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					
//				System.out.println("Lister start " + (event.getSource() == btn[2]));
					stopCellEditing();
	
					if (event.getSource() == btn[IDX_START]) {
						setCurrentLine(0);	
					} else if (event.getSource() == btn[IDX_PREV]) {
						changeRow(-1);
					} else if (event.getSource() == btn[IDX_PARENT]) {
						AbstractLine l = record.getCurrentLine().getTreeDetails().getParentLine();
						if (l != null) {
							setLine(l); 
						}
					} else if (event.getSource() == btn[IDX_CHILD]) {
						AbstractTreeDetails children =  record.getCurrentLine().getTreeDetails();
						if (children != null && children.getChildCount() > 0) {
							List<AbstractLine> list = children.getLines(0);
							if (list.size() > 0) {
								setLine(list.get(0));
							}
						}
					} else if (event.getSource() == btn[IDX_NEXT]) {
							changeRow(1);
					} else if (event.getSource() == btn[IDX_LAST]) {
						setCurrentLine(fileView.getRowCount() - 1);	
					} else if (event.getSource() == oneLineHex) {
					    ap_100_setHexFormat();
					}
					
//					System.out.println("Lister End " + (event.getSource() == btn[2]));

			//	}
			}
	};

	/**
	 * Creates a Record Screen
	 *
	 * @param group      - Layout Definition of the file
	 * @param viewOfFile - Current view of the file
	 * @param masterFile - Internal representation of the file
	 * @param cRow       - current row
	 */
	public LineFrameTree(final FileView viewOfFile,
	        		 final int cRow) {
		super("Record: ", viewOfFile, false, ! viewOfFile.getLayout().isXml());

		JPanel btnPanel = new JPanel();

		super.setDisplayType(TREE_DISPLAY);
		
		record = new LineModel(fileView);
		setModel(record);

		record.setCurrentLine(fileView.getLine(cRow), fileView.getCurrLayoutIdx());
		init_200_setupFields(btnPanel, icons, listner);
		init_300_setupScreen(btnPanel);

		show();
	}


	public LineFrameTree(final FileView viewOfFile,
   		 final AbstractLine line) {
		super("Record: ", viewOfFile, false, ! viewOfFile.getLayout().isXml());
		
		if (line == null) {
			Common.logMsg("Line Can not be Viewed !!!!", null);
			this.closeWindow();
			return;
		} 
		
		JPanel btnPanel = new JPanel();
		
		record = new LineModel(fileView);
		setModel(record);
		
		record.setCurrentLine(line, fileView.getCurrLayoutIdx());
		//currRow = Common.NULL_INTEGER;
		init_200_setupFields(btnPanel, icons, listner);
		init_300_setupScreen(btnPanel);
		
		show();
		
	}


	/**
	 * Get the Row being displayed
	 *
	 * @return current row
	 */
	public int getCurrRow() {
		int ret = Constants.NULL_INTEGER;
		AbstractLine l = record.getCurrentLine();
		
		if (l != null) {
			l = ExpandLineTree.getRootLine(l);
			ret = fileView.indexOf(l);
		}
		return ret;
	}

	 /**
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplay#getTreeLine()
	 */
	@Override
	public AbstractLine getTreeLine() {
		return record.getCurrentLine();
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#setCurrRow(net.sf.RecordEditor.edit.file.FilePosition)
	 */
	@Override
	public void setCurrRow(FilePosition position) {
		setLine(position.currentLine);
	}

	
	
	/**
	 * Set the row to be displayed
	 *
	 * @param newRow new row to display
	 * @param layout layout the field was found on
	 * @param fieldNum new field number
	 *
	 * @see net.sf.RecordEditor.edit.BaseLineDisplay.setCurrRow
	 */
	public void setCurrRow(int newRow, int layout, int fieldNum) {
		setCurrentLine(newRow);
		if (fieldNum > 0 && getLayoutIndex() == layout) {
		    tblDetails.getSelectionModel().clearSelection();
		    tblDetails.getSelectionModel().setSelectionInterval(fieldNum, fieldNum);
		    tblDetails.editCellAt(fieldNum, LineModel.DATA_COLUMN);
		}
	}


	/**
	 * Deleting one record
	 */
	public void deleteLines() {
		AbstractLine line = record.getCurrentLine();
		if (line.getTreeDetails().getParentLine() == null) {
			getFileView().deleteLine(getCurrRow());
		} else {
			// TODO delete item from parent
		}

		setLayoutIndex(record.getCurrentLayout());
		setDirectionButtonStatus();

	}


	/**
	 * @return get the selected rows
	 */
	public int[] getSelectedRows() {
		return new int[] {getCurrRow()};
	}

	/**
	 * @see java.awt.event.ActionListner#actionPerformed
	 */


	/**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent event) {

		switch (event.getType()) {
			case (TableModelEvent.INSERT):

			break;
			case (TableModelEvent.DELETE):
				int currRow = getCurrRow();
				if (currRow < 0) {
					setCurrentLine(Math.min(fileView.getRowCount(), event.getFirstRow()));
				} else if (currRow > event.getLastRow()) {
				} else if (currRow > event.getFirstRow()) {
					setCurrentLine(Math.min(fileView.getRowCount(), event.getFirstRow()));
				}
			break;
			default:
				//System.out.println("## Data Changed ");
				record.fireTableDataChanged();
		}
		setFullLine();
		//System.out.println("## Setting Full Line: " + super.fullLine.getText());
	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

    	switch (action) {
    	case(ReActionHandler.REPEAT_RECORD) :
        	AbstractLine l = getFileView().repeatLine(record.getCurrentLine());
        	if (l != null) {
        		record.setCurrentLine(l, getLayoutIndex());
        		setDirectionButtonStatus();
        	}
        break;
    	case (ReActionHandler.DELETE_RECORD) :
        	getFileView().deleteLine(record.getCurrentLine());
        	this.closeWindow();
        break;
		case(ReActionHandler.PASTE_RECORD):
		case ReActionHandler.PASTE_RECORD_PRIOR:
			executeTreeAction(action);
        default:
            super.executeAction(action);
        }
    }


//	/**
//	 * This method updates the Screen Display after a change of record.
//	 * It updates the layout if required
//	 */
//	private void rowChanged() {
//
//	    stopCellEditing();
//
////		if (currRow >= getFileView().getRowCount()) {
////			if (currRow == 0) {
////				this.closeWindow();
////			} else {
////				currRow -= 1;
////			}
////		}
//
//		//lineNum.setText(Integer.toString(currRow + 1));
//
//		record.setCurrentLayout(getLayoutIndex());
//
//		//TODO record.setCurrentLine(currRow, getLayoutIndex());
//		int newIdx = record.getCurrentLayout();
//
//		if ((newIdx != Common.NULL_INTEGER)
//		&&  (newIdx != getLayoutIndex())) {
//	    	   setLayoutIndex(newIdx);
//
//	    	   changeLayout();
//	    	   setColWidths();
//	    	   setDirectionButtonStatus();
//	    	   setFullLine();
//
///*			System.out.println("::Creating Swing Worker ... ");
//			(new SwingWorker<String, Object>() {
//			       @Override
//			       public String doInBackground() {
//						System.out.println("::Starting Swing Worker ... ");
//
//			    	   int newIdx = record.getCurrentLayout();
//			    	   setLayoutIndex(newIdx);
//
//			    	   changeLayout();
//						System.out.println("::Swing Worker ... 1 ");
//			    	   setColWidths();
//						System.out.println("::Swing Worker ... 2 ");
//			    	   setDirectionButtonStatus();
//						System.out.println("::Swing Worker ... 3 ");
//			    	   setFullLine();
//						System.out.println("::Swing Worker ... 4 ");
//		    	   return null;
//			       }
//			}).execute();*/
//		} else {
////			System.out.println("::Setting up display ");
//			setColWidths();
//			setDirectionButtonStatus();
//			setFullLine();
//		}
////		System.out.println("::Exit Row changed ");
//
//	}

	/**
	 * Set enabled / disabled status of the direction buttons
	 */
	protected void setDirectionButtonStatus() {


		AbstractLine l = record.getCurrentLine();
	    btn[IDX_START].setEnabled(true);
		if (l== null) {
		    btn[IDX_PREV].setEnabled(false);
		    btn[IDX_PARENT].setEnabled(false);
		    btn[IDX_CHILD].setEnabled(false);
		    btn[IDX_NEXT].setEnabled(false);
		} else {
			int rowCount = fileView.getRowCount();
			AbstractLine parent = l.getTreeDetails().getParentLine();
			boolean changeParent = (parent == null) && (rowCount > 1);
			
		    btn[IDX_PREV].setEnabled(l != prevLine(l) || (changeParent && fileView.getLine(0) != l));
		    btn[IDX_PARENT].setEnabled(parent != null);
		    btn[IDX_CHILD].setEnabled(l.getTreeDetails().getChildCount() > 0);
		    btn[IDX_NEXT].setEnabled(l != nextLine(l) || (changeParent && fileView.getLine(rowCount-1) != l));
		}
	    btn[IDX_LAST].setEnabled(true);
	}
	
	private void setCurrentLine(int num) {
		setLine(fileView.getLine(num));
	}
	
	
	private void changeRow(int amount) {
		AbstractLine l = record.getCurrentLine();
		//int[] colWidths = getColumnWidths();
		int i;
		
		if (l.getTreeDetails().getParentLine() == null) {
			int idx = fileView.indexOf(l);
			if (idx < 0) {
				this.closeWindow();
				return;
			}
			idx = Math.min(Math.max(0,idx + amount), fileView.getRowCount() - 1);
			l = fileView.getLine(idx);
		} else {
			if (amount < 0) {
				l = prevLine(l);
			} else {
				l = nextLine(l); 
			}
		}
		setLine(l);
	}
	
	private void setLine(AbstractLine l) {
		
//		System.out.println("## Change Layout ?? ... " + (l != record.getCurrentLine()) 
//				+ " LayoutIndex: " + getLayoutIndex());
		if (l != record.getCurrentLine()) {
			int[] colWidths = getColumnWidths();
			record.setCurrentLine(l, getLayoutIndex());
			
			setLayoutIndex(record.getCurrentLayout());
			//changeLayout(); 
			//			System.out.println("## Change Layout "+ " LayoutIndex: " + getLayoutIndex());
			setDirectionButtonStatus(); //System.out.println("## Set Buttons " + " LayoutIndex: " + getLayoutIndex());
			setColWidths(); //System.out.println("## Set Columns " + " LayoutIndex: " + getLayoutIndex());
			setColumnWidths(colWidths); //System.out.println("## Set Column Widths " + " LayoutIndex: " + getLayoutIndex());
			setFullLine(); //System.out.println("## Set Fulline " + " LayoutIndex: " + getLayoutIndex());
		}

	}
	
	private AbstractLine prevLine(AbstractLine l) {
		int j;
		AbstractLine prev = l;
		AbstractLine parent =  l.getTreeDetails().getParentLine();
		if (parent != null) {
			AbstractTreeDetails children =parent.getTreeDetails();
			
			for (int i = 0; i < children.getChildCount(); i++) {
				List<AbstractLine> list = children.getLines(i);
				for (AbstractLine line : list) {
					if (line == l) {
						return prev;
					}
					prev = line;
				}
			}
		}
		return prev;
	}

	
	private AbstractLine nextLine(AbstractLine l) {
		int j;
		AbstractLine next = l;
		AbstractLine parent =  l.getTreeDetails().getParentLine();
		if (parent != null) {
			AbstractTreeDetails children =parent.getTreeDetails();
			boolean found = false;
			
			if (children != null && children.getChildCount() > 0) {
				for (int i = 0; i < children.getChildCount(); i++) {
					List<AbstractLine> list = children.getLines(i);
					for (AbstractLine line : list) {
						if (found) {
							return line;
						}
						found = line == l;
					}
				}
			}
		}
		return next;
	}



	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterLine()
	 */
	@Override
	protected AbstractLine getInsertAfterLine(boolean prev) {

		if (prev) {
			return prevLine(record.getCurrentLine());
		}
		return record.getCurrentLine();
	}

}