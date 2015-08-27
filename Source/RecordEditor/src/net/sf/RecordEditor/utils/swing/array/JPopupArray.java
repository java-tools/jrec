package net.sf.RecordEditor.utils.swing.array;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * Display array Field in a Popup Table
 *
 *
 * @author Bruce Martin
 *
 */

@SuppressWarnings("serial")
public class JPopupArray extends ReFrame
implements ActionListener,  TableModelListener {
    //private Calendar date = null;
    protected ArrayTableModel model;
    private JTable table;
    private JTable parentTbl;
    private int parentR, parentC;
    private ArrayInterface arrayDtls;

    private JButton addBtn = SwingUtils.newButton("Add Row Before");
    private JButton addAfterBtn = SwingUtils.newButton("Add Row After");
    private JButton deleteBtn = SwingUtils.newButton("Delete Rows");
    private Object parentDoc;
    private boolean changed = false;
    //private final TableModel parentMdl;




    public JPopupArray(String documentName, Object document,
    		ArrayInterface array, JTable parentTable, int parentRow, int parentColumn) {
    	super(  documentName,
        		"",
        		LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Array View:")
        			+ " " + parentRow + ", " + parentColumn,
        		"",
        		document);

        arrayDtls = array;
        parentTbl = parentTable;
        parentR = parentRow;
        parentC = parentColumn;
        parentDoc = document;
        //parentMdl = parentTblMdl;

    	model = new ArrayTableModel(array);
    	table = new JTable(model);

        BasePanel pnl = new BasePanel();
        JPanel p = new JPanel();

        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        p.add(addBtn);
        p.add(addAfterBtn);
        p.add(deleteBtn);
  		pnl.addComponentRE(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, p);

		pnl.addComponentRE(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, table);

		addMainComponent(pnl);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addBtn.addActionListener(this);
		addAfterBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		setVisible(true);

		super.setToMaximum(false);

		TableCellRenderer render = array.getTableCellRenderer();
		if (render != null) {
			TableCellEditor editor = array.getTableCellEditor();
			int height = array.getFieldHeight();
			TableColumn tc =  table.getColumnModel().getColumn(1);
			tc.setCellRenderer(render);

			if (editor != null) {
				tc.setCellEditor(editor);
			}

			if (height > 0) {
				table.setRowHeight(height);
			}
			table.addMouseListener(new MouseAdapter() {
		 		   /**
			     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			     */
			    public void mousePressed(MouseEvent e) {
			    	super.mousePressed(e);

		      		int col = table.columnAtPoint(e.getPoint());
		            int row = table.rowAtPoint(e.getPoint());

			       		if (! table.hasFocus()) {
		       			table.requestFocusInWindow();
		       		}

 		            if (row >= 0 && row != table.getEditingRow()
		        	&&  col >= 0 && col != table.getEditingColumn()) {
		            	table.editCellAt(row, col);
		            }
			    }

			});
		}


		this.addFocusListener(new FocusAdapter() {
	    	public void focusLost(FocusEvent e) {
	    		notifyParent();
	    	}
		});

	    this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e)  {
            	notifyParent();
            }
	    });

	    AbstractTableModel fv;

	    TableModel parentMdl = parentTbl.getModel();
	    if (parentMdl instanceof AbstractTableModel) {
	    	fv = (AbstractTableModel) parentMdl;
	    	fv.addTableModelListener(this);
	    } else if (parentDoc instanceof AbstractTableModel) {
	    	fv = (AbstractTableModel) parentDoc;
	    	fv.addTableModelListener(this);
	    }
    }

	private void notifyParent() {
    	TableModel parentMdl = parentTbl.getModel();

    	Common.stopCellEditing(table);
    	arrayDtls.flush();
    	if (parentMdl instanceof AbstractTableModel) {
    		((AbstractTableModel) parentMdl).fireTableDataChanged();
    	} else if (parentTbl != null) {
    		parentTbl.setValueAt(parentTbl.getValueAt(parentR, parentC), parentR, parentC);
    	}

    	if (parentDoc instanceof ArrayNotifyInterface) {
    		ArrayNotifyInterface doc = (ArrayNotifyInterface) parentDoc;

    		doc.notifyOfChange(arrayDtls.getLine());
        	if ((changed || model.changed)) {
        		 doc.setChanged(true);
        	}
    	}
    }


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == deleteBtn) {
			int[] rows = table.getSelectedRows();
			if (rows != null && rows.length > 0) {
				//Common.stopCellEditing(table);
				for (int i = rows.length - 1; i>= 0; i--) {
					arrayDtls.remove(rows[i]);
				}
				model.fireTableDataChanged();
			}
		} else {
			int row = table.getSelectedRow();
			if (event.getSource() == addAfterBtn) {
				row += 1;
			}

			//Common.stopCellEditing(table);
			if (row >= 0 && row < arrayDtls.size()) {
				arrayDtls.add(row, null);
			} else {
				arrayDtls.add(null);
			}
			model.fireTableDataChanged();
		}
		changed = true;
	}


	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent arg0) {
		//System.out.println("## Table Changed");
		arrayDtls.retrieveArray();
		model.fireTableDataChanged();
	}
}
