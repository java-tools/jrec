/**
 *
 */
package net.sf.RecordEditor.diff;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LineDisplay extends AbstractCompareDisplay {

    private static Color ADDED_COLOR    = Color.yellow;
    private static Color DELETED_COLOR  = Color.cyan;
    private static Color MODIFIED_COLOR = Color.green;

	private CmpLineModel model;

	private JButton[] btn       = new JButton[4];
	private String[] moveHints  = LangConversion.convertArray(LangConversion.ST_FIELD_HINT, "RecordBtns", new String[] {
	        	"Start of File", "Previous Record",
	        	"Next Record",   "Last Record"});

	private ActionListener lineListner = new ActionListener() {
		public void actionPerformed(ActionEvent event) {

				//stopCellEditing();

				if (event.getSource() == btn[0]) {
					setCurrRow(0);
					rowChanged();
				} else if (event.getSource() == btn[1]) {
					if (getCurrRow() > 0) {
						setCurrRow(getCurrRow() - 1);
						rowChanged();
					}
				} else if (event.getSource() == btn[2]) {
					if (getCurrRow() < displayBefore.size() - 1) {
						//int hold = getCurrRow();
						//System.out.println("Starting " + hold);
						setCurrRow(getCurrRow() + 1);
						rowChanged();
						//System.out.println("Ending ... " + hold + " " + getCurrRow());
					}
				} else if (event.getSource() == btn[3]) {
					setCurrRow(displayBefore.size() - 1);
					rowChanged();
				}

				//System.out.println("Lister End " + (event.getSource() == btn[2]));

		//	}
		}
	};

	/**
	 * @param name
	 * @param recordLayout
	 * @param before
	 * @param after
	 * @param chgBefore
	 * @param chgAfter
	 * @param primary
	 */
	public LineDisplay(String name,
			@SuppressWarnings("rawtypes") AbstractLayoutDetails recordLayout,
			ArrayList<LineCompare> before,
			ArrayList<LineCompare> after, ArrayList<LineCompare> chgBefore,
			ArrayList<LineCompare> chgAfter, boolean primary, boolean allRows) {
		super("Record Display ", name, recordLayout, before, after, chgBefore, chgAfter,
				primary, allRows);

		ImageIcon[] icon = Common.getArrowIcons();
		JPanel btnPanel = new JPanel();
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton("", icon[i]);
			btnPanel.add(btn[i]);
			btn[i].addActionListener(lineListner);
			btn[i].setToolTipText(moveHints[i]);
		}
        super.setDisplayFields(ALL_FIELDS);

        init_100_SetupJtables();

        pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
                         BasePanel.FULL, BasePanel.FULL,
                         tblDetails);

        pnl.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
                btnPanel);

        addMainComponent(pnl);


        pack();
        setBounds(1, 1,
                Math.min(this.getWidth() + 200, screenSize.width  - 1),
                this.getHeight());

        /* set tot prefered Inde */
        model.setRecordIdx(Integer.MAX_VALUE);
        super.setLayoutIndex(model.getRecordIdx());

        setVisible(true);
	}


	/**
	 * Setup Table details
	 */
	private void init_100_SetupJtables() {
		Render cellRenderer = new Render();
		model = new CmpLineModel(layout, displayBefore, displayAfter);

		setDisplay(displayType);
		tblDetails = new JTable(model);
		tblDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Common.calcColumnWidths(tblDetails, 0, 220);

		tblDetails.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		tblDetails.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
	}


	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#setDisplay(int)
	 */
	@Override
	public void setDisplay(int type) {
		super.setDisplay(type);

		model.setCurrentRow(Math.min(model.getCurrentRow(), displayBefore.size()));
		model.setDisplayRows(displayBefore, displayAfter);
	}

	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#changeLayout()
	 */
	@Override
	public void changeLayout() {
		if (model != null) {
			model.setRecordIdx(getLayoutIndex());
		}
	}


	public void setDisplayFields(int fieldDisplay) {

		model.setDisplayChangedFields(fieldDisplay == CHANGED_FIELDS);
		super.setDisplayFields(fieldDisplay);
	}


	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#getCurrRow()
	 */
	@Override
	public int getCurrRow() {

		return model.getCurrentRow();
	}

	public void setCurrRow(int newRow) {
		model.setCurrentRow(newRow);
		//model.fireTableDataChanged();
	}

	public void rowChanged() {
		super.setLayoutIndex(model.getRecordIdx());
	}


	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#setCurrRow(int, int, int)
	 */
	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		// TODO Auto-generated method stub

	}


	private class Render implements TableCellRenderer {


	    private DefaultTableCellRenderer text    = new DefaultTableCellRenderer();

	    public Render() {
	    	text.setBorder(null);
	    }
		/**
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
		 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(
			JTable tbl,
			Object value,
			boolean isCellSelected,
			boolean hasFocus,
			int row,
			int column) {

			if (value == null) {
				text.setText("");
			} else {
				text.setText(value.toString());
			}

    		LineCompare cmp  = model.getCurrentCompareLine();
    		Color color = Color.WHITE;

    		if (row == 0) {
    		} else if (cmp == null || cmp.line == null) {
    			if (column == 4) {
    				color = ADDED_COLOR;
    			}
    		} else if (cmp.code == LineCompare.DELETED  && column == 3) {
    			color = DELETED_COLOR;
    		} else if (cmp.code == LineCompare.CHANGED
    			   &&  model.isFieldChanged(row)) {
     			color = MODIFIED_COLOR;
    		}
    		text.setBackground(color);

			return text;
		}
	}
}
