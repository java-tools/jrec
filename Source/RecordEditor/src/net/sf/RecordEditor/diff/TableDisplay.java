/**
 *
 */
package net.sf.RecordEditor.diff;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.Options;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class TableDisplay extends AbstractCompareDisplay {

	private CmpTableRender render = new CmpTableRender();
	private CmpTableModel model;
	private JTextPane msgTxt = new JTextPane();

	/**
	 * @param name
	 * @param recordLayout
	 * @param before
	 * @param after
	 * @param chgBefore
	 * @param chgAfter
	 * @param primary
	 */
	public TableDisplay(String name,
			AbstractLayoutDetails recordLayout, ArrayList<LineCompare> before,
			ArrayList<LineCompare> after, ArrayList<LineCompare> chgBefore,
			ArrayList<LineCompare> chgAfter, boolean primary) {
		super("Table Display ", name, recordLayout, before, after, chgBefore, chgAfter,
				primary, false);

        init_100_SetupJtables();

//        pnl.setHelpURL(Common.formatHelpURL(Common.HELP_RECORD_TABLE));

        if (chgBefore.size() == 0 && chgAfter.size() == 0) {
        	try {
        		Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );

        		StyledDocument doc = msgTxt.getStyledDocument();
        		Style bold = doc.addStyle( "bold", def );
        	    StyleConstants.setBold( bold, true );
        	    StyleConstants.setFontSize( bold, 16 );
        	    StyleConstants.setAlignment(bold, StyleConstants.ALIGN_CENTER);

        		//Style large = doc.addStyle( "large", bold);
        	    //StyleConstants.setFontSize( large, 16 );

        		doc.insertString( doc.getLength(), "Files are Identical !!!", bold );
        		//msgTxt.setAlignmentX(CENTER_ALIGNMENT);

		         pnl.addLineRE("", msgTxt);
		         pnl.setGapRE(BasePanel.GAP0);
        	} catch (Exception e) {
			}
        } else if (Common.TEST_MODE) {
	         pnl.addLineRE("", msgTxt);
       }


        pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                         BasePanel.FULL, BasePanel.FULL,
                         new JScrollPane(tblDetails));


        addMainComponent(pnl);


        setBounds(1, 1,
                  screenSize.width  - 1,
                  screenSize.height - 1);
        model.setRecordIndex(Integer.MAX_VALUE);  /* use prefered layout */
        setTableFeatures() ;

		render.setSchemaIdx(getLayoutIndex());

        setVisible(true);
	}

	private void init_100_SetupJtables() {
		AbstractLayoutDetails l = super.layout;
		render.setList(displayBefore, displayAfter);
		model = new CmpTableModel(layout, displayBefore, displayAfter);

		setDisplay(USE_CHANGE_LIST);
		tblDetails = new JTable(model);
		tblDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int tblHeight = l.getOption(Options.OPT_TABLE_ROW_HEIGHT);
		
		if (tblHeight <= 1 && displayAfter.size() > 0) {
			AbstractLine line = null;
			int i = 0;
			while (i < displayAfter.size() && (displayAfter.get(i) == null || (line = displayAfter.get(i).line) == null)) {
				i += 1;
			}
			if (line != null) {
				tblHeight = line.getLayout().getOption(Options.OPT_TABLE_ROW_HEIGHT);
			}
		}
		if (tblHeight > 1) {
			tblDetails.setRowHeight(tblHeight * SwingUtils.TABLE_ROW_HEIGHT);
		} 

		//tblDetails.setC
//		Common.calcColumnWidths(tblDetails, 1);

		tblDetails.addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent m) {
	                int col = tblDetails.columnAtPoint(m.getPoint());

	                int popupRow = tblDetails.rowAtPoint(m.getPoint());

	                if (col  < 2) {
	                	LineDisplay disp = new LineDisplay("Line", layout, fullBefore, fullAfter,
	                			changeBefore, changeAfter, false,  displayType == USE_FULL_LIST);
	                	disp.setCurrRow(popupRow / 2);
	                	disp.setDisplay(displayType);
	                }
	            }

		});
	}

	public void setTableFeatures() {
		TableColumnModel colMdl;

		Common.calcColumnWidths(tblDetails, 3);

		colMdl = tblDetails.getColumnModel();
		colMdl.getColumn(0).setPreferredWidth(5);
		colMdl.getColumn(1).setPreferredWidth(50);

		for (int i = 0; i < colMdl.getColumnCount(); i++) {
			colMdl.getColumn(i).setCellRenderer(render);
		}

	}


	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#setDisplay(int)
	 */
	@Override
	public void setDisplay(int type) {
		boolean recalcSize = displayBefore.size() == 0;
		super.setDisplay(type);

		render.setList(displayBefore, displayAfter);
		model.setDisplayRows(displayBefore, displayAfter);

		if (recalcSize && tblDetails != null) {
			Common.calcColumnWidths(tblDetails, 3);
			TableColumnModel colMdl = tblDetails.getColumnModel();
			colMdl.getColumn(0).setPreferredWidth(5);
			colMdl.getColumn(1).setPreferredWidth(50);
		}
	}

	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#changeLayout()
	 */
	@Override
	public void changeLayout() {

		if (model != null) {
			int layoutIndex = getLayoutIndex();

			render.setSchemaIdx(layoutIndex);
			if (model.setRecordIndex(layoutIndex)) {
				setTableFeatures();
			}
		}

	}

	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#getCurrRow()
	 */
	@Override
	public int getCurrRow() {
		return 0;
	}


	/**
	 * @see net.sf.RecordEditor.diff.AbstractCompareDisplay#setCurrRow(int, int, int)
	 */
	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {

	}

}
