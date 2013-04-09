package net.sf.RecordEditor.edit.display;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.models.BaseLineModel;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.StandardRendor;

@SuppressWarnings("serial")
public abstract class BaseLineAsColumn
extends BaseLineDisplay
implements AbstractFileDisplayWithFieldHide {

	protected ChooseRender render = new ChooseRender();
	protected MenuPopupListener popupListner;

	private BaseLineModel model;

	private JMenu[] showFieldMenus;
	private JMenu currentShowFields = null;



	public BaseLineAsColumn(String formType, FileView viewOfFile,
			boolean primary, boolean fullLine, final boolean changeRow) {
		super(formType, viewOfFile, primary, fullLine, false, false, changeRow);

		showFieldMenus = new JMenu[viewOfFile.getLayout().getRecordCount()];
	}



	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#fireLayoutIndexChanged()
	 */
	@Override
	public void fireLayoutIndexChanged() {

//		System.out.println("Change Layout ... " + model.getCurrentLayout()
//				+ " != " +  getLayoutIndex());
		if (model.getCurrentLayout() != getLayoutIndex()) {
			model.setCurrentLayout(getLayoutIndex());
			model.fireTableStructureChanged();
			setColWidths();
		}
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#setNewLayout(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void setNewLayout(AbstractLayoutDetails newLayout) {
		super.setNewLayout(newLayout);
		showFieldMenus = new JMenu[newLayout.getRecordCount()];
		model.layoutChanged(newLayout);
		setTableFormatDetails(getLayoutIndex());
		setColWidths();
	}



	/**
	 * @return the model
	 */
	protected BaseLineModel getModel() {
		return model;
	}


	/**
	 * @param newModel the model to set
	 */
	protected void setModel(BaseLineModel newModel) {
		model = newModel;
	}



	protected final void setStandardColumnWidths(int dataCol) {

	//    if (tblDetails != null) {
		JTable tbl = getJTable();
        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn tc = tbl.getColumnModel().getColumn(0);
        tc.setPreferredWidth(125);
        tc = tbl.getColumnModel().getColumn(1);
        tc.setPreferredWidth(40);
        if (dataCol > 2) {
	        tc = tbl.getColumnModel().getColumn(2);
	        tc.setPreferredWidth(40);
	        if (dataCol > 3) {
		        tc = tbl.getColumnModel().getColumn(3);
		        tc.setPreferredWidth(Common.getColumnWidth(tbl, 3, 0, tbl.getRowCount(), 40) + 15);
	        }
        }
        tc = tbl.getColumnModel().getColumn(dataCol);
        tc.setPreferredWidth(180);
	//    }
	}



	/**
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide#getFieldVisibility(int)
	 */
	@Override
	public boolean[] getFieldVisibility(int recordIndex) {
		return model.getFieldMapping().getFieldVisibility(recordIndex);
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.util.AbstractFileDisplayWithHide#setFieldVisibilty(int, boolean[])
	 */
	@Override
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisible) {
		int i;
		boolean allFieldsDisplayed = true;
		model.getFieldMapping().setFieldVisibilty(recordIndex, fieldVisible);
		model.fireTableDataChanged();

		for (i = 0; allFieldsDisplayed && i < fieldVisible.length; i++) {
			allFieldsDisplayed = fieldVisible[i];
		}

		if (allFieldsDisplayed) {
			if (showFieldMenus[recordIndex]  != null) {
				showFieldMenus[recordIndex].removeAll();
			}
			showFieldMenus[recordIndex] = null;
		} else {
			setupShowFields(recordIndex);
			showFieldMenus[recordIndex].removeAll();
			for (i = 0; i < fieldVisible.length; i++) {
				if (! fieldVisible[i]) {
					addHiddenFieldToMenu(recordIndex, i, layout.getAdjFieldNumber(recordIndex, i));
				}
			}
		}

		if (recordIndex == getLayoutIndex()) {
			setShowFieldsMenu(recordIndex);
		}

		getParentFrame().setToActiveFrame();
	}


    public void setLayoutIndex(int recordIndex) {
    	int idx = getLayoutIndex();
    	if (idx != recordIndex) {
    		setShowFieldsMenu(recordIndex);
     	}
    	super.setLayoutIndex(recordIndex);
    }


    private void setShowFieldsMenu(int recordIndex) {

   		if (currentShowFields != null) {
    		popupListner.getPopup().remove(currentShowFields);
   		}
   		if (recordIndex < showFieldMenus.length) {
			currentShowFields = showFieldMenus[recordIndex] ;
			if (currentShowFields != null) {
	 			popupListner.getPopup().add(currentShowFields);
			}
   		}
    }


    protected final void hideRow(int row) {
      	int index = model.getFixedCurrentLayout();

    	if (showFieldMenus[index] == null) {
    		setupShowFields(index);
    		currentShowFields = showFieldMenus[index] ;
			popupListner.getPopup().add(currentShowFields);
    	}

    	addHiddenFieldToMenu(
    			index,
    			model.getFieldMapping().getRealColumn(index, row),
    			model.getRealRow(row));

    	model.hideRow(row);
    }

    private void setupShowFields(int index) {
    	if (showFieldMenus[index] == null) {
    		showFieldMenus[index] = new JMenu(LangConversion.convert("Show {0} Fields", layout.getRecord(index).getRecordName()));
    	}
    }

    private void addHiddenFieldToMenu(int index, int row, int realRow) {

    	String s = layout.getRecord(index).getField(realRow).getName();
    	int idx;

    	if ((idx = s.indexOf('|')) >= 0) {
    		s = s.substring(idx + 1);
    	}
    	showFieldMenus[index].add(new ShowFieldAction(
    			s, index,
    			row));
    }

    protected abstract void setColWidths();

	/**
	 * This class will choose the rendor
	 *
	 * @author Bruce Martin
	 *
	 */
    class ChooseRender implements TableCellRenderer {

        //private JTextField fld     = new JTextField();
    	StandardRendor stdRender = new StandardRendor();

        /**
    	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
    	 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
    	 */
    	public Component getTableCellRendererComponent(
    		JTable tbl,
    		Object value,
    		boolean isFldSelected,
    		boolean hasFocus,
    		int row,
    		int column) {

    		TableCellRenderer render;
    		int calcRow = model.getFieldMapping().getRealColumn(
    									getLayoutIndex(),
    									row);

//    		System.out.print("Choose Render: " + row + ", " + column
//    			+ " " + model.getFieldMapping().getRealColumn(getLayoutIndex(), row)
//    			+ " " + calcRow
//    			+ " " + layout.getUnAdjFieldNumber(getLayoutIndex(),row));
    		if (cellRenders.length <= row || calcRow >= cellRenders.length || cellRenders[calcRow] == null) {
    	        render = stdRender;
    	        //System.out.println(" Std");
    	    } else {
     	    	render = cellRenders[calcRow];
     	    	//System.out.println(" render " + render.getClass().getName());
    	    }

    	    return render.getTableCellRendererComponent(
				    	        		tbl,
				    	        		value,
				    	        		isFldSelected,
				    	        		hasFocus,
				    	        		row,
				    	        		column);

    	}
    }

    private class ShowFieldAction extends JMenuItem implements ActionListener {
    	final int theRow, idx;

    	public ShowFieldAction(String s, int index, int row) {
    		super(s);
    		theRow = row;
    		idx =index;
    		super.addActionListener(this);
    	}

    	@Override
		public void actionPerformed(ActionEvent e) {

    		BaseLineAsColumn.this.model.showRow(theRow);

			showFieldMenus[idx].remove(this);

			if (showFieldMenus[idx].getMenuComponentCount() == 0) {
				showFieldMenus[idx] = null;
				setShowFieldsMenu(getLayoutIndex());
			}
		}
     }
}